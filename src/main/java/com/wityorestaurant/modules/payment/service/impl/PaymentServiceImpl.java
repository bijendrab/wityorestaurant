package com.wityorestaurant.modules.payment.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.customerdata.CustomerCartItems;
import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.orderservice.repository.OrderRepository;
import com.wityorestaurant.modules.payment.dto.BillingDetailItem;
import com.wityorestaurant.modules.payment.dto.BillingDetailResponse;
import com.wityorestaurant.modules.payment.dto.TaxDetails;
import com.wityorestaurant.modules.payment.service.PaymentService;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
import com.wityorestaurant.modules.tax.model.TaxComponent;
import com.wityorestaurant.modules.tax.model.TaxProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Akash Beura
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private OrderRepository orderRepository;
    private ReservationRepository reservationRepository;
    private RestTableRepository restTableRepository;
    private double totalTaxedPrice = 0;
    private double totalComponentCost = 0.0;

    public PaymentServiceImpl() {
    }

    @Autowired
    public PaymentServiceImpl(OrderRepository orderRepository, ReservationRepository reservationRepository, RestTableRepository restTableRepository) {
        this.orderRepository = orderRepository;
        this.reservationRepository = reservationRepository;
        this.restTableRepository = restTableRepository;
    }

    private double getTotalPrice() {
        return this.totalTaxedPrice;
    }

    private void setTotalTaxedPrice(double cost) {
        this.totalTaxedPrice = this.totalTaxedPrice + cost;
    }

    private void setTotalComponentPrice(double cost) {
        this.totalComponentCost = totalComponentCost + cost;
    }

    private void resetTotalComponentPrice() {
        this.totalComponentCost = 0.0;
    }

    private double getTotalComponentPrice() {
        return this.totalComponentCost;
    }

    private BillingDetailItem orderToBDto(OrderItem orderItem) {
        CustomerCartItems cartItem = new Gson().fromJson(orderItem.getCustomerCartItems(), CustomerCartItems.class);
        Product product = new Gson().fromJson(cartItem.getProductJson(), Product.class);
        TaxProfile taxProfile = product.getAppliedTax();
        BillingDetailItem billingDetailsDto = new BillingDetailItem();
        billingDetailsDto.setItemName(orderItem.getItemName());
        billingDetailsDto.setOrderId(orderItem.getOrder().getOrderId());
        billingDetailsDto.setQuantity(orderItem.getQuantity());
        billingDetailsDto.setPrice(cartItem.getPrice());
        billingDetailsDto.setValue(orderItem.getPrice());
        billingDetailsDto.setAppliedTaxProfile(taxProfile);
        return billingDetailsDto;
    }

    /* Method to find out the total tax mapping based on componenets */
    private Map<String, Map<Double, List<String>>> getTaxChargesPerItem(BillingDetailResponse response) {
        Map<String, Map<Double, List<String>>> taxMap = response.getTaxCharges();
        response.getBillingDetailItems().forEach(billingItem -> {

            float taxPercent = billingItem.getAppliedTaxProfile().getTaxPercent();
            if (billingItem.getAppliedTaxProfile().getTaxComponents().size() == 0) {

                double taxPercentWeightageCalculated = taxPercent / 100;
                if (taxMap.containsKey(billingItem.getAppliedTaxProfile().getTaxProfileName())) {
                    if (taxMap.get(billingItem.getAppliedTaxProfile().getTaxProfileName())
                        .containsKey(taxPercentWeightageCalculated)) {
                        List<String> itemNameList = taxMap.get(billingItem.getAppliedTaxProfile().getTaxProfileName())
                            .get(taxPercentWeightageCalculated);
                        itemNameList.add(billingItem.getItemName());
                    } else {
                        HashMap<Double, List<String>> weightageMapping = new HashMap<>();
                        List<String> itemNameList = new ArrayList<>();
                        itemNameList.add(billingItem.getItemName());
                        weightageMapping.put(taxPercentWeightageCalculated, itemNameList);
                        taxMap.put(billingItem.getAppliedTaxProfile().getTaxProfileName(), weightageMapping);
                    }
                }
            } else {
                billingItem.getAppliedTaxProfile().getTaxComponents().forEach(taxComponent -> {
                    double taxPercentWeightageCalculated = taxPercent * (taxComponent.getWeightage() / 100);
                    String taxComponentName = taxComponent.getComponentName();
                    // Check if component name exists in the map
                    if (taxMap.containsKey(taxComponentName)) {
                        // check if that tax percent is present in that map
                        if (taxMap.get(taxComponent.getComponentName()).containsKey(taxPercentWeightageCalculated)) {
                            taxMap.get(taxComponent.getComponentName()).get(taxPercentWeightageCalculated)
                                .add(taxComponentName);
                        } else {
                            // if that tax percent is not present in that map
                            List<String> itemNames = new ArrayList<>();
                            itemNames.add(taxComponentName);
                            taxMap.get(taxComponent.getComponentName()).put(taxPercentWeightageCalculated, itemNames);
                        }
                    }
                    // if component name doesn't exist in the map
                    else {
                        HashMap<Double, List<String>> componentMapping = new HashMap<>();
                        List<String> itemNameList = new ArrayList<>();
                        itemNameList.add(billingItem.getItemName());
                        componentMapping.put(taxPercentWeightageCalculated, itemNameList);
                        taxMap.put(taxComponentName, componentMapping);
                    }

                });
            }

        });
        return taxMap;

    }

    private double getTaxedItemPrice(List<Order> orders, double taxRate, String orderItemName) {

        this.setTotalTaxedPrice(0);
        for (int i = 0; i < orders.size(); i++) {
            orders.get(i).getMenuItemOrders().forEach(orderItem -> {
                if (orderItem.getItemName().equals(orderItemName)) {
                    this.setTotalTaxedPrice((orderItem.getPrice() * taxRate));
                }
            });
            if (getTotalPrice() > 0) {
                break;
            }
        }
        return this.getTotalPrice();
    }

    /*
     * Method to check the total taxes for each taxRate present and store it in a
     * response map
     */
    private List<TaxDetails> findTotalTaxes(BillingDetailResponse response, List<Order> orders) {
        // Check if component exists
        List<TaxDetails> taxDetailsList = new ArrayList<>();
        response.getBillingDetailItems().forEach(billingItem -> {
            if (!billingItem.getAppliedTaxProfile().getTaxComponents().isEmpty()) {
                billingItem.getAppliedTaxProfile().getTaxComponents().forEach(taxComponent -> {
                    double taxCal = (billingItem.getAppliedTaxProfile().getTaxPercent() / 100) * (taxComponent.getWeightage() / 100);
                    double taxAmount = Math.floor((billingItem.getPrice() * taxCal) * 100) / 100;
                    float taxPercent = billingItem.getAppliedTaxProfile().getTaxPercent()*taxComponent.getWeightage()/100;
                    int flag = 0;
                    if (taxDetailsList.isEmpty()){
                        refractedMethod1(taxDetailsList, billingItem, taxComponent, taxAmount, taxPercent);
                    }
                    else{
                        for(TaxDetails taxDetails1:taxDetailsList){
                            if(taxDetails1.getTaxType().equals(taxComponent.getComponentName())
                                && taxDetails1.getTaxPercentage().equals(taxPercent)){
                                double taxAmountNew=taxDetailsList.get(taxDetailsList.indexOf(taxDetails1)).getTaxTotal();
                                taxDetailsList.get(taxDetailsList.indexOf(taxDetails1)).setTaxTotal(taxAmountNew + taxAmount);
                                taxDetailsList.get(taxDetailsList.indexOf(taxDetails1)).getItems().add(billingItem.getItemName());
                                flag =1;
                                break;
                            }
                        }
                        if (flag==0) {
                            refractedMethod1(taxDetailsList, billingItem, taxComponent, taxAmount, taxPercent);
                        }
                    }
                });

            }
            else{
                int flagNew = 0;
                double taxCal = (billingItem.getAppliedTaxProfile().getTaxPercent() / 100);
                Double taxAmount = Math.floor((billingItem.getPrice() * taxCal) * 100) / 100;
                if (taxDetailsList.isEmpty()) {
                    refractedMethod2(taxDetailsList, billingItem, taxAmount);
                }
                else{
                    for(TaxDetails taxDetails1:taxDetailsList){
                        if(taxDetails1.getTaxType().equals(billingItem.getAppliedTaxProfile().getTaxType()) && taxDetails1.getTaxPercentage().equals(billingItem.getAppliedTaxProfile().getTaxPercent())){
                            double taxAmountNew=taxDetailsList.get(taxDetailsList.indexOf(taxDetails1)).getTaxTotal();
                            taxDetailsList.get(taxDetailsList.indexOf(taxDetails1)).setTaxTotal(taxAmountNew + taxAmount);
                            taxDetailsList.get(taxDetailsList.indexOf(taxDetails1)).getItems().add(billingItem.getItemName());
                            flagNew =1;
                            break;
                        }
                    }
                    if (flagNew==0) {
                        refractedMethod2(taxDetailsList, billingItem, taxAmount);
                    }
                }
			}
        });
        return taxDetailsList;
    }

    private void refractedMethod1(List<TaxDetails> taxDetailsList, BillingDetailItem billingItem, TaxComponent taxComponent, double taxAmount, float taxPercent) {
        List<String> itemList=new ArrayList<>();
        itemList.add(billingItem.getItemName());
        TaxDetails taxDetails = new TaxDetails();
        taxDetails.setTaxTotal(taxAmount);
        taxDetails.setItems(itemList);
        taxDetails.setTaxType(taxComponent.getComponentName());
        taxDetails.setTaxPercentage(taxPercent);
        taxDetailsList.add(taxDetails);
    }

    private void refractedMethod2(List<TaxDetails> taxDetailsList, BillingDetailItem billingItem, Double taxAmount) {
        List<String> itemList = new ArrayList<>();
        itemList.add(billingItem.getItemName());
        TaxDetails taxDetails = new TaxDetails();
        taxDetails.setTaxTotal(taxAmount);
        taxDetails.setItems(itemList);
        taxDetails.setTaxType(billingItem.getAppliedTaxProfile().getTaxType());
        taxDetails.setTaxPercentage(billingItem.getAppliedTaxProfile().getTaxPercent());
        taxDetailsList.add(taxDetails);
    }

    /* Method to calculate all the payment summary */
    public BillingDetailResponse getOrderPaymentSummary(Long restId, Long tableId) {
        RestTable restTable = restTableRepository.findByRestaurantIdAndTableId(tableId, restId);
        List<Order> orders = orderRepository.getOrderByTable(tableId, restId);
        BillingDetailResponse billingDetailsResponse = new BillingDetailResponse();
        List<BillingDetailItem> billingDetailsDtoList = new ArrayList<>();
        orders.forEach(order -> {
            order.getMenuItemOrders().forEach(orderItem -> {
                billingDetailsDtoList.add(orderToBDto(orderItem));
            });
        });
        billingDetailsResponse.setBillingDetailItems(billingDetailsDtoList);

        //Map<String, Map<Double, List<String>>> taxCharges = getTaxChargesPerItem(billingDetailsResponse);
        billingDetailsResponse.setTaxCharges(null);
        billingDetailsResponse.setTotalCalculatedTaxed(findTotalTaxes(billingDetailsResponse, orders));
        if(restTable.isPackagingChargeEnabled()){
            billingDetailsResponse.setPackagingCharge(restTable.getPackagingCharge());
        }
        else{
            billingDetailsResponse.setPackagingCharge(0);
        }
        if(restTable.isServiceChargeEnabled()){
            billingDetailsResponse.setServiceCharge(restTable.getServiceCharge());
        }
        else{
            billingDetailsResponse.setServiceCharge(0);
        }
        if(restTable.isOverAllDiscountEnabled()){
            billingDetailsResponse.setOverallDiscount(restTable.getOverallDiscount());
        }
        else{
            billingDetailsResponse.setOverallDiscount(0);
        }

        return billingDetailsResponse;

    }

}

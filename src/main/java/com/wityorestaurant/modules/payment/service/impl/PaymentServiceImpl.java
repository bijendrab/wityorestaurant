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
import com.wityorestaurant.modules.payment.service.PaymentService;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
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
    private Map<String, List<Map<String, Double>>> findTotalTaxes(BillingDetailResponse response, List<Order> orders) {
        // Check if component exists
        Map<String, List<Map<String, Double>>> totalTaxes = new HashMap<>();
        response.getBillingDetailItems().forEach(billingItem -> {
            List<Map<String, Double>> taxList = new ArrayList<Map<String, Double>>();
			Map<String, Double> eachTax = new HashMap<>();
			eachTax.put("Total", billingItem.getPrice());
            if (!billingItem.getAppliedTaxProfile().getTaxComponents().isEmpty()) {
                billingItem.getAppliedTaxProfile().getTaxComponents().forEach(taxComponent -> {
                    double taxCal = (billingItem.getAppliedTaxProfile().getTaxPercent() / 100) * (taxComponent.getWeightage() / 100);
                    Double taxAmount = Math.floor((billingItem.getPrice() * taxCal) * 100) / 100;
                    eachTax.put(taxComponent.getComponentName(), taxAmount);
                    eachTax.replace("Total", Math.floor((eachTax.get("Total") + taxAmount) * 100) / 100);
                });

            }
			taxList.add(eachTax);
            totalTaxes.put(billingItem.getItemName(), taxList);


        });
		/*taxCharges.forEach((componentName, rateStringMap) -> {
			rateStringMap.forEach((taxRate, itemNameList) -> {
				String taxName = componentName + " " + taxRate + "%";
				this.resetTotalComponentPrice();
				itemNameList.forEach(orderedItemName -> {
					this.setTotalComponentPrice(getTaxedItemPrice(orders, taxRate, orderedItemName));
				});
				totalTaxes.put(taxName, this.getTotalComponentPrice());
			});
		});*/

        return totalTaxes;
    }

    /* Method to calculate all the payment summary */
    public BillingDetailResponse getOrderPaymentSummary(Long restId, Long tableId) {
        //Reservation reservation = reservationRepository.getByCustomerId(new Gson().toJson(customerInfoDTO), restId);
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

        Map<String, Map<Double, List<String>>> taxCharges = getTaxChargesPerItem(billingDetailsResponse);
        billingDetailsResponse.setTaxCharges(taxCharges);
        billingDetailsResponse.setTotalCalculatedTaxed(this.findTotalTaxes(billingDetailsResponse, orders));
        billingDetailsResponse.setPackagingCharge(restTable.getPackagingCharge());
        billingDetailsResponse.setServiceCharge(restTable.getServiceCharge());
        billingDetailsResponse.setOverallDiscount(restTable.getOverallDiscount());
        return billingDetailsResponse;

    }

}

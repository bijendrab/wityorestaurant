package com.wityorestaurant.modules.payment.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.wityorestaurant.modules.config.model.RestTable;
import com.wityorestaurant.modules.config.repository.RestTableRepository;
import com.wityorestaurant.modules.customerdata.CustomerCartItems;
import com.wityorestaurant.modules.discount.model.Discount;
import com.wityorestaurant.modules.discount.repository.DiscountRepository;
import com.wityorestaurant.modules.menu.model.Product;
import com.wityorestaurant.modules.menu.model.ProductQuantityOptions;
import com.wityorestaurant.modules.menu.repository.MenuRepository;
import com.wityorestaurant.modules.orderservice.model.Order;
import com.wityorestaurant.modules.orderservice.model.OrderItem;
import com.wityorestaurant.modules.orderservice.model.OrderItemAddOn;
import com.wityorestaurant.modules.orderservice.repository.OrderRepository;
import com.wityorestaurant.modules.payment.dto.BillingDetailItem;
import com.wityorestaurant.modules.payment.dto.BillingDetailResponse;
import com.wityorestaurant.modules.payment.dto.DiscountDetails;
import com.wityorestaurant.modules.payment.dto.TaxDetails;
import com.wityorestaurant.modules.payment.service.PaymentService;
import com.wityorestaurant.modules.reservation.repository.ReservationRepository;
import com.wityorestaurant.modules.tax.model.TaxComponent;
import com.wityorestaurant.modules.tax.model.TaxProfile;
import com.wityorestaurant.modules.tax.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Akash Beura
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    public static final int PERCENT = 100;
    public static final int ZERO = 0;
    private OrderRepository orderRepository;
    private RestTableRepository restTableRepository;
    private MenuRepository menuRepository;
    private TaxRepository taxRepository;
    private DiscountRepository discountRepository;
    private double totalTaxedPrice = ZERO;
    private double totalComponentCost = 0.0;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public PaymentServiceImpl() {
    }

    @Autowired
    public PaymentServiceImpl(OrderRepository orderRepository, RestTableRepository restTableRepository,
                              MenuRepository menuRepository, TaxRepository taxRepository,
                              DiscountRepository discountRepository) {
        this.orderRepository = orderRepository;
        this.restTableRepository = restTableRepository;
        this.menuRepository = menuRepository;
        this.taxRepository = taxRepository;
        this.discountRepository = discountRepository;
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

    private BillingDetailItem orderToBDto(OrderItem orderItem,Long restId) {
        CustomerCartItems cartItem = new Gson().fromJson(orderItem.getCustomerCartItems(), CustomerCartItems.class);
        Product productJson = new Gson().fromJson(cartItem.getProductJson(), Product.class);
        Product product = menuRepository.findByItemAndRestId(productJson.getProductId(),restId);
        TaxProfile taxProfile = product.getAppliedTax();
        BillingDetailItem billingDetailsDto = new BillingDetailItem();
        billingDetailsDto.setProductId(product.getProductId());
        billingDetailsDto.setItemName(orderItem.getItemName());
        billingDetailsDto.setQuantityOption(orderItem.getQuantityOption());
        billingDetailsDto.setOrderId(orderItem.getOrder().getOrderId());
        billingDetailsDto.setQuantity(orderItem.getQuantity());
        if(orderItem.getSpecialDiscount()){
            billingDetailsDto.setSpecialDiscount((double)orderItem.getSpecialDiscountValue());
        }
        else{
            billingDetailsDto.setSpecialDiscount(0d);
        }
        for(ProductQuantityOptions pq:product.getProductQuantityOptions()){
            if(pq.getQuantityOption().equals(orderItem.getQuantityOption())){
                double itemCost = orderItem.getQuantity()*pq.getPrice();
                billingDetailsDto.setPrice(pq.getPrice());
                billingDetailsDto.setValue(itemCost);
            }
        }
        billingDetailsDto.setAppliedTaxProfile(taxProfile);
        billingDetailsDto.setOrderItemAddOns(orderItem.getOrderItemAddOns());
        return billingDetailsDto;
    }

    /* Method to find out the total tax mapping based on componenets */
    private Map<String, Map<Double, List<String>>> getTaxChargesPerItem(BillingDetailResponse response) {
        Map<String, Map<Double, List<String>>> taxMap = response.getTaxCharges();
        response.getBillingDetailItems().forEach(billingItem -> {

            float taxPercent = billingItem.getAppliedTaxProfile().getTaxPercent();
            if (billingItem.getAppliedTaxProfile().getTaxComponents().size() == ZERO) {

                double taxPercentWeightageCalculated = taxPercent / PERCENT;
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
                    double taxPercentWeightageCalculated = taxPercent * (taxComponent.getWeightage() / PERCENT);
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

        this.setTotalTaxedPrice(ZERO);
        for (int i = ZERO; i < orders.size(); i++) {
            orders.get(i).getMenuItemOrders().forEach(orderItem -> {
                if (orderItem.getItemName().equals(orderItemName)) {
                    this.setTotalTaxedPrice((orderItem.getPrice() * taxRate));
                }
            });
            if (getTotalPrice() > ZERO) {
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
                    double taxCal = (billingItem.getAppliedTaxProfile().getTaxPercent() / PERCENT) * (taxComponent.getWeightage() / PERCENT);
                    double taxAmount = ((billingItem.getValue() * taxCal) * PERCENT / PERCENT);
                    BigDecimal taxDouble = getBigDecimal(taxAmount);
                    float taxPercent = billingItem.getAppliedTaxProfile().getTaxPercent()*taxComponent.getWeightage()/ PERCENT;
                    int flag = ZERO;
                    if (taxDetailsList.isEmpty()){
                        refractedMethod1(taxDetailsList, billingItem, taxComponent, taxDouble.doubleValue(), taxPercent);
                    }
                    else{
                        for(TaxDetails taxDetails1:taxDetailsList){
                            if(taxDetails1.getTaxType().equals(taxComponent.getComponentName())
                                && taxDetails1.getTaxPercentage().equals(taxPercent)){
                                flag = getTaxFlag(taxDetailsList, billingItem, taxDouble.doubleValue(), taxDetails1);
                                break;
                            }
                        }
                        if (flag== ZERO) {
                            refractedMethod1(taxDetailsList, billingItem, taxComponent, taxDouble.doubleValue(), taxPercent);
                        }
                    }
                });

            }
            else{
                int flagNew = ZERO;
                double taxCal = (billingItem.getAppliedTaxProfile().getTaxPercent() / PERCENT);
                double taxAmount = (billingItem.getValue() * taxCal) * PERCENT / PERCENT;
                BigDecimal taxDouble = getBigDecimal(taxAmount);
                if (taxDetailsList.isEmpty()) {
                    refractedMethod2(taxDetailsList, billingItem, taxDouble.doubleValue());
                }
                else{
                    for(TaxDetails taxDetails2:taxDetailsList){
                        if(taxDetails2.getTaxType().equals(billingItem.getAppliedTaxProfile().getTaxType()) && taxDetails2.getTaxPercentage().equals(billingItem.getAppliedTaxProfile().getTaxPercent())){
                            flagNew = getTaxFlag(taxDetailsList, billingItem, taxDouble.doubleValue(), taxDetails2);
                        }
                    }
                    if (flagNew== ZERO) {
                        refractedMethod2(taxDetailsList, billingItem, taxDouble.doubleValue());
                    }
                }
			}
        });
        return taxDetailsList;
    }
    private List<DiscountDetails> findTotalDiscount(BillingDetailResponse response, Long restId) {
        List<DiscountDetails> discountDetailsList = new ArrayList<>();
        for (BillingDetailItem billingItem: response.getBillingDetailItems()){
            Discount discount = discountRepository.getDiscountByRestIdProductIdQuantityOption(restId,billingItem.getProductId(),billingItem.getQuantityOption());
            DiscountDetails discountDetails= new DiscountDetails();
            if(discount==null) {
                discountDetails.setItem(billingItem.getItemName());
                discountDetails.setQuantityOption(billingItem.getQuantityOption());
                discountDetails.setDiscountType("");
                discountDetails.setDiscountValue(0);
                BigDecimal specialDiscountValue = getBigDecimal(billingItem.getValue() * billingItem.getSpecialDiscount() / 100);
                discountDetails.setSpecialDiscount(specialDiscountValue.doubleValue());
                discountDetails.setDiscountTotal(discountDetails.getSpecialDiscount());
                discountDetailsList.add(discountDetails);
                continue;
            }

            discountDetails.setItem(billingItem.getItemName());
            discountDetails.setQuantityOption(billingItem.getQuantityOption());
            discountDetails.setDiscountType(discount.getDiscountType());
            discountDetails.setDiscountValue(discount.getDiscountValue());
            discountDetails.setSpecialDiscount(billingItem.getValue() * billingItem.getSpecialDiscount()/100);
            if(discount.getStartDate()==null || LocalDateTime.of(discount.getStartDate(),discount.getStartTime()).compareTo(LocalDateTime.now())<0 ) {
                if(discount.getEndOption().equalsIgnoreCase("Never")|| discount.getEndOption().isEmpty() || (discount.getEndOption().equalsIgnoreCase("On Date") && LocalDateTime.of(discount.getEndDate(),discount.getEndTime()).compareTo(LocalDateTime.now())>0 )) {
                    if (discount.getDiscountValueType().equalsIgnoreCase("percentage")) {
                        BigDecimal discountValue = getBigDecimal(billingItem.getValue() * discount.getDiscountValue() / 100);
                        discountDetails.setDiscountTotal(discountValue.doubleValue()+discountDetails.getSpecialDiscount());
                    } else if (discount.getDiscountValueType().equalsIgnoreCase("rupees")) {
                        BigDecimal discountValue = getBigDecimal(discount.getDiscountValue());
                        discountDetails.setDiscountTotal(discountValue.doubleValue()+discountDetails.getSpecialDiscount());
                    }
                }
            }
            else{
                discountDetails.setDiscountTotal(0d+discountDetails.getSpecialDiscount());
            }

            discountDetailsList.add(discountDetails);
        }
        return discountDetailsList;
    }

    private int getTaxFlag(List<TaxDetails> taxDetailsList, BillingDetailItem billingItem, double taxAmount, TaxDetails taxDetails) {
        int flag;
        double taxAmountNew=taxDetailsList.get(taxDetailsList.indexOf(taxDetails)).getTaxTotal();
        taxDetailsList.get(taxDetailsList.indexOf(taxDetails)).setTaxTotal(taxAmountNew + taxAmount);
        taxDetailsList.get(taxDetailsList.indexOf(taxDetails)).getItems().add(billingItem.getItemName());
        flag =1;
        return flag;
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
                billingDetailsDtoList.add(orderToBDto(orderItem,restId));
            });
        });
        billingDetailsResponse.setBillingDetailItems(billingDetailsDtoList);

        //Map<String, Map<Double, List<String>>> taxCharges = getTaxChargesPerItem(billingDetailsResponse);
        billingDetailsResponse.setTaxCharges(null);
        List<TaxDetails> taxDetailsList = findTotalTaxes(billingDetailsResponse, orders);
        List<DiscountDetails> discountDetailsList = findTotalDiscount(billingDetailsResponse,restId);
        billingDetailsResponse.setTotalCalculatedDiscount(discountDetailsList);
        billingDetailsResponse.setTotalCalculatedTaxed(taxDetailsList);
        double totalPriceWithoutTaxAndDiscount=getAllFoodItemCharges(billingDetailsResponse);
        double totalTax = getAllTaxItemCharges(taxDetailsList);
        double totalDiscount= getAllDiscountItemCharges(discountDetailsList);
        double totalAddOnCharges = getAllAddOnCharges(billingDetailsResponse.getBillingDetailItems());
        if(restTable.isPackagingChargeEnabled()){
            billingDetailsResponse.setPackagingCharge(restTable.getPackagingCharge());
        }
        else{
            billingDetailsResponse.setPackagingCharge(ZERO);
        }
        if(restTable.isServiceChargeEnabled()){
            TaxProfile taxProfile = taxRepository.findTaxProfileByRestIdAndTaxType(restId, "SCharge");
            if (taxProfile!=null) {
                billingDetailsResponse.setServiceChargePercent(taxProfile.getTaxPercent());
                double serviceCharge = totalPriceWithoutTaxAndDiscount * taxProfile.getTaxPercent()/ PERCENT;
                BigDecimal totalServiceCharge = getBigDecimal(serviceCharge);
                billingDetailsResponse.setServiceCharge(totalServiceCharge.doubleValue());
            }
            else{
                billingDetailsResponse.setServiceChargePercent(ZERO);
                billingDetailsResponse.setServiceCharge(ZERO);
            }
        }
        else{
            billingDetailsResponse.setServiceChargePercent(ZERO);
            billingDetailsResponse.setServiceCharge(ZERO);
        }
        if(restTable.isOverAllDiscountEnabled()){
            billingDetailsResponse.setOverallDiscountPercent(restTable.getOverallDiscount());
            double overAllDiscount = totalPriceWithoutTaxAndDiscount * restTable.getOverallDiscount()/ PERCENT;
            BigDecimal totalOverAllDiscount = getBigDecimal(overAllDiscount);
            billingDetailsResponse.setOverallDiscount(totalOverAllDiscount.doubleValue());
        }
        else{
            billingDetailsResponse.setOverallDiscountPercent(ZERO);
            billingDetailsResponse.setOverallDiscount(ZERO);
        }

        double totalPrice = (totalPriceWithoutTaxAndDiscount + totalTax + totalAddOnCharges - totalDiscount) +
            billingDetailsResponse.getServiceCharge() +
            billingDetailsResponse.getPackagingCharge()-
            billingDetailsResponse.getOverallDiscount();

        billingDetailsResponse.setTotalCostWithoutTaxAndDiscount(getBigDecimal(totalPriceWithoutTaxAndDiscount).doubleValue());
        billingDetailsResponse.setTotalItemsDiscount(getBigDecimal(totalDiscount).doubleValue());
        billingDetailsResponse.setTotalTax(getBigDecimal(totalTax).doubleValue());
        billingDetailsResponse.setAddOnCharge(totalAddOnCharges);
        billingDetailsResponse.setTotalCost(getBigDecimal(totalPrice).doubleValue());
        return billingDetailsResponse;

    }

    private BigDecimal getBigDecimal(double inputPrice) {
        return new BigDecimal(inputPrice).setScale(2, RoundingMode.HALF_UP);
    }

    public double getAllFoodItemCharges(BillingDetailResponse response){
        double allFoodItemCost= response.getBillingDetailItems().stream().mapToDouble(BillingDetailItem::getValue).sum();
        return allFoodItemCost;
    }

    public double getAllTaxItemCharges(List<TaxDetails> taxDetails){
        double taxTotal= taxDetails.stream().mapToDouble(TaxDetails::getTaxTotal).sum();
        return taxTotal;
    }

    public double getAllDiscountItemCharges(List<DiscountDetails> discountDetails){
        double discountTotal= discountDetails.stream().mapToDouble(DiscountDetails::getDiscountTotal).sum();
        return discountTotal;
    }

    private double getDiscountPercent(Long restId,BillingDetailItem billingDetailItem){
        Discount discount = discountRepository.getDiscountByRestIdProductIdQuantityOption(restId,billingDetailItem.getProductId(),billingDetailItem.getQuantityOption());
        if(discount!=null) {
            return discount.getDiscountValue();
        }
        else{
            return 0;
        }
    }
    public double getAllAddOnCharges(List<BillingDetailItem> billingDetailItems){
        double addOnTotal =0d;
        for(BillingDetailItem billingDetailItem:billingDetailItems){
            for(OrderItemAddOn orderItemAddOn:billingDetailItem.getOrderItemAddOns()){
                addOnTotal=addOnTotal+orderItemAddOn.getPrice();
            }
        }
        return addOnTotal;
    }


}

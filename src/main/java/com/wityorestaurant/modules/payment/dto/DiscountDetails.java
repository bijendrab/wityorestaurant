package com.wityorestaurant.modules.payment.dto;

public class DiscountDetails {
    private float discountValue;
    private double specialDiscount;
    private String discountType;
    private Double discountTotal;
    private String item;
    private String quantityOption;

    public float getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(float discountValue) {
        this.discountValue = discountValue;
    }

    public double getSpecialDiscount() {
        return specialDiscount;
    }

    public void setSpecialDiscount(double specialDiscount) {
        this.specialDiscount = specialDiscount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(Double discountTotal) {
        this.discountTotal = discountTotal;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuantityOption() {
        return quantityOption;
    }

    public void setQuantityOption(String quantityOption) {
        this.quantityOption = quantityOption;
    }
}

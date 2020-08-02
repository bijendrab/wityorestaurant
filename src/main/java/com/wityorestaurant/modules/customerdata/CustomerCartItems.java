package com.wityorestaurant.modules.customerdata;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

public class CustomerCartItems {
    private Long cartItemId;
    private String itemName;
    private String quantityOption;
    private int quantity;
    private double price;
    private Boolean immediateStatus;
    private LocalDateTime addItemToCartTime;
    private LocalDateTime updateItemInCartTime;
    private String productJson;
    private Set<CustomerCartAddOnItems> selectCartAddOnItems;

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantityOption() {
        return quantityOption;
    }

    public void setQuantityOption(String quantityOption) {
        this.quantityOption = quantityOption;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductJson() {
        return productJson;
    }

    public void setProductJson(String productJson) {
        this.productJson = productJson;
    }

    public Boolean getImmediateStatus() {
        return immediateStatus;
    }

    public void setImmediateStatus(Boolean immediateStatus) {
        this.immediateStatus = immediateStatus;
    }

    public LocalDateTime getAddItemToCartTime() {
        return addItemToCartTime;
    }

    public void setAddItemToCartTime(LocalDateTime addItemToCartTime) {
        this.addItemToCartTime = addItemToCartTime;
    }

    public LocalDateTime getUpdateItemInCartTime() {
        return updateItemInCartTime;
    }

    public void setUpdateItemInCartTime(LocalDateTime updateItemInCartTime) {
        this.updateItemInCartTime = updateItemInCartTime;
    }

    public Set<CustomerCartAddOnItems> getSelectCartAddOnItems() {
        return selectCartAddOnItems;
    }

    public void setSelectCartAddOnItems(Set<CustomerCartAddOnItems> selectCartAddOnItems) {
        this.selectCartAddOnItems = selectCartAddOnItems;
    }
}

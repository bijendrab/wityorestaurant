package com.wityorestaurant.modules.menu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "itemQuantity")
public class ProductQuantityOptions implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int QOId;

    @Column(name="qoption")
    private String option;

    @Column(name = "quantity")
    private String quantity;

    @NotNull(message="Please provide some price")
    //@Min(value = 50, message = "Minimum value should be greater than 50")
    @Column(name = "price")
    private double price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "productId")
    private Product product;

    public ProductQuantityOptions() {
    }

    public int getQOId() {
        return QOId;
    }

    public void setQOId(int QOId) {
        this.QOId = QOId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

package com.wityorestaurant.modules.menu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
public class ProductIdentity implements Serializable {
    @Column(name = "productId",nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String productId;

    @Column(name = "restId")
    private Long restId;

    public ProductIdentity() {}

    public ProductIdentity(String productId, Long restId) {
        this.productId = productId;
        this.restId = restId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getRestId() {
        return restId;
    }

    public void setRestId(Long restId) {
        this.restId = restId;
    }
}

package com.wityorestaurant.modules.reservation.dto;


import com.wityorestaurant.modules.customerdata.CustomerInfoDTO;
import com.wityorestaurant.modules.reservation.model.TimeSpan;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/*@Entity
@Table(name = "checkRequest")*/
public class CheckRequestDTO implements Serializable {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;*/

    @NotNull
    private Date date;

    @NotNull
    @Column(name = "numberOfSeats")
    private Integer numberOfSeats;

    @NotNull
    @Column(name = "tableNumber")
    private Integer tableNumber;


    private CustomerInfoDTO customerInfo;

    @NotNull
    @Embedded
    private TimeSpan ts;

    public CheckRequestDTO() {

    }
/*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public CustomerInfoDTO getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfoDTO customerInfo) {
        this.customerInfo = customerInfo;
    }

    public TimeSpan getTs() {
        return ts;
    }

    public void setTs(TimeSpan ts) {
        this.ts = ts;
    }
}



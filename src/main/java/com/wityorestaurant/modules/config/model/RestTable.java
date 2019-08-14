package com.wityorestaurant.modules.config.model;

import com.fasterxml.jackson.annotation.*;
import com.wityorestaurant.modules.reservation.model.Reservation;
import com.wityorestaurant.modules.restaurant.model.RestaurantDetails;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "resttable")
public class RestTable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tableNumber")
    private Long tableNumber;

    @Column(name = "tableSize")
    @Min(2)
    private Integer tableSize;

    @Column(name = "qrCode")
    private Integer qrCode;

    @ManyToOne
    @JoinColumn(name="restId")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="restId")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("restId")
    private RestaurantDetails restaurantDetails;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "relatedTable",orphanRemoval = true, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Reservation> reservationList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Long tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getTableSize() {
        return tableSize;
    }

    public void setTableSize(Integer tableSize) {
        this.tableSize = tableSize;
    }

    public Integer getQrCode() {
        return qrCode;
    }

    public void setQrCode(Integer qrCode) {
        this.qrCode = qrCode;
    }

    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}

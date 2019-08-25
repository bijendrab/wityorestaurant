package com.wityorestaurant.modules.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wityorestaurant.modules.reservation.model.Reservation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "foodorder")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="orderId")
public class Order implements Serializable {
	
	private static final long serialVersionUID = 8311754468336492518L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long orderId;

    @Column(name = "totalCost")
    private Float totalCost;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservationId")
    private Reservation accordingReservation;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<OrderItem> menuItemOrders = new HashSet<OrderItem>(0);
    
    private String orderedBy;

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Reservation getAccordingReservation() {
        return accordingReservation;
    }

    public void setAccordingReservation(Reservation accordingReservation) {
        this.accordingReservation = accordingReservation;
    }

    public Set<OrderItem> getMenuItemOrders() {
        return menuItemOrders;
    }

    public void setMenuItemOrders(Set<OrderItem> menuItemOrders) {
        this.menuItemOrders = menuItemOrders;
    }

	public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}
}

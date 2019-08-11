package com.wityorestaurant.modules.reservation.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wityorestaurant.modules.config.model.RestTable;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "submissionDate")
    private LocalDate submissionDate;

    @NotNull
    private LocalDate reservationDate;

    @NotNull
    @Embedded
    private TimeSpan reservationTime;

    @Size(max = 255)
    private String otherRequirements;

    @Lob
    private String customerInfo;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tableId")
    private RestTable relatedTable;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public TimeSpan getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(TimeSpan reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getOtherRequirements() {
        return otherRequirements;
    }

    public void setOtherRequirements(String otherRequirements) {
        this.otherRequirements = otherRequirements;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }

    public RestTable getRelatedTable() {
        return relatedTable;
    }

    public void setRelatedTable(RestTable relatedTable) {
        this.relatedTable = relatedTable;
    }
    public boolean doesTimeSpanConflicts(TimeSpan ts) {
        int myTimeSpanEnd = Integer.parseInt(this.reservationTime.getEnd());
        int myTimeSpanStart = Integer.parseInt(this.reservationTime.getStart());

        int givenTimeSpanStart = Integer.parseInt(ts.getStart());
        int givenTimeSpanEnd = Integer.parseInt(ts.getEnd());

        boolean endTimeIntersection = givenTimeSpanEnd <= myTimeSpanEnd && givenTimeSpanEnd >= myTimeSpanStart;
        boolean startTimeIntersection = givenTimeSpanStart >= myTimeSpanStart && givenTimeSpanStart <= myTimeSpanEnd;

        return endTimeIntersection || startTimeIntersection;

    }
}

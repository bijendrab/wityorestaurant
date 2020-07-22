package com.wityorestaurant.modules.reservation.dto;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import com.wityorestaurant.modules.reservation.model.TimeSpan;

public class ReservationDTO {
    @NotNull
    private LocalDate date;

    @NotNull
    @Column(name = "numberOfSeats")
    private Integer numberOfSeats;

    @NotNull
    @Column(name = "tableNumber")
    private Integer tableNumber;

    @NotNull
    @Embedded
    private TimeSpan timeSpan;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public TimeSpan getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(TimeSpan timeSpan) {
        this.timeSpan = timeSpan;
    }
}

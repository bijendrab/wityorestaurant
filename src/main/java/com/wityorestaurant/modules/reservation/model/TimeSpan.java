package com.wityorestaurant.modules.reservation.model;


import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Embeddable
public class TimeSpan implements Serializable {

    @Pattern(regexp = "([01]?[0-9]|2[0-3])[0-5][0-9]")
    private String start;   //e.g. "1400" -> 14:00

    @Pattern(regexp = "([01]?[0-9]|2[0-3])[0-5][0-9]")
    private String end;     //e.g. "1630" -> 16:30

    public TimeSpan(){}

    public TimeSpan(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "TimeSpan{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}

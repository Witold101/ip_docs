package by.vistar.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class ListBaySell {
    private Long id;
    private Date date;
    private String number;
    private Good good;
    private Boolean bay;
    private Float quantity;
    private Long sum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Boolean getBay() {
        return bay;
    }

    public void setBay(Boolean bay) {
        this.bay = bay;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

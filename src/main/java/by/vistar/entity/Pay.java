package by.vistar.entity;

import java.time.LocalDateTime;

public class Pay {
    private Long id;
    private LocalDateTime date;
    private String number;
    private Long currencyId;
    private Long payerId;
    private Long payerAccId;
    private Long sum;
    private String info;
    private Boolean debetDoc;
    private Currency currency;
    private Firm payer;
    private Account payerAcc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }

    public Long getPayerAccId() {
        return payerAccId;
    }

    public void setPayerAccId(Long payerAccId) {
        this.payerAccId = payerAccId;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean getDebetDoc() {
        return debetDoc;
    }

    public void setDebetDoc(Boolean debetDoc) {
        this.debetDoc = debetDoc;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Firm getPayer() {
        return payer;
    }

    public void setPayer(Firm payer) {
        this.payer = payer;
    }

    public Account getPayerAcc() {
        return payerAcc;
    }

    public void setPayerAcc(Account payerAcc) {
        this.payerAcc = payerAcc;
    }
}

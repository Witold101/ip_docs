package by.vistar.business.parser;

import by.vistar.entity.Pay;

import java.time.LocalDateTime;
import java.util.List;

public class BankDocPay {
    private LocalDateTime dateTimeOperation;
    private LocalDateTime firstDateOperation;
    private LocalDateTime lastDateOperation;
    private Long beginBalance;
    private Long endBalance;
    private List<Pay> payListOperations;

    public void setDateTimeOperation(LocalDateTime dateTimeOperation) {
        this.dateTimeOperation = dateTimeOperation;
    }

    public void setFirstDateOperation(LocalDateTime firstDateOperation) {
        this.firstDateOperation = firstDateOperation;
    }

    public void setLastDateOperation(LocalDateTime lastDateOperation) {
        this.lastDateOperation = lastDateOperation;
    }

    public void setBeginBalance(Long beginBalance) {
        this.beginBalance = beginBalance;
    }

    public void setEndBalance(Long endBalance) {
        this.endBalance = endBalance;
    }

    public void setPayListOperations(List<Pay> payListOperations) {
        this.payListOperations = payListOperations;
    }

    public LocalDateTime getDateTimeOperation() {
        return dateTimeOperation;
    }

    public LocalDateTime getFirstDateOperation() {
        return firstDateOperation;
    }

    public LocalDateTime getLastDateOperation() {
        return lastDateOperation;
    }

    public Long getBeginBalance() {
        return beginBalance;
    }

    public Long getEndBalance() {
        return endBalance;
    }

    public List<Pay> getPayListOperations() {
        return payListOperations;
    }
}

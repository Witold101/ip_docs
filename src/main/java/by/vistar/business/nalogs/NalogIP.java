package by.vistar.business.nalogs;

import by.vistar.business.parser.BankDocPay;
import by.vistar.business.parser.ParsAbsolutBank;
import by.vistar.entity.Pay;

import java.math.BigDecimal;

public class NalogIP {

    /**
     * Метод для подсчета налога 5% за квартал, работает не корректно т.к.
     * берет период за квартал а не наростающим итогом
     * @param bankDocPay
     * @return
     */
    public static BigDecimal getNalogIp(BankDocPay bankDocPay) {
        Long sum = 0L;
        for (Pay pay : bankDocPay.getPayListOperations()) {
            if (pay.getDebetDoc() == false) {
                sum = sum + pay.getSum();
            }
        }
        BigDecimal sumAll = new BigDecimal(sum);
        BigDecimal nalog = new BigDecimal("0.05");
        sumAll = sumAll.divide(new BigDecimal("100"));
        sumAll = sumAll.multiply(nalog);
        sumAll = sumAll.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return sumAll;
    }
}

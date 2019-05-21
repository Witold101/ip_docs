package by.vistar.business;

import by.vistar.entity.User;
import by.vistar.entity.Good;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceBuy {
    private Long id;
    private String number;
    private LocalDateTime date;
    private User seller;
    private List<Good> goods;

}

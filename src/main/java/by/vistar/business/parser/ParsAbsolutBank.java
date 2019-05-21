package by.vistar.business.parser;

import by.vistar.entity.*;
import by.vistar.services.entity.ServiceCurrency;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParsAbsolutBank extends DefaultHandler implements ParserResult {

    private final String OPENING_BALANCE_TAG = "OpeningBalance";
    private final String OPENING_BALANCE_DATE_TAG = "OpeningBalanceDate";
    private final String FIRST_RATE_DATE_TAG = "FirstRateDate";
    private final String CLOSING_BALANCE_TAG = "ClosingBalance";
    private final String CLOSING_BALANCE_DATE_TAG = "ClosingBalanceDate";
    private final String LAST_RATE_DATE_TAG = "RateDate";
    private final String STATEMENT_DATE_TIME_TAG = "StatementDateTime";

    private final String DEBET_DOCUMENTS_TAG = "DebetDocuments";
    private final String DEBET_DOCUMENTS_ROW_TAG = "DebetDocumentsRow";
    private final String CREDIT_DOCUMENTS_TAG = "CreditDocuments";
    private final String CREDIT_DOCUMENTS_ROW_TAG = "CreditDocumentsRow";

    private final String VALUE_DATE_TAG = "ValueDate";
    private final String DOCUMENT_DATE_TAG = "DocumentDate";
    private final String DOCUMENT_NUMBER_TAG = "DocumentNumber";
    private final String CURR_CODE_TAG = "CurrCode";
    private final String PAYER_TAG = "Payer";
    private final String PAYER_UNP_TAG = "PayerUNP";
    private final String PAYER_BANK_SWIFT_TAG = "PayerBankBIC";
    private final String PAYER_BANK_NAME_TAG = "PayerBankName";
    private final String PAYER_ACC_TAG = "PayerAccount";
    private final String BENEFICIAR_TAG = "Beneficiar";
    private final String BENEFICIAR_UNP_TAG = "BeneficiarUNP";
    private final String BENEFICIAR_BANK_SWIFT_TAG = "BeneficiarBankBIC";
    private final String BENEFICIAR_BANK_NAME_TAG = "BeneficiarBankName";
    private final String BENEFICIAR_ACC_TAG = "BeneficiarAccount";
    private final String AMOUNT_TAG = "Amount";
    private final String INFO_DOC_TAG = "Ground";
    private final String ACCEPT_DATE_TAG = "AcceptDate";

    private List<Pay> payList;
    private String currentElement;
    private Pay pay;
    private Firm firm;
    private Bank bank;
    private Account account;
    private Currency currency;
    private boolean debetDoc;
    private boolean creditDoc;
    private boolean flag; // Так как метод characters вызывается 2 раза то требуется флаг проверки обработан ли этот тег уже или нет.
    private BankDocPay bankDocPay;
    private LocalDateTime statementDateTime;
    private LocalDateTime firstRateDate;
    private LocalDateTime rateDate;
    private Long openBalance;
    private Long closeBalance;

    public ParsAbsolutBank() {
        this.payList = new ArrayList<>();
        this.currentElement = null;
        this.pay = null;
        this.firm = null;
        this.bank = null;
        this.account = null;
        this.currency = null;
        this.debetDoc = false;
        this.creditDoc = false;
        this.flag = false;
    }

    private void pars(String path) {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        Handler handler = new Handler();
        try {
            SAXParser parser = spf.newSAXParser();
            parser.parse(new File(path), handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public BankDocPay getResult(String path) {
        bankDocPay = new BankDocPay();
        pars(path);
        bankDocPay.setPayListOperations(payList);
        bankDocPay.setDateTimeOperation(statementDateTime);
        bankDocPay.setFirstDateOperation(firstRateDate);
        bankDocPay.setLastDateOperation(rateDate);
        bankDocPay.setBeginBalance(openBalance);
        bankDocPay.setEndBalance(closeBalance);
        return bankDocPay;
    }

    private class Handler extends DefaultHandler {

        @Override
        public void startDocument() throws SAXException {
            System.out.println("Begin doc !!!");
        }

        @Override
        public void endDocument() throws SAXException {
            System.out.println("End doc !!!");
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentElement = qName;
            switch (currentElement) {
                case DEBET_DOCUMENTS_ROW_TAG: {
                    pay = new Pay();
                    pay.setDebetDoc(true);
                    debetDoc = true;
                }
                break;
                case CREDIT_DOCUMENTS_ROW_TAG: {
                    pay = new Pay();
                    pay.setDebetDoc(false);
                    creditDoc = true;
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (currentElement != null) {
                switch (currentElement) {
                    case STATEMENT_DATE_TIME_TAG: {
                        int day = Integer.valueOf(String.valueOf(ch, start, 2));
                        int month = Integer.valueOf(String.valueOf(ch, start + 3, 2));
                        int yer = Integer.valueOf(String.valueOf(ch, start + 6, 4));
                        int hour = Integer.valueOf(String.valueOf(ch, start + 11, 2));
                        int minute = Integer.valueOf(String.valueOf(ch, start + 14, 2));
                        statementDateTime = LocalDateTime.of(yer, month, day, hour, minute);
                        currentElement=null;
                    }
                    break;
                    case FIRST_RATE_DATE_TAG: {
                        int day = Integer.valueOf(String.valueOf(ch, start, 2));
                        int month = Integer.valueOf(String.valueOf(ch, start + 3, 2));
                        int yer = Integer.valueOf(String.valueOf(ch, start + 6, 4));
                        firstRateDate = LocalDateTime.of(yer, month, day, 0, 0);
                        currentElement=null;
                    }
                    break;
                    case LAST_RATE_DATE_TAG: {
                        int day = Integer.valueOf(String.valueOf(ch, start, 2));
                        int month = Integer.valueOf(String.valueOf(ch, start + 3, 2));
                        int yer = Integer.valueOf(String.valueOf(ch, start + 6, 4));
                        rateDate = LocalDateTime.of(yer, month, day, 0, 0);
                        currentElement=null;
                    }
                    break;
                    case OPENING_BALANCE_TAG:{
                        String opBalance = String.valueOf(ch, start, length - 3)
                                + String.valueOf(ch, start + length - 2, 2);

                        openBalance = Long.parseLong(opBalance);
                        currentElement=null;
                    }
                    break;
                    case CLOSING_BALANCE_TAG:{
                        String clBalance = String.valueOf(ch, start, length - 3)
                                + String.valueOf(ch, start + length - 2, 2);

                        closeBalance = Long.parseLong(clBalance);
                        currentElement=null;
                    }
                }
            }

            if ((debetDoc || creditDoc) && currentElement != null) {
                switch (currentElement) {
                    case VALUE_DATE_TAG: {
                        int day = Integer.valueOf(String.valueOf(ch, start, 2));
                        int month = Integer.valueOf(String.valueOf(ch, start + 3, 2));
                        int yer = Integer.valueOf(String.valueOf(ch, start + 6, 4));
                        pay.setDate(LocalDateTime.of(yer, month, day, 0, 0));
                    }
                    break;
                    case DOCUMENT_NUMBER_TAG: {
                        pay.setNumber(String.valueOf(ch, start, length));
                    }
                    break;
                    case CURR_CODE_TAG: {
                        String currCode = String.valueOf(ch, start, length);
                        currency = new Currency();
                        currency.setCodeNumberIso4217(currCode);
                        pay.setCurrency(currency);
                    }
                    break;
                    case BENEFICIAR_TAG: {
                        if (debetDoc) {
                            String beneficiar = String.valueOf(ch, start, length);
                            firm = new Firm();
                            firm.setName(beneficiar);
                        }
                    }
                    break;
                    case BENEFICIAR_UNP_TAG: {
                        if (debetDoc) {
                            String unp = String.valueOf(ch, start, length);
                            firm.setVatNumber(unp);
                        }
                    }
                    break;
                    case BENEFICIAR_BANK_SWIFT_TAG: {
                        if (debetDoc) {
                            String swift = String.valueOf(ch, start, length);
                            bank = new Bank();
                            bank.setSwift(swift);
                        }
                    }
                    break;
                    case BENEFICIAR_BANK_NAME_TAG: {
                        if (debetDoc) {
                            String bankName = String.valueOf(ch, start, length);
                            bank.setName(bankName);
                        }
                    }
                    break;
                    case BENEFICIAR_ACC_TAG: {
                        if (debetDoc) {
                            String acc = String.valueOf(ch, start, length);
                            account = new Account();
                            account.setNumber(acc);
                            account.setFirm(firm);
                            account.setBank(bank);
                            account.setCurrency(currency);
                        }
                    }

                    case PAYER_TAG: {
                        if (creditDoc) {
                            String payer = String.valueOf(ch, start, length);
                            firm = new Firm();
                            firm.setName(payer);
                        }
                    }
                    break;
                    case PAYER_UNP_TAG: {
                        if (creditDoc) {
                            String unp = String.valueOf(ch, start, length);
                            firm.setVatNumber(unp);
                        }
                    }
                    break;
                    case PAYER_BANK_SWIFT_TAG: {
                        if (creditDoc) {
                            String swift = String.valueOf(ch, start, length);
                            bank = new Bank();
                            bank.setSwift(swift);
                        }
                    }
                    break;
                    case PAYER_BANK_NAME_TAG: {
                        if (creditDoc) {
                            String bankName = String.valueOf(ch, start, length);
                            bank.setName(bankName);
                        }
                    }
                    break;
                    case PAYER_ACC_TAG: {
                        if (creditDoc) {
                            String acc = String.valueOf(ch, start, length);
                            account = new Account();
                            account.setNumber(acc);
                            account.setFirm(firm);
                            account.setBank(bank);
                            account.setCurrency(currency);
                            pay.setPayerAcc(account);
                        }
                    }
                    break;
                    case AMOUNT_TAG: {
                        String amount = String.valueOf(ch, start, length - 3)
                                + String.valueOf(ch, start + length - 2, 2);

                        pay.setSum(Long.parseLong(amount));
                    }
                    break;
                    case INFO_DOC_TAG: {
                        String info = String.valueOf(ch, start, length);
                        pay.setInfo(info);
                        payList.add(pay);
                        pay = null;
                        debetDoc = false;
                        creditDoc = false;
                    }
                    break;
                }
                currentElement = null;
            }
        }
    }
}

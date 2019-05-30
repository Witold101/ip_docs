package by.vistar;

import by.vistar.business.nalogs.NalogIP;
import by.vistar.business.parser.BankDocPay;
import by.vistar.business.parser.ClasspathResolverURIAdapter;
import by.vistar.business.parser.ConvertToPDF;
import by.vistar.business.parser.ParsAbsolutBank;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
//
    ParsAbsolutBank parsAbsolutBank = new ParsAbsolutBank();
    BankDocPay bankDocPay = parsAbsolutBank.getResult("C:/Users/Witalij/IdeaProjects/comeco_spr/195_Pars.xml");
    System.out.println(NalogIP.getNalogIp(bankDocPay));

    File fo = new File("./src/main/resources/reports/xsl/bookip.fo");
    File xml = new File("./src/main/resources/reports/xml/bookip.xml");
    String config = "fop.xconf";
    File pdf = new File("./arttest.pdf");

    ConvertToPDF convertToPDF = new ConvertToPDF();
        try {
            convertToPDF.convertFO2PDF(fo,xml,pdf,config);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


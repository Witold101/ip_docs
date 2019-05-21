package by.vistar.business.parser;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.*;
import org.xml.sax.SAXException;


public class ConvertToPDF {


    private FopFactory fopFactory = null;// = FopFactory.newInstance(new File(".").toURI());


    public void convertXML2FO(File xml, File xslt, File fo)
            throws IOException, TransformerException {

        //Setup output
        OutputStream out = new java.io.FileOutputStream(fo);
        try {
            //Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslt));

            //Setup input for XSLT transformation
            Source src = new StreamSource(xml);

            //Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new StreamResult(out);

            //Start XSLT transformation and FOP processing
            transformer.transform(src, res);
        } finally {
            out.close();
        }
    }

    public void convertFO2PDF(File fo,File xml, File pdf, String configFaileName) throws Exception {

        fopFactory = getFopFactoryConfiguredFromClasspath(configFaileName);
        OutputStream out = new java.io.FileOutputStream(pdf);
        out = new java.io.BufferedOutputStream(out);

        try {
            // Construct fop with desired output format
//            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(fo));

            // Set the value of a <param> in the stylesheet
            transformer.setParameter("versionParam", "2.0");

            // Setup input for XSLT transformation
            Source src = new StreamSource(xml);

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            transformer.transform(src, res);
        } finally {
            out.close();
        }
        System.out.println("Success!");
    }

    /**
     * The fop configuration and referenced resources (ttf font) are loaded
     * from the file system
     *
     */

    private static FopFactory getFopFactoryConfiguredFromFileSystem(String configFileName) throws Exception {
        FopFactory fopFactory = FopFactory.newInstance(new File("src/main/resources/" + configFileName));
        return fopFactory;
    }

    /**
     * The fop configuration and referenced resources (ttf font) are loaded
     * from the classpath
     *
     */

    private static FopFactory getFopFactoryConfiguredFromClasspath(String configFileName) throws Exception {

        URI baseUri = URI.create("base_uri_is_meaningless_in_this_case");
        //System.out.println("uri: " + uri);

        // create a builder with a ResourceResolver which reads from the classpath.
        // The resourceResolver will be called for loading the resources referenced
        // in the configuration (for example a ttf font)
        FopFactoryBuilder builder = new FopFactoryBuilder(baseUri, new ClasspathResolverURIAdapter());

        // create a configuration which is based on a file read from the classpath
        DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
        Configuration cfg = cfgBuilder.build("./src/main/resources/" + configFileName);

        // sets the configuration
        builder.setConfiguration(cfg);

        FopFactory fopFactory = builder.build();
        return fopFactory;

    }
}

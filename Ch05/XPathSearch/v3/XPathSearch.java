import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class XPathSearch
{
   public static void main(String[] args)
   {
      try
      {
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         dbf.setNamespaceAware(true);
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document doc = db.parse("contacts.xml");
         XPathFactory xpf = XPathFactory.newInstance();
         XPath xp = xpf.newXPath();
         xp.setNamespaceContext(new NSContext());
         xp.setXPathFunctionResolver(new DateResolver());
         XPathExpression xpe;
         String expr;
         expr = "//tt:contact[tt:date(tt:birth) > tt:date('1960-01-01')]" +
                "/tt:name/text()";
         xpe = xp.compile(expr);
         Object result = xpe.evaluate(doc, XPathConstants.NODESET);
         NodeList nl = (NodeList) result;
         for (int i = 0; i < nl.getLength(); i++)
            System.out.println(nl.item(i).getNodeValue()); 
      }
      catch (IOException ioe)
      {
         System.err.println("IOE: " + ioe);
      }
      catch (SAXException saxe)
      {
         System.err.println("SAXE: " + saxe);
      }
      catch (FactoryConfigurationError fce)
      {
         System.err.println("FCE: " + fce);
      }
      catch (ParserConfigurationException pce)
      {
         System.err.println("PCE: " + pce);
      }
      catch (XPathException xpe)
      {
         System.err.println("XPE: " + xpe);
      }
   }
}
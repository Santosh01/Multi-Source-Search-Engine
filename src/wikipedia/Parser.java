package wikipedia;

/**
 * 
 */

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * @author 
 */
public class Parser extends DefaultHandler{
	
	
	private boolean titleFlag;
	private String title;
	private boolean URLFlag;
	private StringBuilder URL;
	private boolean textFlag;
	private StringBuilder text = new StringBuilder();
	private StringBuilder iLink = new StringBuilder();
	private StringBuilder eLink = new StringBuilder();
	
	private boolean redirectFlag;
	
	
	
	public void parse(String filename) {
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		
		
		try
		{  
			SAXParser sp = spf.newSAXParser();
			sp.parse(filename,this);
		}
		catch(SAXException e)
		{
	            System.out.println("SAXException");
	    }
		catch (ParserConfigurationException e)
		{
            System.out.println("ParserConfig error");
		}
		catch(IOException e)
		{
			System.out.println("I/O Error");
		}
	}
		

	@Override
	public void startElement(String namespaceURI,
            String localName,
            String qName, 
            Attributes atts)
            throws SAXException {

		
		if(qName.equalsIgnoreCase("title"))
		{
			titleFlag = true;
			URLFlag = true;
		}
		if(qName.equalsIgnoreCase("text"))
		{
            text = new StringBuilder();
			textFlag = true;
		}
		if(qName.equalsIgnoreCase("redirect"))
		{
            redirectFlag = true;
		}
}
	@Override
	public void endElement(String uri,
           String localName,
            String qName)
            throws SAXException
        {
		  if(qName.equalsIgnoreCase("text"))
	       {
	    	   textFlag = false;
	       }
		   if(qName.equalsIgnoreCase("page"))
	       {
			if(redirectFlag == false)
			{
            title = WikipediaParser.parseTitle(title);
            URL = WikipediaParser.parseURL(URL);
            text = WikipediaParser.parseText(text);
            iLink = WikipediaParser.parseILinks(text.toString());
            eLink = WikipediaParser.parseELinks(text.toString());
            text = WikipediaParser.parseLinksText(text.toString());
            SolrData.addDocument(title, URL.toString(), text.toString() , iLink.toString(), eLink.toString());
			}
            title = "";
            URL = new StringBuilder("");
            text = new StringBuilder("");
            eLink = new StringBuilder("");
            iLink = new StringBuilder("");
            redirectFlag=false;
	       }
	    }
	
	public void characters(char[] ch,
            int start,
            int length)
            throws SAXException
            
            {
			
		      if(titleFlag)
		      {   
		    	  title = new String (ch,start,length);
		    	  URL = new StringBuilder(title);
		    	  titleFlag = false;
		    	  URLFlag = false;
		      }
		      if(textFlag)
		      {   
		    	  
		    	  text.append(new String (ch,start,length));
		    	  
		      }
		      
            }
	}
	
	




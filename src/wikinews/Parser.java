package wikinews;


/**
 * 
 */

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import org.xml.sax.Attributes;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Parser {

	
	public Parser() {
	}
	
	public void parse(String xmlDump) {
		SAXParser(xmlDump);
	}
	
	
	private void SAXParser(String page){
		try {
			HashMap<String,String> doc = new HashMap<String,String>();
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();			
			SAXHandler handler = new SAXHandler(doc);
			saxParser.parse(page,handler);
			
	
			
		} catch (SAXException e) {
			System.out.println("SAXException" + e);
		} catch (IOException e) {
			System.out.println("IOException" + e);
		} catch (ParserConfigurationException e) {
			System.out.println("Parser Exception: " + e);
		}
		
		
	}
	
	class SAXHandler extends DefaultHandler {
	
		
		private int ID = 0;
		
		private boolean btitle = false;
		private boolean bId = false;
		private boolean bredirect = false;
		private boolean btext = false;
		private boolean btimestamp = false;
		
		private String id = "id";
		private String title = "title";
		private String url = "url";
		private String text = "text";
		private String timestamp = "timestamp";
		private String iLink = "iLink";
		private String eLink = "eLink";
		
		private String id_value;
		private String title_value;
		private String url_value;
		private String timestamp_value;

		private StringBuffer text_value = new StringBuffer();
		private HashMap<String,String> mapperList;
		
		
		public SAXHandler(HashMap<String,String> hashMap) {
			this.mapperList=hashMap;
		}
		

		public void startElement(String uri, String localName, String qName, Attributes attributes)
	            throws SAXException {
			
			if (qName.equalsIgnoreCase("id")) {
				bId=true;			
			}else if (qName.equalsIgnoreCase("title")) {
				btitle=true;
				ID++;
			}else if (qName.equalsIgnoreCase("text")) {
				btext=true;
			}else if(qName.equalsIgnoreCase("timestamp")) {
				btimestamp=true;
			}else if (qName.equalsIgnoreCase("redirect")) {
				bredirect=true;
			}

			
		}
		public void endElement(String uri, String localName, String qName) throws SAXException {			
			
			if (qName.equalsIgnoreCase("text")) {
				try {
					if (bredirect == false) {
						
						mapperList.put(id, id_value);
						mapperList.put(title, title_value);
						mapperList.put(url, url_value);
						mapperList.put(timestamp, timestamp_value);
	
						ArrayList<String> parsedText = WikiNewsParser.parse(text_value.toString()); 
				
						
						
						mapperList.put(text, parsedText.get(0));
						mapperList.put(iLink, parsedText.get(1));
						mapperList.put(eLink, parsedText.get(2));
	//					mapperList.put(text, text_value.toString());
	
						SolrAdd.addition(mapperList);
	/*
	 * TESTING SYSO CODE BELOW				
	 */
						
	System.out.println(mapperList.get("id") + '\n');
	System.out.println(mapperList.get("timestamp") + '\n');
//	System.out.println(mapperList.get("title") + '\n');
//	System.out.println(mapperList.get("url") + '\n');
	System.out.println(mapperList.get("text") + '\n');
	System.out.println(mapperList.get("eLink") + '\n');					
					}else {
						ID = ID - 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					text_value = new StringBuffer("");
					btext=false;
					bredirect=false;
					btitle=false;
					bId=false;
					btimestamp=false;
				}
			}		
		}
		public void characters(char ch[], int start, int length) throws SAXException {
			if (bId) {
				id_value= Integer.toString(ID);
				bId = false;
			}else if (btitle) {
				title_value=new String(ch, start, length);
				if (!title_value.contains(":")) {
					String temp = title_value.replaceAll(" ", "_");
					url_value = "http://en.wikinews.org/wiki/"+temp;
					btitle = false;
				} else {
					bredirect = true;
				}
			}else if(btimestamp) {
				timestamp_value = new String(ch, start, length);
				timestamp_value=timestamp_value.split("T")[0];
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = formatter.parse(timestamp_value);
					String[] arr = timestamp_value.split("-");
					timestamp_value = arr[0] + "-" + new DateFormatSymbols().getMonths()[date.getMonth()] + "-" + arr[2];
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				btimestamp = false;
			}else if (btext) {
				
				text_value=text_value.append(ch,start,length);
				
			}
			
		}
	}

}
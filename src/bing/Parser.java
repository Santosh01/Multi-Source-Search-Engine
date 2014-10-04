package bing;

import java.io.IOException;

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
		private boolean biLink = false;

		private String id = "id";
		private String title = "title";
		private String iLink = "iLink";

		private String id_value;
		private String title_value;
		private String iLink_value;

		private HashMap<String,String> mapperList;

		public SAXHandler(HashMap<String,String> hashMap) {
			this.mapperList=hashMap;
		}

	public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {

			if (qName.equalsIgnoreCase("id")) {
				bId=true;
				//ID++;
			}else if (qName.equalsIgnoreCase("title")) {
				btitle=true;
				ID++;
			}else if (qName.equalsIgnoreCase("iLink")) {
				biLink=true;
				ID++;
			}else if (qName.equalsIgnoreCase("redirect")) {
				bredirect=true;
			}
	}
		public void endElement(String uri, String localName, String qName) throws SAXException {			

			if (qName.equalsIgnoreCase("title") || qName.equalsIgnoreCase("iLink") || qName.equalsIgnoreCase("id")  ) {
				try {
					if (bredirect == false) {

						mapperList.put(id, id_value);
						mapperList.put(title, title_value);
						mapperList.put(iLink, iLink_value);

						SolrAdd.addition(mapperList);

						System.out.println(mapperList.get("id") + '\n');
						if(mapperList.get("title") != null){
							System.out.println(mapperList.get("title") + '\n');
						} if(mapperList.get("iLink") != null)
						{
							System.out.println(mapperList.get("iLink") + '\n');
						}
				}else {
						ID = ID-1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					bredirect=false;
					btitle=false;
					bId=false;
					biLink=false;
				}
			}		
		}
		public void characters(char ch[], int start, int length) throws SAXException {
			if (bId) {
				id_value= Integer.toString(ID);
				bId = false;
			}else if (btitle) {
				String temp=new String(ch, start, length);
				if(temp != null)
					title_value = temp;
					btitle = false;
			}else if(biLink) {
				String temp = new String(ch, start, length);
				if(temp != null)
					iLink_value = temp;
					biLink = false;
			}

		}
	}
}
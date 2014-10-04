package wikivoyage;

/**
 * 
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/*
 * XML parser for WikiVoyage
 * @ author 
 */


public class Parser {

	
	public Parser() {
	}
	
	public void parse(String xmlDump) {
		/*
		BufferedReader reader = null;
		try{
			File file = new File(this.xmlDump);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			
			String line, page=null;
			while ((line = reader.readLine()) != null){
				if (!line.contains("</page>")){
					page = page + line;
				}
				else{
					SAXParser(page);
					page=null;
					System.out.println("Ok!");
					
				}
			}
			reader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}*/
		SAXParser(xmlDump);
	}
	
	
	private void SAXParser(String page){
		try {
			HashMap<String,String> doc = new HashMap<String,String>();
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = saxParserFactory.newSAXParser();			
			SAXHandler handler = new SAXHandler(doc);
			/*
			ByteArrayInputStream stream = new ByteArrayInputStream(page.getBytes());
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
*/
			
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
		
		private String id = "id";
		private String title = "title";
		private String url = "url";
		private String text = "text";
		private String iLink = "iLink";
		private String eLink = "eLink";
		
		private String id_value;
		private String title_value;
		private String url_value;

		
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
			}else if (qName.equalsIgnoreCase("redirect")) {
				bredirect=true;
			}

			
		}
		public void endElement(String uri, String localName, String qName) throws SAXException {			
			
			if (qName.equalsIgnoreCase("text")) {
				try {
					if (bredirect == false) {
						if (text_value.length() > 1600) {
						mapperList.put(id, id_value);
						mapperList.put(title, title_value);
						mapperList.put(url, url_value);
	
						ArrayList<String> parsedText = WikiVoyageParser.parse(text_value.toString()); 
				
						
						
						mapperList.put(text, parsedText.get(0));
						mapperList.put(iLink, parsedText.get(1));
						mapperList.put(eLink, parsedText.get(2));
	
	
						SolrAdd.addition(mapperList);
	/*
	 * TESTING SYSO CODE BELOW				
	 */
						
	System.out.println(mapperList.get("id") + '\n');
	System.out.println(mapperList.get("title") + '\n');
	System.out.println(mapperList.get("url") + '\n');
	System.out.println(mapperList.get("iLink") + '\n');
	System.out.println(mapperList.get("text") + '\n');
						} else { ID = ID - 1; }
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
					url_value = "http://en.wikivoyage.org/wiki/"+temp;
					btitle = false;
				} else {
					bredirect = true;
				}
			}else if (btext) {
				text_value=text_value.append(ch,start,length);
				
			}
			
		}
	}

}
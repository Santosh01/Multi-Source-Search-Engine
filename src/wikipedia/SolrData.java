package wikipedia;



import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;



public class SolrData{
	
	static int counter;
	
	public static void addDocument(String title, String URL,String text, String iLink, String eLink)
	{
		String id = ++counter + "";
		String url = "http://localhost:8080/solr/Wikipedia";
		SolrServer solr = new HttpSolrServer(url);
		SolrInputDocument doc = new SolrInputDocument();
		
		doc.addField("id",id);
		doc.addField("title",title, 20);
		doc.addField("url", URL);
		doc.addField("text", text, 5);
		doc.addField("iLink", iLink);
		doc.addField("eLink", eLink);
		   try {
			    solr.add(doc);
				solr.commit();
				
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		}
	}

package bing;


import java.io.IOException;
import java.util.HashMap;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;



public class SolrAdd {

	public SolrAdd() {}
	
	public static void addition(HashMap<String,String> solrdoc){
		
		String urlString = "http://localhost:8080/solr/bing";
		SolrServer solr = new HttpSolrServer(urlString);
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", solrdoc.get("id"));
	//	document.addField("url", solrdoc.get("url"));
		
		//document.addField("timestamp", solrdoc.get("timestamp")); // new
		
		document.addField("title", solrdoc.get("title"), 20.0f);
		//document.addField("text", solrdoc.get("text"), 5.0f);
		document.addField("iLink", solrdoc.get("iLink"));
	//	document.addField("eLink", solrdoc.get("eLink"));
		try {
			solr.add(document);
			solr.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

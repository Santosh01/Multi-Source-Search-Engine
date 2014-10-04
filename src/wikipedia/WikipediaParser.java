package wikipedia;



import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikipediaParser {
	

public static String parseTitle(String title)
{
    return title.trim();
}	
public static StringBuilder parseURL(StringBuilder URL)
{
	String first = new String();
	String second = new String();
	if(URL.toString().contains(":"))
	{
	first = URL.toString().split(":")[1];
	}
	else
	{
	first = URL.toString();
	}
	
	if(first.contains("/"))
	{
		second = first.split("/")[0];	
	}
	else
	{
		second = first;
	}
	
	int length = second.trim().split(" ").length;
	String URLComponent[] = new String[length];
	StringBuilder URLComponents = new StringBuilder();
    URLComponent = second.trim().split(" ");
    for(int i=0;i<length;i++)
    {
    	URLComponents = URLComponents.append(URLComponent[i] + "_");
    }
    return new StringBuilder("http://en.wikipedia.org/wiki/" + URLComponents.toString().substring(0, URLComponents.length()-1));

}
public static StringBuilder parseText(StringBuilder text)
{

	Pattern stylesPattern = Pattern.compile("\\{\\|.*?\\|\\}$", Pattern.MULTILINE | Pattern.DOTALL);
    Pattern refCleanupPattern = Pattern.compile("<ref>.*?</ref>", Pattern.MULTILINE | Pattern.DOTALL);
    Pattern commentsCleanupPattern = Pattern.compile("<!--.*?-->", Pattern.MULTILINE | Pattern.DOTALL);
    String wikiText = text.toString().replaceAll("&gt;", ">");
	wikiText = wikiText.replaceAll("&lt;", "<");
	wikiText = wikiText.replaceAll("&lt;", "<");
	wikiText = commentsCleanupPattern.matcher(wikiText).replaceAll(" ");
	wikiText = stylesPattern.matcher(wikiText).replaceAll(" ");
	wikiText = refCleanupPattern.matcher(wikiText).replaceAll(" ");
	wikiText = wikiText.replaceAll("</?.*?>", " ");
    wikiText = wikiText.replaceAll("'{2,}", "");
    
    
    StringBuilder temp = new StringBuilder();

    int len = wikiText.length();
	
	for(int i=0;i<=len-1;i++)
	{					
	    if(wikiText.charAt(i) == '{')
	    {
	    	if(wikiText.charAt(i+1) == '{')
	    	{
	    		i=i+2;
	    		int counter = 1;
	    		boolean flag = false;
	    		for(int j=i;(j<=len-1);j++)
	    		{
	    			if(wikiText.charAt(j)== '{')
	    			{
	    				if(wikiText.charAt(j+1) == '{')
	    					{counter++;flag= true;j=j+2;}
	    			}
	    			if(wikiText.charAt(j) == '}')
	    			{ 
	    				if(wikiText.charAt(j+1) == '}')
	    				{
	    				j++;
	    				i=j;
	    				if(counter == 1)
	    			    	{break;}
	    				if(flag == true)
	    				{counter --;}
	    				}
	    			}
	    		}
	    	}
	    	else {}
	    }
	    else
	    {
	    	temp.append(wikiText.charAt(i));
	    	if((i+1) == len-1 && wikiText.charAt(i+1)!= '}')
	    	{
	    		temp.append(wikiText.charAt(i+1));
	    	}
	    }
	    
}	
	return new StringBuilder(temp.toString().trim().replaceAll("-", ""));
}

public static StringBuilder parseLinksText(String text) {
	
	//remove internal links
	StringBuffer sb1 = new StringBuffer();
	StringBuffer sb2 = new StringBuffer();
	String pattern1 = "(\\[\\[)([\\w+\\s+^\\[\\-\\,\\(\\)\\'\\.\\!\"]+)(\\|)([\\w+\\s+^\\[\\-\\,\\(\\)\\'\\.\\!\"]+)(\\]\\])";
	Pattern p1 = Pattern.compile(pattern1);
	Matcher m1 = p1.matcher(text);
	while(m1.find())
	{
		m1.appendReplacement(sb1, m1.group(4));			
	}
	m1.appendTail(sb1);
	String pattern2 = "(\\[\\[)([\\w+\\s+\\,\\-\\'\"^\\|^\\;\\.]+)(\\]\\])";
	Pattern p2 = Pattern.compile(pattern2);
	Matcher m2 = p2.matcher(sb1);
	while(m2.find())
	{
		m2.appendReplacement(sb2, m2.group(2));			
	}
	m2.appendTail(sb2);
	

	String pattern3 = "(\\[\\[)Category:(.*)(\\]\\])";
	StringBuffer sb3 = new StringBuffer(sb2.toString().replaceAll(pattern3, "").trim());
	

	String parsePattern = "\\s={2,}|={2,}\\s";
	sb3 = new StringBuffer(sb3.toString().replaceAll(parsePattern, " ").trim());
	

	sb3 = new StringBuffer(sb3.toString().replaceAll("\\#{1,}\\s", ""));
	sb3 = new StringBuffer(sb3.toString().replaceAll("\\*{1,}\\s", ""));
	sb3 = new StringBuffer(sb3.toString().replaceAll("\\:{1,}\\s", ""));
	
	String pattern4 = "(\\[)(http:)(.*)(\\])";
	
	return new StringBuilder(sb3.toString().replaceAll(pattern4, "").trim());
}
public static StringBuilder parseILinks(String text)
{
	StringBuffer sb = new StringBuffer();
	Set<String> links = new HashSet<String>();
	if(text == null)
	{
		links.add("");
	}
	else if(text =="")
	{
	  links.add("");	
	}
	else
	{
	String pattern1 = "(\\[\\[)([\\w+\\s+^\\[\\-\\'\\.\\!\"]+)(\\|)([\\w+\\s+^\\[\\-\\,\\(\\)\\'\\.\\!\"]+)(\\]\\])";
	
	Pattern p11 = Pattern.compile(pattern1);
	Matcher m11 = p11.matcher(text);
	
	String pattern2 = "(\\[\\[)([\\w+\\s+^\\[\\-\\'\\.\\!\"]+)(\\,)([\\w+\\s+^\\[\\-\\'\\.\\!\"]+)(\\|)([\\w+\\s+^\\[\\-\\,\\(\\)\\'\\.\\!\"]+)(\\]\\])";
	
	Pattern p22 = Pattern.compile(pattern2);
	Matcher m22 = p22.matcher(text);
	
	String pattern3 = "(\\[\\[)([\\w+\\s+^\\[\\-\\'\\.\\!\"]+)(\\()([\\w+\\s+^\\[\\-\\'\\.\\!\"]+)(\\))(\\|)([\\w+\\s+^\\[\\-\\,\\(\\)\\'\\.\\!\"]+)(\\]\\])";
	
	Pattern p33 = Pattern.compile(pattern3);
	Matcher m33 = p33.matcher(text);
	
    StringBuffer sb1 = new StringBuffer();
    
    String pattern4 = "(\\[\\[)([\\w+\\s+^\\[\\-\\,\\(\\)\\'\\.\\!\"]+)(\\|)([\\w+\\s+^\\[\\-\\,\\(\\)\\'\\.\\!\"]+)(\\]\\])";
    Pattern p4 = Pattern.compile(pattern4);
    Matcher m4 = p4.matcher(text);
    while(m4.find())
    {
    	m4.appendReplacement(sb1, m4.group(4));			
    }
    m4.appendTail(sb1);
    String pattern5 = "(\\[\\[)([\\w+\\s+\\,\\-\\'\"^\\|^\\;\\.]+)(\\]\\])";
    
    Pattern p55 = Pattern.compile(pattern5);
    Matcher m55 = p55.matcher(sb1);
	
	while(m11.find())
	{
	    links.add(m11.group(2).replace(" ", "_").substring(0, 1).toUpperCase() + m11.group(2).replace(" ", "_").substring(1));
	}
	while(m22.find())
	{
		links.add((m22.group(2) + m22.group(3) + "_" + (m22.group(4)).trim().replace(" ", "_")).substring(0, 1).toUpperCase() + (m22.group(2) + m22.group(3) + "_" + (m22.group(4)).trim().replace(" ", "_")).substring(1));
	}
	while(m33.find())
	{
		links.add((m33.group(2).trim() + "_" + m33.group(3) + m33.group(4) + m33.group(5)).replace(" ", "_").substring(0, 1).toUpperCase() + (m33.group(2).trim() + "_" + m33.group(3) + m33.group(4) + m33.group(5)).replace(" ", "_").substring(1));
	}
	while(m55.find())
	{
		links.add(m55.group(2).replace(" ", "_").substring(0, 1).toUpperCase() + m55.group(2).replace(" ", "_").substring(1));
	}
	
	}
	
	int size = links.size();
	StringBuilder link= new StringBuilder();
	Iterator it = links.iterator();
	int count =0;
	while(it.hasNext())
	{
		link = link.append("http://en.wikipedia.org/wiki/" + it.next() + ",");
		count++;
	}
   return link;
	
}
public static StringBuilder parseELinks(String text)
{
	String pattern = "(\\[)(http:)(.*)(\\])";
	Pattern p = Pattern.compile(pattern);
	Matcher m = p.matcher(text);
	StringBuilder eLink = new StringBuilder();
	while(m.find())
	{
		eLink = eLink.append(m.group(2) + m.group(3).split(" ")[0] + ",");
	}
	return eLink;
}
}
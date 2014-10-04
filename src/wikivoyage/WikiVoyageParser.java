package wikivoyage;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class WikiVoyageParser {
	/* TODO */
	/**
	 * Method to parse section titles or headings.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Sections
	 * @param titleStr: The string to be parsed
	 * @return The parsed string with the markup removed
	 */
	public static ArrayList<String> parse(String titleStr) {
	
			ArrayList<String> text_links = new ArrayList<String>();			
			titleStr=Pattern.compile("[=]+[ ]*([a-zA-z0-9\\- ]+)[ ]*[=]+").matcher(titleStr).replaceAll("$1").trim();
			text_links.add(parseListItem(titleStr));
			String iLinks = parseInternalLinks(titleStr);
			text_links.add(iLinks);
			String eLinks = parseExternalLinks(titleStr);
			text_links.add(eLinks);
			return text_links;
 
	}
	
	/* TODO */
	/**
	 * Method to parse list items (ordered, unordered and definition lists).
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Lists
	 * @param itemText: The string to be parsed
	 * @return The parsed string with markup removed
	 */
	public static String parseListItem(String itemText) {
		
		
			itemText=Pattern.compile("\\*").matcher(itemText).replaceAll("").trim();
			return parseTextFormatting(itemText);
	}
	
	/* TODO */
	/**
	 * Method to parse text formatting: bold and italics.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Text_formatting first point
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTextFormatting(String boldText) {
			boldText=Pattern.compile("'*").matcher(boldText).replaceAll("").trim();
			boldText=Pattern.compile("-").matcher(boldText).replaceAll("");
			return parseTagFormatting(boldText);
		
	}
	
	/* TODO */
	/**
	 * Method to parse *any* HTML style tags like: <xyz ...> </xyz>
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed.
	 */
	public static String parseTagFormatting(String htmlText) {
	
			htmlText = Pattern.compile("&lt;[/a-zA-Z0-9'= ]*&gt;").matcher(htmlText).replaceAll("").trim();
			htmlText = Pattern.compile("&mdash;").matcher(htmlText).replaceAll("").trim();
			
			return parseTemplates(htmlText);
	}
	
	/* TODO */
	/**
	 * Method to parse wikipedia templates. These are *any* {{xyz}} tags
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTemplates(String textTemplate) {
		
		//Pattern pattern=Pattern.compile("\\{\\{[-|=\\[\\],\\w\\s(\\{\\{\\})\\)\\(<>!\\.'\"%&\\+]*\\}\\}");
		
		/*
		textTemplate=Pattern.compile("\\{((?:\\{(?-1)\\}|[^\\{\\}]++)*)\\}").matcher(textTemplate).replaceAll("");
		*/
		textTemplate=Pattern.compile("\\{\\{.*").matcher(textTemplate).replaceAll("");
		textTemplate=Pattern.compile("\\}\\}").matcher(textTemplate).replaceAll("");
		//textTemplate=Pattern.compile("\\|[^=]*=").matcher(textTemplate).replaceAll("");
		//textTemplate=Pattern.compile("[^=]*=(.*?)\\|",Pattern.MULTILINE | Pattern.DOTALL).matcher(textTemplate).replaceAll("$1");
		//textTemplate=Pattern.compile("\\{\\{([^\\}]|\\n)*\\}\\}").matcher(textTemplate).replaceAll("");
		//textTemplate=Pattern.compile("^\\|[^\\}]*\\n").matcher(textTemplate).replaceAll("");
		//textTemplate=Pattern.compile("\\}\\}").matcher(textTemplate).replaceAll("");
		textTemplate=Pattern.compile("\\{+.*\\}+").matcher(textTemplate).replaceAll("");
		textTemplate=Pattern.compile("\\n").matcher(textTemplate).replaceAll(" ");
		textTemplate=Pattern.compile("\\| .*?=").matcher(textTemplate).replaceAll(" ");
		return linksText(textTemplate);
		
	}
	
	public static String linksText(String linksText) {
		linksText = Pattern.compile("\\[http\\S+ ([A-Za-z0-9 ]+)\\]").matcher(linksText).replaceAll(" $1 ");
		linksText = Pattern.compile("\\[\\[([^:\\|]*?)\\]\\]").matcher(linksText).replaceAll("$1");
	//	linksText = Pattern.compile("\\[\\[[^\\|]*?\\|(.*?)\\]\\]").matcher(linksText).replaceAll(" $1 ");
	//	linksText = Pattern.compile("\\[\\[[^\\|]*?\\|((?:(?!\\|).)*)\\]\\]").matcher(linksText).replaceAll(" $1 ");
		
		return linksRemove(linksText);
		
	}
	
	public static String linksRemove(String linksRemove) {
		linksRemove = Pattern.compile("\\[\\[[wW]ikipedia:(.*?)\\]\\]").matcher(linksRemove).replaceAll(" $1 ");
		linksRemove = Pattern.compile("\\[+.*?\\]+").matcher(linksRemove).replaceAll(" ");
		linksRemove = Pattern.compile("[^0-9a-zA-Z]+").matcher(linksRemove).replaceAll(" ");
		linksRemove = Pattern.compile("[ ]+").matcher(linksRemove).replaceAll(" ");
		return linksRemove;
	}
	/* TODO */
	/**
	 * Method to parse links and URLs.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Links_and_URLs
	 * @param text: The text to be parsed
	 * @return An array containing two elements as follows - 
	 *  The 0th element is the parsed text as visible to the user on the page
	 *  The 1st element is the link url
	 */
	
	public static String parseInternalLinks(String internalLinks) {
		
		String interLinks = new String();
		internalLinks = Pattern.compile("\\[\\[[wW]ikipedia:(.*?)\\]\\]").matcher(internalLinks).replaceAll("[[$1]]");
		Pattern pattern = Pattern.compile("\\[\\[([^:]*?)\\]\\]");
		Matcher matcher = pattern.matcher(internalLinks);
		while (matcher.find()) //master while till matches keep coming
		{			
				interLinks += matcher.group(1)+",";
		}
		interLinks = Pattern.compile("\\|[^,]*?,").matcher(interLinks).replaceAll("");
		
		if (interLinks.length() > 0) {
			interLinks = interLinks.substring(0, interLinks.length()-1);
			interLinks = interLinks.replaceAll(" ","_");
			return interLinks;
		} else return "";
	}
	
	
	public static String parseExternalLinks(String externalLinks) {
		
		String externLinks = new String();
		Pattern pattern = Pattern.compile("(http[^\\s]+? )");
		Matcher matcher = pattern.matcher(externalLinks);
		while (matcher.find())
		{			
				externLinks += matcher.group(1)+",";
		}
		if (externLinks.length() > 0) {
			externLinks = externLinks.substring(0, externLinks.length()-1);
			return externLinks;
		}
		else return "";
	}
}


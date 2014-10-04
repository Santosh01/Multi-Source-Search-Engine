package wikipedia;



import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class WikipediaRunner {

	/**
	 * @param args
	 */
	public static void starter() {
		String path, XMLDump = null;
		Properties properties = new Properties();
		try{
			properties.load(new FileInputStream("files/properties.config"));
			path = properties.getProperty("root.dir");
			XMLDump = properties.getProperty("pedia.dump.filename");
			XMLDump = path + XMLDump;
			System.out.println("dump:"+XMLDump);
		}catch (IOException e){
			System.err.println("XML dump is invalid or doesn't exist!!");
		}
		if (XMLDump == null){
			System.err.println("No XML dump specified!");
		}
			
		Parser parser = new Parser();
			
		parser.parse(XMLDump);
		
	}
}
		
	
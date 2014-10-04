package launch;

//import Bing.BingRunner;
import wikinews.WikiNewsRunner;
import wikipedia.WikipediaRunner;
import wikivoyage.WikiVoyageRunner;
import bing.bingRunner;


public class IndexSolr {

	public static void main(String[] args) {
		
		/*bingRunner.starter();
		System.out.println("Bing Indexed.\n");
		*/

		WikiNewsRunner.starter();
		System.out.println("WikiNews Indexed.\n");
		
		
		/*WikiVoyageRunner.starter();
		System.out.println("WikiVoyage Indexed.\n");
		
		WikipediaRunner.starter();
		System.out.println("Wikipedia Indexed.\n");*/
		
		
		System.out.println("Done!");
	}
	
	
	
}

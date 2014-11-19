package swedish;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.*;

public class Calculator {
	
	static boolean verbose = true;

	public static void main(String[] args){

		try{

			File fXmlFile = new File("res/he.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			ArrayList<Rule> rules = prepareRules();
			
			for(Rule rule : rules){
				System.out.println(rule.calculate(doc));
			}			
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	private static ArrayList<Rule> prepareRules() throws IOException {
		
		

			ArrayList<Rule> rules = new ArrayList<Rule>();
			
			HashMap<Range,String> rangesToPoints = new HashMap<Range,String>();
			
			byte[] encoded = Files.readAllBytes(Paths.get("res/he_swedish.json"));
			String jsonFileContents = new String(encoded, Charset.defaultCharset());
			
			JSONObject obj = new JSONObject(jsonFileContents);
			
			
			String unitName = obj.getString("name");
			
			JSONArray arr = obj.getJSONArray("costs");

			for (int i = 0; i < arr.length(); i++)
			{
			    int rangeLow = arr.getJSONObject(i).getInt("low");
			    int rangeHigh = arr.getJSONObject(i).getInt("high");
					
			    String cost = arr.getJSONObject(i).getString("cost");
			    
			    rangesToPoints.put(new Range(rangeLow,rangeHigh), cost);
			    
			    //System.out.printf("%d %d %s\n",rangeLow,rangeHigh,cost);
			}
			
			Rule r = new SimpleUnitRule(unitName,rangesToPoints);
			
			rules.add(r);
			
			return rules;
		
		
	}

}

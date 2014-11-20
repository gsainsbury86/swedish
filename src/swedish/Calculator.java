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

			int totalCost = 0;

			for(Rule rule : rules){
				totalCost += rule.calculate(doc);
			}

			System.out.println("Score: " + (300+(0.0+totalCost))/10.0);

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	// http://www.jsoneditoronline.org
	private static ArrayList<Rule> prepareRules() throws IOException {

		ArrayList<Rule> rules = new ArrayList<Rule>();

		byte[] encoded = Files.readAllBytes(Paths.get("res/he_swedish.json"));
		String jsonFileContents = new String(encoded, Charset.defaultCharset());

		JSONObject obj = new JSONObject(jsonFileContents);

		rules.addAll(prepareSimpleUnitRules(obj));

		rules.addAll(prepareDuplicateRules(obj));

		rules.addAll(prepareSimpleSingleModelRules(obj));
		
		rules.addAll(prepareSimpleItemRules(obj));

		return rules;

	}

	public static ArrayList<Rule> prepareSimpleUnitRules(JSONObject obj){

		ArrayList<Rule> rules = new ArrayList<Rule>();

		JSONArray simple = obj.getJSONArray("simple_unit");

		for (int j = 0; j < simple.length(); j++){

			JSONObject simpleObj = simple.getJSONObject(j);

			String unitName = simpleObj.getString("name");

			JSONArray arr = simpleObj.getJSONArray("costs");

			HashMap<Range,String> rangesToPoints = new HashMap<Range,String>();

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

		}

		return rules;
	}



	public static ArrayList<Rule> prepareDuplicateRules(JSONObject obj){


		ArrayList<Rule> rules = new ArrayList<Rule>();


		JSONArray duplicate = obj.getJSONArray("duplicate");

		for (int j = 0; j < duplicate.length(); j++){

			JSONObject duplicateObj = duplicate.getJSONObject(j);

			String unitName = duplicateObj.getString("name");

			JSONArray arr = duplicateObj.getJSONArray("costs");

			HashMap<Range,String> rangesToPoints = new HashMap<Range,String>();

			for (int i = 0; i < arr.length(); i++)
			{
				int rangeLow = arr.getJSONObject(i).getInt("low");
				int rangeHigh = arr.getJSONObject(i).getInt("high");

				String cost = arr.getJSONObject(i).getString("cost");

				rangesToPoints.put(new Range(rangeLow,rangeHigh), cost);

				//System.out.printf("%d %d %s\n",rangeLow,rangeHigh,cost);
			}

			Rule r = new DuplicateRule(unitName,rangesToPoints);

			rules.add(r);

		}

		return rules;
	}


	public static ArrayList<Rule> prepareSimpleSingleModelRules(JSONObject obj){


		ArrayList<Rule> rules = new ArrayList<Rule>();

		JSONArray simpleModel = obj.getJSONArray("simple_single_model");

		for (int j = 0; j < simpleModel.length(); j++){

			JSONObject simpleModeleObj = simpleModel.getJSONObject(j);

			String unitName = simpleModeleObj.getString("name");

			int cost = simpleModeleObj.getInt("cost");

			Rule r = new SimpleSingleModelRule(unitName,cost);

			rules.add(r);

		}

		return rules;
	}
	
	public static ArrayList<Rule> prepareSimpleItemRules(JSONObject obj){


		ArrayList<Rule> rules = new ArrayList<Rule>();

		JSONArray simpleModel = obj.getJSONArray("simple_item");

		for (int j = 0; j < simpleModel.length(); j++){

			JSONObject simpleModeleObj = simpleModel.getJSONObject(j);

			String itemName = simpleModeleObj.getString("name");

			int cost = simpleModeleObj.getInt("cost");

			Rule r = new SimpleItemRule(itemName,cost);

			rules.add(r);

		}

		return rules;
	}
	
	public static ArrayList<Rule> prepareSimpleMountRules(JSONObject obj){


		ArrayList<Rule> rules = new ArrayList<Rule>();

		JSONArray simpleModel = obj.getJSONArray("simple_mount");

		for (int j = 0; j < simpleModel.length(); j++){

			JSONObject simpleModeleObj = simpleModel.getJSONObject(j);

			String unitName = simpleModeleObj.getString("name");

			JSONArray arr = simpleModeleObj.getJSONArray("mounts");
			
			HashMap<String,String> mountsToPoints = new HashMap<String,String>();

			for (int i = 0; i < arr.length(); i++)
			{

				String mountName =  arr.getJSONObject(i).getString("mount");
				String cost = arr.getJSONObject(i).getString("cost");

				mountsToPoints.put(mountName, cost);

				//System.out.printf("%d %d %s\n",rangeLow,rangeHigh,cost);
			}



			Rule r = new SimpleMountRule(unitName,mountsToPoints);

			rules.add(r);

		}

		return rules;
	}
	
	
}

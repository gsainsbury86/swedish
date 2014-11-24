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

			File fXmlFile = new File("res/mages.xml");
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
		rules.addAll(prepareSimpleMountRules(obj));
		rules.addAll(prepareSimpleUpgradeRules(obj));
		rules.addAll(prepareCombinationUpgradeRules(obj));

		return rules;
	}

	public static ArrayList<Rule> prepareSimpleUnitRules(JSONObject obj){

		ArrayList<Rule> rules = new ArrayList<Rule>();

		JSONArray array = obj.getJSONArray("simple_unit");

		for (int j = 0; j < array.length(); j++){

			JSONObject simple = array.getJSONObject(j);
			String name = simple.getString("name");
			JSONArray costs = simple.getJSONArray("costs");

			HashMap<Range,String> rangesToCosts = new HashMap<Range,String>();

			for (int i = 0; i < costs.length(); i++)
			{
				int rangeLow = costs.getJSONObject(i).getInt("low");
				int rangeHigh = costs.getJSONObject(i).getInt("high");
				String cost = costs.getJSONObject(i).getString("cost");

				rangesToCosts.put(new Range(rangeLow,rangeHigh), cost);
			}

			rules.add(new SimpleUnitRule(name,rangesToCosts));
		}
		return rules;
	}



	public static ArrayList<Rule> prepareDuplicateRules(JSONObject obj){

		ArrayList<Rule> rules = new ArrayList<Rule>();

		JSONArray array = obj.getJSONArray("duplicate");

		for (int j = 0; j < array.length(); j++){

			JSONObject duplicate = array.getJSONObject(j);
			String name = duplicate.getString("name");
			JSONArray costs = duplicate.getJSONArray("costs");

			HashMap<Range,String> rangesToCosts = new HashMap<Range,String>();

			for (int i = 0; i < costs.length(); i++)
			{
				int rangeLow = costs.getJSONObject(i).getInt("low");
				int rangeHigh = costs.getJSONObject(i).getInt("high");
				String cost = costs.getJSONObject(i).getString("cost");

				rangesToCosts.put(new Range(rangeLow,rangeHigh), cost);
			}

			rules.add(new DuplicateRule(name,rangesToCosts));
		}
		return rules;
	}


	public static ArrayList<Rule> prepareSimpleSingleModelRules(JSONObject obj){

		ArrayList<Rule> rules = new ArrayList<Rule>();

		JSONArray array = obj.getJSONArray("simple_single_model");

		for (int j = 0; j < array.length(); j++){

			JSONObject simpleSingleModel = array.getJSONObject(j);
			String unitName = simpleSingleModel.getString("name");
			int cost = simpleSingleModel.getInt("cost");

			rules.add(new SimpleSingleModelRule(unitName,cost));
		}
		return rules;
	}

	public static ArrayList<Rule> prepareSimpleItemRules(JSONObject obj){

		ArrayList<Rule> rules = new ArrayList<Rule>();

		JSONArray array = obj.getJSONArray("simple_item");

		for (int j = 0; j < array.length(); j++){

			JSONObject simpleItem = array.getJSONObject(j);
			String itemName = simpleItem.getString("name");
			int cost = simpleItem.getInt("cost");

			rules.add(new SimpleItemRule(itemName,cost));
		}
		return rules;
	}

	public static ArrayList<Rule> prepareSimpleMountRules(JSONObject obj){

		ArrayList<Rule> rules = new ArrayList<Rule>();

		JSONArray array = obj.getJSONArray("simple_mount");

		for (int j = 0; j < array.length(); j++){

			JSONObject simpleMount = array.getJSONObject(j);
			String unitName = simpleMount.getString("name");
			JSONArray mounts = simpleMount.getJSONArray("mounts");

			HashMap<String,String> mountsToCosts = new HashMap<String,String>();

			for (int i = 0; i < mounts.length(); i++)
			{
				String mountName =  mounts.getJSONObject(i).getString("name");
				String cost = mounts.getJSONObject(i).getString("cost");

				mountsToCosts.put(mountName, cost);
			}
			rules.add(new SimpleMountRule(unitName,mountsToCosts));
		}
		return rules;
	}


	public static ArrayList<Rule> prepareSimpleUpgradeRules(JSONObject obj){

		ArrayList<Rule> rules = new ArrayList<Rule>();

		JSONArray array = obj.getJSONArray("simple_upgrade");

		for (int j = 0; j < array.length(); j++){

			JSONObject simpleUpgrade = array.getJSONObject(j);
			String unitName = simpleUpgrade.getString("name");
			JSONArray upgrades = simpleUpgrade.getJSONArray("upgrades");

			HashMap<String,String> upgradesToCosts = new HashMap<String,String>();

			for (int i = 0; i < upgrades.length(); i++)
			{
				String mountName =  upgrades.getJSONObject(i).getString("name");
				String cost = upgrades.getJSONObject(i).getString("cost");

				upgradesToCosts.put(mountName, cost);
			}
			rules.add(new SimpleUpgradeRule(unitName,upgradesToCosts));
		}
		return rules;
	}	

	public static ArrayList<Rule> prepareCombinationUpgradeRules(JSONObject obj){

		ArrayList<Rule> rules = new ArrayList<Rule>();

		JSONArray array = obj.getJSONArray("combination_upgrade");

		for (int j = 0; j < array.length(); j++){

			JSONObject combination = array.getJSONObject(j);
			String unitName = combination.getString("name");
			String cost = combination.getString("cost");

			JSONArray upgrades = combination.getJSONArray("upgrades");

			ArrayList<String> upgradeList = new ArrayList<String> ();

			for (int i = 0; i < upgrades.length(); i++)
			{
				String upgrade =  upgrades.getString(i);

				upgradeList.add(upgrade);
			}
			rules.add(new CombinationUpgradeRule(unitName, cost, upgradeList));
		}
		return rules;
	}
}

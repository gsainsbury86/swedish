package swedish.rules;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import swedish.Calculator;

public class CombinationExclusionRule implements Rule {


	private String ruleName;
	private HashMap<String, String> namesAndTypes;
	private String cost;

	public CombinationExclusionRule(String ruleName, HashMap<String, String> namesAndTypes, String cost
			) {
		super();
		this.namesAndTypes = namesAndTypes;
		this.ruleName = ruleName;
		this.cost = cost;
	}


	public int calculate(Document doc) {

		NodeList nList = doc.getElementsByTagName("squad");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				for(String name : namesAndTypes.keySet()){

					if(eElement.getAttribute("name").equals(name) && namesAndTypes.get(name).equals("unit")){
						return 0;
					}

					NodeList unitList = eElement.getElementsByTagName("link");

					for(int j = 0; j < unitList.getLength(); j++){

						Node nLinkNode = unitList.item(j);
						Element eLinkElement = (Element) nLinkNode;

						if(eLinkElement.getAttribute("name").equals(name) && namesAndTypes.get(name).equals("item")){
							return 0;
						}

					}

				}
			}

		}
		
		if(Calculator.verbose){
			System.out.println(this.ruleName + " = " + cost);
		}		
		return Integer.parseInt(cost);

	}

}

package swedish.rules;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import swedish.Calculator;

public class SimpleUpgradeRule implements Rule {

	private String unitName;
	private HashMap<String, String> upgrades;

	public SimpleUpgradeRule(String unitName, HashMap<String, String> mountsToPoints
			) {
		super();
		this.upgrades = mountsToPoints;
		this.unitName = unitName;
	}

	public int calculate(Document doc) {

		int totalPoints = 0;

		NodeList nList = doc.getElementsByTagName("squad");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				if(eElement.getAttribute("name").equals(unitName)){

					NodeList unitList = eElement.getElementsByTagName("link");

					for(int j = 0; j < unitList.getLength(); j++){

						Node nLinkNode = unitList.item(j);
						Element eLinkElement = (Element) nLinkNode;

						//if(eLinkElement.getAttribute("category").equals("Mount")){

							for(String upgrade : upgrades.keySet()){
								
								if(eLinkElement.getAttribute("name").equals(upgrade)){

									int thisPoints = Integer.parseInt(upgrades.get(upgrade));
									totalPoints+=thisPoints;
									if(Calculator.verbose){
										System.out.println(this.unitName + " upgraded with " + upgrade + " = " + thisPoints);
									}
								}
							}
						//}
					}
				}
			}
		}

		return totalPoints;
	}



}

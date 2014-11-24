package swedish;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CombinationUpgradeRule implements Rule {

	private String unitName;
	private ArrayList<String> upgradeList;
	private String cost;

	public CombinationUpgradeRule(String unitName, String cost, ArrayList<String> upgradeList
			) {
		super();
		this.upgradeList = upgradeList;
		this.unitName = unitName;
		this.cost = cost;
	}

	public int calculate(Document doc) {

		int presenceCount = 0;

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

						for(String mountName : upgradeList){

							if(eLinkElement.getAttribute("name").equals(mountName)){
								presenceCount++;
							}
						}
					}
				}
			}
		}
		
		if(presenceCount == upgradeList.size()){
			if(Calculator.verbose){
				System.out.println(this.unitName + ": " + this.unitName + " upgrade " + upgradeList + " = " + cost);
			}

			return Integer.parseInt(cost);
		}
		return 0;

	}
}
package swedish.rules;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import swedish.Calculator;

public class SimpleItemRule implements Rule{


	private String itemName;
	private int points;

	public SimpleItemRule(String unitName, int points
			) {
		super();
		this.points = points;
		this.itemName = unitName;
	}

	public int calculate(Document doc) {

		int totalPoints = 0;

		NodeList nList = doc.getElementsByTagName("item");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				if(eElement.getAttribute("name").equals(itemName)){

					if(Calculator.verbose){
						System.out.println(this.itemName + " = " + points);
					}

					totalPoints +=points;

				}
			}

		}
		return totalPoints;
	}


}

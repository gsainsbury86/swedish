package swedish.rules;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import swedish.Calculator;

public class SimpleSingleModelRule implements Rule{


	private String unitName;
	private int points;

	public SimpleSingleModelRule(String unitName, int points
			) {
		super();
		this.points = points;
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

					if(Calculator.verbose){
						System.out.println(this.unitName + " = " + points);
					}

					totalPoints +=points;

				}
			}

		}
		return totalPoints;
	}


}

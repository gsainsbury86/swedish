package swedish;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DuplicateRule implements Rule {

	private String unitName;
	private HashMap<Range, String> rangesToPoints;

	public DuplicateRule(String unitName, HashMap<Range, String> rangesToPoints
			) {
		super();
		this.rangesToPoints = rangesToPoints;
		this.unitName = unitName;
	}

	public int calculate(Document doc) {

		int count = 0;
		int totalPoints = 0;

		NodeList nList = doc.getElementsByTagName("squad");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				if(eElement.getAttribute("name").equals(unitName)){
					count++;

					for(Range r: rangesToPoints.keySet()){
						if(r.contains(count)){

							int unitPoints = Integer.parseInt(rangesToPoints.get(r));

							if(Calculator.verbose){
								System.out.println(this.unitName + ": " + count + " " + this.unitName + " = " + unitPoints);
							}
							
							totalPoints +=unitPoints;

						}
					}

				}
			}
		}
		return totalPoints;

	}

}

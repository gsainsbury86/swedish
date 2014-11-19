package swedish;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SimpleUnitRule implements Rule {

	private String unitName;
	private HashMap<Range, String> rangesToPoints;

	public SimpleUnitRule(String unitName, HashMap<Range, String> rangesToPoints
			) {
		super();
		this.rangesToPoints = rangesToPoints;
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
					for(Range r: rangesToPoints.keySet()){
						//TODO: Change to unit/@count because models doesn't work for mounted characters
						int modelCount = Integer.parseInt(eElement.getAttribute("models"));
						if(r.contains(modelCount)){
							MathEval math = new MathEval();
							math.setVariable("x",modelCount);

							int thisPoints = (int) math.evaluate(rangesToPoints.get(r));
							totalPoints+=thisPoints;
							if(Calculator.verbose){
								System.out.println(this.unitName + ": " + modelCount + " " + this.unitName + " = " + thisPoints);
							}
						}
					}
				}
			}
		}

		return totalPoints;
	}



}

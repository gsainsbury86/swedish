package swedish;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SimpleMountRule implements Rule {

	private String unitName;
	private HashMap<String, String> mountsToPoints;

	public SimpleMountRule(String unitName, HashMap<String, String> mountsToPoints
			) {
		super();
		this.mountsToPoints = mountsToPoints;
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
					
					//TODO: Finish
//					for(Range r: mountsToPoints.keySet()){
//
//						NodeList unitList = eElement.getElementsByTagName("unit");
//
//						for(int j = 0; j < unitList.getLength(); j++){
//
//							Node nUnitNode = unitList.item(j);
//							Element eUnitElement = (Element) nUnitNode;
//
//							if(nUnitNode.getParentNode().getNodeName().equals("squad")){
//
//								int modelCount = Integer.parseInt(eUnitElement.getAttribute("count"));
//								if(r.contains(modelCount)){
//									MathEval math = new MathEval();
//									math.setVariable("x",modelCount);
//
//									int thisPoints = (int) math.evaluate(mountsToPoints.get(r));
//									totalPoints+=thisPoints;
//									if(Calculator.verbose){
//										System.out.println(this.unitName + ": " + modelCount + " " + this.unitName + " = " + thisPoints);
//									}
//								}
//							}
//						}
//					}
				}
			}
		}

		return totalPoints;
	}



}

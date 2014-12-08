/**
 * 
 */

/**
 * @author Ryan
 *
 */
public class SearchNode {
	int number;
	int parentNumber;
	int totalCost;
	int estimatedGoalCost;
	String DiseaseName;
	String childNodes;// Separated by spaces
	String[] unsatisfiedSymptom;// = new String[6];
	String[] satisfiedSymptom;// = new String[6];
	String Symptom;
	
	public SearchNode(int num, int pnum, String unsat, int currCost, int estimate, String symp, String Dis){
		number = num;
		parentNumber = pnum;
		unsatisfiedSymptom = unsat.split(" ");
		totalCost = currCost;
		estimatedGoalCost = estimate;
		Symptom = symp;
		DiseaseName = Dis;
		System.out.println("Created Node: " + number + " with disease: " + Dis + " with symptom: " + symp + " with parent: " + parentNumber + " with current cost: " + totalCost + " and unsatisfied Symptoms: " + unsat);
	}
		
}

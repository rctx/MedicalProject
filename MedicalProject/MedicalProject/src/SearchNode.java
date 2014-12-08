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
	String unsatStr;
	String Symptom;
	int fn;
	
	public SearchNode(int num, int pnum, String unsat, int currCost, int estimate, String symp, String Dis){
		number = num;
		parentNumber = pnum;
		unsatStr = unsat;
		unsatisfiedSymptom = unsat.split(" ");
		totalCost = currCost;
		estimatedGoalCost = estimate;
		Symptom = symp;
		DiseaseName = Dis;
		//fn = 
		System.out.println("  Created " + this.toStringS());
		//System.out.println("Created Node: " + number + " with disease: " + Dis + " with symptom: " + symp + " with parent: " + parentNumber + " with current cost: " + totalCost + " and unsatisfied Symptoms: " + unsat);
	}
	
	public String toString(){		
		return "Node: " + number + " with disease: " + DiseaseName + " with symptom: " + Symptom + " with parent: " + parentNumber + " with current cost: " + totalCost + " with Estimated Goal Cost(f(n)): " + estimatedGoalCost +  " and unsatisfied Symptoms: " + unsatStr;		
	}
	
	public String toStringS(){		
		return "Node: " + number + " with disease: " + DiseaseName + " with symptom: " + Symptom + " with Estimated Goal Cost(f(n)): " + estimatedGoalCost +  " and unsatisfied Symptoms: " + unsatStr;		
	}
		
}

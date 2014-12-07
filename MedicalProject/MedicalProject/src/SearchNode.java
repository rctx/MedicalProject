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
	String childNodes;// Separated by spaces
	String[] unsatisfiedSymptom;// = new String[6];
	String[] satisfiedSymptom;// = new String[6];
	
	public SearchNode(int num, int pnum, String unsat, int currCost ){
		number = num;
		parentNumber = pnum;
		unsatisfiedSymptom = unsat.split(" ");
		totalCost = currCost;
	}
		
}

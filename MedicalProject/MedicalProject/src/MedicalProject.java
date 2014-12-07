
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.DatasetFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;
public class MedicalProject {

	public static void main(String[] args) {
		// Temp values for now, later make variable to needs
		String[] Nodes = new String[100];
		String[] Depth = new String[20]; //hold last node# for that depth
		String[] UserSymptoms = new String[3];
		
		//
		// Change this to relational
		//
		String directory = "file:C:\\Users\\Ryan\\Documents\\Med4.owl";
		SPRQLQuery queryObj = new SPRQLQuery(directory);
		
		String SymptomList = queryObj.getSymptoms();
		//System.out.println("SymptomList:" + SymptomList);
		String[] AllSymptomList = SymptomList.split(" ");
		
		// Get input of symptoms from user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int numSymptoms = 0;
		while(numSymptoms < 3){
			System.out.println("Enter Symptom " + (numSymptoms + 1) + " of 3:");
			String inSymp = "";
			try {
				inSymp = br.readLine();
		      } catch (IOException ioe) {
		         System.out.println("IO error trying to read your symptom!");
		         System.exit(1);
		      }
			if(inSymp.equals("list")){
				System.out.println("List of Symptoms: " + SymptomList);
			}
			else if(!inSymp.equals("")){
				// Check if its actually a Symptom
				boolean realSymptom = false;
				for(int g = 0; g < AllSymptomList.length; g++){
					if(AllSymptomList[g].equals(inSymp)){
						realSymptom = true;
					}
				}
				
				// If it is then save it
				if(realSymptom){
					UserSymptoms[numSymptoms] = inSymp;
					numSymptoms++;
				}
				else{
					System.out.println("Error trying to read your symptom!");
					System.out.println("You can enter 'list' to see all symptoms.");
				}
			}
		}// Done gathering input
		
		// Create Initial Search Node
		//SearchNode(int num, int pnum, String unsat, int currCost )
		
		
		
		
		
		//SPRQLQuery queryObj = new SPRQLQuery(directory);
		
		
		String DiseaseList = queryObj.getDisease("Coughing");
		System.out.println("DiseaseList:" + DiseaseList);
		
		
		String DiseaseList2 = queryObj.getDisease("Pain", "Ebola Pneumonia FoodAllergy");
		System.out.println("DiseaseList2:" + DiseaseList2);
		if(true) {return;}
		
		
		
		//Dataset dataset = DatasetFactory.assemble(directory);//,"http://www.semanticweb.org/ryan/ontologies/2014/11/untitled-ontology-2#");
		Model model = FileManager.get().loadModel("file:C:\\Users\\Ryan\\Documents\\Med4.owl");
		
		
		//Model model = dataset.getDefaultModel();
		
		
		//String queryString = "SELECT * WHERE { ?s ?p ?o }";
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX Med4: <http://www.semanticweb.org/ryan/ontologies/2014/11/untitled-ontology-2#> SELECT * WHERE { ?disease ?severity ?symptom . ?severity rdfs:subPropertyOf Med4:has_symptom }";
	    Query query = QueryFactory.create(queryString);
	    QueryExecution qexec = QueryExecutionFactory.create(query, model);
	    try {
	        ResultSet results = qexec.execSelect();
	        for (; results.hasNext();) {
	            QuerySolution soln = results.nextSolution();
	            System.out.println(soln);
	            RDFNode x = soln.get("disease");       // Get a result variable by name.
	            RDFNode y = soln.get("severity");
	            RDFNode z = soln.get("symptom");
	            String xs = x.toString();
	            xs = xs.replaceAll(".*#","");
	            String ys = y.toString();
	            ys = ys.replaceAll(".*#","");
	            String zs = z.toString();
	            zs = zs.replaceAll(".*#","");

	            System.out.println(" xs=" + xs + " ys=" + ys + " zs=" + zs);
	            Resource r = soln.getResource("VarR"); // Get a result variable - must be a resource
	            Literal l = soln.getLiteral("VarL");   // Get a result variable - must be a literal
	        }
	    } catch (Exception e) {
	        System.err.print("Error:"+e.getMessage());
	        
	    } finally {
	        qexec.close();
	    }
	}

}

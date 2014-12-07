import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.FileManager;

/**
 * 
 */

/**
 * @author Ryan
 *
 */
public class SPRQLQuery {
	String ontologyDirectory;
	Model model;
	
	public SPRQLQuery(String ontologyFile){
		ontologyDirectory = ontologyFile;
		model = FileManager.get().loadModel(ontologyFile);
	}
	
	public String getSymptoms(){
		String Symptoms = "";
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX Med4: <http://www.semanticweb.org/ryan/ontologies/2014/11/untitled-ontology-2#> SELECT * WHERE {?symptom a Med4:Symptom }";
	    Query query = QueryFactory.create(queryString);
	    QueryExecution qexec = QueryExecutionFactory.create(query, model);
	    try {
	        ResultSet results = qexec.execSelect();
	        for (; results.hasNext();) {
	            QuerySolution soln = results.nextSolution();
	            //System.out.println(soln);
	            RDFNode z = soln.get("symptom");
	            String zs = z.toString();
	            zs = zs.replaceAll(".*#","");
	            //System.out.println("zs=" + zs);
	            Symptoms+= zs + " ";
	        }
	    } catch (Exception e) {
	        System.err.print("Error:"+e.getMessage());
	        //return "error";
	        
	    } finally {
	        qexec.close();
	    }
	    return Symptoms;
	}
	
	// Given a Symptom, return a list of all Diseases that have the symptom
	//returns Disease:severity Disease:severity Disease:severity
	public String getDisease(String Symptom){
		String Diseases = "";
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX Med4: <http://www.semanticweb.org/ryan/ontologies/2014/11/untitled-ontology-2#> SELECT * WHERE { ?disease ?severity Med4:" + Symptom + " . ?severity rdfs:subPropertyOf Med4:has_symptom }";
		Query query = QueryFactory.create(queryString);
	    QueryExecution qexec = QueryExecutionFactory.create(query, model);
	    try {
	        ResultSet results = qexec.execSelect();
	        for (; results.hasNext();) {
	            QuerySolution soln = results.nextSolution();
	            //System.out.println(soln);
	            RDFNode z = soln.get("disease");
	            String zs = z.toString();
	            zs = zs.replaceAll(".*#","");
	            RDFNode x = soln.get("severity");
	            String xs = x.toString();
	            xs = xs.replaceAll(".*#","");
	            //System.out.println("zs=" + zs + "xs=" + xs);
	            Diseases+= zs + ":" + xs + " ";
	        }
	    } catch (Exception e) {
	        System.err.print("Error:"+e.getMessage());
	        //return "error";
	        
	    } finally {
	        qexec.close();
	    }
		return Diseases;
	}
	
	// Given a Symptom AND list of diseases return a list of all Diseases from the list that have the symptom
	// returns Disease:severity Disease:severity Disease:severity
	// accepts diseases separated by spaces
	public String getDisease(String Symptom, String IncomingDiseases){
		String Diseases = "";
		String[] diseaseList = IncomingDiseases.split(" ");
		String[] diseaseTmp = new String[diseaseList.length];
		String[] severityTmp = new String[diseaseList.length];
		//String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX Med4: <http://www.semanticweb.org/ryan/ontologies/2014/11/untitled-ontology-2#> SELECT * WHERE { ?disease ?severity Med4:" + Symptom + ". { ";
		String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX Med4: <http://www.semanticweb.org/ryan/ontologies/2014/11/untitled-ontology-2#> SELECT * WHERE { ?disease ?severity Med4:" + Symptom + " . ?severity rdfs:subPropertyOf Med4:has_symptom }";
		/*for(int i = 0; i < diseaseList.length; i++){
			if(i == 0){queryString += " { ?disease a Med4:Disease }";}
			else{
				queryString += " UNION { ?disease a Med4:Disease }";
			}
		}
		queryString += "} }";*/
		//System.out.println("GetDiseases query:" + queryString);
		
		Query query = QueryFactory.create(queryString);
	    QueryExecution qexec = QueryExecutionFactory.create(query, model);
	    int index = 0;
	    try {
	        ResultSet results = qexec.execSelect();
	        for (; results.hasNext();) {
	            QuerySolution soln = results.nextSolution();
	            //System.out.println(soln);
	            RDFNode z = soln.get("disease");
	            String zs = z.toString();
	            zs = zs.replaceAll(".*#","");
	            diseaseTmp[index] = zs;
	            RDFNode x = soln.get("severity");
	            String xs = x.toString();
	            xs = xs.replaceAll(".*#","");
	            severityTmp[index] = xs;
	            //System.out.println("zs=" + zs + "xs=" + xs);
	            //Diseases+= zs + ":" + xs + " ";
	            index++;
	        }
	    } catch (Exception e) {
	        System.err.print("Error:"+e.getMessage());
	        //return "error";
	        
	    } finally {
	        qexec.close();
	    }
		for(int i = 0; i < diseaseList.length; i++){
			if(diseaseTmp[i] == null){continue;}
			for(int k = 0; k< diseaseList.length; k++){
				if(diseaseTmp[i].equals(diseaseList[k])){
					Diseases+= diseaseTmp[i] + ":" + severityTmp[i] + " ";
				}
			}
		}
		
		return Diseases;
	}
}

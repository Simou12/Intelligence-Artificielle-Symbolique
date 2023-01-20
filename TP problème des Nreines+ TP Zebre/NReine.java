package tp_modelisation;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class NReine {
	private static int  n=8;
	
	public static int getN() {
		return n;
	}

	public static void setN(int n) {
		NReine.n = n;
	}

	public static void main(String[] args) {
		
		Model model= new Model("NReine");
		IntVar [] t=model.intVarArray("x",n,0,n-1);
		
		model.allDifferent(t).post();
		
		//TODO améliorer ce code pour qu'il marche 
		/*
		int i; int j=0;
		i=0;
		
		while(i<n-1) {
			j=i+1;
			
			//Contrainte 1
			model.distance(t[i],t[i+1],"!=",1).post();
			if(i<n-2) {
				model.distance(t[i],t[i+2],"!=",2).post();
			}
			while(j<n) {
				//Contrainte 2
				model.arithm(t[i],"!=",t[j]).post();
				j++;
			}
			i++;
		}
		*/
		
		
		for(int i=0;i<n-1;i++) {
			for (int j=i+1;j<n;j++) {
				//colonnes différentes
				model.arithm(t[i],"!=",t[j]).post();
				//diagonales différentes
				model.distance(t[i],t[j],"!=",j-i).post();		
			}
		}
		
		
		
		// premiere solution
		if(model.getSolver().solve()) 
			System.out.println(" -------- FIRST SOLUTION :-------- ");
		else 
			System.out.println(" solution not found ");
		System.out.println(model);
			
		//calcul de toutes les solutions 
		
		System.out.println(" ------TOUTES LES SOLUTIONS ------- ");
		Solver solve= model.getSolver();
		while(solve.solve()) {
			System.out.println("solution: "+ model.getSolver().getSolutionCount()+ "\n"+model);
		}
		
		System.out.println("------ STATISTIQUE -----");
		System.out.println("\n\n*** Bilan ***");        
        model.getSolver().printStatistics();
			
			
				
		
		
		
		
	}
	
}

package projet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class Expe {

	private static Model lireReseau(BufferedReader in) throws Exception{
			Model model = new Model("Expe");
			int nbVariables = Integer.parseInt(in.readLine());				// le nombre de variables
			int tailleDom = Integer.parseInt(in.readLine());				// la valeur max des domaines
			IntVar []var = model.intVarArray("x",nbVariables,0,tailleDom-1); 	
			int nbConstraints = Integer.parseInt(in.readLine());			// le nombre de contraintes binaires		
			for(int k=1;k<=nbConstraints;k++) { 
				String chaine[] = in.readLine().split(";");
				IntVar portee[] = new IntVar[]{var[Integer.parseInt(chaine[0])],var[Integer.parseInt(chaine[1])]}; 
				int nbTuples = Integer.parseInt(in.readLine());				// le nombre de tuples		
				Tuples tuples = new Tuples(new int[][]{},true);
				for(int nb=1;nb<=nbTuples;nb++) { 
					chaine = in.readLine().split(";");
					int t[] = new int[]{Integer.parseInt(chaine[0]), Integer.parseInt(chaine[1])};
					tuples.add(t);
					
				}
				model.table(portee,tuples).post();	
			}
			in.readLine();
			return model;
	}	
		
			
	public static void main(String[] args) throws Exception{
		
		//Le programme calculons le pourcentage des réseaux qui ont au moins une solution avant modification
	
	 	/*String ficName = "bench.txt";
		String ficName2= "benchSatisf.txt";
		String ficName3= "benchInsat.txt";
		String bash="bash.sh";
		int nbrResSol=0;
		int nbRes=3;
		BufferedReader readFile = new BufferedReader(new FileReader(ficName2));
		
		/*for(int nb=1 ; nb<=nbRes; nb++) {
			Model model=lireReseau(readFile);
			if(model==null) {
				System.out.println("Problème de lecture de fichier !\n");
			}
			if(model.getSolver().solve()) nbrResSol++;
			System.out.println("Réseau lu "+nb+" :\n"+model+"\n\n");
		}
		System.out.println("Le nombre de réseau ayant une solution est : "+nbrResSol);
		return ;	
	}*/

		
		//Déclaration des variables
		int debut;
		int fin;
		String ficName;
		String fiName2;
		String fiName3;
		String csp;
		String csv;
		int nbrResSol;
		int nbRes;
		int pourcentage;
		double durete;
		int tailleDomaine;
		double domaine;
		String pourcentageDurete;
		long startTime , startCpuTime , startUserTime , userTime, cpuTime , sysTime ,realTime; 
		int nbrNoeudResTot;
		
		
		//Initialisation des variables
		 debut=178;
		 fin=211;
		 ficName = "bench.txt";
		 fiName2= "benchSatisf.txt";
		 fiName3= "benchInsat.txt";
		 csp="csp/csp";
		 csv="CSVbench1.csv";
		 nbrResSol=0;
		 nbRes=20;
		 pourcentage=0;
		 durete=0;
		 tailleDomaine=17;
		 domaine=0;
		 pourcentageDurete="";
		 nbrNoeudResTot=0;
		 
		
		  int tot=0;
		 //Creation du fichier csv contenat le couple durete;%
		try {
	        File myFile = new File(csv); 
	        if (myFile.createNewFile()){
	          System.out.println("Le fichier "+csv+" est créé.");
	        }else{
	          System.out.println("Le fichier "+csv+" existe déjà.");
	        }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
		//Bufferwriter permettant d'ecrire dans le fichier afin d'ajouter le couple durete;%
				System.out.println("Réseau lu "+nb+" :\n"+model+"\n\n");	
				System.out.println("Réseau lu "+nb+" \n");
				nbrNoeudResTot+=solver.getNodeCount();		BufferedWriter writeFile=new BufferedWriter(new FileWriter(csv));	

		for(int i=debut;i<=fin;i+=3) {
			ThreadMXBean thread = ManagementFactory.getThreadMXBean();
			startTime = System.nanoTime();
		    startCpuTime = thread.getCurrentThreadCpuTime();
			startUserTime = thread.getCurrentThreadUserTime();
			nbrResSol=0;
			csp="csp/csp";
			csp+=i+".txt";
			BufferedReader readFile = new BufferedReader(new FileReader(csp));
			nbrNoeudResTot=0;
			for(int nb=1 ; nb<=nbRes; nb++) {
				Model model=lireReseau(readFile);
				if(model==null) {
					System.out.println("Problème de lecture de fichier !\n");
				}
				Solver solver=model.getSolver();
				//solver.limitTime("0.1s");
				if(solver.solve()) 
					nbrResSol++;
			}
			userTime = thread.getCurrentThreadUserTime() - startUserTime;
			cpuTime = thread.getCurrentThreadCpuTime() - startCpuTime;
			sysTime = cpuTime - userTime;
			System.out.println("Le nombre de reseaux du fichier "+csp+" ayant une solution est : "+nbrResSol);
			pourcentage=(nbrResSol*100)/nbRes;
			domaine=((double)tailleDomaine)*((double)tailleDomaine);
			durete=(domaine-(double)i)/(domaine);
			pourcentageDurete=Double.toString(durete)+";"+Integer.toString(pourcentage)+";"+Double.toString(cpuTime*0.000001)+";"+Integer.toString(nbrNoeudResTot/nbRes);
			writeFile.write(pourcentageDurete+"\n");
			System.out.println(csp+ ": " +pourcentage+"% des réseaux ont des solutions");
			
		   }
		 writeFile.close();
		 return;	
	}
}

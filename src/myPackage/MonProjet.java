package myPackage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class MonProjet {
	
	/** le choix ce fait uniquement sur le cout minimum d'un ensemble
	 * @param listB
	 * @param listE
	 * @return le cout
	 */
	public static List<Base> simpleAlgo(List<Base> listB, List<String> listE) {
		Set<Base> finalBase = new HashSet<Base>();
		Set<String> dejaInclu = new HashSet<String>();
		for ( String entreprise : listE ) {
			// Si je n'ai pas déja inclu l'entreprise
			// j'ajoute alors celle qui me coute le moins chère
//			System.err.print("entreprise à ajouté: "+entreprise);
			if ( !(dejaInclu.contains(entreprise)) ) {
				Base tmp = Util.coutMini(listB,entreprise);
//				System.err.println(" avec la liste "+tmp.getEntreprises());
				if ( finalBase.add(tmp) ) {
					dejaInclu.addAll(tmp.getEntreprises());
				} else {
//					System.err.println("ADD FAILED for base"+tmp.getEntreprises().toString());
				}
			} else {
//				System.err.println(" entreprise déjà contenu");
			}
		}

		return new ArrayList<Base>(finalBase);
	}
	
	/** le choix ce fait uniquement sur le taille de l'ensemble
	 * @param listB
	 * @param listE
	 * @return le cout
	 */
	private static List<Base> simpleAlgo2(List<Base> listB, List<String> listE) {
		Set<Base> finalBase = new TreeSet<Base>();
		Set<String> dejaInclu = new TreeSet<String>();
		for ( String entreprise : listE ) {
			if ( !(dejaInclu.contains(entreprise)) ) {
				Base tmp =Util.tailleMax(listB,entreprise);
				if ( finalBase.add(tmp) ) {
					dejaInclu.addAll(tmp.getEntreprises());
				}
			}
		}		
		return new ArrayList<Base>(finalBase);		
	}
	
	/**
	 * Retire les Bases ne contenant pas d'entreprises utile
	 */
	private static List<Base> clean(List<Base> listB, List<String> listE){
		boolean contient = false;
		List<Base> tmp = new ArrayList<Base>(listB);
		for (Base b : tmp) {
			for (String s : listE) {
				if ( b.getEntreprises().contains(s) ) {
					contient=true;
					break;
				}
			}
		if (!contient)
			tmp.remove(b);
		}
		return tmp;
	}
	
	/**
	 * retire une élement et retourne une nouvelle liste
	 */
	private static List<Base> remove( List<Base> bases, int index) {
		List<Base> newList = new ArrayList<Base>(bases.subList( 0, index ));
		newList.addAll( bases.subList( index+1 , bases.size() ));
		return newList;
	}

	public static List<ConcurrentSkipListSet<Base>> myCollection = new ArrayList<ConcurrentSkipListSet<Base>>();
	public static int index = 0;
	
	public static void recursive (List<Base> listB, List<String> listE) {
		

		if ( listE.isEmpty() ) {
			index++;
			return;
		} else {
		
			String entreprise = listE.get(0);
			
			for ( Base base : listB) {
					// init le nouvel élément 
					if ( myCollection.size() == index) {
						ConcurrentSkipListSet<Base> item= new ConcurrentSkipListSet<Base>();
						myCollection.add(item);
					}
					// vérifi que l'élement courant n'a pas déjà été inséré
					for ( Base b : myCollection.get(index) )
						if ( b.getEntreprises().contains(entreprise)){
							recursive(listB,listE.subList(1, listE.size()));
							return;
						}
						
					// test si la base contient l'entreprise
					if (base.getEntreprises().contains(entreprise)) {
						// retourner la base trouver 
						// plus la liste moins la base et moins l'entreprise
						myCollection.get(index).add(base);
						
						recursive(listB,listE.subList(1, listE.size()));
					}
			}
		}
			
	}

	private static void complexAlgo(List<Base> listB, List<String> listE) {

		List<Base> bases = new ArrayList<Base>(listB);//clean(listB, listE);
		Base[][] collection = new Base[listE.size()][bases.size()];
		
		
		/**
		 * j'initialise la premiere ligne de ma matrice en indiquant le cout
		 * d'ajout du premier objet en fonction du cout de la base la contenant
		 * 
		 */
	
		for ( int j=0; j<bases.size(); j++ ) {
			if ( bases.get(j).getEntreprises().contains(listE.get(0)) ) {
				collection[0][j] = bases.get(j);
			} else {
				collection[0][j] = new Base();
			}
		}
		
		/**
		 * Pour toute les bases suivantes si elles contiennent la nouvelle entreprise
		 * alors j'additionne les valeurs de la base avec le résultat le moins couteux
		 * de la recherche précédente
		 */
		
		for ( int i=1; i<listE.size(); i++ ) {
			for ( int j=0; j<bases.size(); j++ ) {
				if ( bases.get(j).getEntreprises().contains(listE.get(i)) ) {
					Base minBase = Util.min(collection[i-1]);
					if (!minBase.getEntreprises().contains(listE.get(i))) {
//						int newcout = ( bases.get(j).getCout() + minBase.getCout() );
						int newcout = ( bases.get(j).getCout() );
						ArrayList<String> newList = new ArrayList<String>(bases.get(j).getEntreprises());
//						newList.addAll(minBase.getEntreprises());
						collection[i][j] = new Base(newcout, newList);
					} else {
						collection[i][j] = collection[i-1][j];
					}
				} else {
					collection[i][j] = new Base();
				}
			}
		}
		
		/**
		 * Affichage
		 */
		for ( int j=0; j<bases.size(); j++ )
			System.out.print(String.format("|%1$3s",j+1));
		System.out.println("|");
		int total=0;
		for ( int i=0; i<listE.size(); i++ ) {
			for ( int j=0; j<bases.size(); j++ ) {
				if (collection[i][j].getCout()!=-1){
					total++;
					System.out.print("|"+String.format("%03d",collection[i][j].getCout()));
				} else {
					System.out.print("|   ");
				}
			}
			System.out.print("|  => "+total+"\n");
			total=0;
		}
		System.err.println(listE);
		System.err.print(collection[listE.size()-1][bases.size()-2].getCout()+";");
		listE.removeAll(collection[listE.size()-1][bases.size()-2].getEntreprises());
		System.err.println(listE);
		
		
		
	}

	public static void main(String[] args) throws IOException {
//		initialisation		
		Parser p= Parser.getInstance();
		final List<Base> listB = p.parseListeBase("Liste Bases1.txt");
		final List<String> listE = p.parseListe("Liste Ent1.txt", Parser.LISTE_ENTREPRISE);
		
		long startTime = System.currentTimeMillis();
		recursive(listB, listE);
		long calculTime = System.currentTimeMillis()-startTime;
		
		
		int minimum = Util.coutTotal(listB);
		for ( ConcurrentSkipListSet<Base> item : MonProjet.myCollection) {
			ArrayList<Base> baseTmp = new ArrayList<Base>(item);
			if (Util.nbEntreprisesRestantes(baseTmp, listE)==0) {
				int tmp = Util.coutTotal(baseTmp);
				if ( tmp < minimum )
					minimum = ( tmp < minimum ) ? tmp : minimum;
			}
			

		}
		System.err.println("temp de calcul "+calculTime+"ms "+"mini="+minimum);

//		System.out.println("time parse: "+((endTime-startTime))+"ms");
////		System.err.println(Util.affichageBases(listB));
//				
////		variable de sortie
////		
		List<Base> resultat;
		resultat = simpleAlgo(listB, listE );
		Util.out(resultat,listE,Util.ALGO_1);
//
//		
//		System.out.println("temps de calcul pour algo 1: "+((endTime-startTime))+"ms");
//		
//		startTime = System.currentTimeMillis();
		resultat = simpleAlgo2(listB, listE);
//		endTime = System.currentTimeMillis();
//		
//		System.out.println("temps de calcul pour algo 2: "+((endTime-startTime))+"ms");
		Util.out(resultat,listE,Util.ALGO_2);	
		 
//		complexAlgo(listB , listE);
//		int resultat1 = Util.coutTotal(minBase);
//		System.err.println("resultat="+resultat1+" pour la base "+Util.affichageBases(minBase));
	}

}
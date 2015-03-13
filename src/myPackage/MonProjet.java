package myPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class MonProjet {

	/**
	 * le choix ce fait uniquement sur le cout minimum d'un ensemble
	 * 
	 * @param listB
	 * @param listE
	 * @return le cout
	 */
	public static List<Base> simpleAlgo(List<Base> listB, List<String> listE) {
		Set<Base> finalBase = new HashSet<Base>();
		Set<String> dejaInclu = new HashSet<String>();
		for (String entreprise : listE) {
			// Si je n'ai pas déja inclu l'entreprise
			// j'ajoute alors celle qui me coute le moins chère
			// System.err.print("entreprise à ajouté: "+entreprise);
			if (!(dejaInclu.contains(entreprise))) {
				Base tmp = Util.coutMini(listB, entreprise);
				// System.err.println(" avec la liste "+tmp.getEntreprises());
				if (finalBase.add(tmp)) {
					dejaInclu.addAll(tmp.getEntreprises());
				} else {
					// System.err.println("ADD FAILED for base"+tmp.getEntreprises().toString());
				}
			} else {
				// System.err.println(" entreprise déjà contenu");
			}
		}

		return new ArrayList<Base>(finalBase);
	}

	/**
	 * le choix ce fait uniquement sur le taille de l'ensemble
	 * 
	 * @param listB
	 * @param listE
	 * @return le cout
	 */
	private static List<Base> simpleAlgo2(List<Base> listB, List<String> listE) {
		Set<Base> finalBase = new TreeSet<Base>();
		Set<String> dejaInclu = new TreeSet<String>();
		for (String entreprise : listE) {
			if (!(dejaInclu.contains(entreprise))) {
				Base tmp = Util.tailleMax(listB, entreprise);
				if (finalBase.add(tmp)) {
					dejaInclu.addAll(tmp.getEntreprises());
				}
			}
		}
		return new ArrayList<Base>(finalBase);
	}

	/**
	 * Retire les Bases ne contenant pas d'entreprises utile
	 */
	private static List<Base> clean(List<Base> listB, List<String> listE) {
		boolean contient = false;
		List<Base> tmp = new ArrayList<Base>(listB);
		for (Base b : tmp) {
			for (String s : listE) {
				if (b.getEntreprises().contains(s)) {
					contient = true;
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
	private static List<Base> remove(List<Base> bases, int index) {
		List<Base> newList = new ArrayList<Base>(bases.subList(0, index));
		newList.addAll(bases.subList(index + 1, bases.size()));
		return newList;
	}

	public static List<ConcurrentSkipListSet<Base>> myCollection = new ArrayList<ConcurrentSkipListSet<Base>>();
	public static int index = 0;

	public static List<MyCollection> myCollection2 = new ArrayList<MyCollection>();

	public static void recursive(List<Base> listB, List<String> listE) {

		// si ma liste enterprise est vide, j'ai trouvé un fonctionnel
		// je passe donc a l'élément suivant
		if (listE.isEmpty()) {
			index++;
			return;
		} else {
			// je prend mon premiers élément
			String entreprise = listE.get(0);

			for (Base base : listB) {
				// si mon index a dépassé la taille de ma collection
				// j'ajoute un nouvelle élément a remplire
				if (myCollection2.size() == index) {
					MyCollection item = new MyCollection();
					myCollection2.add(item);
				}
				// pour chaque base d'un item de ma collection
				for (Base b : myCollection2.get(index))
					// si la liste d'entreprises de ma base contient mon
					// enterprise
					if (b.getEntreprises().contains(entreprise)) {
						recursive(listB, listE.subList(1, listE.size()));
						return;
					}

				// test si la base contient l'entreprise
				if (base.getEntreprises().contains(entreprise)) {
					// retourner la base trouver
					// plus la liste moins la base et moins l'entreprise
					myCollection2.get(index).add(base);

					recursive(listB, listE.subList(1, listE.size()));
				}
			}
		}
	}

	public static MyCollection myMinCollection;
	public static void recursive2(List<Base> listB, List<String> listE,
			MyCollection mCollection) {
		// si ma liste entreprise est vide
		// j'ai trouvé un cas validant
	    	if (myMinCollection!=null && myMinCollection.getCoutMin()<mCollection.getCoutMin())
	    	    return;
		if (listE.isEmpty()) {
			// je l'ajoute dans ma liste de collection		    
		    	if ( (myMinCollection==null) )
		    	    myMinCollection= new MyCollection(mCollection);
		    	else { 
			    int minActual = myMinCollection.getCoutMin();
			    int newValue = mCollection.getCoutMin();
		    	    if (minActual>newValue){
		    		myMinCollection= new MyCollection(mCollection);
			    	Util.out(mCollection, listE, Util.ALGO_RECURS2);
		    	    }
		    	}
			
		} else {
			String entreprise = listE.get(0);
			for (Base base : listB) {
				// Si ma collection a déjà cette base de maniere indirect
				if (mCollection.isIn(entreprise)) {
					recursive2(listB, listE.subList(1, listE.size()), mCollection);
//					mCollection.remove(base);
				} else {
        				// Si l'entreprise est dans la liste de ma base courante
        				if (base.getEntreprises().contains(entreprise)) {
        					// j'ajoute la base dans ma collection
        					mCollection.add(base);
        					// et je lance le cas récursif sur l'entreprise suivante
        					recursive2(listB, listE.subList(1, listE.size()),
        							mCollection);
        					mCollection.remove(base);
        				}
				}

			}
		}

	}

	// private static void complexAlgo(List<Base> listB, List<String> listE) {
	//
	// List<Base> bases = new ArrayList<Base>(listB);//clean(listB, listE);
	// Base[][] collection = new Base[listE.size()][bases.size()];
	//
	//
	// /**
	// * j'initialise la premiere ligne de ma matrice en indiquant le cout
	// * d'ajout du premier objet en fonction du cout de la base la contenant
	// *
	// */
	//
	// for ( int j=0; j<bases.size(); j++ ) {
	// if ( bases.get(j).getEntreprises().contains(listE.get(0)) ) {
	// collection[0][j] = bases.get(j);
	// } else {
	// collection[0][j] = new Base();
	// }
	// }
	//
	// /**
	// * Pour toute les bases suivantes si elles contiennent la nouvelle
	// entreprise
	// * alors j'additionne les valeurs de la base avec le résultat le moins
	// couteux
	// * de la recherche précédente
	// */
	//
	// for ( int i=1; i<listE.size(); i++ ) {
	// for ( int j=0; j<bases.size(); j++ ) {
	// if ( bases.get(j).getEntreprises().contains(listE.get(i)) ) {
	// Base minBase = Util.min(collection[i-1]);
	// if (!minBase.getEntreprises().contains(listE.get(i))) {
	// // int newcout = ( bases.get(j).getCout() + minBase.getCout() );
	// int newcout = ( bases.get(j).getCout() );
	// ArrayList<String> newList = new
	// ArrayList<String>(bases.get(j).getEntreprises());
	// // newList.addAll(minBase.getEntreprises());
	// collection[i][j] = new Base(newcout, newList);
	// } else {
	// collection[i][j] = collection[i-1][j];
	// }
	// } else {
	// collection[i][j] = new Base();
	// }
	// }
	// }
	//
	// /**
	// * Affichage
	// */
	// for ( int j=0; j<bases.size(); j++ )
	// System.out.print(String.format("|%1$3s",j+1));
	// System.out.println("|");
	// int total=0;
	// for ( int i=0; i<listE.size(); i++ ) {
	// for ( int j=0; j<bases.size(); j++ ) {
	// if (collection[i][j].getCout()!=-1){
	// total++;
	// System.out.print("|"+String.format("%03d",collection[i][j].getCout()));
	// } else {
	// System.out.print("|   ");
	// }
	// }
	// System.out.print("|  => "+total+"\n");
	// total=0;
	// }
	// System.err.println(listE);
	// System.err.print(collection[listE.size()-1][bases.size()-2].getCout()+";");
	// listE.removeAll(collection[listE.size()-1][bases.size()-2].getEntreprises());
	// System.err.println(listE);
	// }

	public static void main(String[] args) throws IOException {
		// initialisation
		Parser p = Parser.getInstance();
		final List<Base> listB = p.parseListeBase("Liste Bases1.txt");
		final List<String> listE = p.parseListe("Liste Ent1.txt",
				Parser.LISTE_ENTREPRISE);

		long startTime = System.currentTimeMillis();
		recursive2(listB, listE, new MyCollection());
		long calculTime = System.currentTimeMillis() - startTime;
		System.err.print("temp de calcul " + calculTime + "ms | ");
		System.err.println(" "+Util.affichageBases(myMinCollection));
//		int minimum = Util.coutTotal(listB);
//
//		startTime = System.currentTimeMillis();
//
//		for (MyCollection item : MonProjet.myCollection2)
//			if ((item.getCoutMin() != 0) && (item.nbEntreprisesRestantes(listE) == 0))
//				minimum = (minimum > item.getCoutMin()) ? item.getCoutMin()
//						: minimum;
//
//		calculTime = System.currentTimeMillis() - startTime;
//		System.err.println("Temps d'affichage" + calculTime + " mini="+ minimum);

		// variable de sortie

		List<Base> resultat;
		resultat = simpleAlgo(listB, listE);
		Util.out(resultat, listE, Util.ALGO_1);
		//
		//
		// System.out.println("temps de calcul pour algo 1: "+((endTime-startTime))+"ms");
		//
		// startTime = System.currentTimeMillis();
		resultat = simpleAlgo2(listB, listE);
		// endTime = System.currentTimeMillis();
		//
		// System.out.println("temps de calcul pour algo 2: "+((endTime-startTime))+"ms");
		Util.out(resultat, listE, Util.ALGO_2);

		// complexAlgo(listB , listE);
		// int resultat1 = Util.coutTotal(minBase);
		// System.err.println("resultat="+resultat1+" pour la base "+Util.affichageBases(minBase));
	}

}
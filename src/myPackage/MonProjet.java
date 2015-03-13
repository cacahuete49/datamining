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
		for (Base b : listB) {
			for (String s : listE) {
				if (b.getEntreprises().contains(s)) {
					contient = true;
					break;
				}
			}
			if (!contient)
				tmp.remove(b);
			contient = false;
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
	    	// si ma liste minimum a un cout inférieur a ma liste courante je shunt l'execution
	    	if (myMinCollection!=null && myMinCollection.getCoutMin()<mCollection.getCoutMin())
	    	    return;
		// si ma liste entreprise est vide => j'ai trouvé un cas validant
		// je l'ajoute dans ma liste de collection
		if (listE.isEmpty()) {
		    	// si ma collection est null je l'initialise
		    	// sinon je test le cout pour mettre a jour ou non la collectionMin
		    	if ( (myMinCollection==null) )
		    	    myMinCollection= new MyCollection(mCollection);
		    	else {
			    int minActual = (myMinCollection.getCoutMin()==0)?Integer.MAX_VALUE:myMinCollection.getCoutMin();
			    int newValue = mCollection.getCoutMin();
		    	    if (minActual>newValue){
		    		myMinCollection= new MyCollection(mCollection);
		    	    }
		    	}
			
		} else {
			String entreprise = listE.get(0);
			// Si ma collectionMin a déjà cette entreprise de maniere indirect
			if (mCollection.isIn(entreprise))
			    	// Je lance mon cas recursif sans prendre en compte cette entreprise
			    	recursive2(listB, listE.subList(1, listE.size()), mCollection);
			else 	
			    	// Je parcours toutes mes bases
        			for (Base base : listB) {
                				// Si l'entreprise est dans la base
                				if (base.getEntreprises().contains(entreprise)) {
                					// j'ajoute la base dans ma collectionMin
                					mCollection.add(base);
                					// et je lance le cas récursif sur l'entreprise suivante
                					recursive2(listB, listE.subList(1, listE.size()),mCollection);
                					// a la fin du traitement je retire l'élément
                					mCollection.remove(base);
                				}
        			}
		}

	}


	public static void main(String[] args) throws IOException {
		// initialisation
	    	long startTime;
		Parser p = Parser.getInstance();
		List<Base> listB = p.parseListeBase("Liste Bases1.txt");
		final List<String> listE = p.parseListe("Liste Ent1.txt",
				Parser.LISTE_ENTREPRISE);
		
		List<Base> baseClean = clean(listB,listE);

		startTime = System.nanoTime();
		recursive2(listB, listE, new MyCollection());
		System.out.println("temp de calcul " + (System.nanoTime() - startTime)/1000l + "us");
		Util.out(myMinCollection, listE, Util.ALGO_RECURS2);

		
		// variable de sortie
		List<Base> resultat;
		
		startTime = System.nanoTime();
		resultat = simpleAlgo(baseClean, listE);
		System.out.println("temp de calcul " + (System.nanoTime() - startTime)/1000l + "us");
		Util.out(resultat, listE, Util.ALGO_1);
		
		
		startTime = System.nanoTime();
		resultat = simpleAlgo2(baseClean, listE);
		System.out.println("temp de calcul " + (System.nanoTime() - startTime)/1000l + "us");
		Util.out(resultat, listE, Util.ALGO_2);

	}

}
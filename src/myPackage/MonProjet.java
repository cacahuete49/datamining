package myPackage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
	 * @return 
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
	 * Le choix ce fait sur l'efficience d'une base
	 * @param listB
	 * @param listE
	 * @return
	 */
	public static List<Base> simpleAlgo3(List<Base> listB, List<String> listE) {
	    List<Base> finalBase = new ArrayList<Base>();
	    boolean jump = false;
	    for (String entreprise : listE) {
		jump=false;
		// si ma finalBase contient ma chaine je saute ce parcours
		for (Base b : finalBase) {
		    if ( b.getEntreprises().contains(entreprise) ){
			jump = true;
			break;
		    }
		}
		
		if (jump)
		    continue;
		else
		    finalBase.add(Util.maxEfficiency(listB, listE , entreprise));
	    }
	    return finalBase;
	}


	public static MyCollection myMinCollection;
	
	public static void recursive2(List<Base> listB, List<String> listE,MyCollection mCollection) {
	    	// si ma liste minimum a un cout inférieur a ma liste courante je shunt l'execution
	    	if (myMinCollection!=null && myMinCollection.getCoutMin()<mCollection.getCoutMin())
	    	    return;
		// si ma liste entreprise est vide => j'ai trouvé un cas validant
		// je l'ajoute dans ma liste de collection
		if (listE.isEmpty()) {
		    	// si ma collection est null je l'initialise
		    	// sinon je test le cout pour mettre a jour ou non la collectionMin
		    	if ( (myMinCollection==null) ) {
		    	    myMinCollection= new MyCollection(mCollection);
		    	} else {
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
		Parser p = Parser.getInstance();

		List<List<Base>> listB = new ArrayList<List<Base>>();
		List<List<String>> listE = new ArrayList<List<String>>();
		List<Base> baseClean = null;
		for (int i=1;i<=3;i++) {
      			try {
			    listB.add(p.parseListeBase("Liste_Bases"+i+".txt"));
			    listE.add(p.parseListe("Liste_Ent"+i+".txt",Parser.LISTE_ENTREPRISE));
			} catch (URISyntaxException e) {
			    System.out.println("erreur de lecture");
			    e.printStackTrace();
			}
			
		}
		
		for (int i=0;i<=2;i++) {
    		    for (int j=0;j<=2;j++){
    			baseClean = Util.clean(listB.get(i),listE.get(j));
    			
    			System.out.println("Bases["+(i+1)+"] & Enterprises["+(j+1)+"]:");
    			
    			long start = System.nanoTime();
    			recursive2(baseClean, listE.get(j), new MyCollection());
    			System.out.print("durée="+( (System.nanoTime()-start)/1000)+"µs\t");
    			Util.out(myMinCollection, listE.get(j), Util.ALGO_RECURS2);
    			
    			start = System.nanoTime();
    			List<Base> b = simpleAlgo(baseClean, listE.get(j));
    			System.out.print("durée="+( (System.nanoTime()-start)/1000)+"µs\t");
    			Util.out(b , listE.get(j), Util.ALGO_1);
    			
    			
    			start = System.nanoTime();
    			b = simpleAlgo2(baseClean, listE.get(j));
    			System.out.print("durée="+( (System.nanoTime()-start)/1000)+"µs\t"); 
    			Util.out(b , listE.get(j), Util.ALGO_2);

    			start = System.nanoTime();
    			b = simpleAlgo3(baseClean, listE.get(j));
    			System.out.print("durée="+( (System.nanoTime()-start)/1000)+"µs\t");
    			Util.out(b, listE.get(j), Util.ALGO_3);
    			
    			myMinCollection=null;
    			System.out.println();
    		    }
    		}
    		System.err.println();
	}
	
}
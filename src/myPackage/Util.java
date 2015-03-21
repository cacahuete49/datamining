package myPackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {
	
	public static final String ALGO_1 = "algo1";
	public static final String ALGO_2 = "algo2";
	public static final String ALGO_3 = "algo3";
	public static final String ALGO_RECURS = "algo_recursive";
	public static final String ALGO_COMPLEX = "algo_complex";
	public static final String ALGO_RECURS2 = "algo_recursive2";
	public static final String BRUT_FORCE = "brute_force";
	
	public static int NB_ALGO=0;
	
	public static int coutTotal(Collection<Base> list) {
		int resultat = 0;
		for ( Base b : list)
			resultat += b.getCout();
		return resultat;
	}
	
	/**
	 * Retourne le nombre d'entreprises qui n'est pas contenu dans la liste
	 * @param list
	 * @param entreprises
	 * @return le nombre d'entreprises qui n'est pas contenu dans la liste 
	 */
	public static int nbEntreprisesRestantes(Collection<Base> list , List<String> entreprises) {
		List <String> tmp = new ArrayList<String>(entreprises);
		
		for ( Base b : list ) {
			tmp.removeAll(b.getEntreprises());
		}
		return tmp.size();
	}
	
	/**
	 * Retourne la base contenant l'entreprise qui coute le moins chère 
	 * @param listB
	 * @param entreprise
	 * @return la base contenant l'entreprise qui coute le moins chère
	 */
	public static Base coutMini( List<Base> listB , String entreprise ) {
		List<Base> bases = new ArrayList<Base>(listB);
		Collections.shuffle(bases);
		Base baseMin = listB.get(0);
		for (Base base : bases) {
			if ( base.getEntreprises().contains(entreprise) ) {
				if ( base.getCout() < baseMin.getCout() )
					baseMin = base;
			}
		}
		return baseMin;
	}
	
	/**
	 * Renvoie la base la moins chère 
	 * @param tab
	 * @return la base la moins chère
	 */
	public static Base min( Base[] tab) {
		Base min = tab[0];
		for (Base b : tab) {
			if (b.getCout()==-1) continue;
			min = ( (min.getCout()==-1) || ( min.getCout() > b.getCout() ) ) ? b : min;
		}
		return min;
	}
	/**
	 * Deprecated ?
	 * @param list
	 * @return
	 */
	public static String affichageBases(Collection<Base> list) {
		String resultat= " ";
		if (list==null) return resultat;
		for (Base b : list)
			resultat += b.getNum()+";";
		return resultat.substring(0, resultat.length()-1);		
	}
	
	/**
	 * Retourne la base la plus grosse contenant l'entreprise
	 * @param bases
	 * @param entreprise
	 * @return référence sur la base contenant le plus d'élément dont l'entreprise
	 */
	public static Base tailleMax( List<Base> bases , String entreprise ) {
		Base baseMax = bases.get(0);
		Collections.shuffle(bases);
		for (Base base : bases) {
			if ( base.getEntreprises().contains(entreprise) ) {
				if ( base.getEntreprises().size() > baseMax.getEntreprises().size() )
					baseMax = base;
			}
		}
		return baseMax;
	}

	/**
	 * 
	 * @param listB
	 * @param entreprises
	 * @param target
	 * @return
	 */
	public static Base maxEfficiency(List<Base> listB, List<String> entreprises, String target){

	    // best
	    Base bestMatch = null;
	    float bestEfficiency = Float.MAX_VALUE;

	    // current
	    Base match;
	    float nb = 0f;
	    for (Base b : listB) {
		if (b.getEntreprises().contains(target)) {
		    match = b;
		    nb = 0;
		    for (String e : entreprises) {
			if (match.getEntreprises().contains(e))
			    nb++;
		    }
		    if ( (match.getCout()/nb) < bestEfficiency) {
			bestEfficiency = (match.getCout()/nb);
		    	bestMatch = match;
		    }
		}
		
	    }
	    
	    return bestMatch;
	}
	
	/**
	 * Retire les Bases ne contenant pas d'entreprises utile
	 * @param listB
	 * @param listE
	 * @return une nouvelle liste ne contenant que les entreprises utiles
	 */
	public static List<Base> clean(List<Base> listB, List<String> listE) {
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
	 * Cette méthode a le même comportement que la méthode remove d'une liste a la différence qu'au lieux de retourner 
	 * un booléen elle renvoi la liste privé de l'élément, sans modifier la liste passé en paramètre
	 * @param bases
	 * @param index
	 * @return bases sans la base situé a l'index
	 */
	public static List<Base> remove( List<Base> bases, int index) {
		List<Base> newList = new ArrayList<Base>(bases.subList( 0, index ));
		newList.addAll( bases.subList( index+1 , bases.size() ));
		return newList;
	}
	
	/**
	 * Affichage des résultats
	 * @param list
	 * @param entreprises
	 * @param type
	 */
	public static void out(Collection<Base> list, List<String> entreprises, String type) {
		System.out.println(type+" n°"+(++NB_ALGO)+":("+list.size()+" éléments) "+affichageBases(list)+" -> cout:"+coutTotal(list));
	}
}

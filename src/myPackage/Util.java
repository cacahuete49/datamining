package myPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {
	
	public static final String ALGO_1 = "algo1";
	public static final String ALGO_2 = "algo2";
	public static final String ALGO_RECURS = "algo_recursive";
	public static final String ALGO_COMPLEX = "algo_complex";
	
	public static int NB_ALGO=0;
	
	public static int coutTotal(List<Base> list) {
		int resultat = 0;
		for ( Base b : list)
			resultat += b.getCout();
		return resultat;
	}
	
	public static int nbEntreprisesRestantes(List<Base> bases , List<String> entreprises) {
		List <String> tmp = new ArrayList<String>(entreprises);
		
		for ( Base b : bases ) {
			tmp.removeAll(b.getEntreprises());
		}
		return tmp.size();
	}
	
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
	
	public static Base min( Base[] tab) {
		Base min = tab[0];
		for (Base b : tab) {
			if (b.getCout()==-1) continue;
			min = ( (min.getCout()==-1) || ( min.getCout() > b.getCout() ) ) ? b : min;
		}
		return min;
	}
	
	public static String affichageBases(List<Base> list) {
		String resultat= " ";
		for (Base b : list)
			resultat += b.getCout()+";";
		return resultat.substring(0, resultat.length()-1);		
	}
	
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
	
	public static List<Base> remove( List<Base> bases, int index) {
		List<Base> newList = new ArrayList<Base>(bases.subList( 0, index ));
		newList.addAll( bases.subList( index+1 , bases.size() ));
		return newList;
	}
	
	public static void out(List<Base> list, List<String> entreprises, String type) {
		System.err.println(type+" n°"+(++NB_ALGO)+":("+list.size()+" éléments) "+affichageBases(list)+" -> cout,"+coutTotal(list)+"; nbEntreprisesRestantes,"+nbEntreprisesRestantes(list, entreprises));
	}
}

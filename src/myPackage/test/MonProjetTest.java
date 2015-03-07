package myPackage.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import myPackage.Base;
import myPackage.MonProjet;
import myPackage.Util;

import org.junit.Test;

public class MonProjetTest {
	
	@Test
	public void coutMiniTest(){
		List<Base> listB = buildListe();
		assertEquals(Util.coutMini(listB,"DIGI+"),listB.get(0));
		assertEquals(Util.coutMini(listB,"NINFO"),listB.get(1));
		assertEquals(Util.coutMini(listB,"HARD-SOFT"),listB.get(2));
		assertEquals(Util.coutMini(listB,"DATA-PASS"),listB.get(3));
	}

	private List<Base> buildListe() {
		List<Base> liste = new ArrayList<Base>();
		Base b1 = new Base(4, new ArrayList<String>() );
			b1.getEntreprises().add("NINFO");
			b1.getEntreprises().add("HARD-SOFT");
			b1.getEntreprises().add("DIGI+");
			liste.add(b1);
		Base b2 = new Base(2, new ArrayList<String>() );
			b2.getEntreprises().add("NINFO");
			b2.getEntreprises().add("WEB-OFF");
			liste.add(b2);
		Base b3 = new Base(3, new ArrayList<String>() );
			b3.getEntreprises().add("DATA-PASS");
			b3.getEntreprises().add("HARD-SOFT");
			liste.add(b3);
		Base b4 = new Base(2, new ArrayList<String>() );
			b4.getEntreprises().add("DATA-PASS");
			b4.getEntreprises().add("WEB-OFF");
			liste.add(b4);
		Base b5 = new Base(10, new ArrayList<String>() );
			b5.getEntreprises().add("DATA-PASS");
			b5.getEntreprises().add("WEB-OFF");
			b5.getEntreprises().add("DIGI+");
			liste.add(b5);
		return liste;
	}
	
	private List<String> buildEntreprises() {
		List<String> entreprises = new ArrayList<String>();
		entreprises.add("WEB-OFF");
		entreprises.add("DIGI+");
		entreprises.add("DATA-PASS");
		return entreprises;
	}
	
	@Test
	public void simpleAlgo1Test() {
		List<Base> listB = buildListe();
		List<String> entreprises = buildEntreprises();
		List<Base> resultat = MonProjet.simpleAlgo(listB, entreprises);

		Util.out(resultat, entreprises, Util.ALGO_1);
	}
	
	@Test
	public void recursiveTest() {
		List<Base> listB = buildListe();
		List<String> entreprises = buildEntreprises();
		MonProjet.recursive(listB, entreprises);
		for ( ConcurrentSkipListSet<Base> item : MonProjet.myCollection) {
			List<Base> cast = new ArrayList<Base>(item);
			Util.out(cast, entreprises, Util.ALGO_RECURS);
		}
			
	}

}

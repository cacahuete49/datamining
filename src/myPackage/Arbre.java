package myPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quentin
 *
 */
public class Arbre implements Comparable<Object>{

	/**
	 * 
	 */
	public static int compteur =0;
	
	private Base base;
	
	private List<Arbre> arbres;
	
	public Arbre() {
		this.base=new Base(0, new ArrayList<String>() );
		this.arbres = new ArrayList<Arbre>();
	}
	
	public Arbre(Base base, List<Arbre> arbres) {
		compteur++;
		this.base = base;
		this.arbres = arbres;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public List<Arbre> getArbres() {
		return arbres;
	}

	public void setArbres(List<Arbre> arbres) {
		this.arbres = arbres;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if ( (this.base == ( (Arbre)o).getBase() ) &&
		(	this.arbres	== ( (Arbre)o).getArbres() ) )
			return 0;
		else
			return 1;
	}

}

/**
 * 
 */
package myPackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author quentin
 *
 */
public class MyCollection extends ConcurrentSkipListSet<Base> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int coutMin;
	
	public MyCollection(){
		super();
		setCoutMin(0);
	}

	public MyCollection(int cout){
		super();
		this.setCoutMin(cout);
	}
	
	public MyCollection(MyCollection mCollection) {
		super(mCollection);
		this.coutMin = mCollection.getCoutMin();
	}

	@Override
	public boolean add(Base base ) {
		boolean ok = super.add(base);
		this.coutMin += base.getCout();
		return ok;
	}
	
	@Override
	public boolean remove(Object o){
	    if ( super.remove((Base)o) ){
		this.coutMin-=((Base)o).getCout();
		return true;
	    }
	    return false;
		
	}
	
	@Override
	public boolean removeAll(Collection<?> c){
		boolean ok=true;
		for (Base i : this){
				if ( !(this.remove(i)) )
					ok=false;
				else
				    this.coutMin-=i.getCout();
		}
		return ok;
		
	}
	
	@Override
	public boolean equals(Object o) {
		if ( o instanceof MyCollection){
			// diff de taille
			if (this.size()!=((MyCollection)o).size())
				return false;
			// diff de cout
			if (this.getCoutMin()!=((MyCollection)o).getCoutMin())
				return false;
			for ( Base b : this) {
				if ( !((MyCollection)o).contains(b) )
					return false;
			}
			return true;
		}
		return false;
	}
	
	public int getCoutMin() {
		return coutMin;
	}

	public void setCoutMin(int coutMin) {
		this.coutMin = coutMin;
	}
	
	public int nbEntreprisesRestantes(List<String> entreprises) {
		ArrayList<String> tmp = new ArrayList<String>(entreprises);
		for ( Base b : this ) {
			tmp.removeAll(b.getEntreprises());
		}
		return tmp.size();
	}
	
	public boolean isIn(String s){
		for (Base b : this) {
			if (b.getEntreprises().contains(s))
				return true;
		}
		return false;
	}
}

package myPackage;

import java.util.ArrayList;
import java.util.List;

public class Base implements Comparable<Object>{
	
	private int cout;
	
	private List<String> entreprises;
	
	public Base(){
		this.setCout(-1);
		this.setEntreprises(new ArrayList<String>());
	}
	
	public Base(int cout , List<String> list){
		this.setCout(cout);
		this.setEntreprises(list);
	}

	public List<String> getEntreprises() {
		return entreprises;
	}

	public void setEntreprises(List<String> entreprises) {
		this.entreprises = entreprises;
	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	@Override
	public int compareTo(Object o) {
		if ( 	( this.cout 	== ((Base)o).getCout() ) 	&& 
			( this.entreprises 	== ((Base)o).getEntreprises() ) )
			return 0;
		else
			return 1;
	}

}

package myPackage;

import java.util.ArrayList;
import java.util.List;

public class Base implements Comparable<Object>{
	
	private int cout;
	
	private final int num;
	
	private List<String> entreprises;
	
	public Base(final int num){
		this.setCout(-1);
		this.setEntreprises(new ArrayList<String>());
		this.num=num;
	}
	
	public Base(int cout, final int num , List<String> list){
		this.setCout(cout);
		this.setEntreprises(list);
		this.num=num;
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

	public int getNum() {
		return num;
	}

}

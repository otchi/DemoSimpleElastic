package org.edifixio.amine.demo.entites;

public class SimpleSearchBean {
	
	private String search="";
	private int from=0;
	private int size=0;
	private int total=0;
	
	public SimpleSearchBean() {
		super();
		from=0; 
	}
	
	public SimpleSearchBean(String search) {
		super();
		this.search = search;
		from=0;
	}

	public boolean next(){
		if(from+size<total) {
			from+=size;
			return true;
		}
		return false;
	}
	
	public boolean last(){
		if(from>0){
			from = (from-size<0) ? 0 : from-size;
			return true;
		}
		return false;
	}
	
	public String getSearch() {
		return search;
	}
	
	public void setSearch(String search) {
		from=0;
		total=0;
		this.search = search;
	}
	public int getFrom() {
		return from;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		from=0;
		this.size = size;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		from=0;
		this.total = total;
	}

	@Override
	public String toString() {
		return "SimpleSearchBean [search=" + search + ", from=" + from + ", size=" + size + ", total=" + total + "]";
	}
	
	
	
}

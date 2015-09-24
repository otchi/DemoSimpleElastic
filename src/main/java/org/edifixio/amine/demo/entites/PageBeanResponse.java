package org.edifixio.amine.demo.entites;

import com.edifixio.amine.application.elasticResults.AggrsResultObject;

public class PageBeanResponse {
	
	private String search;
	private AggrsResultObject aro;
	
	public PageBeanResponse() {
		super();
	}
	
	public PageBeanResponse(String search, AggrsResultObject aro) {
		super();
		this.search = search;
		this.aro = aro;
	}

	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public AggrsResultObject getAro() {
		return aro;
	}
	public void setAro(AggrsResultObject aro) {
		this.aro = aro;
	}
	
	
	

}

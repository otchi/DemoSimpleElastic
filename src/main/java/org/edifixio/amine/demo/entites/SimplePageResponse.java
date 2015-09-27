package org.edifixio.amine.demo.entites;

import com.edifixio.amine.application.elasticResults.AggrsReturnObject;

public class SimplePageResponse {
	
	private String search;
	private AggrsReturnObject aro;
	
	public SimplePageResponse() {
		super();
	}
	
	public SimplePageResponse(String search, AggrsReturnObject aro) {
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
	public AggrsReturnObject getAro() {
		return aro;
	}
	public void setAro(AggrsReturnObject aro) {
		this.aro = aro;
	}
	
	
	

}

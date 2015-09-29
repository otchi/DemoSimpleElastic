package org.edifixio.amine.demo.entites;

public class Detail {
	
	private String biographie;
	private String prix;
	
	public String getBiographie() {
		return biographie;
	}
	public void setBiographie(String biographie) {
		this.biographie = biographie;
	}
	public String getPrix() {
		return prix;
	}
	public void setPrix(String prix) {
		this.prix = prix;
	}
	@Override
	public String toString() {
		return "Detail [biographie=" + biographie + ", prix=" + prix + "]";
	}
	
	
}

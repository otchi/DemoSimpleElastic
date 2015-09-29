package org.edifixio.amine.demo.entites;

public class Voiture {
	
	private String nomVoiture;
	private Integer cylendres;
	private Integer annee;
	private String pays;
	private Integer poid;
	
//	
	public String getNomVoiture() {
		return nomVoiture;
	}
	public void setNomVoiture(String nomVoiture) {
		this.nomVoiture = nomVoiture;
	}
	public Integer getCylendres() {
		return cylendres;
	}
	public void setCylendres(Integer cylendres) {
		this.cylendres = cylendres;
	}
	public Integer getAnnee() {
		return annee;
	}
	public void setAnnee(Integer annee) {
		this.annee = annee;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
		
	public Integer getPoid() {
		return poid;
	}
	public void setPoid(Integer poid) {
		this.poid = poid;
	}
	
	@Override
	public String toString() {
		return "Voiture [nomVoiture=" + nomVoiture + ", cylendres=" + cylendres + ", annee=" + annee + ", pays=" + pays
				+ ", poid=" + poid + "]";
	}


	
	
	
	
	


	
	

}

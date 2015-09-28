package org.edifixio.amine.demo.entites;

public class Ecrivain {

	private String nom;
	private String livreMajeur;
	private String pays;
	private Detail detail;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getLivreMajeur() {
		return livreMajeur;
	}

	public void setLivreMajeur(String livreMajeur) {
		this.livreMajeur = livreMajeur;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

}

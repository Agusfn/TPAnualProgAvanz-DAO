package modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Telefono implements Serializable {

	private int id;
	private String nroPersonal;
	private String nroCelular;
	private String nroLaboral;

	
	
	public Telefono(String nroPersonal, String nroCelular, String nroLaboral) {
		super();
		this.nroPersonal = nroPersonal;
		this.nroCelular = nroCelular;
		this.nroLaboral = nroLaboral;
	}
	public Telefono() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getNroPersonal() {
		return nroPersonal;
	}
	public void setNroPersonal(String nroPersonal) {
		this.nroPersonal = nroPersonal;
	}
	public String getNroCelular() {
		return nroCelular;
	}
	public void setNroCelular(String nroCelular) {
		this.nroCelular = nroCelular;
	}
	public String getNroLaboral() {
		return nroLaboral;
	}
	public void setNroLaboral(String nroLaboral) {
		this.nroLaboral = nroLaboral;
	}
	
}

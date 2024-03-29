package modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PasajeroFrecuente implements Serializable {

	private int id;
	private Aerolinea aerolinea;
	private String numero;
	private String categoria;
	
	public PasajeroFrecuente() 
	{
	}	
	public PasajeroFrecuente(int id) 
	{
		this.id = id;
	}
	public PasajeroFrecuente(Aerolinea aerolinea, String numero, String categoria) {
		super();
		this.aerolinea = aerolinea;
		this.numero = numero;
		this.categoria = categoria;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public Aerolinea getAerolinea() {
		return aerolinea;
	}
	public void setAerolinea(Aerolinea aerolinea) {
		this.aerolinea = aerolinea;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
	
}

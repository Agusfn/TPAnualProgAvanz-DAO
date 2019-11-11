package modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Provincia implements Serializable {

	private int id;
	private String nombre;
	private Pais pais;
	
	public Provincia () {
		
	}
	public Provincia(int id) 
	{
		this.id = id;
	}
	public Provincia(String nombre, Pais pais) {
		super();
		this.nombre = nombre;
		this.pais = pais;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	
	
	
	
}

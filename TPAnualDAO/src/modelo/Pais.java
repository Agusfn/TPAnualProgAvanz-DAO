package modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Pais implements Serializable {

	private int id;
	private String nombre;
	
	
	public Pais() 
	{
	}		
	public Pais(int id) 
	{
		this.id = id;
	}
	
	public Pais(String nombre) {
		super();
		this.nombre = nombre;
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
	
}

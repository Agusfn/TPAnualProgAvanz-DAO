package modelo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Aerolinea implements Serializable {

	private int id;
	private String nombre;
	private Alianza alianza;
	private List<Vuelo> vuelos;

	public Aerolinea() {
		// TODO Auto-generated constructor stub
	}
	public Aerolinea(int id) 
	{
		this.id = id;
	}
	public Aerolinea(String nombre, Alianza alianza, List<Vuelo> vuelos) {
		super();
		this.nombre = nombre;
		this.alianza = alianza;
		this.vuelos = vuelos;
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
	public Alianza getAlianza() {
		return alianza;
	}
	public void setAlianza(Alianza alianza) {
		this.alianza = alianza;
	}
	public List<Vuelo> getVuelos() {
		return vuelos;
	}
	public void setVuelos(List<Vuelo> vuelos) {
		this.vuelos = vuelos;
	}
	
	
	
}

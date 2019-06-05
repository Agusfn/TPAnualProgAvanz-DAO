package modelo;

import java.util.List;

public class Aerolinea {

	private int id;
	private String nombre;
	private String alianza;
	private List<Vuelo> vuelos;
	
	
	public Aerolinea(int id, String nombre, String alianza, List<Vuelo> vuelos) {
		super();
		this.id = id;
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
	public String getAlianza() {
		return alianza;
	}
	public void setAlianza(String alianza) {
		this.alianza = alianza;
	}
	public List<Vuelo> getVuelos() {
		return vuelos;
	}
	public void setVuelos(List<Vuelo> vuelos) {
		this.vuelos = vuelos;
	}
	
	
	
}

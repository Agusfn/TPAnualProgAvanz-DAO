package modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Aeropuerto implements Serializable {

	private int id;	
	private String identificacion;
	private String ciudad;
	private Provincia provincia;
	private String nombreProvincia;
	private Pais pais;
	private String nombrePais;
	

	public Aeropuerto() 
	{
	}
	public Aeropuerto(int id) 
	{
		this.id = id;
	}
	public Aeropuerto(String identificacion, String ciudad, Provincia provincia, Pais pais) {
		super();
		this.identificacion = identificacion;
		this.ciudad = ciudad;
		this.provincia = provincia;
		this.pais = pais;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public Provincia getProvincia() {
		return provincia;
	}
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public String getNombreProvincia() {
		return nombreProvincia;
	}
	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}
	public String getNombrePais() {
		return nombrePais;
	}
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}
	
	
	
}

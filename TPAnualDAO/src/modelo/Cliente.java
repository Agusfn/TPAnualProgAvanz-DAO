package modelo;

import java.util.Date;

public class Cliente {

	private int id;
	private String nombreYApellido;
	private String dni;
	private Pasaporte pasaporte;
	private String cuitOCuil;
	private Date fechaNacimiento;
	private String email;
	private Telefono telefono;
	private PasajeroFrecuente pasajeroFrecuente;
	private Direccion direccion;
	

	public Cliente(int id, String nombreYApellido, String dni, Pasaporte pasaporte, String cuitOCuil,
			Date fechaNacimiento, String email, Telefono telefono, PasajeroFrecuente pasajeroFrecuente,
			Direccion direccion) {
		super();
		this.id = id;
		this.nombreYApellido = nombreYApellido;
		this.dni = dni;
		this.pasaporte = pasaporte;
		this.cuitOCuil = cuitOCuil;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.telefono = telefono;
		this.pasajeroFrecuente = pasajeroFrecuente;
		this.direccion = direccion;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getNombreYApellido() {
		return nombreYApellido;
	}
	public void setNombreYApellido(String nombreYApellido) {
		this.nombreYApellido = nombreYApellido;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Pasaporte getPasaporte() {
		return pasaporte;
	}
	public void setPasaporte(Pasaporte pasaporte) {
		this.pasaporte = pasaporte;
	}
	public String getCuitOCuil() {
		return cuitOCuil;
	}
	public void setCuitOCuil(String cuitOCuil) {
		this.cuitOCuil = cuitOCuil;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Telefono getTelefono() {
		return telefono;
	}
	public void setTelefono(Telefono telefono) {
		this.telefono = telefono;
	}
	public PasajeroFrecuente getPasajeroFrecuente() {
		return pasajeroFrecuente;
	}
	public void setPasajeroFrecuente(PasajeroFrecuente pasajeroFrecuente) {
		this.pasajeroFrecuente = pasajeroFrecuente;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	
}

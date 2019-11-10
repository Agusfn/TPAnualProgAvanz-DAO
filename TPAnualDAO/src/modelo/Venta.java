package modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class Venta implements Serializable {

	private int id;
	private Cliente cliente;
	private Vuelo vuelo;
	private Aerolinea aerolinea;
	private LocalDateTime fechaHora;
	private String formaDePago;
	
	public Venta () {
	}
	
	
	public Venta(Cliente cliente, Vuelo vuelo, Aerolinea aerolinea, LocalDateTime fechaHora, String formaDePago) {
		super();
		this.cliente = cliente;
		this.vuelo = vuelo;
		this.aerolinea = aerolinea;
		this.fechaHora = fechaHora;
		this.formaDePago = formaDePago;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}		
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Vuelo getVuelo() {
		return vuelo;
	}
	public void setVuelo(Vuelo vuelo) {
		this.vuelo = vuelo;
	}
	public Aerolinea getAerolinea() {
		return aerolinea;
	}
	public void setAerolinea(Aerolinea aerolinea) {
		this.aerolinea = aerolinea;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	public String getFormaDePago() {
		return formaDePago;
	}
	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}
	
	
	

}

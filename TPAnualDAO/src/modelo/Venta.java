package modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class Venta implements Serializable {

	private int id;
	private Cliente cliente;
	private Vuelo vuelo;
	private LocalDateTime fechaHora;
	private String formaDePago;
	private double monto;
	private int cuotas;
	
	public Venta () {
	}
	
	public Venta(int id) 
	{
		this.id = id;
	}
	public Venta(Cliente cliente, Vuelo vuelo, LocalDateTime fechaHora, String formaDePago, double monto, int cuotas) {
		super();
		this.cliente = cliente;
		this.vuelo = vuelo;
		this.fechaHora = fechaHora;
		this.formaDePago = formaDePago;
		this.monto = monto;
		this.cuotas = cuotas;
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

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public int getCuotas() {
		return cuotas;
	}

	public void setCuotas(int cuotas) {
		this.cuotas = cuotas;
	}
	
	
	

}

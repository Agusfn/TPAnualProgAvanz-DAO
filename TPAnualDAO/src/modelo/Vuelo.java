package modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class Vuelo implements Serializable {

	private int id;
	private String numero;
	private int cantAsientos;
	private Aeropuerto aeropSalida;
	private Aeropuerto aeropLlegada;
	private LocalDateTime fechaHoraSalida;
	private LocalDateTime fechaHoraLlegada;
	private int tiempoVueloMinutos;
	
	public Vuelo() {
	}
	public Vuelo(int id) 
	{
		this.id = id;
	}
	public Vuelo(String numero, int cantAsientos, Aeropuerto aeropSalida, Aeropuerto aeropLlegada,
			LocalDateTime fechaHoraSalida, LocalDateTime fechaHoraLlegada, int tiempoVueloMinutos) {
		super();
		this.numero = numero;
		this.cantAsientos = cantAsientos;
		this.aeropSalida = aeropSalida;
		this.aeropLlegada = aeropLlegada;
		this.fechaHoraSalida = fechaHoraSalida;
		this.fechaHoraLlegada = fechaHoraLlegada;
		this.tiempoVueloMinutos = tiempoVueloMinutos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getCantAsientos() {
		return cantAsientos;
	}
	public void setCantAsientos(int cantAsientos) {
		this.cantAsientos = cantAsientos;
	}
	public Aeropuerto getAeropSalida() {
		return aeropSalida;
	}
	public void setAeropSalida(Aeropuerto aeropSalida) {
		this.aeropSalida = aeropSalida;
	}
	public Aeropuerto getAeropLlegada() {
		return aeropLlegada;
	}
	public void setAeropLlegada(Aeropuerto aeropLlegada) {
		this.aeropLlegada = aeropLlegada;
	}
	public LocalDateTime getFechaHoraSalida() {
		return fechaHoraSalida;
	}
	public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
		this.fechaHoraSalida = fechaHoraSalida;
	}
	public LocalDateTime getFechaHoraLlegada() {
		return fechaHoraLlegada;
	}
	public void setFechaHoraLlegada(LocalDateTime fechaHoraLlegada) {
		this.fechaHoraLlegada = fechaHoraLlegada;
	}
	public int getTiempoVueloMinutos() {
		return tiempoVueloMinutos;
	}
	public void setTiempoVueloMinutos(int tiempoVueloMinutos) {
		this.tiempoVueloMinutos = tiempoVueloMinutos;
	}
	
	
}

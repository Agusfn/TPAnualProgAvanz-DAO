package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Vuelo {

	private int id;
	private String numero;
	private int cantAsientos;
	private Aeropuerto aeropSalida;
	private Aeropuerto aeropLlegada;
	private LocalDateTime fechaHoraSalida;
	private LocalDateTime fechaHoraLlegada;
	private String tiempoDeVuelo;
	
	public Vuelo() {
		
	}
	
	public Vuelo(int id, String numero, int cantAsientos, Aeropuerto aeropSalida, Aeropuerto aeropLlegada,
			LocalDateTime fechaHoraSalida, LocalDateTime fechaHoraLlegada, String tiempoDeVuelo) {
		super();
		this.id = id;
		this.numero = numero;
		this.cantAsientos = cantAsientos;
		this.aeropSalida = aeropSalida;
		this.aeropLlegada = aeropLlegada;
		this.fechaHoraSalida = fechaHoraSalida;
		this.fechaHoraLlegada = fechaHoraLlegada;
		this.tiempoDeVuelo = tiempoDeVuelo;
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
	public String getTiempoDeVuelo() {
		return tiempoDeVuelo;
	}
	public void setTiempoDeVuelo(String tiempoDeVuelo) {
		this.tiempoDeVuelo = tiempoDeVuelo;
	}
	
	
}

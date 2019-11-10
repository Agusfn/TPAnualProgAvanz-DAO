package modelo;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class Pasaporte implements Serializable {

	private int id;
	private String numero;
	private Pais paisEmision;
	private String autoridadEmision;
	private LocalDate fechaEmision;
	private LocalDate fechVencimiento;
	
	public Pasaporte()
	{
	}

	public Pasaporte(String numero, Pais paisEmision, String autoridadEmision, LocalDate fechaEmision,
			LocalDate fechVencimiento) {
		super();
		this.numero = numero;
		this.paisEmision = paisEmision;
		this.autoridadEmision = autoridadEmision;
		this.fechaEmision = fechaEmision;
		this.fechVencimiento = fechVencimiento;
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
	public Pais getPaisEmision() {
		return paisEmision;
	}
	public void setPaisEmision(Pais paisEmision) {
		this.paisEmision = paisEmision;
	}
	public String getAutoridadEmision() {
		return autoridadEmision;
	}
	public void setAutoridadEmision(String autoridadEmision) {
		this.autoridadEmision = autoridadEmision;
	}
	public LocalDate getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(LocalDate fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public LocalDate getFechVencimiento() {
		return fechVencimiento;
	}
	public void setFechVencimiento(LocalDate fechVencimiento) {
		this.fechVencimiento = fechVencimiento;
	}
	
}

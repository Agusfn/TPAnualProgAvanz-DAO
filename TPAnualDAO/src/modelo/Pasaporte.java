package modelo;

import java.util.Date;

public class Pasaporte {

	private int id;
	private String numero;
	private Pais paisEmision;
	private String autoridadEmision;
	private Date fechaEmision;
	private Date fechVencimiento;
	

	public Pasaporte(int id, String numero, Pais paisEmision, String autoridadEmision, Date fechaEmision,
			Date fechVencimiento) {
		super();
		this.id = id;
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
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public Date getFechVencimiento() {
		return fechVencimiento;
	}
	public void setFechVencimiento(Date fechVencimiento) {
		this.fechVencimiento = fechVencimiento;
	}
	
}

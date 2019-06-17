package modelo;

public class PasajeroFrecuente {

	private int id;
	private String alianza;
	private Aerolinea aerolinea;
	private String numero;
	private String categoria;
	
	public PasajeroFrecuente()
	{
	}

	public PasajeroFrecuente(int id, String alianza, Aerolinea aerolinea, String numero, String categoria) {
		super();
		this.id = id;
		this.alianza = alianza;
		this.aerolinea = aerolinea;
		this.numero = numero;
		this.categoria = categoria;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getAlianza() {
		return alianza;
	}
	public void setAlianza(String alianza) {
		this.alianza = alianza;
	}
	public Aerolinea getAerolinea() {
		return aerolinea;
	}
	public void setAerolinea(Aerolinea aerolinea) {
		this.aerolinea = aerolinea;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
	
}

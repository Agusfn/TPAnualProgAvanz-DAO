package dao.implementations.archivo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IPasaporteDao;
import modelo.Pasaporte;
import util.Archivo;
import util.Dates;
import util.Properties;

public class PasaporteDaoImplArchivo implements IPasaporteDao {

	
	/**
	 * Nombre del archivo que almacenará al objeto.
	 */
	private static String nombreArchivo = Properties.getProperty("csv_pasaportes");
	
	
	/**
	 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en toda la clase.
	 */
	private Archivo archivo;
	
	
	/**
	 * Inicializar DAO, creando archivo si no existe.
	 */
	public PasaporteDaoImplArchivo() throws IOException
	{
		archivo = new Archivo(nombreArchivo);
		
		if(!archivo.existe()) {
			archivo.crear();
		}
	}
	
	
	/**
	 * Obtener un pais del archivo a partir de su id.
	 */
	@Override
	public Pasaporte obtener(int id) throws IOException {
		
		String linea;

		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Pasaporte pasaporte = csvToPasaporte(linea);
			
			if(pasaporte.getId() == id) {
				archivo.reiniciarReader();
				return pasaporte;
			}	
		}
		
		return null;
	}
	
	
	/**
	 * Obtener lista de todos los paises del archivo.
	 */
	@Override
	public List<Pasaporte> obtenerTodos() throws IOException {
		
		List<Pasaporte> pasaportes = new ArrayList<Pasaporte>();
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			pasaportes.add(csvToPasaporte(linea));
		}
		
		return pasaportes;
	}

	
	/**
	 * Agregar nuevo pais al archivo. Se le asigna una id nueva auto-incremental.
	 * No se debe usar este metodo con un objeto ya existente en el archivo porque se duplica.
	 */
	@Override
	public void agregar(Pasaporte pasaporte) throws IOException {

		pasaporte.setId(obtenerSiguienteId());
		
		String lineaCsv = pasaporteToCsv(pasaporte);
		
		archivo.agregarLinea(lineaCsv);
	}

	
	/**
	 * Eliminar país de archivo.
	 * Se elimina la linea del pais que posea la id del pais dado.
	 */
	@Override
	public void eliminar(Pasaporte pasaporteAEliminar) throws IOException {
		
		String contenido = "";
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Pasaporte pasaporte = csvToPasaporte(linea);
			if(pasaporte.getId() != pasaporteAEliminar.getId()) {
				contenido += pasaporteToCsv(pasaporte) + System.lineSeparator();
			}
		}
		archivo.guardarContenido(contenido);
	}

	/**
	 * Actualizar un pais en el archivo. Se actualiza el país según la id.
	 */
	@Override
	public void actualizar(Pasaporte pasaporte) throws IOException {

		String contenido = "";
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			Pasaporte pasaporteLinea = csvToPasaporte(linea);
			if(pasaporteLinea.getId() == pasaporte.getId()) {
				contenido += pasaporteToCsv(pasaporte) + System.lineSeparator();
			}
			else {
				contenido += linea + System.lineSeparator();
			}
			
		}
		archivo.guardarContenido(contenido);
		
	}
	
	/**
	 * Obtiene id del siguiente objeto a agregar al archivo.
	 * Esta id es la id del ultimo objeto + 1. Si no hay elementos, devuelve 1.
	 * @return
	 */
	private int obtenerSiguienteId() throws IOException
	{

		Pasaporte ultimoPasaporte = null;
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			ultimoPasaporte = csvToPasaporte(linea);
		}
		
		if(ultimoPasaporte == null)
			return 1;
		else 
			return ultimoPasaporte.getId() + 1;
	}
	
	
	/**
	 * Convierte una línea CSV de archivo a un país.
	 * @param csv
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws IOException 
	 */
	private Pasaporte csvToPasaporte(String csv) throws ArrayIndexOutOfBoundsException, IOException
	{
		String[] props = csv.split(",");
		
		Pasaporte pasaporte = new Pasaporte();
		pasaporte.setId(Integer.parseInt(props[0]));
		pasaporte.setNumero(props[1]);
		
		int idPais = Integer.parseInt(props[2]);
		PaisDaoImplArchivo dao = new PaisDaoImplArchivo();
		pasaporte.setPaisEmision(dao.obtener(idPais));
		
		pasaporte.setAutoridadEmision(props[3]);
		pasaporte.setFechaEmision((LocalDate)Dates.fromString(props[4]));
		pasaporte.setFechVencimiento((LocalDate)Dates.fromString(props[5]));
		
		
		return pasaporte;
	}
	
	
	private String pasaporteToCsv(Pasaporte pasaporte)
	{
		return pasaporte.getId() + "," + pasaporte.getNumero() + "," + pasaporte.getPaisEmision().getId() + "," + 
				pasaporte.getAutoridadEmision() + "," + Dates.toString(pasaporte.getFechaEmision()) + "," + 
				Dates.toString(pasaporte.getFechVencimiento());
	}
	
	
	
}

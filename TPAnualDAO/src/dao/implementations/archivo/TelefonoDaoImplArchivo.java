package dao.implementations.archivo;

import java.util.ArrayList;
import java.util.List;

import dao.interfaces.ITelefonoDao;
import modelo.Telefono;
import util.Archivo;
import util.Properties;

public class TelefonoDaoImplArchivo implements ITelefonoDao {

	

	/**
	 * Nombre del archivo que almacenará al objeto.
	 */
	private static String nombreArchivo = Properties.getProperty("csv_telefonos");
	
	
	/**
	 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en toda la clase.
	 */
	private Archivo archivo;
	
	
	/**
	 * Inicializar DAO, creando archivo si no existe.
	 */
	public TelefonoDaoImplArchivo()
	{
		archivo = new Archivo(nombreArchivo);
		
		if(!archivo.existe()) {
			archivo.crear();
		}
	}
	
	
	/**
	 * Obtener un telefono del archivo a partir de su id.
	 */
	@Override
	public Telefono obtener(int id) {
		
		String linea;

		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Telefono telefono = csvToTelefono(linea);
			
			if(telefono.getId() == id) {
				archivo.reiniciarReader();
				return telefono;
			}	
		}
		
		return null;
	}
	
	
	/**
	 * Obtener lista de todos los telefonos del archivo.
	 */
	@Override
	public List<Telefono> obtenerTodos() {
		
		List<Telefono> telefonos = new ArrayList<Telefono>();
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			telefonos.add(csvToTelefono(linea));
		}
		
		return telefonos;
	}

	
	/**
	 * Agregar nuevo telefono al archivo. Se le asigna una id nueva auto-incremental.
	 * No se debe usar este metodo con un objeto ya existente en el archivo porque se duplica.
	 */
	@Override
	public void agregar(Telefono telefono) {

		telefono.setId(obtenerSiguienteId());
		
		String lineaCsv = telefonoToCsv(telefono);
		
		archivo.agregarLinea(lineaCsv);
	}

	
	/**
	 * Eliminar telefono de archivo.
	 * Se elimina la linea del telefono que posea la id del telefono dado.
	 */
	@Override
	public void eliminar(Telefono telefonoAEliminar) {
		
		List<String> lineas = new ArrayList<String>();
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Telefono telefono = csvToTelefono(linea);
			if(telefono.getId() != telefonoAEliminar.getId()) {
				lineas.add(linea);
			}
			
		}
		
	}

	/**
	 * Actualizar un telefono en el archivo. Se actualiza el telefono según la id.
	 */
	@Override
	public void actualizar(Telefono telefono) {

		String contenido = "";
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			Telefono telefonoLinea = csvToTelefono(linea);
			if(telefonoLinea.getId() == telefono.getId()) {
				contenido += telefonoToCsv(telefono) + System.lineSeparator();
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
	private int obtenerSiguienteId()
	{

		Telefono ultimoTelefono = null;
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			ultimoTelefono = csvToTelefono(linea);
		}
		
		if(ultimoTelefono == null)
			return 1;
		else 
			return ultimoTelefono.getId() + 1;
	}
	
	
	/**
	 * Convierte una línea CSV de archivo a un telefono.
	 * @param csv
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	private Telefono csvToTelefono(String csv) throws ArrayIndexOutOfBoundsException
	{
		String[] props = csv.split(",");
		
		Telefono telefono = new Telefono();
		telefono.setId(Integer.parseInt(props[0]));
		telefono.setNroPersonal(props[1]);
		telefono.setNroCelular(props[2]);
		telefono.setNroLaboral(props[3]);
		
		return telefono;
	}
	
	
	private String telefonoToCsv(Telefono telefono)
	{
		return telefono.getId() + "," + telefono.getNroPersonal() + "," + telefono.getNroCelular() + "," + telefono.getNroLaboral();
	}
	
	
	
}

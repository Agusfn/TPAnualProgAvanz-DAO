package dao.implementations.archivo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.IAlianzaDao;
import modelo.Alianza;
import util.Archivo;
import util.Properties;

public class AlianzaDaoImplArchivo implements IAlianzaDao {

	
	/**
	 * Nombre del archivo que almacenará al objeto.
	 */
	private static String nombreArchivo = Properties.getProperty("csv_alianzas");
	
	
	/**
	 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en toda la clase.
	 */
	private Archivo archivo;
	
	
	/**
	 * Inicializar DAO, creando archivo si no existe.
	 */
	public AlianzaDaoImplArchivo() throws IOException
	{
		archivo = new Archivo(nombreArchivo);
		
		if(!archivo.existe()) {
			archivo.crear();
		}
	}
	
	
	/**
	 * Obtener un alianza del archivo a partir de su id.
	 */
	@Override
	public Alianza obtener(int id) throws IOException {
		
		String linea;

		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Alianza alianza = csvToAlianza(linea);
			
			if(alianza.getId() == id) {
				archivo.reiniciarReader();
				return alianza;
			}	
		}
		return null;
	}
	
	
	/**
	 * Obtener lista de todos los alianzas del archivo.
	 */
	@Override
	public List<Alianza> obtenerTodos() throws IOException {
		
		List<Alianza> alianzas = new ArrayList<Alianza>();
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			alianzas.add(csvToAlianza(linea));
		}
		
		return alianzas;
	}

	
	/**
	 * Agregar nuevo alianza al archivo. Se le asigna una id nueva auto-incremental.
	 * No se debe usar este metodo con un objeto ya existente en el archivo porque se duplica.
	 */
	@Override
	public void agregar(Alianza alianza) throws IOException {

		alianza.setId(obtenerSiguienteId());
		
		String lineaCsv = alianzaToCsv(alianza);
		
		archivo.agregarLinea(lineaCsv);
	}

	
	/**
	 * Eliminar país de archivo.
	 * Se elimina la linea del alianza que posea la id del alianza dado.
	 */
	@Override
	public void eliminar(Alianza alianzaAEliminar) throws IOException {
		
		String contenido = "";
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Alianza alianza = csvToAlianza(linea);
			if(alianza.getId() != alianzaAEliminar.getId()) {
				contenido += alianzaToCsv(alianza) + System.lineSeparator();
			}
		}
		archivo.guardarContenido(contenido);
	}

	
	/**
	 * Actualizar un alianza en el archivo. Se actualiza el país según la id.
	 */
	@Override
	public void actualizar(Alianza alianza) throws IOException {

		String contenido = "";
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			Alianza alianzaLinea = csvToAlianza(linea);
			if(alianzaLinea.getId() == alianza.getId()) {
				contenido += alianzaToCsv(alianza) + System.lineSeparator();
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

		Alianza ultimoAlianza = null;
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			ultimoAlianza = csvToAlianza(linea);
		}
		
		if(ultimoAlianza == null)
			return 1;
		else 
			return ultimoAlianza.getId() + 1;
	}
	
	
	/**
	 * Convierte una línea CSV de archivo a un país.
	 * @param csv
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	private Alianza csvToAlianza(String csv) throws ArrayIndexOutOfBoundsException
	{
		String[] props = csv.split(",");
		
		Alianza alianza = new Alianza();
		alianza.setId(Integer.parseInt(props[0]));
		alianza.setNombre(props[1]);
		
		return alianza;
	}
	
	
	private String alianzaToCsv(Alianza alianza)
	{
		return alianza.getId() + "," + alianza.getNombre();
	}
	
	
	
	public void close() throws Exception
	{
	}
	
}

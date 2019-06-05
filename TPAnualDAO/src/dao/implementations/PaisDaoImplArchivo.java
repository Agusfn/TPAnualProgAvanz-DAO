package dao.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.IPaisDao;
import modelo.Pais;
import util.Archivo;

public class PaisDaoImplArchivo implements IPaisDao {

	
	/**
	 * Nombre del archivo que almacenará al objeto.
	 */
	private static String nombreArchivo = "paises.txt";
	
	
	/**
	 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en toda la clase.
	 */
	private Archivo archivo;
	
	
	/**
	 * Inicializar DAO, creando archivo si no existe.
	 * @throws IOException
	 */
	public PaisDaoImplArchivo() throws IOException
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
	public Pais obtener(int id) throws IOException {
		
		String linea;

		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Pais pais = csvToPais(linea);
			
			if(pais.getId() == id) {
				archivo.reiniciarReader();
				return pais;
			}	
		}
		
		return null;
	}
	
	
	/**
	 * Obtener lista de todos los paises del archivo.
	 */
	@Override
	public List<Pais> obtenerTodos() throws IOException {
		
		List<Pais> paises = new ArrayList<Pais>();
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			paises.add(csvToPais(linea));
		}
		
		return paises;
	}

	
	/**
	 * Agregar nuevo pais al archivo. Se le asigna una id nueva auto-incremental.
	 * No se debe usar este metodo con un objeto ya existente en el archivo porque se duplica.
	 */
	@Override
	public void agregar(Pais pais) throws IOException {

		pais.setId(obtenerSiguienteId());
		
		String lineaCsv = paisToCsv(pais);
		
		archivo.agregarLinea(lineaCsv);
	}

	
	/**
	 * Eliminar país de archivo.
	 * Se elimina la linea del pais que posea la id del pais dado.
	 */
	@Override
	public void eliminar(Pais paisAEliminar) throws IOException {
		
		List<String> lineas = new ArrayList<String>();
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Pais pais = csvToPais(linea);
			if(pais.getId() != paisAEliminar.getId()) {
				lineas.add(linea);
			}
			
		}
		
	}

	/**
	 * Actualizar un pais en el archivo. Se actualiza el país según la id.
	 */
	@Override
	public void actualizar(Pais pais) throws IOException {

		String contenido = "";
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			Pais paisLinea = csvToPais(linea);
			if(paisLinea.getId() == pais.getId()) {
				contenido += paisToCsv(pais) + System.lineSeparator();
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
	 * @throws IOException
	 */
	private int obtenerSiguienteId() throws IOException
	{

		Pais ultimoPais = null;
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			ultimoPais = csvToPais(linea);
		}
		
		if(ultimoPais == null)
			return 1;
		else 
			return ultimoPais.getId() + 1;
	}
	
	
	/**
	 * Convierte una línea CSV de archivo a un país.
	 * @param csv
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	private Pais csvToPais(String csv) throws ArrayIndexOutOfBoundsException
	{
		String[] props = csv.split(",");
		
		Pais pais = new Pais();
		pais.setId(Integer.parseInt(props[0]));
		pais.setNombre(props[1]);
		
		return pais;
	}
	
	
	private String paisToCsv(Pais pais)
	{
		return pais.getId() + "," + pais.getNombre();
	}
	
	
	
}

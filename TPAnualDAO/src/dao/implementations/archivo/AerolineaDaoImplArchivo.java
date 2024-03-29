package dao.implementations.archivo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IAerolineaDao;
import modelo.Aerolinea;
import modelo.Alianza;
import util.Archivo;
import util.Properties;

public class AerolineaDaoImplArchivo implements IAerolineaDao {


	
	/*
	 * Nombre del archivo que almacenar� al objeto.
	 */
	private static String nombreArchivo = Properties.getProperty("csv_aerolineas");
	
	
	/**
	 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en toda la clase.
	 */
	private Archivo archivo;
	
	
	/**
	 * Inicializar DAO, creando archivo si no existe.
	 */
	public AerolineaDaoImplArchivo() throws IOException
	{
		
		archivo = new Archivo(nombreArchivo);
		
		if(!archivo.existe()) {
			archivo.crear();
		}
	}
	
	
	/**
	 * Obtener una aerolinea del archivo a partir de su id.
	 */
	@Override
	public Aerolinea obtener (int id) throws IOException {
		
		String linea;

		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Aerolinea aerolinea = csvToAerolinea(linea);
			
			if(aerolinea.getId() == id) {
				archivo.reiniciarReader();
				return aerolinea;
			}	
		}
		
		return null;
	}
	
	
	/**
	 * Obtener lista de todos las aerolineas del archivo.
	 */
	@Override
	public List<Aerolinea> obtenerTodos() throws IOException {
		
		List<Aerolinea> aerolineas = new ArrayList<Aerolinea>();
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			aerolineas.add(csvToAerolinea(linea));
		}
		
		return aerolineas;
	}

	
	/**
	 * Agregar nueva aerolinea al archivo. Se le asigna una id nueva auto-incremental.
	 * No se debe usar este metodo con un objeto ya existente en el archivo porque se duplica.
	 */
	@Override
	public void agregar(Aerolinea aerolinea) throws IOException {

		aerolinea.setId(obtenerSiguienteId());
		
		String lineaCsv = aerolineaToCsv(aerolinea);
		
		archivo.agregarLinea(lineaCsv);
	}

	
	/**
	 * Eliminar aerolinea de archivo.
	 * Se elimina la linea de la aerolinea que posea la id de la aerolinea dada.
	 */
	@Override
	public void eliminar(Aerolinea aerolineaAEliminar) throws IOException {
		
		List<String> lineas = new ArrayList<String>();
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Aerolinea aerolinea = csvToAerolinea(linea);
			if(aerolinea.getId() != aerolineaAEliminar.getId()) {
				lineas.add(linea);
			}
			
		}
		
	}

	/**
	 * Actualizar una aerolinea en el archivo. Se actualiza la aerolinea seg�n la id.
	 */
	@Override
	public void actualizar(Aerolinea aerolinea) throws IOException {

		String contenido = "";
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			Aerolinea aerolineaLinea = csvToAerolinea(linea);
			if(aerolineaLinea.getId() == aerolinea.getId()) {
				contenido += aerolineaToCsv(aerolinea) + System.lineSeparator();
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

		Aerolinea ultimoAerolinea = null;
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			ultimoAerolinea = csvToAerolinea(linea);
		}
		
		if(ultimoAerolinea == null)
			return 1;
		else 
			return ultimoAerolinea.getId() + 1;
	}
	
	
	/**
	 * Convierte una l�nea CSV de archivo a una aerolinea.
	 * @param csv
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	private Aerolinea csvToAerolinea(String csv) throws ArrayIndexOutOfBoundsException
	{
		String[] props = csv.split(",");
		
		Aerolinea aerolinea = new Aerolinea();
		aerolinea.setId(Integer.parseInt(props[0]));
		aerolinea.setNombre(props[1]);
		
		Alianza alianza = null;
		if(!props[2].equals("")) {
			alianza = new Alianza(Integer.parseInt(props[2]));
		}
		aerolinea.setAlianza(alianza);
		
		return aerolinea;
	}
	
	
	private String aerolineaToCsv(Aerolinea aerolinea)
	{
		String idAlianza = "";
		if(aerolinea.getAlianza() != null) {
			idAlianza = Integer.toString(aerolinea.getAlianza().getId());
		}
		
		return aerolinea.getId() + "," + aerolinea.getNombre() + "," + idAlianza;
		
	}

	
	public void close() throws Exception
	{
	}


}

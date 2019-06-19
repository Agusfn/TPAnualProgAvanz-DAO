package dao.implementations;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.IVueloDao;
import modelo.Vuelo;
import util.*;

public class VueloDaoImplArchivo implements IVueloDao {

	
	/**
	 * Nombre del archivo que almacenará al objeto.
	 */
	private static String nombreArchivo = "Vuelos.txt";
	
	
	/**
	 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en toda la clase.
	 */
	private Archivo archivo;
	
	
	/**
	 * Inicializar DAO, creando archivo si no existe.
	 * @throws IOException
	 */
	public VueloDaoImplArchivo() throws IOException
	{
		archivo = new Archivo(nombreArchivo);
		
		if(!archivo.existe()) {
			archivo.crear();
		}
	}
	
	
	/**
	 * Obtener un vuelo del archivo a partir de su id.
	 */
	@Override
	public Vuelo obtener(int id) throws IOException {
		
		String linea;

		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Vuelo vuelo = csvToVuelo(linea);
			
			if(vuelo.getId() == id) {
				archivo.reiniciarReader();
				return vuelo;
			}	
		}
		
		return null;
	}
	
	
	/**
	 * Obtener lista de todos los vuelos del archivo.
	 */
	@Override
	public List<Vuelo> obtenerTodos() throws IOException {
		
		List<Vuelo> vuelos = new ArrayList<Vuelo>();
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			vuelos.add(csvToVuelo(linea));
		}
		
		return vuelos;
	}

	
	/**
	 * Agregar nuevo vuelo al archivo. Se le asigna una id nueva auto-incremental.
	 * No se debe usar este metodo con un objeto ya existente en el archivo porque se duplica.
	 */
	@Override
	public void agregar(Vuelo vuelo) throws IOException {

		vuelo.setId(obtenerSiguienteId());
		
		String lineaCsv = VueloToCsv(vuelo);
		
		archivo.agregarLinea(lineaCsv);
	}

	
	/**
	 * Eliminar país de archivo.
	 * Se elimina la linea del vuelo que posea la id del vuelo dado.
	 */
	@Override
	public void eliminar(Vuelo vueloAEliminar) throws IOException {
		
		List<String> lineas = new ArrayList<String>();
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Vuelo vuelo = csvToVuelo(linea);
			if(vuelo.getId() != vueloAEliminar.getId()) {
				lineas.add(linea);
			}
			
		}
		
	}

	/**
	 * Actualizar un vuelo en el archivo. Se actualiza el vuelo según la id.
	 */
	@Override
	public void actualizar(Vuelo vuelo) throws IOException {

		String contenido = "";
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			Vuelo vueloLinea = csvToVuelo(linea);
			if(vueloLinea.getId() == vuelo.getId()) {
				contenido += VueloToCsv(vuelo) + System.lineSeparator();
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

		Vuelo ultimoVuelo = null;
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			ultimoVuelo = csvToVuelo(linea);
		}
		
		if(ultimoVuelo == null)
			return 1;
		else 
			return ultimoVuelo.getId() + 1;
	}
	
	
	/**
	 * Convierte una línea CSV de archivo a un vuelo.
	 * @param csv
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	
	private Vuelo csvToVuelo(String csv) throws ArrayIndexOutOfBoundsException, IOException 
	{
		String[] props = csv.split(",");
		
		Vuelo vuelo = new Vuelo();
		
		vuelo.setId(Integer.parseInt(props[0]));
		
		AeropuertoDaoImplArchivo dao = new AeropuertoDaoImplArchivo();
		vuelo.setAeropSalida(dao.obtener(Integer.parseInt(props[1])));
		vuelo.setAeropLlegada(dao.obtener(Integer.parseInt(props[2])));
		vuelo.setCantAsientos(Integer.parseInt(props[3]));
		vuelo.setTiempoDeVuelo(props[4]);
		
		vuelo.setFechaHoraLlegada( (LocalDateTime)Dates.fromString(props[5]) );
		vuelo.setFechaHoraSalida( (LocalDateTime)Dates.fromString(props[6]) );
		
		
		return vuelo;
	}
	
	
	private String VueloToCsv(Vuelo vuelo)
	{
		return vuelo.getId() + "," + vuelo.getAeropSalida().getId()  + "," + vuelo.getAeropLlegada().getId()  + "," + vuelo.getCantAsientos()   + "," + vuelo.getTiempoDeVuelo()  + "," + Dates.toString(vuelo.getFechaHoraLlegada()) + "," + Dates.toString(vuelo.getFechaHoraSalida());  		
	
	}
	
	
	
}

package dao.implementations;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IAeropuertoDao;
import modelo.Aeropuerto;
import modelo.Pais;
import modelo.Provincia;
import util.Archivo;

public class AeropuertoDaoImplArchivo implements IAeropuertoDao{

		/**
		 * Nombre del archivo que almacenará al objeto.
		 */
		private static String nombreArchivo = "aeropuerto.txt";
		
		
		/**
		 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en toda la clase.
		 */
		private Archivo archivo;
		
		
		/**
		 * Inicializar DAO, creando archivo si no existe.
		 * @throws IOException
		 */
		public AeropuertoDaoImplArchivo() throws IOException
		{
			archivo = new Archivo(nombreArchivo);
			
			if(!archivo.existe()) {
				archivo.crear();
			}
		}
		
		
		/**
		 * Obtener un aeropuerto del archivo a partir de su id.
		 */
		@Override
		public Aeropuerto obtener(int id) throws IOException {
			
			String linea;

			while ((linea = archivo.siguienteLinea() ) != null) {
				
				Aeropuerto aeropuerto = csvToAeropuerto(linea);
				
				if(aeropuerto.getId() == id) {
					archivo.reiniciarReader();
					return aeropuerto;
				}	
			}
			
			return null;
		}
		
		
		/**
		 * Obtener lista de todos los aeropuertos del archivo.
		 */
		@Override
		public List<Aeropuerto> obtenerTodos() throws IOException {
			
			List<Aeropuerto> aeropuertos = new ArrayList<Aeropuerto>();
			
			String linea;
			
			while ((linea = archivo.siguienteLinea() ) != null) {
				aeropuertos.add(csvToAeropuerto(linea));
			}
			
			return aeropuertos;
		}

		
		/**
		 * Agregar nuevo aeropuerto al archivo. Se le asigna una id nueva auto-incremental.
		 * No se debe usar este metodo con un objeto ya existente en el archivo porque se duplica.
		 */
		@Override
		public void agregar(Aeropuerto aeropuerto) throws IOException {

			aeropuerto.setId(obtenerSiguienteId());
			
			String lineaCsv = aeropuertoToCsv(aeropuerto);
			
			archivo.agregarLinea(lineaCsv);
		}

		
		/**
		 * Eliminar aeropuerto de archivo.
		 * Se elimina la linea del aeropuerto que posea la id del aeropuerto dado.
		 */
		@Override
		public void eliminar(Aeropuerto aeropuertoAEliminar) throws IOException {
			
			List<String> lineas = new ArrayList<String>();
			
			String linea;
			while ((linea = archivo.siguienteLinea() ) != null) {
				
				Aeropuerto aeropuerto = csvToAeropuerto(linea);
				if(aeropuerto.getId() != aeropuertoAEliminar.getId()) {
					lineas.add(linea);
				}
				
			}
			
		}

		/**
		 * Actualizar un aeropuerto en el archivo. Se actualiza el aeropuerto según la id.
		 */
		@Override
		public void actualizar(Aeropuerto aeropuerto) throws IOException {

			String contenido = "";
			
			String linea;
			
			while ((linea = archivo.siguienteLinea() ) != null) {
				Aeropuerto aeropuertoLinea = csvToAeropuerto(linea);
				if(aeropuertoLinea.getId() == aeropuerto.getId()) {
					contenido += aeropuertoToCsv(aeropuerto) + System.lineSeparator();
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

			Aeropuerto ultimoAeropuerto = null;
			
			String linea;
			while ((linea = archivo.siguienteLinea() ) != null) {
				ultimoAeropuerto = csvToAeropuerto(linea);
			}
			
			if(ultimoAeropuerto == null)
				return 1;
			else 
				return ultimoAeropuerto.getId() + 1;
		}
		
		
		/**
		 * Convierte una línea CSV de archivo a un aeropuerto;
		 * @param csv
		 * @return
		 * @throws ArrayIndexOutOfBoundsException
		 */
		private Aeropuerto csvToAeropuerto(String csv) throws ArrayIndexOutOfBoundsException, IOException
		{
			String[] props = csv.split(",");
						
			Aeropuerto aeropuerto = new Aeropuerto();
			aeropuerto.setId(Integer.parseInt(props[0]));
			aeropuerto.setIdentificacion(props[1]);
			aeropuerto.setCiudad(props[2]);
			
			
			
			ProvinciaDaoImplArchivo provinciasDao = new ProvinciaDaoImplArchivo();
			Provincia provincia = provinciasDao.obtener(Integer.parseInt(props[3]));
			aeropuerto.setProvincia(provincia);
			
			
			PaisDaoImplArchivo paisesDao = new PaisDaoImplArchivo();
			Pais pais = paisesDao.obtener(Integer.parseInt(props[4]));
			aeropuerto.setPais(pais);
			
			return aeropuerto;
		}
		
		
		private String aeropuertoToCsv(Aeropuerto aeropuerto)
		{
			return aeropuerto.getId() + "," + aeropuerto.getIdentificacion()+","+aeropuerto.getCiudad()+","+aeropuerto.getProvincia().getId()+","+aeropuerto.getPais().getId();
			
		}
		
		
		
	}

	


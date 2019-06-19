package dao.implementations;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IProvinciaDao;
import modelo.Pais;
import modelo.Provincia;
import util.Archivo;
import util.PropertiesUtil;



public class ProvinciaDaoImplArchivo implements IProvinciaDao{
		
		/*
		 * Nombre del archivo que almacenará al objeto.
		 */
		private static String nombreArchivo;
		
		
		/**
		 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en toda la clase.
		 */
		private Archivo archivo;
		
		
		/**
		 * Inicializar DAO, creando archivo si no existe.
		 * @throws IOException
		 */
		public ProvinciaDaoImplArchivo() throws IOException
		{
			nombreArchivo = PropertiesUtil.provinciasFile("archivo");
			archivo = new Archivo(nombreArchivo);
			
			if(!archivo.existe()) {
				archivo.crear();
			}
		}
		
		
		/**
		 * Obtener una Provincia del archivo a partir de su id.
		 */
		@Override
		public Provincia obtener (int id) throws IOException {
			
			String linea;

			while ((linea = archivo.siguienteLinea() ) != null) {
				
				Provincia provincia = csvToProvincia(linea);
				
				if(provincia.getId() == id) {
					archivo.reiniciarReader();
					return provincia;
				}	
			}
			
			return null;
		}
		
		
		/**
		 * Obtener lista de todos las Provincias del archivo.
		 */
		@Override
		public List<Provincia> obtenerTodos() throws IOException {
			
			List<Provincia> provincias = new ArrayList<Provincia>();
			
			String linea;
			
			while ((linea = archivo.siguienteLinea() ) != null) {
				provincias.add(csvToProvincia(linea));
			}
			
			return provincias;
		}

		
		/**
		 * Agregar nueva Provincia al archivo. Se le asigna una id nueva auto-incremental.
		 * No se debe usar este metodo con un objeto ya existente en el archivo porque se duplica.
		 */
		@Override
		public void agregar(Provincia provincia) throws IOException {

			provincia.setId(obtenerSiguienteId());
			
			String lineaCsv = provinciaToCsv(provincia);
			
			archivo.agregarLinea(lineaCsv);
		}

		
		/**
		 * Eliminar Provincia de archivo.
		 * Se elimina la linea de la Provincia que posea la id de la Provincia dada.
		 */
		@Override
		public void eliminar(Provincia provinciaAEliminar) throws IOException {
			
			String contenido="";
			
			String linea;
			while ((linea = archivo.siguienteLinea() ) != null) {
				
				Provincia provincia = csvToProvincia(linea);
				if(provincia.getId() != provinciaAEliminar.getId()) {
					contenido += provinciaToCsv(provincia) + System.lineSeparator();

				}
				
				archivo.guardarContenido(contenido);
				
			}
			
		}

		/**
		 * Actualizar una Provincia en el archivo. Se actualiza la Provincia según la id.
		 */
		@Override
		public void actualizar(Provincia provincia) throws IOException {

			String contenido = "";
			
			String linea;
			
			while ((linea = archivo.siguienteLinea() ) != null) {
				Provincia provinciaLinea = csvToProvincia(linea);
				if(provinciaLinea.getId() == provincia.getId()) {
					contenido += provinciaToCsv(provincia) + System.lineSeparator();
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

			Provincia ultimoProvincia = null;
			
			String linea;
			while ((linea = archivo.siguienteLinea() ) != null) {
				ultimoProvincia = csvToProvincia(linea);
			}
			
			if(ultimoProvincia == null)
				return 1;
			else 
				return ultimoProvincia.getId() + 1;
		}
		
		
		/**
		 * Convierte una línea CSV de archivo a una Provincia.
		 * @param csv
		 * @return
		 * @throws ArrayIndexOutOfBoundsException
		 * @throws IOException 
		 * @throws NumberFormatException 
		 */
		private Provincia csvToProvincia(String csv) throws ArrayIndexOutOfBoundsException, NumberFormatException, IOException
		{
			String[] props = csv.split(",");
			
			Provincia provincia = new Provincia();
			provincia.setId(Integer.parseInt(props[0]));
			provincia.setNombre(props[1]);
			
			PaisDaoImplArchivo paisesDao = new PaisDaoImplArchivo();
			Pais pais = paisesDao.obtener(Integer.parseInt(props[2]));
			provincia.setPais(pais);
			
			
			
	
			return provincia;
		}
		
		
		private String provinciaToCsv(Provincia provincia)
		{
			return provincia.getId() + "," + provincia.getNombre() + "," + provincia.getPais().getId();
			
		}

		
		
		
		
		


	}

	
	
	
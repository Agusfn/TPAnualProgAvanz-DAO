package dao.implementations;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.IDireccion;
import modelo.Direccion;
import modelo.Pais;
import modelo.Provincia;
import util.Archivo;

public class DireccionDaoImplArchivo implements IDireccion {
	

	
			
		/**
		 * Nombre del archivo que almacenará al objeto.
		 */
		private static String nombreArchivo = "direcciones.txt";
		
		
		/**
		 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en toda la clase.
		 */
		private Archivo archivo;
		
		
		/**
		 * Inicializar DAO, creando archivo si no existe.
		 * @throws IOException
		 */
		public DireccionDaoImplArchivo() throws IOException
		{
			archivo = new Archivo(nombreArchivo);
			
			if(!archivo.existe()) {
				archivo.crear();
			}
		}
		
		
		/**
		 * Obtener una direccion del archivo a partir de su id.
		 */
		@Override
		public Direccion obtener(int id) throws IOException {
			
			String linea;

			while ((linea = archivo.siguienteLinea() ) != null) {
				
				Direccion direccion = csvToDireccion(linea);
				
				if(direccion.getId() == id) {
					archivo.reiniciarReader();
					return direccion;
				}	
			}
			
			return null;
		}
		
		
		/**
		 * Obtener lista de todos las direcciones del archivo.
		 */
		@Override
		public List<Direccion> obtenerTodos() throws IOException {
			
			List<Direccion> direcciones = new ArrayList<Direccion>();
			
			String linea;
			
			while ((linea = archivo.siguienteLinea() ) != null) {
				direcciones.add(csvToDireccion(linea));
			}
			
			return direcciones;
		}

		
		/**
		 * Agregar nuevo pais al archivo. Se le asigna una id nueva auto-incremental.
		 * No se debe usar este metodo con un objeto ya existente en el archivo porque se duplica.
		 */
		@Override
		public void agregar(Direccion direccion) throws IOException {

			direccion.setId(obtenerSiguienteId());
			
			String lineaCsv = direccionToCsv(direccion);
			
			archivo.agregarLinea(lineaCsv);
		}

		
		/**
		 * Eliminar direccion de archivo.
		 * Se elimina la linea de la direccion que posea la id del pais dado.
		 */
		@Override
		public void eliminar(Direccion direccionAEliminar) throws IOException {
			
			List<String> lineas = new ArrayList<String>();
			
			String linea;
			while ((linea = archivo.siguienteLinea() ) != null) {
				
				Direccion direccion = csvToDireccion(linea);
				if(direccion.getId() != direccionAEliminar.getId()) {
					lineas.add(linea);
				}
				
			}
			
		}

		/**
		 * Actualizar una direccion en el archivo. Se actualiza la direccion según la id.
		 */
		@Override
		public void actualizar(Direccion direccion) throws IOException {

			String contenido = "";
			
			String linea;
			
			while ((linea = archivo.siguienteLinea() ) != null) {
				Direccion direccionLinea = csvToDireccion(linea);
				if(direccionLinea.getId() == direccion.getId()) {
					contenido += direccionToCsv(direccion) + System.lineSeparator();
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

			Direccion ultimoDireccion = null;
			
			String linea;
			while ((linea = archivo.siguienteLinea() ) != null) {
				ultimoDireccion = csvToDireccion(linea);
			}
			
			if(ultimoDireccion == null)
				return 1;
			else 
				return ultimoDireccion.getId() + 1;
		}
		
		
		/**
		 * Convierte una línea CSV de archivo a una direccion.
		 * @param csv
		 * @return
		 * @throws ArrayIndexOutOfBoundsException
		 * @throws IOException 
		 */
		private Direccion csvToDireccion(String csv) throws ArrayIndexOutOfBoundsException, IOException
		{
			String[] props = csv.split(",");
			
			Direccion direccion = new Direccion();
			direccion.setId(Integer.parseInt(props[0]));
			direccion.setCalle(props[1]);
			direccion.setAltura(props[2]);
			direccion.setCiudad(props[3]);
			
			
			ProvinciaDaoImplArchivo provinciaDao = new ProvinciaDaoImplArchivo();
			Provincia provincia = provinciaDao.obtener(Integer.parseInt(props[4]));

			direccion.setProvincia(provincia);
			
			
			PaisDaoImplArchivo paisesDao = new PaisDaoImplArchivo();
			Pais pais = paisesDao.obtener(Integer.parseInt(props[5]));
			direccion.setPais(pais);
			
			direccion.setCodigoPostal(props[6]);
			
			return direccion;
		}
		
		
		private String direccionToCsv(Direccion direccion)
		{
			return direccion.getId() + "," + direccion.getCalle()+","+direccion.getAltura()+","+direccion.getCiudad()+","+direccion.getProvincia().getId()+","+direccion.getPais().getId()+","+direccion.getCodigoPostal();
		}
	

}

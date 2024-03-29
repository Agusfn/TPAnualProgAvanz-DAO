package dao.implementations.archivo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IVentaDao;
import modelo.Venta;
import util.Archivo;
import util.Dates;
import util.Properties;

public class VentaDaoImplArchivo implements IVentaDao {

	
	/**
	 * Nombre del archivo que almacenar� al objeto.
	 */
	private static String nombreArchivo = Properties.getProperty("csv_ventas");
	
	
	/**
	 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en toda la clase.
	 */
	private Archivo archivo;
	
	
	/**
	 * Inicializar DAO, creando archivo si no existe.
	 * @throws IOException 
	 */
	public VentaDaoImplArchivo() throws IOException
	{
		archivo = new Archivo(nombreArchivo);
		
		if(!archivo.existe()) {
			archivo.crear();
		}
	}
	
	
	/**
	 * Obtener una venta del archivo a partir de su id.
	 * @throws IOException 
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	@Override
	public Venta obtener(int id) throws ArrayIndexOutOfBoundsException, IOException {
		
		String linea;

		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Venta venta = csvToVenta(linea);
			
			if(venta.getId() == id) {
				archivo.reiniciarReader();
				return venta;
			}	
		}
		
		return null;
	}
	
	
	/**
	 * Obtener lista de todas las ventas del archivo.
	 * @throws IOException 
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	@Override
	public List<Venta> obtenerTodos() throws ArrayIndexOutOfBoundsException, IOException {
		
		List<Venta> ventas = new ArrayList<Venta>();
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			ventas.add(csvToVenta(linea));
		}
		
		return ventas;
	}

	
	/**
	 * Agregar una nueva venta al archivo. Se le asigna una id nueva auto-incremental.
	 * No se debe usar este metodo con un objeto ya existente en el archivo porque se duplica.
	 * @throws IOException 
	 */
	@Override
	public void agregar(Venta venta) throws IOException {

		venta.setId(obtenerSiguienteId());
		
		String lineaCsv = ventaToCsv(venta);
		
		archivo.agregarLinea(lineaCsv);
	}

	
	/**
	 * Eliminar venta de archivo.
	 * Se elimina la linea de la venta que posea la id de la venta dada.
	 * @throws IOException 
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	@Override
	public void eliminar(Venta ventaAEliminar) throws ArrayIndexOutOfBoundsException, IOException {
		
		String contenido = "";
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Venta venta = csvToVenta(linea);
			if(venta.getId() != ventaAEliminar.getId()) {
				contenido += ventaToCsv(venta) + System.lineSeparator();
			}
		}
		archivo.guardarContenido(contenido);
	}

	/**
	 * Actualizar una venta en el archivo. Se actualiza el vuelo seg�n la id.
	 * @throws IOException 
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	@Override
	public void actualizar(Venta venta) throws ArrayIndexOutOfBoundsException, IOException {

		String contenido = "";
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			Venta ventaLinea = csvToVenta(linea);
			if(ventaLinea.getId() == venta.getId()) {
				contenido += ventaToCsv(venta) + System.lineSeparator();
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
	 * @throws ArrayIndexOutOfBoundsException 
	 */
	private int obtenerSiguienteId() throws ArrayIndexOutOfBoundsException, IOException
	{

		Venta ultimaVenta = null;
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			ultimaVenta = csvToVenta(linea);
		}
		
		if(ultimaVenta == null)
			return 1;
		else 
			return ultimaVenta.getId() + 1;
	}
	
	
	/**
	 * Convierte una l�nea CSV de archivo a una venta.
	 * @param csv
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws IOException 
	 */
	
	private Venta csvToVenta(String csv) throws ArrayIndexOutOfBoundsException, IOException
	{
		String[] props = csv.split(",");
		
		Venta venta = new Venta();
		
		venta.setId(Integer.parseInt(props[0]));
		
		ClienteDaoImplArchivo dao = new ClienteDaoImplArchivo();
		venta.setCliente(dao.obtener(Integer.parseInt(props[1])));
		
		VueloDaoImplArchivo dao2 = new VueloDaoImplArchivo();
		venta.setVuelo(dao2.obtener(Integer.parseInt(props [2])));
	
		
		venta.setFechaHora((LocalDateTime) Dates.fromString(props [4]));
		
		venta.setFormaDePago(props[5]);
		
		return venta;
	}
	
	/**
	 * Convierte una venta en un string csv
	 */
		
	private String ventaToCsv(Venta venta)
	{
		return venta.getId() + "," + venta.getCliente().getId() + "," + venta.getVuelo().getId() + "," + Dates.toString(venta.getFechaHora()) + "," + venta.getFormaDePago();
		
	}
	
	public void close() throws Exception
	{
	}
	
}

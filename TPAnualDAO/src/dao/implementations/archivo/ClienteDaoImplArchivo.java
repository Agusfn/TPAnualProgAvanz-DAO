package dao.implementations.archivo;

import java.util.ArrayList;
import java.util.List;
import dao.interfaces.IClienteDao;
import modelo.Cliente;
import util.Archivo;
import util.Dates;
import util.Properties;

public class ClienteDaoImplArchivo implements IClienteDao{

	
	
	/*
	 * Nombre del archivo que almacenará al objeto.
	 */
	private static String nombreArchivo = Properties.getProperty("csv_clientes");
	
	
	/**
	 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en toda la clase.
	 */
	private Archivo archivo;
	
	
	/**
	 * Inicializar DAO, creando archivo si no existe.
	 */
	public ClienteDaoImplArchivo()
	{
		archivo = new Archivo(nombreArchivo);
		
		if(!archivo.existe()) {
			archivo.crear();
		}
	}
	
	
	/**
	 * Obtener un cliente del archivo a partir de su id.
	 */
	@Override
	public Cliente obtener(int id) {
		
		String linea;

		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Cliente cliente = csvToCliente(linea);
			
			if(cliente.getId() == id) {
				archivo.reiniciarReader();
				return cliente;
			}	
		}
		
		return null;
	}
	
	
	/**
	 * Obtener lista de todos los clientes del archivo.
	 */
	@Override
	public List<Cliente> obtenerTodos() {
		
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			clientes.add(csvToCliente(linea));
		}
		
		return clientes;
	}

	
	/**
	 * Agregar nuevo cliente al archivo. Se le asigna una id nueva auto-incremental.
	 * No se debe usar este metodo con un objeto ya existente en el archivo porque se duplica.
	 */
	@Override
	public void agregar(Cliente cliente) {

		cliente.setId(obtenerSiguienteId());
		
		String lineaCsv = clienteToCsv(cliente);
		
		archivo.agregarLinea(lineaCsv);
	}

	
	/**
	 * Eliminar cliente de archivo.
	 * Se elimina la linea del pais que posea la id del pais dado.
	 */
	@Override
	public void eliminar(Cliente clienteAEliminar) {
		
		List<String> lineas = new ArrayList<String>();
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			
			Cliente cliente = csvToCliente(linea);
			if(cliente.getId() != clienteAEliminar.getId()) {
				lineas.add(linea);
			}
			
		}
		
	}

	/**
	 * Actualizar un cliente en el archivo. Se actualiza el cliente según la id.
	 */
	@Override
	public void actualizar(Cliente cliente) {

		String contenido = "";
		
		String linea;
		
		while ((linea = archivo.siguienteLinea() ) != null) {
			Cliente clienteLinea = csvToCliente(linea);
			if(clienteLinea.getId() == cliente.getId()) {
				contenido += clienteToCsv(cliente) + System.lineSeparator();
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

		Cliente ultimoCliente = null;
		
		String linea;
		while ((linea = archivo.siguienteLinea() ) != null) {
			ultimoCliente = csvToCliente(linea);
		}
		
		if(ultimoCliente == null)
			return 1;
		else 
			return ultimoCliente.getId() + 1;
	}
	
	
	/**
	 * Convierte una línea CSV de archivo a un cliente.
	 * @param csv
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	private Cliente csvToCliente(String csv) throws ArrayIndexOutOfBoundsException
	{
		String[] props = csv.split(",");
		
		Cliente cliente = new Cliente();
		cliente.setId(Integer.parseInt(props[0]));
		cliente.setNombreYApellido(props[1]);
		cliente.setDni(props[2]);
	//	cliente.setPasaporte(props[3);
		cliente.setCuitOCuil(props[4]);
		cliente.setFechaNacimiento(Dates.fromString(props[5]));
	//	cliente.setTelefono(props[6]);
	//	cliente.setPasajeroFrecuente(PasajeroFrecuente.getId);
	//	cliente.setDireccion(props[8]);
		
		return cliente;
	}
	
	
	private String clienteToCsv(Cliente cliente)
	{
		return cliente.getId() + "," + cliente.getNombreYApellido() + "," + cliente.getDni() + "," +
	cliente.getPasaporte() + "," + cliente.getCuitOCuil() + "," + cliente.getFechaNacimiento() + "," +
	cliente.getTelefono() + "," + cliente.getPasajeroFrecuente() + "," + cliente.getDireccion();
	}
	
	
	
}

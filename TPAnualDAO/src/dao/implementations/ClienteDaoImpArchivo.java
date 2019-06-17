package dao.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.IClienteDao;
import modelo.Cliente;
import util.Archivo;

public class ClienteDaoImpArchivo implements IClienteDao{

	
	
	/*
	 * Nombre del archivo que almacenará al objeto.
	 */
	private static String nombreArchivo = "cliente.txt";
	
	
	/**
	 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en toda la clase.
	 */
	private Archivo archivo;
	
	
	/**
	 * Inicializar DAO, creando archivo si no existe.
	 * @throws IOException
	 */
	public ClienteDaoImpArchivo() throws IOException
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
	public Cliente obtener(int id) throws IOException {
		
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
	public List<Cliente> obtenerTodos() throws IOException {
		
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
	public void agregar(Cliente cliente) throws IOException {

		cliente.setId(obtenerSiguienteId());
		
		String lineaCsv = clienteToCsv(cliente);
		
		archivo.agregarLinea(lineaCsv);
	}

	
	/**
	 * Eliminar cliente de archivo.
	 * Se elimina la linea del pais que posea la id del pais dado.
	 */
	@Override
	public void eliminar(Cliente clienteAEliminar) throws IOException {
		
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
	public void actualizar(Cliente cliente) throws IOException {

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
	 * @throws IOException
	 */
	private int obtenerSiguienteId() throws IOException
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
	//	cliente.setFechaNacimiento(props[5]);
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

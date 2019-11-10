package dao.implementations.serializacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import util.Properties;
//
public class ClienteDaoImplSerializacion {


	
	private static String nombreArchivo = Properties.getProperty("serial_clientes");
	
	
	public ClienteDaoImplSerializacion() throws IOException
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Cliente>());
		}
	}
	
	
	/**
	 * Obtener cliente con id determinado de la lista del archivo.
	 * @throws IOException 
	 */
	public Cliente obtener(int id) throws IOException
	{
		List<Cliente> clientes = obtenerTodos();
		
		for(Cliente cliente: clientes)
		{
			if(cliente.getId() == id)
				return cliente;
		}
		return null;
	}
	
	
	/**
	 * Obtener lista de clientes del archivo serializado.
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public List<Cliente> obtenerTodos() throws IOException
	{
		
		FileInputStream fis = new FileInputStream(nombreArchivo);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			return (ArrayList<Cliente>)ois.readObject();
		} catch (ClassNotFoundException e) {
			return new ArrayList<Cliente>();
		}
		finally {
			ois.close();
		}	
	}
	
	
	/**
	 * Agregar un cliente al archivo de lista de paises serializado.
	 * @throws IOException 
	 */
	public void agregar(Cliente cliente) throws IOException
	{
		cliente.setId(obtenerSiguienteId());
		
		List<Cliente> clientes = obtenerTodos();
		clientes.add(cliente);
		
		guardarLista(clientes);
	}
	
	
	/**
	 * Eliminar cliente de lista de clientes en archivo. Se elimina el elemento con la id del elemento dado.
	 * @throws IOException 
	 */
	public void eliminar(Cliente clienteAEliminar) throws IOException
	{
		List<Cliente> clientes = obtenerTodos();
		
		for(int i=0; i<clientes.size(); i++)
		{
			Cliente cliente = clientes.get(i);
			if(cliente.getId() == clienteAEliminar.getId()) {
				clientes.remove(i);
				guardarLista(clientes);
			}
		}
	}
	
	/**
	 * Actualizar un cliente de la lista del archivo serializado. Se actualiza según el id.
	 * @throws IOException 
	 */
	public void actualizar(Cliente clienteActualizado) throws IOException
	{
		List<Cliente> clientes = obtenerTodos();
		
		for(int i=0; i<clientes.size(); i++)
		{
			Cliente cliente = clientes.get(i);
			if(cliente.getId() == clienteActualizado.getId()) {
				clientes.set(i, clienteActualizado);
				guardarLista(clientes);
			}
		}
	}
	
	
	/**
	 * Obtener siguiente id de cliente a guardar (id del ultimo de la lista+1).
	 * @throws IOException 
	 */
	private int obtenerSiguienteId() throws IOException
	{
		List<Cliente> clientes = obtenerTodos();
		
		if(clientes.size() > 0) {
			return clientes.get(clientes.size()-1).getId() + 1;
		}
		else {
			return 1;
		}
	}	
	
	/**
	 * Guardar lista de clientes en su archivo correspondiente (pisando el contenido anterior).
	 * @param clientes
	 * @throws IOException 
	 */
	private void guardarLista(List<Cliente> clientes) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(nombreArchivo);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(clientes);
		oos.close();	
	}
	
}

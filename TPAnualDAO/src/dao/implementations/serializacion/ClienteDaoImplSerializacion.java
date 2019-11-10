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
	
	
	public ClienteDaoImplSerializacion()
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Cliente>());
		}
	}
	
	
	/**
	 * Obtener cliente con id determinado de la lista del archivo.
	 */
	public Cliente obtener(int id)
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
	 */
	@SuppressWarnings("unchecked")
	public List<Cliente> obtenerTodos()
	{
		
		try {
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
		catch(IOException e) 
		{
			System.out.println("Error leyendo lista de objetos serializados de " + nombreArchivo + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
			return null;
		}		
	}
	
	
	/**
	 * Agregar un cliente al archivo de lista de paises serializado.
	 */
	public void agregar(Cliente cliente)
	{
		cliente.setId(obtenerSiguienteId());
		
		List<Cliente> clientes = obtenerTodos();
		clientes.add(cliente);
		
		guardarLista(clientes);
	}
	
	
	/**
	 * Eliminar cliente de lista de clientes en archivo. Se elimina el elemento con la id del elemento dado.
	 */
	public void eliminar(Cliente clienteAEliminar)
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
	 */
	public void actualizar(Cliente clienteActualizado)
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
	 */
	private int obtenerSiguienteId()
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
	private void guardarLista(List<Cliente> clientes)
	{
		try {
			FileOutputStream fos = new FileOutputStream(nombreArchivo);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(clientes);
			oos.close();
		} 
		catch(IOException e) {
			System.out.println("Error guardando lista de objetos serializados en " + nombreArchivo + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
		}		
	}
	
}

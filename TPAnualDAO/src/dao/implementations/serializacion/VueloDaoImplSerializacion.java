package dao.implementations.serializacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IVueloDao;
import modelo.Vuelo;
import util.Properties;

public class VueloDaoImplSerializacion implements IVueloDao {

	
	private static String nombreArchivo = Properties.getProperty("serial_vuelos");
	
	
	public VueloDaoImplSerializacion()
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Vuelo>());
		}
	}
	
	
	/**
	 * Obtener vuelo con id determinado de la lista del archivo.
	 */
	public Vuelo obtener(int id)
	{
		List<Vuelo> vuelos = obtenerTodos();
		
		for(Vuelo vuelo:vuelos)
		{
			if(vuelo.getId() == id)
				return vuelo;
		}
		return null;
	}
	
	
	/**
	 * Obtener lista de vuelos del archivo serializado.
	 */
	@SuppressWarnings("unchecked")
	public List<Vuelo> obtenerTodos()
	{
		
		try {
			FileInputStream fis = new FileInputStream(nombreArchivo);
			ObjectInputStream ois = new ObjectInputStream(fis);
	
			try {
				return (ArrayList<Vuelo>)ois.readObject();
			} catch (ClassNotFoundException e) {
				return new ArrayList<Vuelo>();
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
	 * Agregar un vuelo al archivo de lista de vuelos serializado.
	 */
	public void agregar(Vuelo vuelo)
	{
		vuelo.setId(obtenerSiguienteId());
		
		List<Vuelo> vuelos = obtenerTodos();
		vuelos.add(vuelo);
		
		guardarLista(vuelos);
	}
	
	
	/**
	 * Eliminar vuelo de lista de vuelos en archivo. Se elimina el elemento con la id del elemento dado.
	 */
	public void eliminar(Vuelo vueloAEliminar)
	{
		List<Vuelo> vuelos = obtenerTodos();
		
		for(int i=0; i<vuelos.size(); i++)
		{
			Vuelo vuelo = vuelos.get(i);
			if(vuelo.getId() == vueloAEliminar.getId()) {
				vuelos.remove(i);
				guardarLista(vuelos);
			}
		}
	}
	
	/**
	 * Actualizar un vuelo de la lista del archivo serializado. Se actualiza según el id.
	 */
	public void actualizar(Vuelo vueloActualizado)
	{
		List<Vuelo> vuelos = obtenerTodos();
		
		for(int i=0; i<vuelos.size(); i++)
		{
			Vuelo vuelo = vuelos.get(i);
			if(vuelo.getId() == vueloActualizado.getId()) {
				vuelos.set(i, vueloActualizado);
				guardarLista(vuelos);
			}
		}
	}
	
	
	/**
	 * Obtener siguiente id de vuelo a guardar (id del ultimo de la lista+1).
	 */
	private int obtenerSiguienteId()
	{
		List<Vuelo> vuelos = obtenerTodos();
		
		if(vuelos.size() > 0) {
			return vuelos.get(vuelos.size()-1).getId() + 1;
		}
		else {
			return 1;
		}
	}	
	
	/**
	 * Guardar lista de vuelos en su archivo correspondiente (pisando el contenido anterior).
	 * @param vuelos
	 * @throws IOException 
	 */
	private void guardarLista(List<Vuelo> vuelos)
	{
		try {
			FileOutputStream fos = new FileOutputStream(nombreArchivo);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(vuelos);
			oos.close();
		} 
		catch(IOException e) {
			System.out.println("Error guardando lista de objetos serializados en " + nombreArchivo + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
		}			
	}

	
}

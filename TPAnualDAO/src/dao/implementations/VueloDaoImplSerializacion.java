package dao.implementations;

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
import util.PropertiesUtil;

public class VueloDaoImplSerializacion implements IVueloDao {

	
	private static String nombreArchivo;
	
	
	public VueloDaoImplSerializacion() throws IOException
	{
		nombreArchivo = PropertiesUtil.vuelosFile("serializacion");
		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Vuelo>());
		}
	}
	
	
	/**
	 * Obtener vuelo con id determinado de la lista del archivo.
	 */
	public Vuelo obtener(int id) throws IOException
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
	public List<Vuelo> obtenerTodos() throws IOException
	{
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
	
	
	/**
	 * Agregar un vuelo al archivo de lista de vuelos serializado.
	 */
	public void agregar(Vuelo vuelo) throws IOException
	{
		vuelo.setId(obtenerSiguienteId());
		
		List<Vuelo> vuelos = obtenerTodos();
		vuelos.add(vuelo);
		
		guardarLista(vuelos);
	}
	
	
	/**
	 * Eliminar vuelo de lista de vuelos en archivo. Se elimina el elemento con la id del elemento dado.
	 */
	public void eliminar(Vuelo vueloAEliminar) throws IOException
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
	public void actualizar(Vuelo vueloActualizado) throws IOException
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
	private int obtenerSiguienteId() throws IOException
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
	private void guardarLista(List<Vuelo> vuelos) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(nombreArchivo);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(vuelos);
		oos.close();
	}

	
}

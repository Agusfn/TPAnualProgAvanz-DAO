package dao.implementations.serializacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IAerolineaDao;
import modelo.Aerolinea;
import util.Properties;
//
public class AerolineaDaoImplSerializacion implements IAerolineaDao {
	

	
	private static String nombreArchivo = Properties.getProperty("serial_aerolineas");;
	
	
	public AerolineaDaoImplSerializacion() throws IOException
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Aerolinea>());
		}
	}
	
	
	/**
	 * Obtener aerolinea con id determinado de la lista del archivo.
	 * @throws IOException 
	 */
	public Aerolinea obtener(int id) throws IOException
	{
		List<Aerolinea> aerolineas = obtenerTodos();
		
		for(Aerolinea aerolinea: aerolineas)
		{
			if(aerolinea.getId() == id)
				return aerolinea;
		}
		return null;
	}
	
	
	/**
	 * Obtener lista de aerolineas del archivo serializado.
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public List<Aerolinea> obtenerTodos() throws IOException
	{
		
		FileInputStream fis = new FileInputStream(nombreArchivo);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			return (ArrayList<Aerolinea>)ois.readObject();
		} catch (ClassNotFoundException e) {
			return new ArrayList<Aerolinea>();
		}
		finally {
			ois.close();
		}

	}
	
	
	/**
	 * Agregar un aerolinea al archivo de lista de paises serializado.
	 * @throws IOException 
	 */
	public void agregar(Aerolinea aerolinea) throws IOException
	{
		aerolinea.setId(obtenerSiguienteId());
		
		List<Aerolinea> aerolineas = obtenerTodos();
		aerolineas.add(aerolinea);
		
		guardarLista(aerolineas);
	}
	
	
	/**
	 * Eliminar aerolinea de lista de aerolineas en archivo. Se elimina el elemento con la id del elemento dado.
	 * @throws IOException 
	 */
	public void eliminar(Aerolinea aerolineaAEliminar) throws IOException
	{
		List<Aerolinea> aerolineas = obtenerTodos();
		
		for(int i=0; i<aerolineas.size(); i++)
		{
			Aerolinea aerolinea = aerolineas.get(i);
			if(aerolinea.getId() == aerolineaAEliminar.getId()) {
				aerolineas.remove(i);
				guardarLista(aerolineas);
			}
		}
	}
	
	/**
	 * Actualizar una aerolinea de la lista del archivo serializado. Se actualiza según el id.
	 * @throws IOException 
	 */
	public void actualizar(Aerolinea aerolineaActualizado) throws IOException
	{
		List<Aerolinea> aerolineas = obtenerTodos();
		
		for(int i=0; i<aerolineas.size(); i++)
		{
			Aerolinea aerolinea = aerolineas.get(i);
			if(aerolinea.getId() == aerolineaActualizado.getId()) {
				aerolineas.set(i, aerolineaActualizado);
				guardarLista(aerolineas);
			}
		}
	}
	
	
	/**
	 * Obtener siguiente id de aerolinea a guardar (id del ultimo de la lista+1).
	 * @throws IOException 
	 */
	private int obtenerSiguienteId() throws IOException
	{
		List<Aerolinea> aerolineas = obtenerTodos();
		
		if(aerolineas.size() > 0) {
			return aerolineas.get(aerolineas.size()-1).getId() + 1;
		}
		else {
			return 1;
		}
	}	
	
	/**
	 * Guardar lista de aerolineas en su archivo correspondiente (pisando el contenido anterior).
	 * @param aerolineas
	 * @throws IOException 

	 */
	private void guardarLista(List<Aerolinea> aerolineas) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(nombreArchivo);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(aerolineas);
		oos.close();
	}
	
	public void close() throws Exception
	{
	}
	
	
}

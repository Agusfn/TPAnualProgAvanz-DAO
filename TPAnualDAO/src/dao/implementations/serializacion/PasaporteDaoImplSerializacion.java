package dao.implementations.serializacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IPasaporteDao;
import modelo.Pasaporte;
import util.Properties;

public class PasaporteDaoImplSerializacion implements IPasaporteDao {

	
	private static String nombreArchivo = Properties.getProperty("serial_pasaportes");
	
	
	public PasaporteDaoImplSerializacion() throws IOException
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Pasaporte>());
		}
	}
	
	
	/**
	 * Obtener pasaporte con id determinado de la lista del archivo.
	 * @throws IOException 
	 */
	public Pasaporte obtener(int id) throws IOException
	{
		List<Pasaporte> pasaportes = obtenerTodos();
		
		for(Pasaporte pasaporte: pasaportes)
		{
			if(pasaporte.getId() == id)
				return pasaporte;
		}
		return null;
	}
	
	
	/**
	 * Obtener lista de pasaportes del archivo serializado.
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public List<Pasaporte> obtenerTodos() throws IOException
	{
	
		FileInputStream fis = new FileInputStream(nombreArchivo);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			return (ArrayList<Pasaporte>)ois.readObject();
		} catch (ClassNotFoundException e) {
			return new ArrayList<Pasaporte>();
		}
		finally {
			ois.close();
		}

		
	}
	
	
	/**
	 * Agregar un pasaporte al archivo de lista de pasaportes serializado.
	 * @throws IOException 
	 */
	public void agregar(Pasaporte pasaporte) throws IOException
	{
		pasaporte.setId(obtenerSiguienteId());
		
		List<Pasaporte> pasaportes = obtenerTodos();
		pasaportes.add(pasaporte);
		
		guardarLista(pasaportes);
	}
	
	
	/**
	 * Eliminar pasaporte de lista de pasaportes en archivo. Se elimina el elemento con la id del elemento dado.
	 * @throws IOException 
	 */
	public void eliminar(Pasaporte pasaporteAEliminar) throws IOException
	{
		List<Pasaporte> pasaportes = obtenerTodos();
		
		for(int i=0; i<pasaportes.size(); i++)
		{
			Pasaporte pasaporte = pasaportes.get(i);
			if(pasaporte.getId() == pasaporteAEliminar.getId()) {
				pasaportes.remove(i);
				guardarLista(pasaportes);
			}
		}
	}
	
	/**
	 * Actualizar un pasaporte de la lista del archivo serializado. Se actualiza según el id.
	 * @throws IOException 
	 */
	public void actualizar(Pasaporte pasaporteActualizado) throws IOException
	{
		List<Pasaporte> pasaportes = obtenerTodos();
		
		for(int i=0; i<pasaportes.size(); i++)
		{
			Pasaporte pasaporte = pasaportes.get(i);
			if(pasaporte.getId() == pasaporteActualizado.getId()) {
				pasaportes.set(i, pasaporteActualizado);
				guardarLista(pasaportes);
			}
		}
	}
	
	
	/**
	 * Obtener siguiente id de pasaporte a guardar (id del ultimo de la lista+1).
	 * @throws IOException 
	 */
	private int obtenerSiguienteId() throws IOException
	{
		List<Pasaporte> pasaportes = obtenerTodos();
		
		if(pasaportes.size() > 0) {
			return pasaportes.get(pasaportes.size()-1).getId() + 1;
		}
		else {
			return 1;
		}
	}	
	
	/**
	 * Guardar lista de pasaportes en su archivo correspondiente (pisando el contenido anterior).
	 * @param pasaportes
	 * @throws IOException 
	 */
	private void guardarLista(List<Pasaporte> pasaportes) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(nombreArchivo);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(pasaportes);
		oos.close();
		
	}
	
	
	public void close() throws Exception
	{
	}
	
	
}

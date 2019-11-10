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
	
	
	public PasaporteDaoImplSerializacion()
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Pasaporte>());
		}
	}
	
	
	/**
	 * Obtener pasaporte con id determinado de la lista del archivo.
	 */
	public Pasaporte obtener(int id)
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
	 */
	@SuppressWarnings("unchecked")
	public List<Pasaporte> obtenerTodos()
	{
		
		try {
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
		catch(IOException e) 
		{
			System.out.println("Error leyendo lista de objetos serializados de " + nombreArchivo + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
			return null;
		}		
		
	}
	
	
	/**
	 * Agregar un pasaporte al archivo de lista de pasaportes serializado.
	 */
	public void agregar(Pasaporte pasaporte)
	{
		pasaporte.setId(obtenerSiguienteId());
		
		List<Pasaporte> pasaportes = obtenerTodos();
		pasaportes.add(pasaporte);
		
		guardarLista(pasaportes);
	}
	
	
	/**
	 * Eliminar pasaporte de lista de pasaportes en archivo. Se elimina el elemento con la id del elemento dado.
	 */
	public void eliminar(Pasaporte pasaporteAEliminar)
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
	 */
	public void actualizar(Pasaporte pasaporteActualizado)
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
	 */
	private int obtenerSiguienteId()
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
	private void guardarLista(List<Pasaporte> pasaportes)
	{
		try {
			FileOutputStream fos = new FileOutputStream(nombreArchivo);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(pasaportes);
			oos.close();
		} 
		catch(IOException e) {
			System.out.println("Error guardando lista de objetos serializados en " + nombreArchivo + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
		}			
	}
	
	
	
}

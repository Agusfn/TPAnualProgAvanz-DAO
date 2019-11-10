package dao.implementations.serializacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.ITelefonoDao;
import modelo.Telefono;
import util.Properties;
//
public class TelefonoDaoImpSerializacion implements ITelefonoDao {
	

	
	private static String nombreArchivo = Properties.getProperty("serial_telefonos");
	
	
	public TelefonoDaoImpSerializacion()
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Telefono>());
		}
	}
	
	
	/**
	 * Obtener telefono con id determinado de la lista del archivo.
	 */
	public Telefono obtener(int id)
	{
		List<Telefono> telefonos = obtenerTodos();
		
		for(Telefono telefono: telefonos)
		{
			if(telefono.getId() == id)
				return telefono;
		}
		return null;
	}
	
	
	/**
	 * Obtener lista de paises del archivo serializado.
	 */
	@SuppressWarnings("unchecked")
	public List<Telefono> obtenerTodos()
	{
		
		try {
			FileInputStream fis = new FileInputStream(nombreArchivo);
			ObjectInputStream ois = new ObjectInputStream(fis);
	
			try {
				return (ArrayList<Telefono>)ois.readObject();
			} catch (ClassNotFoundException e) {
				return new ArrayList<Telefono>();
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
	 * Agregar un telefono al archivo de lista de telefonos serializado.
	 */
	public void agregar(Telefono telefono)
	{
		telefono.setId(obtenerSiguienteId());
		
		List<Telefono> telefonos = obtenerTodos();
		telefonos.add(telefono);
		
		guardarLista(telefonos);
	}
	
	
	/**
	 * Eliminar telefono de lista de paises en archivo. Se elimina el elemento con la id del elemento dado.
	 */
	public void eliminar(Telefono telefonoAEliminar)
	{
		List<Telefono> telefonos = obtenerTodos();
		
		for(int i=0; i<telefonos.size(); i++)
		{
			Telefono telefono = telefonos.get(i);
			if(telefono.getId() == telefonoAEliminar.getId()) {
				telefonos.remove(i);
				guardarLista(telefonos);
			}
		}
	}
	
	/**
	 * Actualizar un telefono de la lista del archivo serializado. Se actualiza según el id.
	 */
	public void actualizar(Telefono telefonoActualizado)
	{
		List<Telefono> telefonos = obtenerTodos();
		
		for(int i=0; i<telefonos.size(); i++)
		{
			Telefono telefono = telefonos.get(i);
			if(telefono.getId() == telefonoActualizado.getId()) {
				telefonos.set(i, telefonoActualizado);
				guardarLista(telefonos);
			}
		}
	}
	
	
	/**
	 * Obtener siguiente id de telefono a guardar (id del ultimo de la lista+1).
	 */
	private int obtenerSiguienteId()
	{
		List<Telefono> telefonos = obtenerTodos();
		
		if(telefonos.size() > 0) {
			return telefonos.get(telefonos.size()-1).getId() + 1;
		}
		else {
			return 1;
		}
	}	
	
	/**
	 * Guardar lista de telefonos en su archivo correspondiente (pisando el contenido anterior).
	 * @param telefonos
	 * @throws IOException 
	 */
	private void guardarLista(List<Telefono> telefonos)
	{
		try {
			FileOutputStream fos = new FileOutputStream(nombreArchivo);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(telefonos);
			oos.close();
		} 
		catch(IOException e) {
			System.out.println("Error guardando lista de objetos serializados en " + nombreArchivo + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
		}			
	}

}

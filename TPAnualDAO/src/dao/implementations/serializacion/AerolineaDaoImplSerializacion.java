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
	
	
	public AerolineaDaoImplSerializacion()
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Aerolinea>());
		}
	}
	
	
	/**
	 * Obtener aerolinea con id determinado de la lista del archivo.
	 */
	public Aerolinea obtener(int id)
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
	 */
	@SuppressWarnings("unchecked")
	public List<Aerolinea> obtenerTodos()
	{
		
		try {
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
		catch(IOException e) 
		{
			System.out.println("Error leyendo lista de objetos serializados de " + nombreArchivo + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
			return null;
		}
		
	}
	
	
	/**
	 * Agregar un aerolinea al archivo de lista de paises serializado.
	 */
	public void agregar(Aerolinea aerolinea)
	{
		aerolinea.setId(obtenerSiguienteId());
		
		List<Aerolinea> aerolineas = obtenerTodos();
		aerolineas.add(aerolinea);
		
		guardarLista(aerolineas);
	}
	
	
	/**
	 * Eliminar aerolinea de lista de aerolineas en archivo. Se elimina el elemento con la id del elemento dado.
	 */
	public void eliminar(Aerolinea aerolineaAEliminar)
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
	 */
	public void actualizar(Aerolinea aerolineaActualizado)
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
	 */
	private int obtenerSiguienteId()
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

	 */
	private void guardarLista(List<Aerolinea> aerolineas)
	{
		try {
			FileOutputStream fos = new FileOutputStream(nombreArchivo);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(aerolineas);
			oos.close();
		} 
		catch(IOException e) {
			System.out.println("Error guardando lista de objetos serializados en " + nombreArchivo + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
		}
	}
	

}

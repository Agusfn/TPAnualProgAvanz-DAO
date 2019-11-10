package dao.implementations.serializacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IPaisDao;
import modelo.Pais;
import util.Properties;

public class PaisDaoImplSerializacion implements IPaisDao {

	
	private static String nombreArchivo = Properties.getProperty("serial_paises");
	
	
	public PaisDaoImplSerializacion()
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Pais>());
		}
	}
	
	
	/**
	 * Obtener pais con id determinado de la lista del archivo.
	 */
	public Pais obtener(int id)
	{
		List<Pais> paises = obtenerTodos();
		
		for(Pais pais: paises)
		{
			if(pais.getId() == id)
				return pais;
		}
		return null;
	}
	
	
	/**
	 * Obtener lista de paises del archivo serializado.
	 */
	@SuppressWarnings("unchecked")
	public List<Pais> obtenerTodos()
	{
		
		try
		{
			FileInputStream fis = new FileInputStream(nombreArchivo);
			ObjectInputStream ois = new ObjectInputStream(fis);
	
			try {
				return (ArrayList<Pais>)ois.readObject();
			} catch (ClassNotFoundException e) {
				return new ArrayList<Pais>();
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
	 * Agregar un pais al archivo de lista de paises serializado.
	 */
	public void agregar(Pais pais)
	{
		pais.setId(obtenerSiguienteId());
		
		List<Pais> paises = obtenerTodos();
		paises.add(pais);
		
		guardarLista(paises);
	}
	
	
	/**
	 * Eliminar pais de lista de paises en archivo. Se elimina el elemento con la id del elemento dado.
	 */
	public void eliminar(Pais paisAEliminar)
	{
		List<Pais> paises = obtenerTodos();
		
		for(int i=0; i<paises.size(); i++)
		{
			Pais pais = paises.get(i);
			if(pais.getId() == paisAEliminar.getId()) {
				paises.remove(i);
				guardarLista(paises);
			}
		}
	}
	
	/**
	 * Actualizar un pais de la lista del archivo serializado. Se actualiza según el id.
	 */
	public void actualizar(Pais paisActualizado)
	{
		List<Pais> paises = obtenerTodos();
		
		for(int i=0; i<paises.size(); i++)
		{
			Pais pais = paises.get(i);
			if(pais.getId() == paisActualizado.getId()) {
				paises.set(i, paisActualizado);
				guardarLista(paises);
			}
		}
	}
	
	
	/**
	 * Obtener siguiente id de pais a guardar (id del ultimo de la lista+1).
	 */
	private int obtenerSiguienteId()
	{
		List<Pais> paises = obtenerTodos();
		
		if(paises.size() > 0) {
			return paises.get(paises.size()-1).getId() + 1;
		}
		else {
			return 1;
		}
	}	
	
	/**
	 * Guardar lista de paises en su archivo correspondiente (pisando el contenido anterior).
	 * @param paises
	 * @throws IOException 
	 */
	private void guardarLista(List<Pais> paises)
	{
		try 
		{
			FileOutputStream fos = new FileOutputStream(nombreArchivo);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(paises);
			oos.close();
		} 
		catch(IOException e) {
			System.out.println("Error guardando lista de objetos serializados en " + nombreArchivo + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}

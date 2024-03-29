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
	
	
	public PaisDaoImplSerializacion() throws IOException
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Pais>());
		}
	}
	
	
	/**
	 * Obtener pais con id determinado de la lista del archivo.
	 * @throws IOException 
	 */
	public Pais obtener(int id) throws IOException
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
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public List<Pais> obtenerTodos() throws IOException
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
	
	
	/**
	 * Agregar un pais al archivo de lista de paises serializado.
	 * @throws IOException 
	 */
	public void agregar(Pais pais) throws IOException
	{
		pais.setId(obtenerSiguienteId());
		
		List<Pais> paises = obtenerTodos();
		paises.add(pais);
		
		guardarLista(paises);
	}
	
	
	/**
	 * Eliminar pais de lista de paises en archivo. Se elimina el elemento con la id del elemento dado.
	 * @throws IOException 
	 */
	public void eliminar(Pais paisAEliminar) throws IOException
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
	 * Actualizar un pais de la lista del archivo serializado. Se actualiza seg�n el id.
	 * @throws IOException 
	 */
	public void actualizar(Pais paisActualizado) throws IOException
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
	 * @throws IOException 
	 */
	private int obtenerSiguienteId() throws IOException
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
	private void guardarLista(List<Pais> paises) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(nombreArchivo);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(paises);
		oos.close();
	}
	
	public void close() throws Exception
	{
	}
	
	
}

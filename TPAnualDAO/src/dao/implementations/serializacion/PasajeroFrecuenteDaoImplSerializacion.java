package dao.implementations.serializacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.IPasajeroFrecuenteDao;
import modelo.PasajeroFrecuente;
import util.Properties;

public class PasajeroFrecuenteDaoImplSerializacion implements IPasajeroFrecuenteDao {

	private static String nombreArchivo = Properties.getProperty("serial_pasajerosfrecuentes");
	
	
	public PasajeroFrecuenteDaoImplSerializacion()
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<PasajeroFrecuente>());
		}
	}
	
	
	/**
	 * Obtener pasajero frecuente con id determinado de la lista del archivo.
	 */
	public PasajeroFrecuente obtener(int id)
	{
		List<PasajeroFrecuente> pasajerosFrecuentes = obtenerTodos();
		
		for(PasajeroFrecuente pasajeroFrecuente: pasajerosFrecuentes)
		{
			if(pasajeroFrecuente.getId() == id)
				return pasajeroFrecuente;
		}
		return null;
	}
	
	
	/**
	 * Obtener lista de pasajeros frecuentes del archivo serializado.
	 */
	@SuppressWarnings("unchecked")
	public List<PasajeroFrecuente> obtenerTodos()
	{
		
		try {
			FileInputStream fis = new FileInputStream(nombreArchivo);
			ObjectInputStream ois = new ObjectInputStream(fis);
	
			try {
				return (ArrayList<PasajeroFrecuente>)ois.readObject();
			} catch (ClassNotFoundException e) {
				return new ArrayList<PasajeroFrecuente>();
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
	 * Agregar un pasajero frecuente al archivo de lista de pasajeros frecuentes serializado.
	 */
	public void agregar(PasajeroFrecuente pasajeroFrecuente)
	{
		pasajeroFrecuente.setId(obtenerSiguienteId());
		
		List<PasajeroFrecuente> pasajerosFrecuentes = obtenerTodos();
		pasajerosFrecuentes.add(pasajeroFrecuente);
		
		guardarLista(pasajerosFrecuentes);
	}
	
	
	/**
	 * Eliminar pasajero frecuente de lista de paises en archivo. Se elimina el elemento con la id del elemento dado.
	 */
	public void eliminar(PasajeroFrecuente pasajeroFrecuenteAEliminar)
	{
		List<PasajeroFrecuente> pasajerosFrecuentes = obtenerTodos();
		
		for(int i=0; i<pasajerosFrecuentes.size(); i++)
		{
			PasajeroFrecuente pasajeroFrecuente = pasajerosFrecuentes.get(i);
			if(pasajeroFrecuente.getId() == pasajeroFrecuenteAEliminar.getId()) {
				pasajerosFrecuentes.remove(i);
				guardarLista(pasajerosFrecuentes);
			}
		}
	}
	
	/**
	 * Actualizar un pasajero frecuente de la lista del archivo serializado. Se actualiza según el id.
	 */
	public void actualizar(PasajeroFrecuente pasajeroFrecuenteActualizado)
	{
		List<PasajeroFrecuente> pasajerosFrecuentes = obtenerTodos();
		
		for(int i=0; i<pasajerosFrecuentes.size(); i++)
		{
			PasajeroFrecuente pasajeroFrecuente = pasajerosFrecuentes.get(i);
			if(pasajeroFrecuente.getId() == pasajeroFrecuenteActualizado.getId()) {
				pasajerosFrecuentes.set(i, pasajeroFrecuenteActualizado);
				guardarLista(pasajerosFrecuentes);
			}
		}
	}
	
	
	/**
	 * Obtener siguiente id de pasajero frecuente a guardar (id del ultimo de la lista+1).
	 */
	private int obtenerSiguienteId()
	{
		List<PasajeroFrecuente> pasajerosFrecuentes = obtenerTodos();
		
		if(pasajerosFrecuentes.size() > 0) {
			return pasajerosFrecuentes.get(pasajerosFrecuentes.size()-1).getId() + 1;
		}
		else {
			return 1;
		}
	}	
	
	/**
	 * Guardar lista de pasajeros frecuentes en su archivo correspondiente (pisando el contenido anterior).
	 * @param pasajerosFrecuentes
	 * @throws IOException 
	 */
	private void guardarLista(List<PasajeroFrecuente> pasajerosFrecuentes)
	{
		try {
			FileOutputStream fos = new FileOutputStream(nombreArchivo);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(pasajerosFrecuentes);
			oos.close();
		} 
		catch(IOException e) {
			System.out.println("Error guardando lista de objetos serializados en " + nombreArchivo + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
		}
		
		
		
	}

}

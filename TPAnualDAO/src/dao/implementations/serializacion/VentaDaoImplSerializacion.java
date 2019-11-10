package dao.implementations.serializacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IVentaDao;
import modelo.Venta;
import util.Properties;

public class VentaDaoImplSerializacion implements IVentaDao {

	
	private static String nombreArchivo = Properties.getProperty("serial_ventas");
	
	
	public VentaDaoImplSerializacion() throws IOException
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Venta>());
		}
	}
	
	
	/**
	 * Obtener venta con id determinado de la lista del archivo.
	 * @throws IOException 
	 */
	public Venta obtener(int id) throws IOException
	{
		List<Venta> ventas = obtenerTodos();
		
		for(Venta venta: ventas)
		{
			if(venta.getId() == id)
				return venta;
		}
		return null;
	}
	
	
	/**
	 * Obtener lista de ventas del archivo serializado.
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public List<Venta> obtenerTodos() throws IOException
	{
		FileInputStream fis = new FileInputStream(nombreArchivo);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			return (ArrayList<Venta>)ois.readObject();
		} catch (ClassNotFoundException e) {
			return new ArrayList<Venta>();
		}
		finally {
			ois.close();
		}
		
		
	}
	
	
	/**
	 * Agregar una venta al archivo de lista de ventas serializado.
	 * @throws IOException 
	 */
	public void agregar(Venta venta) throws IOException
	{
		venta.setId(obtenerSiguienteId());
		
		List<Venta> ventas = obtenerTodos();
		ventas.add(venta);
		
		guardarLista(ventas);
	}
	
	
	/**
	 * Eliminar venta de lista de ventas en archivo. Se elimina el elemento con la id del elemento dado.
	 * @throws IOException 
	 */
	public void eliminar(Venta ventaAEliminar) throws IOException
	{
		List<Venta> ventas = obtenerTodos();
		
		for(int i=0; i<ventas.size(); i++)
		{
			Venta venta = ventas.get(i);
			if(venta.getId() == ventaAEliminar.getId()) {
				ventas.remove(i);
				guardarLista(ventas);
			}
		}
	}
	
	/**
	 * Actualizar un venta de la lista del archivo serializado. Se actualiza según el id.
	 * @throws IOException 
	 */
	public void actualizar(Venta ventaActualizada) throws IOException
	{
		List<Venta> ventas = obtenerTodos();
		
		for(int i=0; i<ventas.size(); i++)
		{
			Venta venta = ventas.get(i);
			if(venta.getId() == ventaActualizada.getId()) {
				ventas.set(i, ventaActualizada);
				guardarLista(ventas);
			}
		}
	}
	
	
	/**
	 * Obtener siguiente id de venta a guardar (id del ultimo de la lista+1).
	 * @throws IOException 
	 */
	private int obtenerSiguienteId() throws IOException
	{
		List<Venta> ventas = obtenerTodos();
		
		if(ventas.size() > 0) {
			return ventas.get(ventas.size()-1).getId() + 1;
		}
		else {
			return 1;
		}
	}	
	
	/**
	 * Guardar lista de ventas en su archivo correspondiente (pisando el contenido anterior).
	 * @param ventas
	 * @throws IOException 
	 */
	private void guardarLista(List<Venta> ventas) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(nombreArchivo);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(ventas);
		oos.close();		
	}

}

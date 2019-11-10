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
	
	
	public VentaDaoImplSerializacion()
	{		
		File archivo = new File(nombreArchivo);
		
		if(!archivo.exists()) {
			guardarLista(new ArrayList<Venta>());
		}
	}
	
	
	/**
	 * Obtener venta con id determinado de la lista del archivo.
	 */
	public Venta obtener(int id)
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
	 */
	@SuppressWarnings("unchecked")
	public List<Venta> obtenerTodos()
	{
		
		try {
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
		catch(IOException e) 
		{
			System.out.println("Error leyendo lista de objetos serializados de " + nombreArchivo + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
			return null;
		}		
		
	}
	
	
	/**
	 * Agregar una venta al archivo de lista de ventas serializado.
	 */
	public void agregar(Venta venta)
	{
		venta.setId(obtenerSiguienteId());
		
		List<Venta> ventas = obtenerTodos();
		ventas.add(venta);
		
		guardarLista(ventas);
	}
	
	
	/**
	 * Eliminar venta de lista de ventas en archivo. Se elimina el elemento con la id del elemento dado.
	 */
	public void eliminar(Venta ventaAEliminar)
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
	 */
	public void actualizar(Venta ventaActualizada)
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
	 */
	private int obtenerSiguienteId()
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
	private void guardarLista(List<Venta> ventas)
	{
		try {
			FileOutputStream fos = new FileOutputStream(nombreArchivo);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(ventas);
			oos.close();
		} 
		catch(IOException e) {
			System.out.println("Error guardando lista de objetos serializados en " + nombreArchivo + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
		}		
	}

}

package dao.implementations.serializacion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.IDireccionDao;
import modelo.Direccion;
import util.Properties;


public class DireccionDaoImplSerializacion implements IDireccionDao {

		
		private static String nombreArchivo = Properties.getProperty("serial_direcciones");
		
		
		public DireccionDaoImplSerializacion()
		{			
			File archivo = new File(nombreArchivo);
			
			if(!archivo.exists()) {
				guardarLista(new ArrayList<Direccion>());
			}
		}
		
		
		/**
		 * Obtener direccion con id determinado de la lista del archivo.
		 */
		public Direccion obtener(int id)
		{
			List<Direccion> direcciones = obtenerTodos();
			
			for(Direccion direccion: direcciones)
			{
				if(direccion.getId() == id)
					return direccion;
			}
			return null;
		}
		
		
		/**
		 * Obtener lista de direcciones del archivo serializado.
		 */
		@SuppressWarnings("unchecked")
		public List<Direccion> obtenerTodos()
		{
			
			try {
				FileInputStream fis = new FileInputStream(nombreArchivo);
				ObjectInputStream ois = new ObjectInputStream(fis);
	
				try {
					return (ArrayList<Direccion>)ois.readObject();
				} catch (ClassNotFoundException e) {
					return new ArrayList<Direccion>();
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
		 * Agregar un direccion al archivo de lista de direcciones serializado.
		 */
		public void agregar(Direccion direccion)
		{
			direccion.setId(obtenerSiguienteId());
			
			List<Direccion> direcciones = obtenerTodos();
			direcciones.add(direccion);
			
			guardarLista(direcciones);
		}
		
		
		/**
		 * Eliminar direccion de lista de direcciones en archivo. Se elimina el elemento con la id del elemento dado.
		 */
		public void eliminar(Direccion direccionAEliminar)
		{
			List<Direccion> direcciones = obtenerTodos();
			
			for(int i=0; i<direcciones.size(); i++)
			{
				Direccion direccion = direcciones.get(i);
				if(direccion.getId() == direccionAEliminar.getId()) {
					direcciones.remove(i);
					guardarLista(direcciones);
				}
			}
		}
		
		/**
		 * Actualizar una direccion de la lista del archivo serializado. Se actualiza según el id.
		 */
		public void actualizar(Direccion direccionActualizado)
		{
			List<Direccion> direcciones = obtenerTodos();
			
			for(int i=0; i<direcciones.size(); i++)
			{
				Direccion direccion = direcciones.get(i);
				if(direccion.getId() == direccionActualizado.getId()) {
					direcciones.set(i, direccionActualizado);
					guardarLista(direcciones);
				}
			}
		}
		
		
		/**
		 * Obtener siguiente id de direccion a guardar (id del ultimo de la lista+1).
		 */
		private int obtenerSiguienteId()
		{
			List<Direccion> direcciones = obtenerTodos();
			
			if(direcciones.size() > 0) {
				return direcciones.get(direcciones.size()-1).getId() + 1;
			}
			else {
				return 1;
			}
		}	
		
		/**
		 * Guardar lista de direcciones en su archivo correspondiente (pisando el contenido anterior).
		 * @param direcciones
		 * @throws IOException 
		 */
		private void guardarLista(List<Direccion> direcciones)
		{
			try {
				FileOutputStream fos = new FileOutputStream(nombreArchivo);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(direcciones);
				oos.close();
			} 
			catch(IOException e) {
				System.out.println("Error guardando lista de objetos serializados en " + nombreArchivo + System.lineSeparator() + "Stack:");
				e.printStackTrace();
				System.exit(1);
			}
		}
		
}


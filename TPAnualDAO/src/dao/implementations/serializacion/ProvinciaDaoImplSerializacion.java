package dao.implementations.serializacion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.IProvinciaDao;
import modelo.Provincia;
import util.Properties;

public class ProvinciaDaoImplSerializacion implements IProvinciaDao {

		
		private static String nombreArchivo = Properties.getProperty("serial_provincias");
		
		
		public ProvinciaDaoImplSerializacion() throws IOException
		{			
			File archivo = new File(nombreArchivo);
			
			if(!archivo.exists()) {
				guardarLista(new ArrayList<Provincia>());
			}
		}
		
		
		/**
		 * Obtener provincia con id determinado de la lista del archivo.
		 * @throws IOException 
		 */
		public Provincia obtener(int id) throws IOException
		{
			List<Provincia> provincias = obtenerTodos();
			
			for(Provincia provincia: provincias)
			{
				if(provincia.getId() == id)
					return provincia;
			}
			return null;
		}
		
		
		/**
		 * Obtener lista de provincias del archivo serializado.
		 * @throws IOException 
		 */
		@SuppressWarnings("unchecked")
		public List<Provincia> obtenerTodos() throws IOException
		{
		
			FileInputStream fis = new FileInputStream(nombreArchivo);
			ObjectInputStream ois = new ObjectInputStream(fis);

			try {
				return (ArrayList<Provincia>)ois.readObject();
			} catch (ClassNotFoundException e) {
				return new ArrayList<Provincia>();
			}
			finally {
				ois.close();
			}
			
		}
		
		
		/**
		 * Agregar un provincia al archivo de lista de provincias serializado.
		 * @throws IOException 
		 */
		public void agregar(Provincia provincia) throws IOException
		{
			provincia.setId(obtenerSiguienteId());
			
			List<Provincia> provincias = obtenerTodos();
			provincias.add(provincia);
			
			guardarLista(provincias);
		}
		
		
		/**
		 * Eliminar provincia de lista de provincias en archivo. Se elimina el elemento con la id del elemento dado.
		 * @throws IOException 
		 */
		public void eliminar(Provincia provinciaAEliminar) throws IOException
		{
			List<Provincia> provincias = obtenerTodos();
			
			for(int i=0; i<provincias.size(); i++)
			{
				Provincia provincia = provincias.get(i);
				if(provincia.getId() == provinciaAEliminar.getId()) {
					provincias.remove(i);
					guardarLista(provincias);
				}
			}
		}
		
		/**
		 * Actualizar un provincia de la lista del archivo serializado. Se actualiza según el id.
		 * @throws IOException 
		 */
		public void actualizar(Provincia provinciaActualizado) throws IOException
		{
			List<Provincia> provincias = obtenerTodos();
			
			for(int i=0; i<provincias.size(); i++)
			{
				Provincia provincia = provincias.get(i);
				if(provincia.getId() == provinciaActualizado.getId()) {
					provincias.set(i, provinciaActualizado);
					guardarLista(provincias);
				}
			}
		}
		
		
		/**
		 * Obtener siguiente id de provincia a guardar (id del ultimo de la lista+1).
		 * @throws IOException 
		 */
		private int obtenerSiguienteId() throws IOException
		{
			List<Provincia> provincias = obtenerTodos();
			
			if(provincias.size() > 0) {
				return provincias.get(provincias.size()-1).getId() + 1;
			}
			else {
				return 1;
			}
		}	
		
		/**
		 * Guardar lista de provincias en su archivo correspondiente (pisando el contenido anterior).
		 * @param provincias
		 * @throws IOException 
		 */
		private void guardarLista(List<Provincia> provincias) throws IOException
		{
			FileOutputStream fos = new FileOutputStream(nombreArchivo);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(provincias);
			oos.close();
		}
		
		public void close() throws Exception
		{
		}
		
		
}

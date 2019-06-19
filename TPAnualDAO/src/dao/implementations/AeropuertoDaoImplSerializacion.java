package dao.implementations;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import dao.interfaces.IAeropuertoDao;
import modelo.Aeropuerto;
import util.PropertiesUtil;

public class AeropuertoDaoImplSerializacion implements IAeropuertoDao{
	
	private static String nombreArchivo;
		
		
		public AeropuertoDaoImplSerializacion() throws IOException
		{
			nombreArchivo = PropertiesUtil.aeropuertosFile("serializacion");
			
			File archivo = new File(nombreArchivo);
			
			if(!archivo.exists()) {
				guardarLista(new ArrayList<Aeropuerto>());
			}
		}
		
		
		/**
		 * Obtener aeropuerto con id determinado de la lista del archivo.
		 */
		public Aeropuerto obtener(int id) throws IOException
		{
			List<Aeropuerto> aeropuertos = obtenerTodos();
			
			for(Aeropuerto aeropuerto: aeropuertos)
			{
				if(aeropuerto.getId() == id)
					return aeropuerto;
			}
			return null;
		}
		
		
		/**
		 * Obtener lista de aeropuertos del archivo serializado.
		 */
		@SuppressWarnings("unchecked")
		public List<Aeropuerto> obtenerTodos() throws IOException
		{
			FileInputStream fis = new FileInputStream(nombreArchivo);
			ObjectInputStream ois = new ObjectInputStream(fis);

			try {
				return (ArrayList<Aeropuerto>)ois.readObject();
			} catch (ClassNotFoundException e) {
				return new ArrayList<Aeropuerto>();
			}
			finally {
				ois.close();
			}
		}
		
		
		/**
		 * Agregar un aeropuerto al archivo de lista de aeropuertos serializado.
		 */
		public void agregar(Aeropuerto aeropuerto) throws IOException
		{
			aeropuerto.setId(obtenerSiguienteId());
			
			List<Aeropuerto> aeropuertos = obtenerTodos();
			aeropuertos.add(aeropuerto);
			
			guardarLista(aeropuertos);
		}
		
		
		/**
		 * Eliminar aeropuerto de lista de aeropuertos en archivo. Se elimina el elemento con la id del elemento dado.
		 */
		public void eliminar(Aeropuerto aeropuertoAEliminar) throws IOException
		{
			List<Aeropuerto> aeropuertos = obtenerTodos();
			
			for(int i=0; i<aeropuertos.size(); i++)
			{
				Aeropuerto aeropuerto = aeropuertos.get(i);
				if(aeropuerto.getId() == aeropuertoAEliminar.getId()) {
					aeropuertos.remove(i);
					guardarLista(aeropuertos);
				}
			}
		}
		
		/**
		 * Actualizar un aeropuerto de la lista del archivo serializado. Se actualiza según el id.
		 */
		public void actualizar(Aeropuerto aeropuertoActualizado) throws IOException
		{
			List<Aeropuerto> aeropuertos = obtenerTodos();
			
			for(int i=0; i<aeropuertos.size(); i++)
			{
				Aeropuerto aeropuerto = aeropuertos.get(i);
				if(aeropuerto.getId() == aeropuertoActualizado.getId()) {
					aeropuertos.set(i, aeropuertoActualizado);
					guardarLista(aeropuertos);
				}
			}
		}
		
		
		/**
		 * Obtener siguiente id de aeropuerto a guardar (id del ultimo de la lista+1).
		 */
		private int obtenerSiguienteId() throws IOException
		{
			List<Aeropuerto> aeropuertos = obtenerTodos();
			
			if(aeropuertos.size() > 0) {
				return aeropuertos.get(aeropuertos.size()-1).getId() + 1;
			}
			else {
				return 1;
			}
		}	
		
		/**
		 * Guardar lista de aeropuertos en su archivo correspondiente (pisando el contenido anterior).
		 * @param aeropuertos
		 * @throws IOException 
		 */
		private void guardarLista(List<Aeropuerto> aeropuertos) throws IOException
		{
			FileOutputStream fos = new FileOutputStream(nombreArchivo);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(aeropuertos);
			oos.close();
		}
		
}

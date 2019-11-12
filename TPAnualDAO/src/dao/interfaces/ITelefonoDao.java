package dao.interfaces;

import java.util.List;

import modelo.Telefono;

public interface ITelefonoDao {

	public Telefono obtener(int id) throws Exception;
	public List<Telefono> obtenerTodos() throws Exception;	
	public void agregar(Telefono telefono) throws Exception;
	public void eliminar(Telefono telefono) throws Exception;
	public void actualizar(Telefono telefono) throws Exception;
	public void close() throws Exception;
	
		

}

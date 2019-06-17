package dao.interfaces;

import java.io.IOException;
import java.util.List;

import modelo.Telefono;

public interface ITelefonoDao {

	public Telefono obtener(int id) throws IOException;
	public List<Telefono> obtenerTodos() throws IOException;	
	public void agregar(Telefono telefono) throws IOException;
	public void eliminar(Telefono telefono) throws IOException;
	public void actualizar(Telefono telefono) throws IOException;
	
		

}

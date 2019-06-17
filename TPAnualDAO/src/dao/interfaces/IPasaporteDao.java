package dao.interfaces;
import java.io.IOException;
import java.util.List;

import modelo.Pasaporte;

public interface IPasaporteDao {


	public Pasaporte obtener(int id) throws IOException;
	public List<Pasaporte> obtenerTodos() throws IOException;	
	public void agregar(Pasaporte pais) throws IOException;
	public void eliminar(Pasaporte pais) throws IOException;
	public void actualizar(Pasaporte pais) throws IOException;
	
	
}

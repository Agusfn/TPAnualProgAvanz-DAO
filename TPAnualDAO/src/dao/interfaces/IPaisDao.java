package dao.interfaces;
import java.io.IOException;
import java.util.List;

import modelo.Pais;

public interface IPaisDao {


	public Pais obtener(int id) throws IOException;
	public List<Pais> obtenerTodos() throws IOException;	
	public void agregar(Pais pais) throws IOException;
	public void eliminar(Pais pais) throws IOException;
	public void actualizar(Pais pais) throws IOException;
	
	
}

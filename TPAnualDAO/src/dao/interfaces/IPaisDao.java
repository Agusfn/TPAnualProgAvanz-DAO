package dao.interfaces;
import java.util.List;

import modelo.Pais;

public interface IPaisDao {


	public Pais obtener(int id) throws Exception;
	public List<Pais> obtenerTodos() throws Exception;	
	public void agregar(Pais pais) throws Exception;
	public void eliminar(Pais pais) throws Exception;
	public void actualizar(Pais pais) throws Exception;
	
	
}

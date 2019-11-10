package dao.interfaces;
import java.util.List;

import modelo.Pasaporte;

public interface IPasaporteDao {


	public Pasaporte obtener(int id) throws Exception;
	public List<Pasaporte> obtenerTodos() throws Exception;	
	public void agregar(Pasaporte pais) throws Exception;
	public void eliminar(Pasaporte pais) throws Exception;
	public void actualizar(Pasaporte pais) throws Exception;
	
	
}

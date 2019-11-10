package dao.interfaces;
import java.util.List;

import modelo.Pasaporte;

public interface IPasaporteDao {


	public Pasaporte obtener(int id);
	public List<Pasaporte> obtenerTodos();	
	public void agregar(Pasaporte pais);
	public void eliminar(Pasaporte pais);
	public void actualizar(Pasaporte pais);
	
	
}

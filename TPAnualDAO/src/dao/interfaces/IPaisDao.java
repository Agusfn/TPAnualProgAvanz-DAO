package dao.interfaces;
import java.util.List;

import modelo.Pais;

public interface IPaisDao {


	public Pais obtener(int id);
	public List<Pais> obtenerTodos();	
	public void agregar(Pais pais);
	public void eliminar(Pais pais);
	public void actualizar(Pais pais);
	
	
}

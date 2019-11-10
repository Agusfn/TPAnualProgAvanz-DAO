package dao.interfaces;

import java.util.List;

import modelo.Direccion;

public interface IDireccionDao {

	
	public Direccion obtener(int id);
	public List<Direccion> obtenerTodos();	
	public void agregar(Direccion direccion);
	public void eliminar(Direccion direccion);
	public void actualizar(Direccion direccion);
	
	
	
}

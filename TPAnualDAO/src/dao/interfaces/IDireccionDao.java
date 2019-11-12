package dao.interfaces;

import java.util.List;

import modelo.Direccion;

public interface IDireccionDao {

	
	public Direccion obtener(int id) throws Exception;
	public List<Direccion> obtenerTodos() throws Exception;	
	public void agregar(Direccion direccion) throws Exception;
	public void eliminar(Direccion direccion) throws Exception;
	public void actualizar(Direccion direccion) throws Exception;
	public void close() throws Exception;
	
	
	
}

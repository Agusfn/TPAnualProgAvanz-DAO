package dao.interfaces;

import java.util.List;

import modelo.Telefono;

public interface ITelefonoDao {

	public Telefono obtener(int id);
	public List<Telefono> obtenerTodos();	
	public void agregar(Telefono telefono);
	public void eliminar(Telefono telefono);
	public void actualizar(Telefono telefono);
	
		

}

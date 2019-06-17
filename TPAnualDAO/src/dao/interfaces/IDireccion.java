package dao.interfaces;

import java.io.IOException;
import java.util.List;

import modelo.Direccion;

public interface IDireccion {

	
	public Direccion obtener(int id) throws IOException;
	public List<Direccion> obtenerTodos() throws IOException;	
	public void agregar(Direccion direccion) throws IOException;
	public void eliminar(Direccion direccion) throws IOException;
	public void actualizar(Direccion direccion) throws IOException;
	
	
	
}

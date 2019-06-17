package dao.interfaces;

import java.io.IOException;
import java.util.List;

import modelo.Cliente;


public interface IClienteDao {

	public Cliente obtener(int id) throws IOException;
	public List<Cliente> obtenerTodos() throws IOException;	
	public void agregar(Cliente cliente) throws IOException;
	public void eliminar(Cliente cliente) throws IOException;
	public void actualizar(Cliente cliente) throws IOException;
	//
	
}

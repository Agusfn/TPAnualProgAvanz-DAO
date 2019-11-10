package dao.interfaces;

import java.util.List;

import modelo.Cliente;


public interface IClienteDao {

	public Cliente obtener(int id) throws Exception;
	public List<Cliente> obtenerTodos() throws Exception;	
	public void agregar(Cliente cliente) throws Exception;
	public void eliminar(Cliente cliente) throws Exception;
	public void actualizar(Cliente cliente) throws Exception;
	//
	
}

package dao.interfaces;

import java.util.List;

import modelo.Cliente;


public interface IClienteDao {

	public Cliente obtener(int id);
	public List<Cliente> obtenerTodos();	
	public void agregar(Cliente cliente);
	public void eliminar(Cliente cliente);
	public void actualizar(Cliente cliente);
	//
	
}

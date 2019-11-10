package dao.interfaces;

import java.util.List;

import modelo.Vuelo;


public interface IVueloDao {


	public Vuelo obtener(int id);
	public List<Vuelo> obtenerTodos();	
	public void agregar(Vuelo vuelo);
	public void eliminar(Vuelo vuelo);
	public void actualizar(Vuelo vuelo);

}

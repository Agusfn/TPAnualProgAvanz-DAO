package dao.interfaces;

import java.util.List;

import modelo.Vuelo;


public interface IVueloDao {


	public Vuelo obtener(int id) throws Exception;
	public List<Vuelo> obtenerTodos() throws Exception;	
	public void agregar(Vuelo vuelo) throws Exception;
	public void eliminar(Vuelo vuelo) throws Exception;
	public void actualizar(Vuelo vuelo) throws Exception;
	public void close() throws Exception;

}

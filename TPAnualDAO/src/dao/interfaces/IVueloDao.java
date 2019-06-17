package dao.interfaces;

import java.io.IOException;
import java.util.List;

import modelo.Vuelo;

public interface IVueloDao {


	public Vuelo obtener(int id) throws IOException;
	public List<Vuelo> obtenerTodos() throws IOException;	
	public void agregar(Vuelo vuelo) throws IOException;
	public void eliminar(Vuelo vuelo) throws IOException;
	public void actualizar(Vuelo vuelo) throws IOException;

}

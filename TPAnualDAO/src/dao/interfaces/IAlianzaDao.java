package dao.interfaces;

import java.util.List;

import modelo.Alianza;

public interface IAlianzaDao {

	public Alianza obtener(int id) throws Exception;
	public List<Alianza> obtenerTodos() throws Exception;	
	public void agregar(Alianza aerolinea) throws Exception;
	public void eliminar(Alianza aerolinea) throws Exception;
	public void actualizar(Alianza aerolinea) throws Exception;
	public void close() throws Exception;
	//
	
}

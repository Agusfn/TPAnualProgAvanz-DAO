package dao.interfaces;

import java.util.List;

import modelo.Aerolinea;

public interface IAerolineaDao {

	public Aerolinea obtener(int id) throws Exception;
	public List<Aerolinea> obtenerTodos() throws Exception;	
	public void agregar(Aerolinea aerolinea) throws Exception;
	public void eliminar(Aerolinea aerolinea) throws Exception;
	public void actualizar(Aerolinea aerolinea) throws Exception;
	//
	
}

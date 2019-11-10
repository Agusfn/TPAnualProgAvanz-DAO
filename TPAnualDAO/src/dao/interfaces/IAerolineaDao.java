package dao.interfaces;

import java.util.List;

import modelo.Aerolinea;

public interface IAerolineaDao {

	public Aerolinea obtener(int id);
	public List<Aerolinea> obtenerTodos();	
	public void agregar(Aerolinea aerolinea);
	public void eliminar(Aerolinea aerolinea);
	public void actualizar(Aerolinea aerolinea);
	//
	
}

package dao.interfaces;

import java.io.IOException;
import java.util.List;

import modelo.Aerolinea;

public interface IAerolineaDao {

	public Aerolinea obtener(int id) throws IOException;
	public List<Aerolinea> obtenerTodos() throws IOException;	
	public void agregar(Aerolinea aerolinea) throws IOException;
	public void eliminar(Aerolinea aerolinea) throws IOException;
	public void actualizar(Aerolinea aerolinea) throws IOException;
	
	
}

package dao.interfaces;

import java.io.IOException;
import java.util.List;

import modelo.Aeropuerto;

public interface IAeropuertoDao {
	
	public Aeropuerto obtener(int id) throws IOException;
	public List<Aeropuerto> obtenerTodos() throws IOException;	
	public void agregar(Aeropuerto aeropuerto) throws IOException;
	public void eliminar(Aeropuerto aeropuerto) throws IOException;
	public void actualizar(Aeropuerto aeropueto) throws IOException;
	

}

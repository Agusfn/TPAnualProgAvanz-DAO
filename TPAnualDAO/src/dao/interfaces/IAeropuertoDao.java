package dao.interfaces;

import java.util.List;

import modelo.Aeropuerto;

public interface IAeropuertoDao {
	
	public Aeropuerto obtener(int id) throws Exception;
	public List<Aeropuerto> obtenerTodos() throws Exception;	
	public void agregar(Aeropuerto aeropuerto) throws Exception;
	public void eliminar(Aeropuerto aeropuerto) throws Exception;
	public void actualizar(Aeropuerto aeropueto) throws Exception;
	

}

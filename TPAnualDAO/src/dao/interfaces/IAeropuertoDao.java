package dao.interfaces;

import java.util.List;

import modelo.Aeropuerto;

public interface IAeropuertoDao {
	
	public Aeropuerto obtener(int id);
	public List<Aeropuerto> obtenerTodos();	
	public void agregar(Aeropuerto aeropuerto);
	public void eliminar(Aeropuerto aeropuerto);
	public void actualizar(Aeropuerto aeropueto);
	

}

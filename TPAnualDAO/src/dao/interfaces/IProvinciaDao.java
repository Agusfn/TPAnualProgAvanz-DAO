package dao.interfaces;

import java.util.List;

import modelo.Provincia;

public interface IProvinciaDao {
	
	public Provincia obtener(int id) throws Exception;
	public List<Provincia> obtenerTodos() throws Exception;	
	public void agregar(Provincia provincia) throws Exception;
	public void eliminar(Provincia provincia) throws Exception;
	public void actualizar(Provincia provincia) throws Exception;
	

}

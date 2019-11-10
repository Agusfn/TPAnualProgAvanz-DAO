package dao.interfaces;

import java.util.List;

import modelo.Provincia;

public interface IProvinciaDao {
	
	public Provincia obtener(int id);
	public List<Provincia> obtenerTodos();	
	public void agregar(Provincia provincia);
	public void eliminar(Provincia provincia);
	public void actualizar(Provincia provincia);
	

}

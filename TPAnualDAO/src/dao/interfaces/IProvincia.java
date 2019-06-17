package dao.interfaces;

import java.io.IOException;
import java.util.List;

import modelo.Provincia;

public interface IProvincia {
	
	public Provincia obtener(int id) throws IOException;
	public List<Provincia> obtenerTodos() throws IOException;	
	public void agregar(Provincia provincia) throws IOException;
	public void eliminar(Provincia provincia) throws IOException;
	public void actualizar(Provincia provincia) throws IOException;
	

}

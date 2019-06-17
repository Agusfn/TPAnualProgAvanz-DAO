package dao.interfaces;

import java.io.IOException;
import java.util.List;

import modelo.Venta;
//hola
public interface IVentaDao {


	public Venta obtener(int id) throws IOException;
	public List<Venta> obtenerTodos() throws IOException;	
	public void agregar(Venta venta) throws IOException;
	public void eliminar(Venta venta) throws IOException;
	public void actualizar(Venta venta) throws IOException;
	
}

package dao.interfaces;
import java.util.List;

import modelo.Venta;

public interface IVentaDao {


	public Venta obtener(int id) throws Exception;
	public List<Venta> obtenerTodos() throws Exception;	
	public void agregar(Venta venta) throws Exception;
	public void eliminar(Venta venta) throws Exception;
	public void actualizar(Venta venta) throws Exception;
	public void close() throws Exception;
	
}

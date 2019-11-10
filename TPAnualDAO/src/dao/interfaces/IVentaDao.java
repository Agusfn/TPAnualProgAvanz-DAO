package dao.interfaces;
import java.util.List;

import modelo.Venta;

public interface IVentaDao {


	public Venta obtener(int id);
	public List<Venta> obtenerTodos();	
	public void agregar(Venta venta);
	public void eliminar(Venta venta);
	public void actualizar(Venta venta);
	
}

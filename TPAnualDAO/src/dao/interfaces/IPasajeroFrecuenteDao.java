package dao.interfaces;
import java.util.List;
import modelo.PasajeroFrecuente;

public interface IPasajeroFrecuenteDao {


	public PasajeroFrecuente obtener(int id);
	public List<PasajeroFrecuente> obtenerTodos();	
	public void agregar(PasajeroFrecuente pais);
	public void eliminar(PasajeroFrecuente pais);
	public void actualizar(PasajeroFrecuente pais);
	
	
}

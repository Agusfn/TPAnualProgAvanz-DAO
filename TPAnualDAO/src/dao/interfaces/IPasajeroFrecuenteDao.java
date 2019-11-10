package dao.interfaces;
import java.util.List;
import modelo.PasajeroFrecuente;

public interface IPasajeroFrecuenteDao {


	public PasajeroFrecuente obtener(int id) throws Exception;
	public List<PasajeroFrecuente> obtenerTodos() throws Exception;	
	public void agregar(PasajeroFrecuente pais) throws Exception;
	public void eliminar(PasajeroFrecuente pais) throws Exception;
	public void actualizar(PasajeroFrecuente pais) throws Exception;
	
	
}

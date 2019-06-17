package dao.interfaces;
import java.io.IOException;
import java.util.List;
import modelo.PasajeroFrecuente;

public interface IPasajeroFrecuenteDao {


	public PasajeroFrecuente obtener(int id) throws IOException;
	public List<PasajeroFrecuente> obtenerTodos() throws IOException;	
	public void agregar(PasajeroFrecuente pais) throws IOException;
	public void eliminar(PasajeroFrecuente pais) throws IOException;
	public void actualizar(PasajeroFrecuente pais) throws IOException;
	
	
}

package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IDireccionDao;
import modelo.Direccion;
import modelo.Pais;
import modelo.Provincia;
import util.DbQuery;

public class DireccionDaoImplDB implements IDireccionDao {

	private DbQuery query;

	private static String tableName = "direcciones";
	
	
	public DireccionDaoImplDB() throws SQLException
	{
		query = new DbQuery();
	}
	
	public DireccionDaoImplDB(DbQuery query)
	{
		this.query = query;
	}
	
	
	public Direccion obtener(int id) throws SQLException
	{
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);
		
		if(query.nextResult()) {
			return createDireccionFromQueryRow();
		}
		else
			return null;
	}
	
	
	public List<Direccion> obtenerTodos() throws SQLException {
		
		List<Direccion> direcciones = new ArrayList<Direccion>();
		
		query.select("SELECT * FROM " + tableName);
		
		while(query.nextResult()) {
			direcciones.add(createDireccionFromQueryRow());
		}
		
		return direcciones;
	}
	
	
	public void agregar(Direccion direccion) throws SQLException
	{
		
		Object idProvincia = null;
		if(direccion.getProvincia() != null) {
			idProvincia = direccion.getProvincia().getId();
		}
		
		Object idPais = null;
		if(direccion.getPais() != null) {
			idPais = direccion.getPais().getId();
		}
		
		query.execute("INSERT INTO " + tableName + " (calle, altura, ciudad, id_provincia, nombre_provincia, id_pais, nombre_pais, codigo_postal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", 
				direccion.getCalle(), direccion.getAltura(), direccion.getCiudad(), idProvincia, direccion.getNombreProvincia(), idPais, direccion.getNombrePais(),
				direccion.getCodigoPostal());

		direccion.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Direccion direccion) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", direccion.getId());
	}
	
	
	public void actualizar(Direccion direccion) throws SQLException
	{
		
		Object idProvincia = null;
		if(direccion.getProvincia() != null) {
			idProvincia = direccion.getProvincia().getId();
		}
		
		Object idPais = null;
		if(direccion.getPais() != null) {
			idPais = direccion.getPais().getId();
		}
		
		query.update("UPDATE " + tableName + "SET calle = ?, altura = ?, ciudad = ?, id_provincia = ?, nombre_provincia = ?, id_pais = ?, nombre_pais = ?, codigo_postal = ? WHERE id = ?", 
				direccion.getCalle(), direccion.getAltura(), direccion.getCiudad(), idProvincia, direccion.getNombreProvincia(), idPais, direccion.getNombrePais(), 
				direccion.getCodigoPostal(), direccion.getId());
	}
	
	
	public void close() throws SQLException
	{
		query.close();
	}
	
	
	/**
	 * Crear un objecto direccion a partir del resultado de la fila actual de la consulta this.query
	 * @return
	 * @throws SQLException 
	 */
	private Direccion createDireccionFromQueryRow() throws SQLException
	{
		Direccion direccion = new Direccion();
		
		direccion.setId(query.getInt("id"));
		direccion.setCalle(query.getString("calle"));
		direccion.setAltura(query.getString("altura"));
		direccion.setCiudad(query.getString("ciudad"));
		direccion.setCodigoPostal(query.getString("codigo_postal"));
		
		
		int idProvincia = query.getInt("id_provincia");
		if(idProvincia != 0) {
			direccion.setProvincia(new Provincia(idProvincia));
		}
		
		direccion.setNombreProvincia(query.getString("nombre_provincia"));
		
		int idPais = query.getInt("id_pais");
		if(idPais != 0) {
			direccion.setPais(new Pais(idPais));
		}
		
		direccion.setNombrePais(query.getString("nombre_pais"));
		
		return direccion;	
	}

	
	
}

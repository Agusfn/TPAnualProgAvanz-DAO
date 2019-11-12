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
		query.execute("INSERT INTO " + tableName + " (calle, altura, ciudad, id_provincia, id_pais, codigo_postal) VALUES (?, ?, ?, ?, ?, ?)", 
				direccion.getCalle(), direccion.getAltura(), direccion.getCiudad(), direccion.getProvincia().getId(), direccion.getPais().getId(),
				direccion.getCodigoPostal());

		direccion.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Direccion direccion) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", direccion.getId());
	}
	
	
	public void actualizar(Direccion direccion) throws SQLException
	{
		query.update("UPDATE " + tableName + "SET calle = ?, altura = ?, ciudad = ?, id_provincia = ?, id_pais = ?, codigo_postal = ? WHERE id = ?", 
				direccion.getCalle(), direccion.getAltura(), direccion.getCiudad(), direccion.getProvincia().getId(), direccion.getPais().getId(),
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
		
		Provincia provincia = new Provincia(query.getInt("id_provincia"));
		direccion.setProvincia(provincia);
		
		Pais pais = new Pais(query.getInt("id_pais"));
		direccion.setPais(pais);
		
		return direccion;	
	}

	
	
}

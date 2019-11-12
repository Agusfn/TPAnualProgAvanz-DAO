package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IProvinciaDao;
import modelo.Pais;
import modelo.Provincia;
import util.DbQuery;

public class ProvinciaDaoImplDB implements IProvinciaDao {

	private DbQuery query;

	private static String tableName = "provincias";
	
	

	public ProvinciaDaoImplDB() throws SQLException
	{
		query = new DbQuery();
	}
	
	public ProvinciaDaoImplDB(DbQuery query)
	{
		this.query = query;
	}
	
	
	public Provincia obtener(int id) throws SQLException
	{
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);
		
		if(query.nextResult()) {
			return createProvinciaFromQueryRow();
		}
		else
			return null;
	}
	
	
	public List<Provincia> obtenerTodos() throws SQLException {
		
		List<Provincia> provincias = new ArrayList<Provincia>();
		
		query.select("SELECT * FROM " + tableName);
		
		while(query.nextResult()) {
			provincias.add(createProvinciaFromQueryRow());
		}
		
		return provincias;
	}
	
	
	public void agregar(Provincia provincia) throws SQLException
	{
		query.update("INSERT INTO " + tableName + " (nombre, id_pais) VALUES (?, ?)", provincia.getNombre(), provincia.getPais().getId());

		provincia.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Provincia provincia) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", provincia.getId());
	}
	
	
	public void actualizar(Provincia provincia) throws SQLException
	{
		query.update("UPDATE " + tableName + "SET nombre = ?, id_pais = ? WHERE id = ?", provincia.getNombre(), provincia.getPais().getId(), provincia.getId());
	}
	
	
	public void close() throws SQLException
	{
		query.close();
	}
	
	
	/**
	 * Crear un objecto pais a partir del resultado de la fila actual de la consulta this.query
	 * @return
	 * @throws SQLException 
	 */
	private Provincia createProvinciaFromQueryRow() throws SQLException
	{
		Provincia nuevaProvincia = new Provincia();
		
		nuevaProvincia.setId(query.getInt("id"));
		nuevaProvincia.setNombre(query.getString("nombre"));
		
		Pais nuevoPais = new Pais();
		nuevoPais.setId(query.getInt("id_pais"));
		nuevaProvincia.setPais(nuevoPais);
		
		return nuevaProvincia;	
	}

	
	
}

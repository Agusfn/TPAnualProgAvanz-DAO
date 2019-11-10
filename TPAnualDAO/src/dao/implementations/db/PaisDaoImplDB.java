package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Pais;
import util.DbQuery;

public class PaisDaoImplDB {

	private DbQuery query;

	private static String tableName = "paises";
	
	
	public PaisDaoImplDB() throws SQLException
	{
		query = new DbQuery();
	}
	
	public PaisDaoImplDB(DbQuery query)
	{
		this.query = query;
	}
	
	
	public Pais obtener(int id) throws SQLException
	{
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);
		
		if(query.nextResult()) {
			return createPaisFromQueryRow();
		}
		else
			return null;
	}
	
	
	public List<Pais> obtenerTodos() throws SQLException {
		
		List<Pais> paises = new ArrayList<Pais>();
		
		query.select("SELECT * FROM " + tableName);
		
		while(query.nextResult()) {
			paises.add(createPaisFromQueryRow());
		}
		
		return paises;
	}
	
	
	public void agregar(Pais pais) throws SQLException
	{
		query.update("INSERT INTO " + tableName + " (nombre) VALUES (?)", pais.getNombre());

		pais.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Pais pais) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", pais.getId());
	}
	
	
	public void actualizar(Pais pais) throws SQLException
	{
		query.update("UPDATE " + tableName + "SET nombre = ? WHERE id = ?", pais.getNombre(), pais.getId());
	}
	
	
	public void closeDb() throws SQLException
	{
		query.close();
	}
	
	
	/**
	 * Crear un objecto pais a partir del resultado de la fila actual de la consulta this.query
	 * @return
	 * @throws SQLException 
	 */
	private Pais createPaisFromQueryRow() throws SQLException
	{
		Pais nuevoPais = new Pais();
		
		nuevoPais.setId(query.getInt("id"));
		nuevoPais.setNombre(query.getString("nombre"));
		
		return nuevoPais;	
	}

	
	
}

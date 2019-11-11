package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Aerolinea;
import util.DbQuery;

public class AerolineaDaoImplDB {

	private DbQuery query;

	private static String tableName = "aerolineas";
	
	
	public AerolineaDaoImplDB() throws SQLException
	{
		query = new DbQuery();
	}
	
	public AerolineaDaoImplDB(DbQuery query)
	{
		this.query = query;
	}
	
	
	public Aerolinea obtener(int id) throws SQLException
	{
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);
		
		if(query.nextResult()) {
			return createAerolineaFromQueryRow();
		}
		else
			return null;
	}
	
	
	public List<Aerolinea> obtenerTodos() throws SQLException {
		
		List<Aerolinea> aerolineas = new ArrayList<Aerolinea>();
		
		query.select("SELECT * FROM " + tableName);
		
		while(query.nextResult()) {
			aerolineas.add(createAerolineaFromQueryRow());
		}
		
		return aerolineas;
	}
	
	
	public void agregar(Aerolinea aerolinea) throws SQLException
	{
		query.execute("INSERT INTO " + tableName + " (nombre, alianza) VALUES (?, ?)", aerolinea.getNombre(), aerolinea.getAlianza());

		aerolinea.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Aerolinea aerolinea) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", aerolinea.getId());
	}
	
	
	public void actualizar(Aerolinea aerolinea) throws SQLException
	{
		query.update("UPDATE " + tableName + "SET nombre = ?, alianza = ? WHERE id = ?", aerolinea.getNombre(), aerolinea.getAlianza(), aerolinea.getId());
	}
	
	
	public void closeDb() throws SQLException
	{
		query.close();
	}
	
	
	/**
	 * Crear un objeto aerolinea a partir del resultado de la fila actual de la consulta this.query
	 * @return
	 * @throws SQLException 
	 */
	private Aerolinea createAerolineaFromQueryRow() throws SQLException
	{
		Aerolinea nuevoAerolinea = new Aerolinea();
		
		nuevoAerolinea.setId(query.getInt("id"));
		nuevoAerolinea.setNombre(query.getString("nombre"));
		nuevoAerolinea.setAlianza(query.getString("alianza"));
		
		return nuevoAerolinea;	
	}

	
	
}

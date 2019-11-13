package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IAerolineaDao;
import modelo.Aerolinea;
import modelo.Alianza;
import util.DbQuery;

public class AerolineaDaoImplDB implements IAerolineaDao {

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
		
		Object idAlianza;
		if(aerolinea.getAlianza() == null) {
			idAlianza = null;
		} else {
			idAlianza = aerolinea.getAlianza().getId();
		}
		
		query.execute("INSERT INTO " + tableName + " (nombre, id_alianza) VALUES (?, ?)", aerolinea.getNombre(), idAlianza);

		aerolinea.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Aerolinea aerolinea) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", aerolinea.getId());
	}
	
	
	public void actualizar(Aerolinea aerolinea) throws SQLException
	{
		
		Object idAlianza;
		if(aerolinea.getAlianza() == null) {
			idAlianza = null;
		} else {
			idAlianza = aerolinea.getAlianza().getId();
		}
		
		query.update("UPDATE " + tableName + "SET nombre = ?, id_alianza = ? WHERE id = ?", aerolinea.getNombre(), idAlianza, 
				aerolinea.getId());
	}
	
	
	public void close() throws SQLException
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
		
		int idAlianza = query.getInt("id_alianza");
		if(idAlianza != 0) {
			nuevoAerolinea.setAlianza(new Alianza(idAlianza));
		}
		
		
		return nuevoAerolinea;	
	}

	
	
}

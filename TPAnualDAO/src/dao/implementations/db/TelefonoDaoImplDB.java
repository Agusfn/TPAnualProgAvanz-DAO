package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Telefono;
import util.DbQuery;

public class TelefonoDaoImplDB {

	private DbQuery query;

	private static String tableName = "telefonos";
	
	
	public TelefonoDaoImplDB() throws SQLException
	{
		query = new DbQuery();
	}
	
	public TelefonoDaoImplDB(DbQuery query)
	{
		this.query = query;
	}
	
	
	public Telefono obtener(int id) throws SQLException
	{
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);
		
		if(query.nextResult()) {
			return createTelefonoFromQueryRow();
		}
		else
			return null;
	}
	
	
	public List<Telefono> obtenerTodos() throws SQLException {
		
		List<Telefono> telefonos = new ArrayList<Telefono>();
		
		query.select("SELECT * FROM " + tableName);
		
		while(query.nextResult()) {
			telefonos.add(createTelefonoFromQueryRow());
		}
		
		return telefonos;
	}
	
	
	public void agregar(Telefono telefono) throws SQLException
	{
		query.execute("INSERT INTO " + tableName + " (nro_personal, nro_celular, nro_laboral) VALUES (?, ?, ?)",
				telefono.getNroPersonal(), telefono.getNroCelular(), telefono.getNroLaboral());

		telefono.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Telefono telefono) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", telefono.getId());
	}
	
	
	public void actualizar(Telefono telefono) throws SQLException
	{
		query.update("UPDATE " + tableName + "SET nro_personal = ?, nro_celular = ?, nro_laboral = ? WHERE id = ?", 
				telefono.getNroPersonal(), telefono.getNroCelular(), telefono.getNroLaboral(), telefono.getId());
	}
	
	
	public void closeDb() throws SQLException
	{
		query.close();
	}
	
	
	/**
	 * Crear un objecto telefono a partir del resultado de la fila actual de la consulta this.query
	 * @return
	 * @throws SQLException 
	 */
	private Telefono createTelefonoFromQueryRow() throws SQLException
	{
		Telefono nuevoTelefono = new Telefono();
		
		nuevoTelefono.setId(query.getInt("id"));
		nuevoTelefono.setNroPersonal(query.getString("nro_personal"));
		nuevoTelefono.setNroCelular(query.getString("nro_celular"));
		nuevoTelefono.setNroLaboral(query.getString("nro_laboral"));
		
		return nuevoTelefono;	
	}

	
	
}

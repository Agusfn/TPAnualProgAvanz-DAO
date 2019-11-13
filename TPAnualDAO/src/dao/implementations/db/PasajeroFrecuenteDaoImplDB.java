package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IPasajeroFrecuenteDao;
import modelo.Aerolinea;
import modelo.PasajeroFrecuente;
import util.DbQuery;

public class PasajeroFrecuenteDaoImplDB implements IPasajeroFrecuenteDao {

	private DbQuery query;

	private static String tableName = "pasajeros_frecuentes";
	
	
	public PasajeroFrecuenteDaoImplDB() throws SQLException
	{
		query = new DbQuery();
	}
	
	public PasajeroFrecuenteDaoImplDB(DbQuery query)
	{
		this.query = query;
	}
	
	
	public PasajeroFrecuente obtener(int id) throws SQLException
	{
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);
		
		if(query.nextResult()) {
			return createPasajeroFrecuenteFromQueryRow();
		}
		else
			return null;
	}
	
	
	public List<PasajeroFrecuente> obtenerTodos() throws SQLException {
		
		List<PasajeroFrecuente> pasajerosFrecuentes = new ArrayList<PasajeroFrecuente>();
		
		query.select("SELECT * FROM " + tableName);
		
		while(query.nextResult()) {
			pasajerosFrecuentes.add(createPasajeroFrecuenteFromQueryRow());
		}
		
		return pasajerosFrecuentes;
	}
	
	
	public void agregar(PasajeroFrecuente pasajeroFrecuente) throws SQLException
	{
		query.execute("INSERT INTO " + tableName + " (id_aerolinea, numero, categoria) VALUES (?, ?, ?)", 
				pasajeroFrecuente.getAerolinea().getId(), pasajeroFrecuente.getNumero(), pasajeroFrecuente.getCategoria());

		pasajeroFrecuente.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(PasajeroFrecuente pasajeroFrecuente) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", pasajeroFrecuente.getId());
	}
	
	
	public void actualizar(PasajeroFrecuente pasajeroFrecuente) throws SQLException
	{
		query.update("UPDATE " + tableName + "SET id_aerolinea = ?, numero = ?, categoria = ? WHERE id = ?", 
				pasajeroFrecuente.getAerolinea().getId(), pasajeroFrecuente.getNumero(), 
				pasajeroFrecuente.getCategoria(), pasajeroFrecuente.getId());
	}
	
	
	public void close() throws SQLException
	{
		query.close();
	}
	
	
	/**
	 * Crear un objecto pasajeroFrecuente a partir del resultado de la fila actual de la consulta this.query
	 * @return
	 * @throws SQLException 
	 */
	private PasajeroFrecuente createPasajeroFrecuenteFromQueryRow() throws SQLException
	{
		PasajeroFrecuente nuevoPasajeroFrecuente = new PasajeroFrecuente();
		
		nuevoPasajeroFrecuente.setId(query.getInt("id"));
		
		Aerolinea aerolinea = new Aerolinea(query.getInt("id_aerolinea"));
		nuevoPasajeroFrecuente.setAerolinea(aerolinea);
		
		nuevoPasajeroFrecuente.setNumero(query.getString("numero"));
		nuevoPasajeroFrecuente.setCategoria(query.getString("categoria"));
		
		return nuevoPasajeroFrecuente;	
	}

	
	
}

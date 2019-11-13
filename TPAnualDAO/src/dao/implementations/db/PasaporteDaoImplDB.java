package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IPasaporteDao;
import modelo.Pais;
import modelo.Pasaporte;
import util.DbQuery;

public class PasaporteDaoImplDB implements IPasaporteDao {

	private DbQuery query;

	private static String tableName = "pasaportes";
	
	
	public PasaporteDaoImplDB() throws SQLException
	{
		query = new DbQuery();
	}
	
	public PasaporteDaoImplDB(DbQuery query)
	{
		this.query = query;
	}
	
	
	public Pasaporte obtener(int id) throws SQLException
	{
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);
		
		if(query.nextResult()) {
			return createPasaporteFromQueryRow();
		}
		else
			return null;
	}
	
	
	public List<Pasaporte> obtenerTodos() throws SQLException {
		
		List<Pasaporte> pasaportes = new ArrayList<Pasaporte>();
		
		query.select("SELECT * FROM " + tableName);
		
		while(query.nextResult()) {
			pasaportes.add(createPasaporteFromQueryRow());
		}
		
		return pasaportes;
	}
	
	
	public void agregar(Pasaporte pasaporte) throws SQLException
	{
		
		Object idPaisEmision = null;
		if(pasaporte.getPaisEmision() != null) {
			idPaisEmision = pasaporte.getPaisEmision().getId();
		}
		
		query.execute("INSERT INTO " + tableName + " (numero, id_pais_emision, nombre_pais, autoridad_emision, fecha_emision, fecha_vencimiento) VALUES (?, ?, ?, ?, ?, ?)", 
				pasaporte.getNumero(), idPaisEmision, pasaporte.getNombrePaisEmision(), pasaporte.getAutoridadEmision(), pasaporte.getFechaEmision(), 
				pasaporte.getFechVencimiento());

		pasaporte.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Pasaporte pasaporte) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", pasaporte.getId());
	}
	
	
	public void actualizar(Pasaporte pasaporte) throws SQLException
	{
		
		Object idPaisEmision = null;
		if(pasaporte.getPaisEmision() != null) {
			idPaisEmision = pasaporte.getPaisEmision().getId();
		}
		
		query.update("UPDATE " + tableName + " SET numero = ?, id_pais_emision = ?, nombre_pais = ?, autoridad_emision = ?, fecha_emision = ?, fecha_vencimiento = ? WHERE id = ?", 
				pasaporte.getNumero(), idPaisEmision, pasaporte.getNombrePaisEmision(), pasaporte.getAutoridadEmision(), pasaporte.getFechaEmision(), 
				pasaporte.getFechVencimiento(), pasaporte.getId());
	}
	
	
	public void close() throws SQLException
	{
		query.close();
	}
	
	
	/**
	 * Crear un objecto pasaporte a partir del resultado de la fila actual de la consulta this.query
	 * @return
	 * @throws SQLException 
	 */
	private Pasaporte createPasaporteFromQueryRow() throws SQLException
	{
		Pasaporte pasaporte = new Pasaporte();
		
		pasaporte.setId(query.getInt("id"));
		pasaporte.setNumero(query.getString("numero"));
		
		int idPaisEmision = query.getInt("id_pais_emision");
		if(idPaisEmision != 0) {
			pasaporte.setPaisEmision(new Pais(idPaisEmision));
		}
		
		pasaporte.setNombrePaisEmision(query.getString("nombre_pais"));
		
		pasaporte.setAutoridadEmision(query.getString("autoridad_emision"));
		pasaporte.setFechaEmision(query.getLocalDate("fecha_emision"));
		pasaporte.setFechVencimiento(query.getLocalDate("fecha_vencimiento"));
		
		return pasaporte;	
	}

	
	
}

package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IAeropuertoDao;
import modelo.Aeropuerto;
import modelo.Pais;
import modelo.Provincia;
import util.DbQuery;

public class AeropuertoDaoImplDB implements IAeropuertoDao{

	private DbQuery query;

	private static String tableName = "aeropuertos";
	
	
	public AeropuertoDaoImplDB() throws SQLException
	{
		query = new DbQuery();
	}
	
	public AeropuertoDaoImplDB(DbQuery query)
	{
		this.query = query;
	}
	
	
	public Aeropuerto obtener(int id) throws SQLException
	{
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);
		
		if(query.nextResult()) {
			return createAeropuertoFromQueryRow();
		}
		else
			return null;
	}
	
	
	public List<Aeropuerto> obtenerTodos() throws SQLException {
		
		List<Aeropuerto> aeropuertos = new ArrayList<Aeropuerto>();
		
		query.select("SELECT * FROM " + tableName);
		
		while(query.nextResult()) {
			aeropuertos.add(createAeropuertoFromQueryRow());
		}
		
		return aeropuertos;
	}
	
	
	public void agregar(Aeropuerto aeropuerto) throws SQLException
	{
		
		Object idProvincia = null;
		if(aeropuerto.getProvincia() != null) {
			idProvincia = aeropuerto.getProvincia().getId();
		}
		
		Object idPais = null;
		if(aeropuerto.getPais() != null) {
			idPais = aeropuerto.getPais().getId();
		}
		
		query.execute("INSERT INTO " + tableName + " (identificacion, ciudad, id_provincia, nombre_provincia, id_pais, nombre_pais) VALUES (?, ?, ?, ?, ?, ?)", 
				aeropuerto.getIdentificacion(), aeropuerto.getCiudad(), idProvincia, aeropuerto.getNombreProvincia(), idPais, aeropuerto.getNombrePais());

		aeropuerto.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Aeropuerto aeropuerto) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", aeropuerto.getId());
	}
	
	
	public void actualizar(Aeropuerto aeropuerto) throws SQLException
	{
		
		Object idProvincia = null;
		if(aeropuerto.getProvincia() != null) {
			idProvincia = aeropuerto.getProvincia().getId();
		}
		
		Object idPais = null;
		if(aeropuerto.getPais() != null) {
			idPais = aeropuerto.getPais().getId();
		}
		
		query.update("UPDATE " + tableName + "SET identificacion = ?, ciudad = ?, id_provincia = ?, nombre_provincia = ?, id_pais = ?, nombre_pais = ? WHERE id = ?", 
				aeropuerto.getIdentificacion(), aeropuerto.getCiudad(), idProvincia, aeropuerto.getNombreProvincia(), idPais, aeropuerto.getNombrePais(), aeropuerto.getId());
	}
	
	
	public void close() throws SQLException
	{
		query.close();
	}
	
	
	/**
	 * Crear un objecto aeropuerto a partir del resultado de la fila actual de la consulta this.query
	 * @return
	 * @throws SQLException 
	 */
	private Aeropuerto createAeropuertoFromQueryRow() throws SQLException
	{
		Aeropuerto nuevoAeropuerto = new Aeropuerto();
		
		nuevoAeropuerto.setId(query.getInt("id"));
		nuevoAeropuerto.setIdentificacion(query.getString("identificacion"));
		nuevoAeropuerto.setCiudad(query.getString("ciudad"));
		
		int idProvincia = query.getInt("id_provincia");
		if(idProvincia != 0) {
			nuevoAeropuerto.setProvincia(new Provincia(idProvincia));
		}
		
		nuevoAeropuerto.setNombreProvincia(query.getString("nombre_provincia"));
	
		int idPais = query.getInt("id_pais");
		if(idPais != 0) {
			nuevoAeropuerto.setPais(new Pais(idPais));
		}
		
		nuevoAeropuerto.setNombrePais(query.getString("nombre_pais"));
		
		
		return nuevoAeropuerto;	
	}

	
	
}

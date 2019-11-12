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
		query.execute("INSERT INTO " + tableName + " (identificacion, ciudad, id_provincia, id_pais) VALUES (?, ?, ?, ?)", 
				aeropuerto.getIdentificacion(), aeropuerto.getCiudad(), aeropuerto.getProvincia().getId(), aeropuerto.getPais().getId());

		aeropuerto.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Aeropuerto aeropuerto) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", aeropuerto.getId());
	}
	
	
	public void actualizar(Aeropuerto aeropuerto) throws SQLException
	{
		query.update("UPDATE " + tableName + "SET identificacion = ?, ciudad = ?, id_provincia = ?, id_pais = ? WHERE id = ?", 
				aeropuerto.getIdentificacion(), aeropuerto.getCiudad(), aeropuerto.getProvincia().getId(), aeropuerto.getPais().getId(), aeropuerto.getId());
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
		
		Provincia provincia = new Provincia(query.getInt("id_provincia"));
		nuevoAeropuerto.setProvincia(provincia);
		
		Pais pais = new Pais(query.getInt("id_pais"));
		nuevoAeropuerto.setPais(pais);
		
		return nuevoAeropuerto;	
	}

	
	
}

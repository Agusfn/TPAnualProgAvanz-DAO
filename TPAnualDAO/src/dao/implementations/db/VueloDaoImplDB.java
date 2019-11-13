package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IVueloDao;
import modelo.Aerolinea;
import modelo.Aeropuerto;
import modelo.Vuelo;
import util.DbQuery;

public class VueloDaoImplDB implements IVueloDao {

	private DbQuery query;

	private static String tableName = "vuelos";
	
	
	public VueloDaoImplDB() throws SQLException
	{
		query = new DbQuery();
	}
	
	public VueloDaoImplDB(DbQuery query)
	{
		this.query = query;
	}
	
	
	public Vuelo obtener(int id) throws SQLException
	{
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);
		
		if(query.nextResult()) {
			return createVueloFromQueryRow();
		}
		else
			return null;
	}
	
	
	public List<Vuelo> obtenerTodos() throws SQLException {
		
		List<Vuelo> vuelos = new ArrayList<Vuelo>();
		
		query.select("SELECT * FROM " + tableName);
		
		while(query.nextResult()) {
			vuelos.add(createVueloFromQueryRow());
		}
		
		return vuelos;
	}
	
	
	public void agregar(Vuelo vuelo) throws SQLException
	{
		query.execute("INSERT INTO " + tableName + " (id_aerolinea, numero, cantidad_asientos, id_aeropuerto_salida, id_aeropuerto_llegada, fecha_hora_salida, "
				+ "fecha_hora_llegada, tiempo_vuelo_minutos) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", vuelo.getAerolinea().getId(), vuelo.getNumero(), vuelo.getCantAsientos(), 
				vuelo.getAeropSalida().getId(), vuelo.getAeropLlegada().getId(), vuelo.getFechaHoraSalida(), vuelo.getFechaHoraLlegada(),
				vuelo.getTiempoVueloMinutos());

		vuelo.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Vuelo vuelo) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", vuelo.getId());
	}
	
	
	public void actualizar(Vuelo vuelo) throws SQLException
	{
		query.update("UPDATE " + tableName + " SET id_aerolinea = ?, numero = ?, cantidad_asientos = ?, id_aeropuerto_salida = ?, id_aeropuerto_llegada = ?, "
				+ "fecha_hora_salida = ?, fecha_hora_llegada = ?, tiempo_vuelo_minutos = ? WHERE id = ?", 
				vuelo.getAerolinea().getId(), vuelo.getNumero(), vuelo.getCantAsientos(), vuelo.getAeropSalida().getId(), vuelo.getAeropLlegada().getId(), vuelo.getFechaHoraSalida(),
				vuelo.getFechaHoraLlegada(), vuelo.getTiempoVueloMinutos(), vuelo.getId());
	}
	
	
	public void close() throws SQLException
	{
		query.close();
	}
	
	
	/**
	 * Crear un objecto vuelo a partir del resultado de la fila actual de la consulta this.query
	 * @return
	 * @throws SQLException 
	 */
	private Vuelo createVueloFromQueryRow() throws SQLException
	{
		Vuelo nuevoVuelo = new Vuelo();
		
		nuevoVuelo.setId(query.getInt("id"));
		
		Aerolinea aerolinea = new Aerolinea(query.getInt("id_aerolinea"));
		nuevoVuelo.setAerolinea(aerolinea);
		
		nuevoVuelo.setNumero(query.getString("numero"));
		nuevoVuelo.setCantAsientos(query.getInt("cantidad_asientos"));
		
		Aeropuerto aeropSalida = new Aeropuerto(query.getInt("id_aeropuerto_salida"));
		nuevoVuelo.setAeropSalida(aeropSalida);
		
		Aeropuerto aeropLlegada = new Aeropuerto(query.getInt("id_aeropuerto_llegada"));
		nuevoVuelo.setAeropLlegada(aeropLlegada);
		
		nuevoVuelo.setFechaHoraSalida(query.getLocalDateTime("fecha_hora_salida"));
		nuevoVuelo.setFechaHoraLlegada(query.getLocalDateTime("fecha_hora_llegada"));
		nuevoVuelo.setTiempoVueloMinutos(query.getInt("tiempo_vuelo_minutos"));
		
		return nuevoVuelo;	
	}

	
	
}

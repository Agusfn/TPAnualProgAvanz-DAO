package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IVentaDao;
import modelo.Aerolinea;
import modelo.Cliente;
import modelo.Venta;
import modelo.Vuelo;
import util.DbQuery;

public class VentaDaoImplDB implements IVentaDao {

	private DbQuery query;

	private static String tableName = "ventas";
	
	
	public VentaDaoImplDB() throws SQLException
	{
		query = new DbQuery();
	}
	
	public VentaDaoImplDB(DbQuery query)
	{
		this.query = query;
	}
	
	
	public Venta obtener(int id) throws SQLException
	{
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);
		
		if(query.nextResult()) {
			return createVentaFromQueryRow();
		}
		else
			return null;
	}
	
	
	public List<Venta> obtenerTodos() throws SQLException {
		
		List<Venta> ventas = new ArrayList<Venta>();
		
		query.select("SELECT * FROM " + tableName);
		
		while(query.nextResult()) {
			ventas.add(createVentaFromQueryRow());
		}
		
		return ventas;
	}
	
	
	public void agregar(Venta venta) throws SQLException
	{
		query.execute("INSERT INTO " + tableName + " (id_cliente, id_vuelo, id_aerolinea, fecha_y_hore, forma_de_pago) VALUES (?, ?, ?, ?, ?)", 
				venta.getCliente().getId(), venta.getVuelo().getId(), venta.getAerolinea().getId(), venta.getFechaHora(), venta.getFormaDePago());

		venta.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Venta venta) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", venta.getId());
	}
	
	
	public void actualizar(Venta venta) throws SQLException
	{
		query.update("UPDATE " + tableName + " SET id_cliente = ?, id_vuelo = ?, id_aerolinea = ?, fecha_y_hore = ?, forma_de_pago = ? WHERE id = ?", 
				venta.getCliente().getId(), venta.getVuelo().getId(), venta.getAerolinea().getId(), venta.getFechaHora(), venta.getFormaDePago(), venta.getId());
	}
	
	
	public void close() throws SQLException
	{
		query.close();
	}
	
	
	/**
	 * Crear un objecto venta a partir del resultado de la fila actual de la consulta this.query
	 * @return
	 * @throws SQLException 
	 */
	private Venta createVentaFromQueryRow() throws SQLException
	{
		Venta nuevoVenta = new Venta();
		
		nuevoVenta.setId(query.getInt("id"));
		
		Cliente cliente = new Cliente(query.getInt("id_cliente"));
		nuevoVenta.setCliente(cliente);
		
		Vuelo vuelo = new Vuelo(query.getInt("id_vuelo"));
		nuevoVenta.setVuelo(vuelo);
		
		Aerolinea aerolinea = new Aerolinea(query.getInt("id_aerolinea"));
		nuevoVenta.setAerolinea(aerolinea);
		
		nuevoVenta.setFechaHora(query.getLocalDateTime("fecha_y_hora"));
		nuevoVenta.setFormaDePago(query.getString("forma_de_pago"));
		
		return nuevoVenta;	
	}

	
	
}

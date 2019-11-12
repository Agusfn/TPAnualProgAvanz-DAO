package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IClienteDao;
import modelo.Cliente;
import modelo.Direccion;
import modelo.PasajeroFrecuente;
import modelo.Pasaporte;
import modelo.Telefono;
import util.DbQuery;

public class ClienteDaoImplDB implements IClienteDao {

	private DbQuery query;

	private static String tableName = "clientes";
	
	
	public ClienteDaoImplDB() throws SQLException
	{
		query = new DbQuery();
	}
	
	public ClienteDaoImplDB(DbQuery query)
	{
		this.query = query;
	}
	
	
	public Cliente obtener(int id) throws SQLException
	{
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);
		
		if(query.nextResult()) {
			return createClienteFromQueryRow();
		}
		else
			return null;
	}
	
	
	public List<Cliente> obtenerTodos() throws SQLException {
		
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		query.select("SELECT * FROM " + tableName);
		
		while(query.nextResult()) {
			clientes.add(createClienteFromQueryRow());
		}
		
		return clientes;
	}
	
	
	public void agregar(Cliente cliente) throws SQLException
	{
		query.execute("INSERT INTO " + tableName + " (nombre_y_apellido, dni, cuil_o_cuit, fecha_nacimiento, email, id_direccion,"
				+ "id_pasajero_frecuente, id_telefono, id_pasaporte) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				cliente.getNombreYApellido(), cliente.getDni(), cliente.getCuitOCuil(), cliente.getFechaNacimiento(), cliente.getEmail(),
				cliente.getDireccion().getId(), cliente.getPasajeroFrecuente().getId(), cliente.getTelefono().getId(), cliente.getPasaporte().getId());

		cliente.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Cliente cliente) throws SQLException
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", cliente.getId());
	}
	
	
	public void actualizar(Cliente cliente) throws SQLException
	{
		query.update("UPDATE " + tableName + "SET nombre_y_apellido = ?, dni = ?, cuil_o_cuit = ?, fecha_nacimiento = ?, email = ?, id_direccion = ?,"
				+ "id_pasajero_frecuente = ?, id_telefono = ?, id_pasaporte = ? WHERE id = ?", 
				cliente.getNombreYApellido(), cliente.getDni(), cliente.getCuitOCuil(), cliente.getFechaNacimiento(), cliente.getEmail(), 
				cliente.getDireccion().getId(), cliente.getPasajeroFrecuente().getId(), cliente.getTelefono().getId(), cliente.getPasaporte().getId(),
				cliente.getId());
	}
	
	
	public void close() throws SQLException
	{
		query.close();
	}
	
	
	/**
	 * Crear un objecto cliente a partir del resultado de la fila actual de la consulta this.query
	 * @return
	 * @throws SQLException 
	 */
	private Cliente createClienteFromQueryRow() throws SQLException
	{
		Cliente cliente = new Cliente();
		
		cliente.setId(query.getInt("id"));
		cliente.setNombreYApellido(query.getString("nombre_y_apellido"));
		cliente.setDni(query.getString("dni"));
		cliente.setCuitOCuil(query.getString("cuil_o_cuit"));
		cliente.setFechaNacimiento(query.getLocalDate("fecha_nacimiento"));
		cliente.setEmail(query.getString("email"));
		
		Direccion direccion = new Direccion(query.getInt("id_direccion"));
		cliente.setDireccion(direccion);
		
		PasajeroFrecuente pasajFreq = new PasajeroFrecuente(query.getInt("id_pasajero_frecuente"));
		cliente.setPasajeroFrecuente(pasajFreq);
		
		Telefono tel = new Telefono(query.getInt("id_telefono"));
		cliente.setTelefono(tel);
		
		Pasaporte pasaporte = new Pasaporte(query.getInt("id_pasaporte"));
		cliente.setPasaporte(pasaporte);
		
		
		return cliente;	
	}

	
	
}

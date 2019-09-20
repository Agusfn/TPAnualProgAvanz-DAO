package dao.implementations.db;

import java.util.ArrayList;
import java.util.List;
import dao.interfaces.IPaisDao;
import modelo.Pais;
import util.DbQuery;

public class PaisDaoImplDB {

	private DbQuery query;

	private static String tableName = "paises";
	
	
	public PaisDaoImplDB()
	{
		query = new DbQuery();
	}
	
	public PaisDaoImplDB(DbQuery query)
	{
		this.query = query;
	}
	
	
	public Pais obtener(int id)
	{
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);
		
		if(query.nextResult()) {
			return createPaisFromQueryRow();
		}
		else
			return null;
	}
	
	
	public List<Pais> obtenerTodos() {
		
		List<Pais> paises = new ArrayList<Pais>();
		
		query.select("SELECT * FROM " + tableName);
		
		while(query.nextResult()) {
			paises.add(createPaisFromQueryRow());
		}
		
		return paises;
	}
	
	
	public void agregar(Pais pais)
	{
		query.update("INSERT INTO " + tableName + " (nombre) VALUES (?)", pais.getNombre());

		pais.setId((int)query.getLastInsertedId());
	}
	
	
	public void eliminar(Pais pais)
	{
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", pais.getId());
	}
	
	
	public void actualizar(Pais pais)
	{
		query.update("UPDATE " + tableName + "SET nombre = ? WHERE id = ?", pais.getNombre(), pais.getId());
	}
	
	
	public void closeDb()
	{
		query.close();
	}
	
	
	/**
	 * Crear un objecto pais a partir del resultado de la fila actual de la consulta this.query
	 * @return
	 */
	private Pais createPaisFromQueryRow()
	{
		Pais nuevoPais = new Pais();
		
		nuevoPais.setId(query.getInt("id"));
		nuevoPais.setNombre(query.getString("nombre"));
		
		return nuevoPais;	
	}

	
	
}

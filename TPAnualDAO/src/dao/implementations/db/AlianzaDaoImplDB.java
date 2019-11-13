package dao.implementations.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IAlianzaDao;
import modelo.Alianza;
import util.DbQuery;

public class AlianzaDaoImplDB implements IAlianzaDao {

	private DbQuery query;

	private static String tableName = "alianzas";

	public AlianzaDaoImplDB() throws SQLException {
		query = new DbQuery();
	}

	public AlianzaDaoImplDB(DbQuery query) {
		this.query = query;
	}

	public Alianza obtener(int id) throws SQLException {
		query.select("SELECT * FROM " + tableName + " WHERE id = ?", id);

		if (query.nextResult()) {
			return createAlianzaFromQueryRow();
		} else
			return null;
	}

	public List<Alianza> obtenerTodos() throws SQLException {

		List<Alianza> alianzas = new ArrayList<Alianza>();

		query.select("SELECT * FROM " + tableName);

		while (query.nextResult()) {
			alianzas.add(createAlianzaFromQueryRow());
		}

		return alianzas;
	}

	public void agregar(Alianza alianza) throws SQLException {
		query.execute("INSERT INTO " + tableName + " (nombre) VALUES (?)", alianza.getNombre());

		alianza.setId((int) query.getLastInsertedId());
	}

	public void eliminar(Alianza alianza) throws SQLException {
		query.execute("DELETE FROM " + tableName + " WHERE id = ?", alianza.getId());
	}

	public void actualizar(Alianza alianza) throws SQLException {
		query.update("UPDATE " + tableName + " SET nombre = ? WHERE id = ?", alianza.getNombre(), alianza.getId());
	}

	public void close() throws SQLException {
		query.close();
	}

	/**
	 * Crear un objeto alianza a partir del resultado de la fila actual de la
	 * consulta this.query
	 * 
	 * @return
	 * @throws SQLException
	 */
	private Alianza createAlianzaFromQueryRow() throws SQLException {
		Alianza nuevoAlianza = new Alianza();

		nuevoAlianza.setId(query.getInt("id"));
		nuevoAlianza.setNombre(query.getString("nombre"));

		return nuevoAlianza;
	}

}

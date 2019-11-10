package util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbQuery {

	
	private Connection con;
	
	private PreparedStatement ps;
	private ResultSet results;
	
	private boolean onTransaction = false;
	private CallableStatement callableStm;
	
	
	
	static
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}	
		catch(ClassNotFoundException e) {
			System.out.println("Ocurrió un error cargando el driver SQL");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	public DbQuery() throws SQLException
	{
		con = DriverManager.getConnection(Properties.getProperty("sqlserver_connection_string"));
	}
	
	
	/**
	 * Hacer una consulta para obtener resultados. Despues se debe usaar nextResult() y getString/Int/etc..
	 * @param sql
	 * @param params
	 * @return ResultSet resultados devueltos.
	 * @throws SQLException 
	 */
	public void select(String sql, Object... params) throws SQLException
	{
		try {
			ps = makePreparedStatement(sql, false, params);
			results = ps.executeQuery();
			
		} catch (SQLException e) {
			
			if(onTransaction) {
				con.rollback(); // throws SQLException
				onTransaction = false;
			}
			throw e;
		}
	}
	
	
	/**
	 * Hacer una consulta de actualización. Devuelve cantidad de filas afectadas.
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException 
	 */
	public int update(String sql, Object... params) throws SQLException
	{		
		try {
			ps = makePreparedStatement(sql, true, params);
			return ps.executeUpdate();
			
		} catch (SQLException e) {
			
			if(onTransaction) {
				con.rollback(); // throws SQLException
				onTransaction = false;
			}
			throw e;
		}
	}
	
	
	/**
	 * Ejecutar una consulta que no devuelve información. (insert, delete, etc)
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException 
	 */
	public boolean execute(String sql, Object... params) throws SQLException
	{
		try {
			ps = makePreparedStatement(sql, true, params);
			return ps.execute();
			
		} catch (SQLException e) {
			
			if(onTransaction) {
				con.rollback(); // throws SQLException
				onTransaction = false;
			}
			throw e;
		}
	}
	
	
	

	/**
	 * Pasa el puntero al siguiente resultado (si lo hay).
	 * @return boolean TRUE si hay resultado siguiente, FALSE si no.
	 * @throws SQLException 
	 */
	public boolean nextResult() throws SQLException
	{
		return results.next();
	}
	
	
	/**
	 * Obtener el valor de una columna dada cuyo tipo de dato es un string, sobre la fila de resultados actual.
	 * @param column	nombre de la columna
	 * @return
	 * @throws SQLException 
	 */
	public String getString(String column) throws SQLException
	{
		return results.getString(column);
	}
	

	/**
	 * Obtener el valor de una columna dada cuyo tipo de dato es un int, sobre la fila de resultados actual.
	 * @param column	nombre de la columna
	 * @return
	 * @throws SQLException 
	 */
	public int getInt(String column) throws SQLException
	{
		return results.getInt(column);
	}	
	
	
	public long getLastInsertedId() throws SQLException
	{
		ResultSet generatedKeys = ps.getGeneratedKeys();
		
        if (generatedKeys.next())
        	return generatedKeys.getLong(1);
        else
        	throw new SQLException("Last inserted ID couldn't be obtained.");
	}
	
	
	/**
	 * Iniciar transacción. Las queries que se hagan se procesan todas al final (excepto aparentemente los selects?)
	 * @throws SQLException 
	 */
	public void startTransaction() throws SQLException
	{
		con.setAutoCommit(false);
		onTransaction = true;
	}

	
	/**
	 * Finalizar y procesar transacción (conjunto de consultas)
	 * @throws SQLException 
	 */
	public void finishTransaction() throws SQLException
	{
		con.commit();
		onTransaction = false;
	}
	
	
	/**
	 * Preparar consulta para llamar a un procedure.
	 * @param	sql puede ser "exec nombre_procedure ?, ?, .." o "{call nombre_procedure(?,?,...)}" Los "?" corresponden a inputs Y outputs.
	 * @param inputParams	array de objetos que se proporcionan como INPUT unicamente al procedure
	 * @throws SQLException 
	 */
	public void prepareProcedureCall(String sql, Object... inputParams) throws SQLException
	{
		callableStm = con.prepareCall(sql);
		setInputToCallableStatement(callableStm, inputParams);
	}
	
	
	/**
	 * Agregar un output al procedure llamado con prepareProcedureCall()
	 * @param paramName		nombre de parámetro igual al nombre de parámetro de salida del procedure en SQL
	 * @param sqlType		Tipo de dato de salida. Están en java.sql.Types
	 * @throws SQLException 
	 */
	public void addOutParamToProcedure(String paramName, int sqlType) throws SQLException
	{
		callableStm.registerOutParameter(paramName, sqlType);
	}
	
	/**
	 * Ejecutar llamado al procedimiento. Se debe haber preparado previamente con prepareProcedureCall()
	 * Si devuelve una tabla pero sin variables de output, se puede utilizar el ResultSet igual que como se hace con un select() ordinario. 
	 * Si se quiere obtener una variable de output se debe usar getProcedureOutput() posteriormente.
	 * @throws SQLException 
	 */
	public void execProcedure() throws SQLException
	{
		callableStm.execute();
		results = callableStm.getResultSet();
	}
	
	
	/**
	 * Obtener el valor de una variable de salida de un procedure ya ejecutado con execProcedure()
	 * @param paramName		nombre de la variable de salida asignada en addOutParamToProcedure() previamente.
	 * @return Object	Se debe castear al tipo de dato necesario.
	 * @throws SQLException 
	 */
	public Object getProcedureOutput(String paramName) throws SQLException
	{
		return callableStm.getObject(paramName);
	}
	
	
	
	/**
	 * Cerrar conexión de la base de datos (uso interno de la clase)
	 * @throws SQLException 
	 */
	public void close() throws SQLException 
	{
		con.close();
	}
	
	
	/**
	 * Preparar consulta SQL con parámetros. 
	 * Actualmente en los parámetros soporta: String, Integer, y Double.
	 * @param sql
	 * @param returnkeys	si devuelve el la clave primaria generada
	 * @param params
	 */
	private PreparedStatement makePreparedStatement(String sql, boolean returnKeys, Object... params) throws SQLException
	{
		PreparedStatement ps;
		
		if(!returnKeys)
			ps = con.prepareStatement(sql);
		else
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		
		int paramNumber = 1;
		
		for(Object param: params) {
			
			if(param instanceof String) {
				ps.setString(paramNumber, (String)param);
			}
			else if(param instanceof Integer) {
				ps.setInt(paramNumber, (int)param);
			}
			else if(param instanceof Double) {
				ps.setDouble(paramNumber, (double)param);
			}

			paramNumber += 1;
		}
		
		return ps;
	}
	
	
	/**
	 * Setear valores de entrada a un CallableStatement (el input de una llamada a un stored procedure)
	 * @param st
	 * @param params
	 * @throws SQLException
	 */
	private void setInputToCallableStatement(CallableStatement st, Object[] params) throws SQLException
	{
		int paramNumber = 1;
		
		for(Object param: params) {
			
			if(param instanceof String) {
				st.setString(paramNumber, (String)param);
			}
			else if(param instanceof Integer) {
				st.setInt(paramNumber, (int)param);
			}
			else if(param instanceof Double) {
				st.setDouble(paramNumber, (double)param);
			}

			paramNumber += 1;
		}
	}
	
		
	
}

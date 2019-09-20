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
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	public DbQuery()
	{
		try {
			con = DriverManager.getConnection("jdbc:sqlserver://localhost:54162;databaseName=TPFinalAerolineas;integratedSecurity=true");
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	/**
	 * Hacer una consulta para obtener resultados. Despues se debe usaar nextResult() y getString/Int/etc..
	 * @param sql
	 * @param params
	 * @return ResultSet resultados devueltos.
	 */
	public void select(String sql, Object... params)
	{
		try {
			ps = makePreparedStatement(sql, false, params);
			results = ps.executeQuery();
			
		} catch (SQLException e) {
			
			if(onTransaction) {
				try {
					con.rollback();
					onTransaction = false;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Hacer una consulta de actualización. Devuelve cantidad de filas afectadas.
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql, Object... params)
	{		
		try {
			ps = makePreparedStatement(sql, true, params);
			return ps.executeUpdate();
			
		} catch (SQLException e) {
			
			if(onTransaction) {
				try {
					con.rollback();
					onTransaction = false;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			e.printStackTrace();
			return -1;
		}
	}
	
	
	/**
	 * Ejecutar una consulta que no devuelve información. (insert, delete, etc)
	 * @param sql
	 * @param params
	 * @return
	 */
	public boolean execute(String sql, Object... params)
	{
		try {
			ps = makePreparedStatement(sql, true, params);
			return ps.execute();
			
		} catch (SQLException e) {
			
			if(onTransaction) {
				try {
					con.rollback();
					onTransaction = false;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			e.printStackTrace();
			return false;
		}
	}
	
	
	

	/**
	 * Pasa el puntero al siguiente resultado (si lo hay).
	 * @return boolean TRUE si hay resultado siguiente, FALSE si no.
	 */
	public boolean nextResult()
	{
		try {
			return results.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * Obtener el valor de una columna dada cuyo tipo de dato es un string, sobre la fila de resultados actual.
	 * @param column	nombre de la columna
	 * @return
	 */
	public String getString(String column)
	{
		try {
			return results.getString(column);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * Obtener el valor de una columna dada cuyo tipo de dato es un int, sobre la fila de resultados actual.
	 * @param column	nombre de la columna
	 * @return
	 */
	public int getInt(String column)
	{
		try {
			return results.getInt(column);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}	
	
	
	public long getLastInsertedId()
	{
		try {
			ResultSet generatedKeys = ps.getGeneratedKeys();
			
            if (generatedKeys.next())
            	return generatedKeys.getLong(1);
            else
            	throw new SQLException("Last inserted ID couldn't be obtained.");
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1l;
		}
	}
	
	
	/**
	 * Iniciar transacción. Las queries que se hagan se procesan todas al final (excepto aparentemente los selects?)
	 */
	public void startTransaction()
	{
		try {
			con.setAutoCommit(false);
			onTransaction = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Finalizar y procesar transacción (conjunto de consultas)
	 */
	public void finishTransaction()
	{
		try {
			con.commit();
			onTransaction = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Preparar consulta para llamar a un procedure.
	 * @param	sql puede ser "exec nombre_procedure ?, ?, .." o "{call nombre_procedure(?,?,...)}" Los "?" corresponden a inputs Y outputs.
	 * @param inputParams	array de objetos que se proporcionan como INPUT unicamente al procedure
	 */
	public void prepareProcedureCall(String sql, Object... inputParams)
	{
		try {
			callableStm = con.prepareCall(sql);
			setInputToCallableStatement(callableStm, inputParams);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Agregar un output al procedure llamado con prepareProcedureCall()
	 * @param paramName		nombre de parámetro igual al nombre de parámetro de salida del procedure en SQL
	 * @param sqlType		Tipo de dato de salida. Están en java.sql.Types
	 */
	public void addOutParamToProcedure(String paramName, int sqlType)
	{
		try {
			callableStm.registerOutParameter(paramName, sqlType);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ejecutar llamado al procedimiento. Se debe haber preparado previamente con prepareProcedureCall()
	 * Si devuelve una tabla pero sin variables de output, se puede utilizar el ResultSet igual que como se hace con un select() ordinario. 
	 * Si se quiere obtener una variable de output se debe usar getProcedureOutput() posteriormente.
	 */
	public void execProcedure()
	{
		try {
			callableStm.execute();
			results = callableStm.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Obtener el valor de una variable de salida de un procedure ya ejecutado con execProcedure()
	 * @param paramName		nombre de la variable de salida asignada en addOutParamToProcedure() previamente.
	 * @return Object	Se debe castear al tipo de dato necesario.
	 */
	public Object getProcedureOutput(String paramName)
	{
		try {
			return callableStm.getObject(paramName);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * Cerrar conexión de la base de datos (uso interno de la clase)
	 */
	public void close() 
	{
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
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

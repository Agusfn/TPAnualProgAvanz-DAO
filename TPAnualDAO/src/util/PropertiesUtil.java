package util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	
	private static Properties properties = new Properties();

	static
	{
		try {
			properties.load(new FileReader("config.properties"));
		} catch (IOException e) {
			System.out.println("Error reading config.properties file.");
		}
	}
	

	public static String aerolineasFile(String method)
	{
		return properties.getProperty(propPrefix(method)+"aerolineas");
	}

	public static String aeropuertosFile(String method)
	{
		return properties.getProperty(propPrefix(method)+"aeropuertos");
	}

	public static String clientesFile(String method)
	{
		return properties.getProperty(propPrefix(method)+"clientes");
	}
	
	public static String direccionesFile(String method)
	{
		return properties.getProperty(propPrefix(method)+"direcciones");
	}
	
	public static String paisesFile(String method)
	{
		return properties.getProperty(propPrefix(method)+"paises");
	}
	
	public static String pasajerosFrecuentesFile(String method)
	{
		return properties.getProperty(propPrefix(method)+"pasajerosfrecuentes");
	}
	
	public static String pasaportesFile(String method)
	{
		return properties.getProperty(propPrefix(method)+"pasaportes");
	}
	
	public static String provinciasFile(String method)
	{
		return properties.getProperty(propPrefix(method)+"provincias");
	}
	
	public static String telefonosFile(String method)
	{
		return properties.getProperty(propPrefix(method)+"telefonos");
	}
	
	public static String ventasFile(String method)
	{
		return properties.getProperty(propPrefix(method)+"ventas");
	}
	
	public static String vuelosFile(String method)
	{
		return properties.getProperty(propPrefix(method)+"vuelos");
	}
	
	
	/**
	 * Obtiene el prefijo del nombre de la propiedad a partir del nombre del metodo.
	 * @param method "archivo" o "serializacion"
	 * @return
	 */
	private static String propPrefix(String method)
	{
		if(method.equals("archivo"))
			return "csv_";
		else
			return "serial_";
	}

	
	
	
}

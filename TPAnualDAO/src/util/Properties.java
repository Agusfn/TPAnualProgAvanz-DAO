package util;

import java.io.FileReader;
import java.io.IOException;

public class Properties {

	
	private static java.util.Properties properties = new java.util.Properties();

	
	static
	{
		try {
			properties.load(new FileReader("config.properties"));
		} catch (IOException e) {
			System.out.println("Error reading config.properties file.");
		}
	}
	
	
	/**
	 * Obtener valor de propiedad a partir de su clave.
	 * @param key
	 * @return
	 */
	public static String getProperty(String key)
	{
		return properties.getProperty(key);
	}

}

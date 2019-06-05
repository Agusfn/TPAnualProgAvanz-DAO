package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Archivo {

		
	private File archivo;	
	
	private FileReader reader = null;
	private BufferedReader bufferedReader = null;
	
	
	/**
	 * Inicializar archivo.
	 * @param nombreArchivo
	 */
	public Archivo(String nombreArchivo)
	{
		archivo = new File(nombreArchivo);
	}
	
	
	/**
	 * Comprobar si el archivo existe.
	 * @return
	 */
	public boolean existe()
	{
		return archivo.exists();
	}
	
	
	/**
	 * Crea archivo vacío.
	 * @throws IOException
	 */
	public void crear() throws IOException
	{
		archivo.createNewFile();
	}
	
	
	/**
	 * Cerramos reader, para volver a empezar de la primera linea al llamar siguienteLinea()
	 * @throws IOException
	 */
	public void reiniciarReader() throws IOException
	{
		reader.close();
		bufferedReader.close();
		reader = null;
		bufferedReader = null;
	}
	
	
	/**
	 * Lee la siguiente línea del archivo utilizando un reader a nivel objeto.
	 * @return
	 * @throws IOException
	 */
	public String siguienteLinea() throws IOException
	{
		if(bufferedReader == null)
		{
			reader = new FileReader(archivo);
			bufferedReader = new BufferedReader(reader);
		}
		
		String line = bufferedReader.readLine();
		
		if(line == null) {
			reiniciarReader();
		}
		
		return line;
		
	}
	
	
	/**
	 * Lee contenido entero de un archivo.
	 * @return
	 * @throws IOException
	 */
	public String leerContenido() throws IOException
	{
		String contenido = "";
		
		String linea;
		while((linea = siguienteLinea()) != null) {
			contenido += linea + System.lineSeparator();
		}
		
		return contenido;
	}
	
	
	/**
	 * Agrega una linea de texto al final de un archivo.
	 * @param linea
	 */
	public void agregarLinea(String linea) throws IOException
	{
		String contenido = leerContenido();
		contenido = contenido + linea + System.lineSeparator();
		guardarContenido(contenido);
	}
	
	
	/**
	 * Guarda contenido de texto en archivo pisando el contenido anterior.
	 * @param contenido
	 * @throws IOException
	 */
	public void guardarContenido(String contenido) throws IOException
	{
		FileWriter writer = new FileWriter(archivo);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
				
		bufferedWriter.write(contenido);
		
		bufferedWriter.close();
		writer.close();
	}
	
	
	
	
	
	
	
}

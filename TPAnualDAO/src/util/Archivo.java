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
	public void crear()
	{
		try {
			archivo.createNewFile();
		} catch (IOException e) {
			System.out.println("Error creando archivo " + archivo.getName() + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	/**
	 * Cerramos reader, para volver a empezar de la primera linea al llamar siguienteLinea()
	 * @throws IOException
	 */
	public void reiniciarReader()
	{
		try
		{
			reader.close();
			bufferedReader.close();
			reader = null;
			bufferedReader = null;
		} catch (IOException e) {
			System.out.println("Error cerrando FileReader o BufferedReader." + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	/**
	 * Lee la siguiente línea del archivo utilizando un reader a nivel objeto.
	 * @return
	 * @throws IOException
	 */
	public String siguienteLinea()
	{
		
		try
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
		} catch (IOException e) {
			
			System.out.println("Error leyendo contenido de archivo " + archivo.getName() + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
			
			return null;
		}
	}
	
	
	/**
	 * Lee contenido entero de un archivo.
	 * @return
	 * @throws IOException
	 */
	public String leerContenido()
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
	public void agregarLinea(String linea)
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
	public void guardarContenido(String contenido)
	{
		try
		{
			FileWriter writer = new FileWriter(archivo);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
					
			bufferedWriter.write(contenido);
			
			bufferedWriter.close();
			writer.close();
			
		} catch (IOException e) {
			System.out.println("Error guardando contenido de texto en archivo " + archivo.getName() + System.lineSeparator() + "Stack:");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	
	
	
	
	
}

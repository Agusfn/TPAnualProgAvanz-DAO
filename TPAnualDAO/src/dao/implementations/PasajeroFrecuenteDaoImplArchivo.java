package dao.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IPasajeroFrecuenteDao;
import modelo.PasajeroFrecuente;
import util.Archivo;

public class PasajeroFrecuenteDaoImplArchivo implements IPasajeroFrecuenteDao {

	/**
	 * Nombre del archivo que almacenar� al objeto.
	 */
	private static String nombreArchivo = "pasajeros_frecuentes.txt";

	/**
	 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en
	 * toda la clase.
	 */
	private Archivo archivo;

	/**
	 * Inicializar DAO, creando archivo si no existe.
	 * 
	 * @throws IOException
	 */
	public PasajeroFrecuenteDaoImplArchivo() throws IOException {
		archivo = new Archivo(nombreArchivo);

		if (!archivo.existe()) {
			archivo.crear();
		}
	}

	/**
	 * Obtener un pais del archivo a partir de su id.
	 */
	@Override
	public PasajeroFrecuente obtener(int id) throws IOException {

		String linea;

		while ((linea = archivo.siguienteLinea()) != null) {

			PasajeroFrecuente pais = csvToPasajeroFrecuente(linea);

			if (pais.getId() == id) {
				archivo.reiniciarReader();
				return pais;
			}
		}

		return null;
	}

	/**
	 * Obtener lista de todos los paises del archivo.
	 */
	@Override
	public List<PasajeroFrecuente> obtenerTodos() throws IOException {

		List<PasajeroFrecuente> paises = new ArrayList<PasajeroFrecuente>();

		String linea;

		while ((linea = archivo.siguienteLinea()) != null) {
			paises.add(csvToPasajeroFrecuente(linea));
		}

		return paises;
	}

	/**
	 * Agregar nuevo pais al archivo. Se le asigna una id nueva auto-incremental. No
	 * se debe usar este metodo con un objeto ya existente en el archivo porque se
	 * duplica.
	 */
	@Override
	public void agregar(PasajeroFrecuente pais) throws IOException {

		pais.setId(obtenerSiguienteId());

		String lineaCsv = pasajeroFrecuenteToCsv(pais);

		archivo.agregarLinea(lineaCsv);
	}

	/**
	 * Eliminar pa�s de archivo. Se elimina la linea del pais que posea la id del
	 * pais dado.
	 */
	@Override
	public void eliminar(PasajeroFrecuente paisAEliminar) throws IOException {

		List<String> lineas = new ArrayList<String>();

		String linea;
		while ((linea = archivo.siguienteLinea()) != null) {

			PasajeroFrecuente pais = csvToPasajeroFrecuente(linea);
			if (pais.getId() != paisAEliminar.getId()) {
				lineas.add(linea);
			}

		}

	}

	/**
	 * Actualizar un pais en el archivo. Se actualiza el pa�s seg�n la id.
	 */
	@Override
	public void actualizar(PasajeroFrecuente pais) throws IOException {

		String contenido = "";

		String linea;

		while ((linea = archivo.siguienteLinea()) != null) {
			PasajeroFrecuente paisLinea = csvToPasajeroFrecuente(linea);
			if (paisLinea.getId() == pais.getId()) {
				contenido += pasajeroFrecuenteToCsv(pais) + System.lineSeparator();
			} else {
				contenido += linea + System.lineSeparator();
			}

		}
		archivo.guardarContenido(contenido);

	}

	/**
	 * Obtiene id del siguiente objeto a agregar al archivo. Esta id es la id del
	 * ultimo objeto + 1. Si no hay elementos, devuelve 1.
	 * 
	 * @return
	 * @throws IOException
	 */
	private int obtenerSiguienteId() throws IOException {

		PasajeroFrecuente ultimoPais = null;

		String linea;
		while ((linea = archivo.siguienteLinea()) != null) {
			ultimoPais = csvToPasajeroFrecuente(linea);
		}

		if (ultimoPais == null)
			return 1;
		else
			return ultimoPais.getId() + 1;
	}

	/**
	 * Convierte una l�nea CSV de archivo a un pa�s.
	 * 
	 * @param csv
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	private PasajeroFrecuente csvToPasajeroFrecuente(String csv) throws ArrayIndexOutOfBoundsException {
		String[] props = csv.split(",");

		PasajeroFrecuente pasajeroFrecuente = new PasajeroFrecuente();
		pasajeroFrecuente.setId(Integer.parseInt(props[0]));
		pasajeroFrecuente.setAlianza(props[1]);

		return pasajeroFrecuente;
	}

	private String pasajeroFrecuenteToCsv(PasajeroFrecuente pasajFrec) {
		return "";
		//return pasajFrec.getId() + "," + pasajFrec.getAlianza() + "," + pasajFrec.getAerolinea().getId() + ;
	}

}

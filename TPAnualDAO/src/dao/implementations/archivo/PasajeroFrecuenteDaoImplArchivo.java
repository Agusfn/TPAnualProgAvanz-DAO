package dao.implementations.archivo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IPasajeroFrecuenteDao;
import modelo.PasajeroFrecuente;
import util.Archivo;
import util.Properties;

public class PasajeroFrecuenteDaoImplArchivo implements IPasajeroFrecuenteDao {

	/**
	 * Nombre del archivo que almacenará al objeto.
	 */
	private static String nombreArchivo = Properties.getProperty("csv_pasajerosfrecuentes");

	/**
	 * Clase archivo con simplificaciones de funciones de archivo que se utilizan en
	 * toda la clase.
	 */
	private Archivo archivo;

	/**
	 * Inicializar DAO, creando archivo si no existe.
	 * 
	 */
	public PasajeroFrecuenteDaoImplArchivo() throws IOException {

		archivo = new Archivo(nombreArchivo);

		if (!archivo.existe()) {
			archivo.crear();
		}
	}

	/**
	 * Obtener un pasajero frecuente del archivo a partir de su id.
	 */
	@Override
	public PasajeroFrecuente obtener(int id) throws IOException {

		String linea;

		while ((linea = archivo.siguienteLinea()) != null) {

			PasajeroFrecuente pasajeroFrecuente = csvToPasajeroFrecuente(linea);

			if (pasajeroFrecuente.getId() == id) {
				archivo.reiniciarReader();
				return pasajeroFrecuente;
			}
		}

		return null;
	}

	/**
	 * Obtener lista de todos los pasajeros del archivo.
	 */
	@Override
	public List<PasajeroFrecuente> obtenerTodos() throws IOException {

		List<PasajeroFrecuente> pasajerosFrecuentes = new ArrayList<PasajeroFrecuente>();

		String linea;

		while ((linea = archivo.siguienteLinea()) != null) {
			pasajerosFrecuentes.add(csvToPasajeroFrecuente(linea));
		}

		return pasajerosFrecuentes;
	}

	/**
	 * Agregar nuevo pasajero al archivo. Se le asigna una id nueva auto-incremental. No
	 * se debe usar este metodo con un objeto ya existente en el archivo porque se
	 * duplica.
	 */
	@Override
	public void agregar(PasajeroFrecuente pasajeroFrecuente) throws IOException {

		pasajeroFrecuente.setId(obtenerSiguienteId());

		String lineaCsv = pasajeroFrecuenteToCsv(pasajeroFrecuente);

		archivo.agregarLinea(lineaCsv);
	}

	
	/**
	 * Eliminar pasajeros de archivo. Se elimina la linea del pasajero que posea la id del
	 * pasajero dado.
	 */
	@Override
	public void eliminar(PasajeroFrecuente pasajeroAEliminar) throws IOException {

		String contenido = "";

		String linea;
		while ((linea = archivo.siguienteLinea()) != null) {

			PasajeroFrecuente pasajero = csvToPasajeroFrecuente(linea);
			if (pasajero.getId() != pasajeroAEliminar.getId()) {
				contenido += pasajeroFrecuenteToCsv(pasajero) + System.lineSeparator();
			}
		}
		archivo.guardarContenido(contenido);
	}

	/**
	 * Actualizar un pasajero en el archivo. Se actualiza el pasajero frec. según la id.
	 */
	@Override
	public void actualizar(PasajeroFrecuente pasajeroFrecuente) throws IOException {

		String contenido = "";

		String linea;

		while ((linea = archivo.siguienteLinea()) != null) {
			PasajeroFrecuente pasajeroLinea = csvToPasajeroFrecuente(linea);
			if (pasajeroLinea.getId() == pasajeroFrecuente.getId()) {
				contenido += pasajeroFrecuenteToCsv(pasajeroFrecuente) + System.lineSeparator();
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
	 */
	private int obtenerSiguienteId() throws IOException {

		PasajeroFrecuente ultimoPasajero = null;

		String linea;
		while ((linea = archivo.siguienteLinea()) != null) {
			ultimoPasajero = csvToPasajeroFrecuente(linea);
		}

		if (ultimoPasajero == null)
			return 1;
		else
			return ultimoPasajero.getId() + 1;
	}

	/**
	 * Convierte una línea CSV de archivo a un pasajero frec.
	 * 
	 * @param csv
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	private PasajeroFrecuente csvToPasajeroFrecuente(String csv) throws ArrayIndexOutOfBoundsException, IOException {
		String[] props = csv.split(",");

		PasajeroFrecuente pasajeroFrecuente = new PasajeroFrecuente();
		
		pasajeroFrecuente.setId(Integer.parseInt(props[0]));
		pasajeroFrecuente.setAlianza(props[1]);
		
		int idAerolinea = Integer.parseInt(props[2]);
		AerolineaDaoImplArchivo dao = new AerolineaDaoImplArchivo();
		pasajeroFrecuente.setAerolinea(dao.obtener(idAerolinea));
		
		pasajeroFrecuente.setNumero(props[3]);
		pasajeroFrecuente.setCategoria(props[4]);
		
		
		return pasajeroFrecuente;
	}

	private String pasajeroFrecuenteToCsv(PasajeroFrecuente pasajFrec) {
		
		return pasajFrec.getId() + "," + pasajFrec.getAlianza() + "," + pasajFrec.getAerolinea().getId() + "," + pasajFrec.getNumero() + "," + pasajFrec.getCategoria();
	}

}

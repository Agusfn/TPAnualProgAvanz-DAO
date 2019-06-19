package dao.factory;

import java.io.IOException;

import dao.implementations.VentaDaoImplArchivo;
import dao.implementations.VentaDaoImplSerializacion;
import dao.interfaces.IVentaDao;

public class VentaFactory {

	public static IVentaDao getImplementation (String source) throws IOException {
		
		if (source.equals(("archivo"))) {
			return new VentaDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new VentaDaoImplSerializacion();
		}
		return null;
		
	}	
	
}

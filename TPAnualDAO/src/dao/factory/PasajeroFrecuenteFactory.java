package dao.factory;

import java.io.IOException;

import dao.implementations.archivo.PasajeroFrecuenteDaoImplArchivo;
import dao.implementations.serializacion.PasajeroFrecuenteDaoImplSerializacion;
import dao.interfaces.IPasajeroFrecuenteDao;

public class PasajeroFrecuenteFactory {

	public static IPasajeroFrecuenteDao getImplementation (String source) throws IOException {
		
		if (source.equals(("archivo"))) {
			return new PasajeroFrecuenteDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new PasajeroFrecuenteDaoImplSerializacion();
		}
		return null;
		
	}	
	
}

package dao.factory;

import dao.implementations.archivo.PasajeroFrecuenteDaoImplArchivo;
import dao.implementations.db.PasajeroFrecuenteDaoImplDB;
import dao.implementations.serializacion.PasajeroFrecuenteDaoImplSerializacion;
import dao.interfaces.IPasajeroFrecuenteDao;

public class PasajeroFrecuenteFactory {

	public static IPasajeroFrecuenteDao getImplementation (String source) throws Exception {
		
		if (source.equals(("archivo"))) {
			return new PasajeroFrecuenteDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new PasajeroFrecuenteDaoImplSerializacion();
		}
		else if (source.equals("db")) {	
			return new PasajeroFrecuenteDaoImplDB();
		}
		return null;
		
	}	
	
}

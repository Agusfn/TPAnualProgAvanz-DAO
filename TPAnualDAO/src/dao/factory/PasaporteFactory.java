package dao.factory;

import dao.implementations.archivo.PasaporteDaoImplArchivo;
import dao.implementations.db.PasaporteDaoImplDB;
import dao.implementations.serializacion.PasaporteDaoImplSerializacion;
import dao.interfaces.IPasaporteDao;

public class PasaporteFactory {

	public static IPasaporteDao getImplementation (String source) throws Exception {
		
		if (source.equals(("archivo"))) {
			return new PasaporteDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new PasaporteDaoImplSerializacion();
		}
		else if (source.equals("db")) {	
			return new PasaporteDaoImplDB();
		}
		return null;
		
	}	
	
}

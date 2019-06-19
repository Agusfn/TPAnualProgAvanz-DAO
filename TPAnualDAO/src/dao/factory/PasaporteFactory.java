package dao.factory;

import java.io.IOException;
import dao.implementations.PasaporteDaoImplArchivo;
import dao.implementations.PasaporteDaoImplSerializacion;
import dao.interfaces.IPasaporteDao;

public class PasaporteFactory {

	public static IPasaporteDao getImplementation (String source) throws IOException {
		
		if (source.equals(("archivo"))) {
			return new PasaporteDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new PasaporteDaoImplSerializacion();
		}
		return null;
		
	}	
	
}

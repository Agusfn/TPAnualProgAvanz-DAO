package dao.factory;

import java.io.IOException;

import dao.implementations.PaisDaoImplArchivo;
import dao.implementations.PaisDaoImplSerializacion;
import dao.interfaces.IPaisDao;

public class PaisFactory {

	public static IPaisDao getImplementation (String source) throws IOException {
		
		if (source.equals(("archivo"))) {
			return new PaisDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new PaisDaoImplSerializacion();
		}
		return null;
		
	}	
	
}

package dao.factory;

import java.io.IOException;

import dao.implementations.archivo.PaisDaoImplArchivo;
import dao.implementations.serializacion.PaisDaoImplSerializacion;
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

package dao.factory;

import java.io.IOException;

import dao.implementations.archivo.AerolineaDaoImplArchivo;
import dao.implementations.serializacion.AerolineaDaoImplSerializacion;
import dao.interfaces.IAerolineaDao;
//

public class AerolineaFactory {
	
public static IAerolineaDao getImplementation (String source) throws IOException {
		
		if (source.equals(("archivo"))) {
			return new AerolineaDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new AerolineaDaoImplSerializacion();
		}
		return null;
		
	}	
}

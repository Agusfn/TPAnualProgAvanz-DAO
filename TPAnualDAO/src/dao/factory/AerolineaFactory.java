package dao.factory;

import java.io.IOException;
import dao.implementations.AerolineaDaoImpArchivo;
import dao.implementations.AerolineaDaoImpSerializacion;
import dao.interfaces.IAerolineaDao;
//

public class AerolineaFactory {
	
public static IAerolineaDao getImplementation (String source) throws IOException {
		
		if (source.equals(("archivo"))) {
			return new AerolineaDaoImpArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new AerolineaDaoImpSerializacion();
		}
		return null;
		
	}	
}

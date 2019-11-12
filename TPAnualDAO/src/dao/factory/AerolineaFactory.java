package dao.factory;

import dao.implementations.archivo.AerolineaDaoImplArchivo;
import dao.implementations.db.AerolineaDaoImplDB;
import dao.implementations.serializacion.AerolineaDaoImplSerializacion;
import dao.interfaces.IAerolineaDao;
//

public class AerolineaFactory {
	
public static IAerolineaDao getImplementation (String source) throws Exception {
		
		if (source.equals(("archivo"))) {
			return new AerolineaDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new AerolineaDaoImplSerializacion();
		}
		else if(source.equals("db")) {
			return new AerolineaDaoImplDB();
		}
		return null;
		
	}	
}

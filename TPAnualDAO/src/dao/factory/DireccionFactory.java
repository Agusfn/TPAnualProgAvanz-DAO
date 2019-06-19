package dao.factory;
import java.io.IOException;

import dao.implementations.DireccionDaoImplArchivo;
import dao.implementations.DireccionDaoImplSerializacion;
import dao.interfaces.IDireccionDao;


public class DireccionFactory {
	public static IDireccionDao getImplementation (String source) throws IOException {
			
		if (source.equals(("archivo"))) {
			return new DireccionDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new DireccionDaoImplSerializacion();
		}
		return null;
			
	}	
		
}

	
	


package dao.factory;
import java.io.IOException;

import dao.implementations.archivo.DireccionDaoImplArchivo;
import dao.implementations.serializacion.DireccionDaoImplSerializacion;
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

	
	


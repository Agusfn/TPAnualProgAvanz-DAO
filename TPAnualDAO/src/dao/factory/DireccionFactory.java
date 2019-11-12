package dao.factory;

import dao.implementations.archivo.DireccionDaoImplArchivo;
import dao.implementations.db.DireccionDaoImplDB;
import dao.implementations.serializacion.DireccionDaoImplSerializacion;
import dao.interfaces.IDireccionDao;


public class DireccionFactory {
	public static IDireccionDao getImplementation (String source) throws Exception {
			
		if (source.equals(("archivo"))) {
			return new DireccionDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new DireccionDaoImplSerializacion();
		}
		else if (source.equals("db")) {	
			return new DireccionDaoImplDB();
		}
		return null;
			
	}	
		
}

	
	


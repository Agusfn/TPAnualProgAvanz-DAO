package dao.factory;

import dao.implementations.archivo.AeropuertoDaoImplArchivo;
import dao.implementations.db.AeropuertoDaoImplDB;
import dao.implementations.serializacion.AeropuertoDaoImplSerializacion;
import dao.interfaces.IAeropuertoDao;


public class AeropuertoFactory {
	
		public static IAeropuertoDao getImplementation (String source) throws Exception {
			
			if (source.equals(("archivo"))) {
				return new AeropuertoDaoImplArchivo();
			}
			else if (source.equals("serializacion")) {	
				return new AeropuertoDaoImplSerializacion();
			}
			else if(source.equals("db")) {
				return new AeropuertoDaoImplDB();
			}
			return null;
			
		}	
		
}

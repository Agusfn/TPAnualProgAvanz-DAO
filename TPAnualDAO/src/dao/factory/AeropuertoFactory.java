package dao.factory;
import java.io.IOException;

import dao.implementations.AeropuertoDaoImplArchivo;
import dao.implementations.AeropuertoDaoImplSerializacion;
import dao.interfaces.IAeropuertoDao;


public class AeropuertoFactory {
	
		public static IAeropuertoDao getImplementation (String source) throws IOException {
			
			if (source.equals(("archivo"))) {
				return new AeropuertoDaoImplArchivo();
			}
			else if (source.equals("serializacion")) {	
				return new AeropuertoDaoImplSerializacion();
			}
			return null;
			
		}	
		
}
package dao.factory;
import java.io.IOException;

import dao.implementations.archivo.ProvinciaDaoImplArchivo;
import dao.implementations.serializacion.ProvinciaDaoImplSerializacion;
import dao.interfaces.IProvinciaDao;


public class ProvinciaFactory {
	
	public static IProvinciaDao getImplementation (String source) throws IOException {
			
			if (source.equals(("archivo"))) {
				return new ProvinciaDaoImplArchivo();
			}
			else if (source.equals("serializacion")) {	
				return new ProvinciaDaoImplSerializacion();
			}
			return null;
			
		}	
		
}
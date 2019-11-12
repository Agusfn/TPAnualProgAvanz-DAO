package dao.factory;

import dao.implementations.archivo.ProvinciaDaoImplArchivo;
import dao.implementations.db.ProvinciaDaoImplDB;
import dao.implementations.serializacion.ProvinciaDaoImplSerializacion;
import dao.interfaces.IProvinciaDao;


public class ProvinciaFactory {
	
	public static IProvinciaDao getImplementation (String source) throws Exception {
			
			if (source.equals(("archivo"))) {
				return new ProvinciaDaoImplArchivo();
			}
			else if (source.equals("serializacion")) {	
				return new ProvinciaDaoImplSerializacion();
			}
			else if (source.equals("db")) {	
				return new ProvinciaDaoImplDB();
			}
			return null;
			
		}	
		
}
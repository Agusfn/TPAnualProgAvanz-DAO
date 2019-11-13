package dao.factory;

import dao.implementations.archivo.AlianzaDaoImplArchivo;
import dao.implementations.db.AlianzaDaoImplDB;
//
import dao.interfaces.IAlianzaDao;

public class AlianzaFactory {
	
public static IAlianzaDao getImplementation (String source) throws Exception {
		
		if (source.equals(("archivo"))) {
			return new AlianzaDaoImplArchivo();
		}
		else if(source.equals("db")) {
			return new AlianzaDaoImplDB();
		}
		return null;
		
	}	
}

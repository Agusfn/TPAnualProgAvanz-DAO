package dao.factory;


import dao.implementations.archivo.TelefonoDaoImplArchivo;
import dao.implementations.db.TelefonoDaoImplDB;
import dao.implementations.serializacion.TelefonoDaoImpSerializacion;
import dao.interfaces.ITelefonoDao;
//
public class TelefonoFactory {
	public static ITelefonoDao getImplementation (String source) throws Exception {
		
		if (source.equals(("archivo"))) {
			return new TelefonoDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new TelefonoDaoImpSerializacion();
		}
		else if (source.equals("db")) {	
			return new TelefonoDaoImplDB();
		}
		return null;
		
	}	
	
}

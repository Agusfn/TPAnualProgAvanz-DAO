package dao.factory;

import java.io.IOException;

import dao.implementations.TelefonoDaoImpArchivo;
import dao.implementations.TelefonoDaoImpSerializacion;
import dao.interfaces.ITelefonoDao;
//
public class TelefonoFactory {
	public static ITelefonoDao getImplementation (String source) throws IOException {
		
		if (source.equals(("archivo"))) {
			return new TelefonoDaoImpArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new TelefonoDaoImpSerializacion();
		}
		return null;
		
	}	
	
}

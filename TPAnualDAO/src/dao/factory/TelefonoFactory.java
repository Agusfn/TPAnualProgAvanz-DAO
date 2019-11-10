package dao.factory;

import java.io.IOException;

import dao.implementations.archivo.TelefonoDaoImplArchivo;
import dao.implementations.serializacion.TelefonoDaoImpSerializacion;
import dao.interfaces.ITelefonoDao;
//
public class TelefonoFactory {
	public static ITelefonoDao getImplementation (String source) throws IOException {
		
		if (source.equals(("archivo"))) {
			return new TelefonoDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new TelefonoDaoImpSerializacion();
		}
		return null;
		
	}	
	
}

package dao.factory;

import java.io.IOException;

import dao.implementations.archivo.ClienteDaoImplArchivo;
import dao.implementations.serializacion.ClienteDaoImplSerializacion;
import dao.interfaces.IClienteDao;
//
public class ClienteFactory {
	public static IClienteDao getImplementation (String source) throws IOException {
		
		if (source.equals(("archivo"))) {
			return new ClienteDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return (IClienteDao) new ClienteDaoImplSerializacion();
		}
		return null;
		
	}	
	
}

package dao.factory;

import java.io.IOException;
import dao.implementations.ClienteDaoImpArchivo;
import dao.implementations.ClienteDaoImpSerializacion;
import dao.interfaces.IClienteDao;
//
public class ClienteFactory {
	public static IClienteDao getImplementation (String source) throws IOException {
		
		if (source.equals(("archivo"))) {
			return new ClienteDaoImpArchivo();
		}
		else if (source.equals("serializacion")) {	
			return (IClienteDao) new ClienteDaoImpSerializacion();
		}
		return null;
		
	}	
	
}

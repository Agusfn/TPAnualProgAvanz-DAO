package dao.factory;


import dao.implementations.archivo.ClienteDaoImplArchivo;
import dao.implementations.db.ClienteDaoImplDB;
import dao.implementations.serializacion.ClienteDaoImplSerializacion;
import dao.interfaces.IClienteDao;
//
public class ClienteFactory {
	public static IClienteDao getImplementation (String source) throws Exception {
		
		if (source.equals(("archivo"))) {
			return new ClienteDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new ClienteDaoImplSerializacion();
		}
		else if (source.equals("db")) {	
			return new ClienteDaoImplDB();
		}
		return null;
		
	}	
	
}

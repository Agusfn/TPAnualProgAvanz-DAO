package dao.factory;


import dao.implementations.archivo.PaisDaoImplArchivo;
import dao.implementations.db.PaisDaoImplDB;
import dao.implementations.serializacion.PaisDaoImplSerializacion;
import dao.interfaces.IPaisDao;

public class PaisFactory {

	public static IPaisDao getImplementation (String source) throws Exception {
		
		if (source.equals(("archivo"))) {
			return new PaisDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new PaisDaoImplSerializacion();
		}
		else if (source.equals("db")) {	
			return new PaisDaoImplDB();
		}
		return null;
		
	}	
	
}

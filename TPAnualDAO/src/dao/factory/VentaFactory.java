package dao.factory;

import dao.implementations.archivo.VentaDaoImplArchivo;
import dao.implementations.db.VentaDaoImplDB;
import dao.implementations.serializacion.VentaDaoImplSerializacion;
import dao.interfaces.IVentaDao;

public class VentaFactory {

	public static IVentaDao getImplementation (String source) throws Exception {
		
		if (source.equals(("archivo"))) {
			return new VentaDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new VentaDaoImplSerializacion();
		}
		else if (source.equals("db")) {	
			return new VentaDaoImplDB();
		}
		return null;
		
	}	
	
}

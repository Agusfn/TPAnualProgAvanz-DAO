package dao.factory;


import dao.implementations.archivo.VueloDaoImplArchivo;
import dao.implementations.db.VueloDaoImplDB;
import dao.implementations.serializacion.VueloDaoImplSerializacion;
import dao.interfaces.IVueloDao;

public class VueloFactory {

	public static IVueloDao getImplementation (String source) throws Exception {
		
		if (source.equals(("archivo"))) {
			return new VueloDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new VueloDaoImplSerializacion();
		}
		else if (source.equals("db")) {	
			return new VueloDaoImplDB();
		}
		return null;
		
	}	
	
}

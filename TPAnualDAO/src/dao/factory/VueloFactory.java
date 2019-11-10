package dao.factory;

import java.io.IOException;

import dao.implementations.archivo.VueloDaoImplArchivo;
import dao.implementations.serializacion.VueloDaoImplSerializacion;
import dao.interfaces.IVueloDao;

public class VueloFactory {

	public static IVueloDao getImplementation (String source) throws IOException {
		
		if (source.equals(("archivo"))) {
			return new VueloDaoImplArchivo();
		}
		else if (source.equals("serializacion")) {	
			return new VueloDaoImplSerializacion();
		}
		return null;
		
	}	
	
}

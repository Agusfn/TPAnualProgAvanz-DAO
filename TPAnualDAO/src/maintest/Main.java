package maintest;

import java.util.List;

import dao.implementations.db.PaisDaoImplDB;
import dao.implementations.db.ProvinciaDaoImplDB;
import modelo.Pais;
import modelo.Provincia;
import util.DbQuery;

public class Main {

	public static void main(String[] args)
	{
		
		
		
		DbQuery query = new DbQuery();
		
		ProvinciaDaoImplDB provinciaDao = new ProvinciaDaoImplDB(query);
		PaisDaoImplDB paisDao = new PaisDaoImplDB(query);
		
		
		Pais pais = paisDao.obtener(1);
		
		Provincia provincia = new Provincia();
		provincia.setNombre("Neuquén");
		provincia.setPais(pais);
		
		provinciaDao.agregar(provincia);
		
		/*provincia.setPais(paisDao.obtener(provincia.getPais().getId()));
		
		System.out.println(provincia.getPais().getNombre());*/
		
		
		
		
		
		query.close();
		
		
		//List<Pais> paises = paisDao.obtenerTodos();
		
		/*for(Pais pais: paises) {
			System.out.println(pais.getNombre());
		}*/
		
		/*paisDao.agregar(pais);
		
		*/
		
		
	}
	
	
}

package maintest;

import java.io.IOException;

import dao.implementations.PaisDaoImplArchivo;
import modelo.Pais;
import util.Archivo;

public class Main {

	public static void main(String[] args) throws IOException
	{
	
		// Ejemplo uso del DAO (con pais)
		
		PaisDaoImplArchivo dao = new PaisDaoImplArchivo();
		
		Pais pais;
		// Agregamos paises
		pais = new Pais();
		pais.setNombre("Argentina");
		dao.agregar(pais);
		
		pais = new Pais();
		pais.setNombre("Chile");
		dao.agregar(pais);
		
		
		pais = dao.obtener(2); // obtenemos pais id 2
		System.out.println(pais.getNombre());
		
		pais.setNombre("China");
		dao.actualizar(pais);
		
		
		
		
		
	}
	
	
}

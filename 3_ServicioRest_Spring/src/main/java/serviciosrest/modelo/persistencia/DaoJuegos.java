package serviciosrest.modelo.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import serviciosrest.modelo.entidad.Juegos;

/**
 * Patron DAO (Data Access Object), objeto que se encarga de hacer las consultas
 * a algun motor de persistencia (BBDD, Ficheros, etc). En este caso vamos a
 * simular que los datos estan guardados en una BBDD trabajando con una lista de
 * objetos cargada en memoria para simplificar el ejemplo.
 * 
 * Hay que tener en cuenta que para simplificar el ejemplo tambien se ha hecho
 * que el ID con el que se dan de alta las personas en la lista coincide
 * exactamente con la posicion del array que ocupan.
 * 
 * Mediante la anotacion @Component, damos de alta un unico objeto de esta clase
 * dentro del contexto de Spring, su ID sera el nombre de la case en notacion
 * lowerCamelCase
 * 
 */
@Component
public class DaoJuegos {

	public List<Juegos> listaJuegos;
	public int contador; 

	/**
	 * Cuando se cree el objeto dentro del contexto de Spring, se ejecutara su
	 * constructor, que creara las personas y las metera en una lista para que
	 * puedan ser consumidas por nuestros clientes
	 */

	public DaoJuegos() {
		System.out.println("DaoJuegos -> Creado la lista de Juegos:");
		listaJuegos = new ArrayList<Juegos>();

		/*Juegos j1 = new Juegos("Sonic the Hedgehog", "Sega", 9);
		Juegos j2 = new Juegos("Super Mario", "Nintendo", 10);
		Juegos j3 = new Juegos("Spider-Man", "Sony", 8);
		Juegos j4 = new Juegos("Tom Raider", "Square Enix", 7);
		Juegos j5 = new Juegos("Minecraft", "Mojang Studios", 7.5);*/
		Juegos j1 = new Juegos(contador++, "Sonic the Hedgehog", "Sega", 9);
		Juegos j2 = new Juegos(contador++, "Super Mario", "Nintendo", 10);
		Juegos j3 = new Juegos(contador++, "Spider-Man", "Sony", 8);
		Juegos j4 = new Juegos(contador++, "Tomb Raider", "Square Enix", 7);
		Juegos j5 = new Juegos(contador++, "Minecraft", "Mojang Studios", 7.5);

		listaJuegos.add(j1);
		listaJuegos.add(j2);
		listaJuegos.add(j3);
		listaJuegos.add(j4);
		listaJuegos.add(j5);
	}

	/**
	 * Devuelve una persona a partir de su posicion del array
	 * 
	 * @param posicion la posicion del arrya que buscamos
	 * @return la persona que ocupe en la posicion del array, null en caso de que no
	 *         exista o se haya ido fuera de rango del array
	 */

	public Juegos get(int posicion) {
		try {
			return listaJuegos.get(posicion);
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("El juego que está buscando no existe");
			return null;
		}
	}

	/**
	 * Metodo que devuelve toda las personas del array
	 * 
	 * @return una lista con todas las personas del array
	 */

	public List<Juegos> list() {
		return listaJuegos;
	}

	/**
	 * Metodo que introduce una persona al final de la lista
	 * 
	 * @param p la persona que queremos introducir
	 */

	public Juegos add(Juegos j) {
		
		for(Juegos js: listaJuegos) {
			if(js.getNombre().equalsIgnoreCase(j.getNombre())) {
				System.out.println("El videojuego que intenta crear ya existe");
				return null;
				
			}
		}
		
		j.setId(contador++);
		listaJuegos.add(j);
		return j ;
		//j.setId(contador++);
		//listaJuegos.add(j);
		//addJuego(j);
	}

	/**
	 * Borramos una persona de una posicion del array
	 * 
	 * @param posicion la posicion a borrar
	 * @return devolvemos la persona que hemos quitado del array, o null en caso de
	 *         que no exista.
	 */

	public Juegos delete(int posicion) {
		try {
			
			return listaJuegos.remove(posicion);
			
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("Delete --> Juego fuera de rango");
			return null;
		}

	}

	/**
	 * Metodo que modifica una persona de una posicion del array
	 * 
	 * @param p contiene todos los datos que queremos modificar, pero p.getId()
	 *          contiene la posicion del array que queremos eliminar
	 * @return la persona modificada en caso de que exista, null en caso contrario
	 */

	public Juegos update(Juegos j) {
		try {
			Juegos jAux = listaJuegos.get(j.getId());
			jAux.setNombre(j.getNombre());
			jAux.setCompania(j.getCompania());
			jAux.setNota(j.getNota());

			return jAux;

		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("Update --> Juego fuera de rango");
			return null;
		}
	}

	/**
	 * Metodo que devuelve todas las personas por nombre. Como puede haber varias
	 * personas con el mismo nombre (HARRY) tengo que devolver una lista con todas
	 * las encontradas
	 * 
	 * @param nombre representa el nombre por el que vamos a hacer la busqueda
	 * @return una lista con las personas que coincidan en el nombre. La lista
	 *         estará vacia en caso de que no hay coincidencias
	 */

	public List<Juegos> listByNombre(String nombre) {
		List<Juegos> listaJuegosAux = new ArrayList<Juegos>();
		for (Juegos j : listaJuegos) {
			if (j.getNombre().equalsIgnoreCase(nombre)) {
				listaJuegosAux.add(j);
			}

		}

		return listaJuegosAux;
	}
	
	/*public String addJuego(Juegos j) {
		for(Juegos js: listaJuegos) {
			if(js.getNombre().equalsIgnoreCase(j.getNombre())) {
				return null;
			}
		}
		
		j.setId(contador++);
		listaJuegos.add(j);
		return "Añadido";
	
	}*/

}

package serviciorest.cliente.servicio;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Juegos;

//@Service: Dar de alta el objeto de tipo ServicioProxyJuego dentro del contexto de Spring

@Service 
public class ServicioProxyJuego {
	
	//URL base del servicio REST de juegos 
	public static final String URL = "http://localhost:8080/juegos/";
	
	//@Autowired: inyectamos el objeto de tipo RestTemplate que ayuda a hacer las peticiones HTTP al servicio REST 
	
	@Autowired
	private RestTemplate restTemplate; 
	
	/* Creamos metodo para obtener un juego del servicio REST a partir de un id. 
	 * En el caso de que el id no exista arrojaria una excepcion que se captura para sacar el código de respuesta. 
	 * 
	 * @param id que queremos obtener
	 * @return retorna el juego que estamos buscando, null en el caso de que el juego no se encuentre en el servidor (devuelva 404)
	 * o haya habido algún error. 
	 */
	
	public Juegos obtener(int id) {
		try {
			// El servicio trabaja con objetos ResponseEntity, por lo que nosotros podemos también hacerlo en el cliente
			ResponseEntity<Juegos> re = restTemplate.getForEntity(URL + id, Juegos.class);
			HttpStatus hs = re.getStatusCode();
			
			if(hs == HttpStatus.OK) {
				// Creamos condicion para saber si el juego existe, vendrá en formato JSON en el body
				// El objeto al ser ResponseEntity de tipo Juegos, convertirá el body automáticamente a tipo Juegos al obtenerlo
				return re.getBody();
			}else {
				System.out.println("Respuesta no contemplada");
				return null;
			}
		}catch (HttpClientErrorException e) {
			System.out.println("obtener -> El juego NO se ha encontrado, id: " + id);
			System.out.println("obtener -> Codigo respuesta: " + e.getStatusCode());
			return null; 
		}
	}
	
	/* Creamos metodo que dará de alta un juego en el servicio REST
	 * 
	 * @param j el juego que vamos a dar de alta 
	 * @return el juego con el id actualizado que se ha dado de alta en el servicio REST. Null en el caso de que no se haya podido dar de alta
	 */
	
	public Juegos alta(Juegos j) {
		try {
			// Para hacer un post de una entidad usamos el metodo postForEntity
			//1º - El primer parametro la URL 
			//2º - El segundo parámetro el juego que irá en el body
			//3º - El tercer parámetro será el objeto que esperamos que nos envíe el servidor 
			
			ResponseEntity<Juegos> re= restTemplate.postForEntity(URL, j, Juegos.class);
			System.out.println("Alta -> Código de respuesta " + re.getStatusCode());
			return re.getBody();
		}catch (HttpClientErrorException e) {
			System.out.println("Alta -> El juego NO se ha dado de alta, id " + j);
			System.out.println("Alta -> Código respuesta: " + e.getStatusCode());
			return null;
		}
	}
	
	/* Creamos método tipo boolean que modifique un juego en el servicio REST 
	 * 
	 * @param j el juego que queremos modificar, se hará a partir del id, por lo que tiene que estar relleno
	 * @return true en caso de que se haya podido modificar el juego, false en caso contrario
	 */
	public boolean modificar(Juegos j) {
		try {
			//Utilizaremos el metodo PUT de Spring, que no devuelve nada, y si no da error se ha dado de alta
			//y si no se ha podido saltará una excepción
			restTemplate.put(URL + j.getId(), j, Juegos.class);
			return true; 
		}catch(HttpClientErrorException e) {
			System.out.println("Modificar -> El juego NO se ha podido modificar, id: " + j.getId());
			System.out.println("Modificar -> Código de respuesta: " + e.getStatusCode());
			return false;
		}
	}
	
	/* Creamos método que borre un juego en el servicio REST 
	 * 
	 * @param id el id del juego que queremos borrar 
	 * @return true en caso de que se haya podido borrar, false en caso contrario
	 * */
	
	public boolean borrar(int id) {
		try {
			// El método delete, al igual que el PUT, tampoco devuelve nada, pero si no se ha podido borrar el juego indicado, 
			// saltará una excepción 
			restTemplate.delete(URL + id);
			return true; 
		}catch(HttpClientErrorException e) {
			System.out.println("Borrar -> El juego NO se ha podido borrar, id: " + id);
			System.out.println("Borrar -> Código de respuesta: " + e.getStatusCode());
			return false; 
		}
	}
	
	/*
	 * Creamos el último método, que devolverá todos los juegos o todos los juegos filtrados por nombre del web service
	 * @param nombre: si se indica uno, devolverá el listado filtrado por el nombre que se le haya pasado por parámetro. 
	 * En caso de que no se pase ningún nombre, que sea null, el listado de los juegos será completo
	 * @return el listado de los juegos segun el parámetro de entrada o null en caso de algún error con el servicio REST
	 */
	
	public List<Juegos> listar(String nombre){
		String queryParams = "";
		if(nombre != null) {
			queryParams += "?nombre=" + nombre; 
		}
		
		try {
			ResponseEntity<Juegos[]>response = restTemplate.getForEntity(URL + queryParams, Juegos[].class);
			Juegos[] arrayJuegos = response.getBody();
			return Arrays.asList(arrayJuegos);
		}catch (HttpClientErrorException e) {
			System.out.println("Listar -> Error al obtener la lista de juegos");
			System.out.println("Listar -> Código de respuesta: " + e.getStatusCode());
			return null; 
		}
	}
	
}

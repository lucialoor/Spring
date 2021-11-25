package serviciorest.cliente.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Service: Damos de alta un objeto de tipo ServicioProxyMensaje dentro del contexto de Spring

@Service
public class ServicioProxyMensaje {

	// URL base del servicio REST
	
	public static final String URL = "http://localhost:8080/";
	
	//@Autowired: Inyectamos el objeto tipo RestTemplate que ayuda a hacer las peticiones HTTP al servicio REST
	@Autowired
	private RestTemplate restTemplate; 
	
	public String obtener(String path) {
		
		/*
		 * Método getForObject del objeto restTemplate para poder hacer peticiones HTTP a un servicio REST. 
		 * Tenemos que pasarle la URL y el tipo que nos va a devolver. 
		 * Este método usará el verbo GET para hacer la request 
		 */
		
		String mensaje = restTemplate.getForObject(URL + path, String.class);
		return mensaje; 
	}
}

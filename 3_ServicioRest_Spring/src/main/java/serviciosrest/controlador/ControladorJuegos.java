package serviciosrest.controlador;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import serviciosrest.modelo.persistencia.DaoJuegos;
import serviciosrest.modelo.entidad.Juegos;

@RestController
public class ControladorJuegos {
	@Autowired
	private DaoJuegos daoJuegos;

	@GetMapping(path = "juegos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Juegos> getJuegos(@PathVariable("id") int id) {
		System.out.println("Buscando juego con id: " + id);

		Juegos j = daoJuegos.get(id);

		if (j != null) {
			return new ResponseEntity<Juegos>(j, HttpStatus.OK);
		}else {
			return new ResponseEntity<Juegos>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(path="juegos", consumes= MediaType.APPLICATION_JSON_VALUE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Juegos> altaJuegos(@RequestBody Juegos j){
		System.out.println("altaJuegos: objeto juego: " + j);
		
		Juegos js = daoJuegos.add(j);
		if(js == null) {
			
			return new ResponseEntity<Juegos>(HttpStatus.NOT_FOUND);
		}else {
			daoJuegos.add(j);
			return new ResponseEntity<Juegos>(j,HttpStatus.CREATED);
		}
		
	}
	
	@GetMapping(path="juegos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Juegos>> listarJuegos(
						@RequestParam(name="nombre", required=false) String nombre){
					List<Juegos> listaJuegos = null;
					
					if(nombre == null) {
						System.out.println("Listando los juegos");
						listaJuegos = daoJuegos.list();
					}else if(nombre != null){
						System.out.println("Listando los juegos por nombre: " + nombre);
						listaJuegos = daoJuegos.listByNombre(nombre);
					}
					System.out.println(listaJuegos);
					return new ResponseEntity<List<Juegos>>(listaJuegos,HttpStatus.OK);
					}
		
						
	@PutMapping(path="juegos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Juegos> modificarJuegos(
			@PathVariable("id") int id, 
			@RequestBody Juegos j){
		System.out.println("ID a modificar: " + id);
		System.out.println("Datos a modificar:" + j);
		j.setId(id);
		Juegos jUpdate = daoJuegos.update(j);
		if(jUpdate != null) {
			return new ResponseEntity<Juegos>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Juegos>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(path="juegos/{id}")
	public ResponseEntity<Juegos> borrarJuegos(@PathVariable int id){
		System.out.println("ID a borrar: " + id);
		Juegos j = daoJuegos.delete(id); 
		if(j != null) {
			return new ResponseEntity<Juegos>(j,HttpStatus.OK); 
		}else {
			return new ResponseEntity<Juegos>(j,HttpStatus.NOT_FOUND); 
		}
	}


}

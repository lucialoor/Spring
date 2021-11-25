package serviciorest.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Juegos;
import serviciorest.cliente.servicio.ServicioProxyJuego;
import serviciorest.cliente.servicio.ServicioProxyMensaje;

@SpringBootApplication
public class Application implements CommandLineRunner{
	
	//En primer lugar inyectaremos todos los objetos que necesitamos para acceder a nuestro ServicioRest: 
	//el ServicioProxyPersona y el servicioProxyMensaje
	
	@Autowired
	private ServicioProxyJuego spj; 
	
	@Autowired 
	private ServicioProxyMensaje spm; 
	
	//Como tendremos que parar la app, necesitaremos acceder al contexto de Spring, ya que esta app al ser una app web se lanzara 
	//en un tomcat. De esta manera le decimos a Spring que nos inyecte su propio contexto
	
	@Autowired
	private ApplicationContext context; 
	
	/*En este método daremos de alta un objeto de tipo RestTemplate que sera el objeto más importante de esta aplicación. 
	 * Será usado por los objetos ServicioProxy para hacer las peticiones HTTP a nuestro servicio REST. 
	 * Como no podemos anotar la clase RestTemplate porque no la hemos creado nosotros, usaremos la anotación @Bean para 
	 * decirle a Spring que ejecute este metodo y meta el objetos devuelto dentro del contexto de Spring con ID "restTemplate
	 * (el nombre del metodo) */
	
	@Bean 
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build(); 
	}
	
	// El método main que lanzará la aplicación 
	public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto Spring");
		SpringApplication.run(Application.class, args);
		
		/*Como este método es estático no podemos acceder a los métodos dinámicos de la clase, 
		 * como spj o spm. Para solucionar esto, haremos que nuestra clase implemente "CommandLineRunner" e implementamos 
		 * el método "run". 
		 * Cuando termine de arrancar el contexto, se llamara automáticamente al método run 
		 * */
	}
	
	//Este método es dinámico por lo tanto ya podemos acceder a los atributos dinámicos 
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		boolean continuar = true; 
		do {
			escribirMenu();
			int opcion = sc.nextInt(); 
			boolean validarId = true;
			switch(opcion) {
			case 1: 
				Juegos juego = new Juegos(); 
				System.out.println("Opción 1 elegida: ");
				sc.nextLine();
				
				int id;
				String nombreAlta;
				String companiaAlta; 
				double notaAlta;
				boolean validarNotaAlta;
				
				do {
					System.out.println("Introduzca el nombre del nuevo juego:");
					nombreAlta= sc.nextLine();
					
					if(nombreAlta != "") {
						juego.setNombre(nombreAlta);
					}else {
						System.out.println("El nombre del nuevo juego no puede estar vacío");
					}
					
				}while(nombreAlta == "" );
				
				
				do {
						
						System.out.println("Introduzca el nombre de la compañía del nuevo juego:");
						companiaAlta= sc.nextLine();
						if(companiaAlta != "") {
							juego.setCompania(companiaAlta);
						}else {
							System.out.println("El nombre de la compañía no puede estar vacío");
						}
						
					}while(companiaAlta == "");
				
				do {
					System.out.println("Introduzca la nota del nuevo juego (Deber ser de 0 a 10):");
					
					try {
						notaAlta= Double.parseDouble(sc.nextLine());
					if(notaAlta <= 10 && notaAlta >= 0) {
						//notaModificar= Double.parseDouble(sc.nextLine());
						validarNotaAlta = true;
						juego.setNota(notaAlta);
						}else {
							System.out.println("La nota tiene tiene que ser entre 0 y 10");
							validarNotaAlta = false;
						}
					}catch(NumberFormatException e) {
						System.out.println("La nota no puede estar vacía ni incluir letras");
						validarNotaAlta = false; 
					}
				}while(!validarNotaAlta);
				
				
				Juegos juegoAlta = spj.alta(juego);
				System.out.println("Run -> Juego dado de alta " + juegoAlta);
				break;
				
			case 2:
				System.out.println("Opción 2 elegida: ");
				sc.nextLine();
				
				do {					
					try {
						System.out.println("Introduzca el id del juego que quiere dar de baja: ");
						id = Integer.parseInt(sc.nextLine());
						boolean baja = spj.borrar(id);
						System.out.println("Run -> Juego con id " + id + " dado de baja: " + baja);
						validarId = true;
					
						break;
					
					}catch(NumberFormatException e) {
						System.out.println("El ID no puede estar vacío");
						validarId = false; 
					}
					
				}while(!validarId);
				break;
				
			case 3: 
				System.out.println("Opción 3 elegida");
				sc.nextLine();
				Juegos jModificar = new Juegos(); 
				int idModificar; 
				String nombreModificar;
				String companiaModificar; 
				double notaModificar;
				 
				boolean validarNota = true; 
				
				do {
					System.out.println("Introduzca el id del juego que quiere modificar: ");
					
					try {
					idModificar= Integer.parseInt(sc.nextLine());
					validarId = true;
					jModificar.setId(idModificar);
					}catch(NumberFormatException e) {
						System.out.println("El ID no puede estar vacío");
						validarId = false; 
					}
				}while(!validarId);
				
				do {
					System.out.println("Introduzca el nombre del juevo que quiere modificar:");
					nombreModificar= sc.nextLine();
					if(nombreModificar != "") {
						jModificar.setNombre(nombreModificar);
					}else {
						System.out.println("El nombre no puede estar vacío");
					}
				}while(nombreModificar == "");
				
				do {
					System.out.println("Introduzca el nombre de la compañía del juego que quiere modificar :");
					companiaModificar= sc.nextLine();
					if(companiaModificar != "") {
						jModificar.setCompania(companiaModificar);
					}else {
						System.out.println("El nombre de la compañía no puede estar vacío");
					}
				}while(companiaModificar == "");
				
				do {
					System.out.println("Introduzca la nota del juego que quiere (Deber ser de 0 a 10):");
					
					try {
						notaModificar= Double.parseDouble(sc.nextLine());
					if(notaModificar <= 10 && notaModificar >= 0) {
						//notaModificar= Double.parseDouble(sc.nextLine());
						validarNota = true;
						jModificar.setNota(notaModificar);
						}else {
							System.out.println("La nota tiene tiene que ser entre 0 y 10");
							validarNota = false;
						}
					}catch(NumberFormatException e) {
						System.out.println("La nota no puede estar vacía ni incluir letras");
						validarNota = false; 
					}
				}while(!validarNota);
				
			
				boolean modificado = spj.modificar(jModificar);
				System.out.println("Run -> juego modificado?" + modificado);
				break;
			case 4: 
				System.out.println("Opción 4 elegida: ");
				sc.nextLine();
				int idOb; 
				//Juegos JObtener = new Juegos();
				//System.out.println("Introduzca el id del juego que quiere obtener: ");
				
				do {
					System.out.println("Introduzca el id del juego que quiere obtener: ");
					
					try {
					idOb= Integer.parseInt(sc.nextLine());
					validarId = true;
					juego = spj.obtener(idOb);
					System.out.println("Run -> El juego con id " + idOb + " es " + juego);
					
					}catch(NumberFormatException e) {
						System.out.println("El ID no puede estar vacío");
						validarId= false; 
					}
				}while(!validarId);
				/*int idObtener = Integer.parseInt(sc.nextLine()); 
				juego = spj.obtener(idObtener);
				
				if (spj.obtener(idObtener) != null) {
					System.out.println("Run -> El juego con id " + idObtener + "es " + juego);
				}else {
					System.out.println("Run -> Persona con id " + idObtener + juego);
				}*/
				break;
			case 5: 
				System.out.println("Opcion 5 elegida: ");
				
				System.out.println("Introduzca el nombre del juego que quiere buscar. Si quiere ver el listado completo pulse intro");
				sc.nextLine();
				String nombreBuscar = sc.nextLine();
				List<Juegos> listarJuegos;
				if(nombreBuscar == "") {
					 listarJuegos = spj.listar(null);
				
					for(Juegos j: listarJuegos) {
						System.out.println(j);
					}
				}else {
					listarJuegos = spj.listar(nombreBuscar);
					for(Juegos j: listarJuegos) {
						if(j.getNombre().equalsIgnoreCase(nombreBuscar)) {
						System.out.println(j);
						}
					}
				}
				break;
			case 6: 
				continuar = false; 
				
				System.out.println("Ha elegido la opción de salir, gracias por su visita");
				break;
			}
		}while(continuar);
		pararAplicacion(); 
		
		
	}
	
	public static void escribirMenu() {
		System.out.println("Elija una de las siguientes opciones:");
		System.out.println("1. Dar de alta un videojuego");
		System.out.println("2. Dar de baja un videojuego por ID");
		System.out.println("3. Modificar un videojuego por ID");
		System.out.println("4. Obtener un videojuego por ID");
		System.out.println("5. Listar todos los videojuegos");
		System.out.println("6. Salir del programa");
	}
	
	public void pararAplicacion() {
		SpringApplication.exit(context, new ExitCodeGenerator() {

			@Override
			public int getExitCode() {
				// TODO Auto-generated method stub
				return 0;
			}
			
		});
	}
	
	

}

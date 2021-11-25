package serviciorest.cliente.entidad;

import org.springframework.stereotype.Component;

@Component
public class Juegos {

	private int id;

	private String nombre;
	private String compania;
	private double nota;

	public Juegos() {
		super();
	}

	public Juegos(int id, String nombre, String compania, double nota) {
		this.id = id;
		this.nombre = nombre;
		this.compania = compania;
		this.nota = nota;
	}

	public int getId() {
	
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCompania() {
		return compania;
	}

	public void setCompania(String compania) {
		this.compania = compania;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return "Juego [ ID =" + id + ", Nombre =" + nombre + ", Compañía = " + compania + ", nota = " + nota + "]";
	}
}
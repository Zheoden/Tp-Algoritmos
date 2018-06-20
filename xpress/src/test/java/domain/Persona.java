package domain;

import xpress.ann.*;
import java.util.Date;

@Table(name="persona")
public class Persona
{
	@Id(strategy=Id.IDENTITY)
	@Column(name="id_persona")
	private int idPersona;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="direccion")
	private String direccion;
	
	@Column(name="fecha_alta")
	private Date fechaAlta;
	
	public Persona(){}
	// otros constructores, setters y getters
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
}
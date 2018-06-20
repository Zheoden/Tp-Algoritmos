package domain;
import xpress.ann.*;
import java.util.List;

@Table(name="rol") 
public class Rol { 
	
	@Id(strategy=Id.IDENTITY) 
	@Column(name="id_rol")
	private int idRol; 
	
	@Column (name="descripcion")
	private String descripcion;  
	
	public Rol(){} 
	
	// otros contructores, setters y getters }
}
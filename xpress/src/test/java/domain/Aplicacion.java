package domain;
import xpress.ann.*;

@Table(name="aplicacion") 
public class Aplicacion { 
	@Id(strategy=Id.IDENTITY) 
	@Column (name="id_plicacion")
	private int idAplicacion; 
	
	@Column (name="descripcion")
	private String descripcion; 
	
	// constructores, setters y getters }
}
package domain;
import xpress.ann.*;

@Table(name="rol_aplicacion") 
public class RolAplicacion { 
	@Id(strategy=Id.IDENTITY) 
	@Column (name="id_rol_aplicacion")
	private int idRolAplicacion; 
	
	@OneToMany(mappedBy="id_rol") 
	private Rol rol; 
	
	@OneToMany(mappedBy="id_aplicacion") 
	private Aplicacion aplicacion; 
	
	// constructores, setters y getters }
}
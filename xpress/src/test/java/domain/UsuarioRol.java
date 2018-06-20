package domain;
import xpress.ann.*;

@Table(name="usuario_rol") 
public class UsuarioRol { 
	@Id(strategy=Id.IDENTITY) 
	@Column(name="id_usuario_rol")
	private int idUsuarioRol; 
	
	@OneToMany(mappedBy="id_usuario")
	private Usuario usuario; 
	
	@OneToMany(mappedBy="id_rol")
	private Rol rol; 

	public Rol getRol()
	{
		return rol;
	}

	public void setRol(Rol rol)
	{
		this.rol = rol;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	public int getIdUsuarioRol()
	{
		return idUsuarioRol;
	}

	public void setIdUsuarioRol(int idUsuarioRol)
	{
		this.idUsuarioRol = idUsuarioRol;
	} 
}
package domain;
import xpress.ann.*;
import java.util.List;

@Table(name="usuario") 
public class Usuario{ 
	@Id(strategy=Id.IDENTITY) 
	@Column (name="id_usuario")
	private int idUsuario; 
	
	@ManyToOne(columnName="id_persona",fetchType=ManyToOne.LAZY) 
	public Persona persona; 
	
	@Column (name="username")
	private String username; 
	
	@Column (name="password") 
	private String password; 
	
	@OneToMany(mappedBy="id_usuario") 
	public List<UsuarioRol> roles; 
	
	public Usuario(){}

	public int getIdUsuario()
	{
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario)
	{
		this.idUsuario=idUsuario;
	}

	public Persona getPersona()
	{
		return persona;
	}

	public void setPersona(Persona persona)
	{
		this.persona=persona;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username=username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password=password;
	}

	public List<UsuarioRol> getRoles()
	{
		return roles;
	}

	public void setRoles(List<UsuarioRol> roles)
	{
		this.roles=roles;
	} 
	
	
	// otros contructores, setters y getters }
}
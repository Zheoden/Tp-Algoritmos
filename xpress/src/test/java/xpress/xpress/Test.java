package xpress.xpress;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;

import domain.*;
import xpress.Utn;
import xpress.UtnConnectionFactory;

public class Test
{
	@org.junit.Test
	public void testFind() throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		Connection con = UtnConnectionFactory.getConnection();
		
		// verifico el find
		Usuario p = Utn.find(con,Usuario.class,2);
		Assert.assertEquals(p.getUsername(),"jschiffer1");

		// persona es LAZY => debe permanecer NULL hasta que haga el get
		Assert.assertNull(p.persona);

		Assert.assertEquals((Integer)p.getPersona().getIdPersona(),(Integer)8);

		// debe traer el objeto
		Persona o = p.getPersona();
		Assert.assertNotNull(o);
	
		// verifico que lo haya traido bien
		Assert.assertEquals(o.getNombre(),"Micaela");
		
		// verifico que venga bien...
		Assert.assertEquals(o.getDireccion(),"Bolivia");
		
		// tipoOcupacion (por default) es EAGER => no debe ser null
//		Assert.assertNotNull(o.getTipoOcupacion());
//		TipoOcupacion to = o.getTipoOcupacion();
		
		// -- Relation --
		
		// las relaciones son LAZY si o si!
		Assert.assertNull(p.roles);
		
		List<UsuarioRol> roles = p.getRoles();
		Assert.assertNotNull(roles);
		
		// debe tener 2 elementos
		Assert.assertEquals(roles.size(),1);
		
		for(UsuarioRol pd:roles)
		{
			Usuario u1 = pd.getUsuario();
			Rol r = pd.getRol();
			
			Assert.assertNotNull(u1);
			Assert.assertNotNull(r);
		
			Assert.assertEquals(u1.getUsername(),p.getUsername());
		}
		
	}

	@org.junit.Test
	public void testXQL() throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IOException {
		Connection con = UtnConnectionFactory.getConnection();
		Utn.query(con, Usuario.class, "$persona.nombre LIKE ?", "Analia");
	}
}

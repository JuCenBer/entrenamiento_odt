package cespi.induccion.estacionamiento.services;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.models.User;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import cespi.induccion.estacionamiento.models.Permission;
import cespi.induccion.estacionamiento.models.Role;

@Service
@Transactional
public class AuthorizationService {
	
	public boolean hasPermission(User user, Permission permission) {
		List<Role> roles = user.getRoles();
		for(Role role: roles) {
			if(role.hasPermission(permission)) return true;
		}
		return false;
	}
	

	
	// We need a signing key, so we'll create one just for this example. Usually
	// the key would be read from your application configuration instead.
	public String generateToken(User user) {
		SecretKey key = Jwts.SIG.HS256.key().build();
		String jws = Jwts.builder().subject(user.getCellphone()).signWith(key).compact();
		return jws;
	}
}

package cespi.induccion.estacionamiento.services;

import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import cespi.induccion.estacionamiento.models.Permission;
import cespi.induccion.estacionamiento.models.Role;

@Service
@Transactional
public class AuthorizationService {
	
	final static SecretKey key = Jwts.SIG.HS256.key().build();

	
	public boolean hasPermission(User user, Permission permission) {
		List<Role> roles = user.getRoles();
		for(Role role: roles) {
			if(role.hasPermission(permission)) return true;
		}
		return false;
	}
	
	
	public String generateToken(String cellphone) {
		String jws = Jwts.builder()
				.subject(cellphone)
				.expiration(null)
				.signWith(key)
				.compact();
		return jws;
	}
	
	private static Jws<Claims> getClaim(String token){
		String prefix = "Bearer";
		if (token.startsWith(prefix)) {
			token = token.substring(prefix.length()).trim();
		}
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token); 
	}
	
	public static boolean validateToken(String token) {
		try{
			Jws<Claims> claim = getClaim(token);
			System.out.println(claim.getPayload().getSubject());
			return true;
	    } catch (ExpiredJwtException exp) {
	        return false;
	    } catch (JwtException e) {
		    return false; // Algo salio mal en la verificacion
	    } 
	}
	
	public String getUser(String token) {
		try {
			Jws<Claims> claim = getClaim(token);			
			return claim.getPayload().getSubject();
		} catch (ExpiredJwtException exp) {
	        return null;
		} catch (JwtException e) {
		    return null; // Algo salio mal en la verificacion
	    } 
	}
}

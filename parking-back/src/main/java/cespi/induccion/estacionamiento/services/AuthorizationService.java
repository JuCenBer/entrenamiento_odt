package cespi.induccion.estacionamiento.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.models.User;
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

}

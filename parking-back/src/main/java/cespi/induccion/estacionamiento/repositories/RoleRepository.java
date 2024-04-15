package cespi.induccion.estacionamiento.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.Role;
import cespi.induccion.estacionamiento.models.User;

public interface RoleRepository extends JpaRepository<Role, Long>{

	public Optional<Role> findByRole(String role);
}

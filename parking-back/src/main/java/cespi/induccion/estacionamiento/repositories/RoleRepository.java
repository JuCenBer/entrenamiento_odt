package cespi.induccion.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}

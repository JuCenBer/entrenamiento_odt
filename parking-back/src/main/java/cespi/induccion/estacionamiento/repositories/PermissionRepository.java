package cespi.induccion.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{

}

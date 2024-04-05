package cespi.induccion.estacionamiento.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{

	public Optional<Permission> findByPermission(String permission);
}

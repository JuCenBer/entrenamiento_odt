package cespi.induccion.estacionamiento.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByCellphone(String cellphone);

}

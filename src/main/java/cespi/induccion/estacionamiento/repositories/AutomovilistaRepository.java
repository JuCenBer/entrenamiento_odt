package cespi.induccion.estacionamiento.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.Automovilista;

public interface AutomovilistaRepository extends JpaRepository<Automovilista, Long> {
	
	public Optional<Automovilista> findByCellphone(String cellphone);

}

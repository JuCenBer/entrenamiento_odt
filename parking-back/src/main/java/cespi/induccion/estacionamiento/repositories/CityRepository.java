package cespi.induccion.estacionamiento.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.City;

public interface CityRepository extends JpaRepository<City, Long>{
	
	public Optional<City> findByCity(String city);

}

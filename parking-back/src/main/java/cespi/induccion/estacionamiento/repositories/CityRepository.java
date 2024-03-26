package cespi.induccion.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.City;

public interface CityRepository extends JpaRepository<City, Long>{

}

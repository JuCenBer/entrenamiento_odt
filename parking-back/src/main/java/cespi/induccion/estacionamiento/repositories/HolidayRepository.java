package cespi.induccion.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long>{

}

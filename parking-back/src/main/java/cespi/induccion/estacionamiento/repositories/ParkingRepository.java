package cespi.induccion.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.Parking;

public interface ParkingRepository extends JpaRepository<Parking, Long>{

}

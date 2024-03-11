package cespi.induccion.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.Automovilista;

public interface AutomovilistaRepository extends JpaRepository<Automovilista, Long> {
	
	public Automovilista findByCellphone(String cellphone);

}

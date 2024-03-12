package cespi.induccion.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}

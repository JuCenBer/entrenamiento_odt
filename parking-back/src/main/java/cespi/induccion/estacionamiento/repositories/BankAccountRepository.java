package cespi.induccion.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cespi.induccion.estacionamiento.models.BankAccount;

public interface BankAccountRepository  extends JpaRepository<BankAccount, Long>{

}

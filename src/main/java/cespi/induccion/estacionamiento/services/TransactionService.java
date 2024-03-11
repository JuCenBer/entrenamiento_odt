package cespi.induccion.estacionamiento.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.repositories.TransactionRepository;

@Service
@Transactional
public class TransactionService {
	//Este se comportara como un builder
	
	@Autowired
	TransactionRepository transactionRepository;

	public ConsumptionTransaction createConsumption() {
		
	}
	
	public RechargeTransaction createRecharge() {
		
	}
}

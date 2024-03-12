package cespi.induccion.estacionamiento.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.models.ConsumptionTransaction;
import cespi.induccion.estacionamiento.models.RechargeTransaction;
import cespi.induccion.estacionamiento.models.Transaction;
import cespi.induccion.estacionamiento.repositories.TransactionRepository;

@Service
@Transactional
public class TransactionService {
	//Este se comportara como un builder
	
	@Autowired
	TransactionRepository transactionRepository;
	
	public void saveTransaction(Transaction transaction) {
		this.transactionRepository.save(transaction);
	}

	public Transaction createConsumption(double monto, String operacion) {
		ConsumptionTransaction consumption = new ConsumptionTransaction(monto, operacion);
		this.saveTransaction(consumption);
		return consumption;
	}
	
	public Transaction createRecharge() {
		RechargeTransaction recharge = new RechargeTransaction();
		this.saveTransaction(recharge);
		return recharge;
	}
}

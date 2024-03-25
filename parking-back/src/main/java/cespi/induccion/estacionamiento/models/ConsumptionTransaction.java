package cespi.induccion.estacionamiento.models;

import cespi.induccion.estacionamiento.DTO.ConsumptionTransactionDTO;
import jakarta.persistence.Entity;

@Entity
public class ConsumptionTransaction extends Transaction{
	
	public ConsumptionTransaction() {
		super();
	}

	public ConsumptionTransaction(double monto, double newBalance, String operacion) {
		super(monto, newBalance, operacion);
	}
	
	public ConsumptionTransactionDTO getDTO() {
		return new ConsumptionTransactionDTO(this.getDate(), this.getAmount(), this.getNewBalance(), this.getOperation());
	}
}

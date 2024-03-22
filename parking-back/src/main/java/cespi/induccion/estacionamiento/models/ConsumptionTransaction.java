package cespi.induccion.estacionamiento.models;

import cespi.induccion.estacionamiento.DTO.ConsumptionTransactionDTO;
import jakarta.persistence.Entity;

@Entity
public class ConsumptionTransaction extends Transaction{
	
	public ConsumptionTransaction() {
		super();
	}

	public ConsumptionTransaction(double monto, String operacion) {
		super(monto, operacion);
	}
	
	public ConsumptionTransactionDTO getDTO() {
		return new ConsumptionTransactionDTO(this.getDate(), this.getAmount(), this.getOperation());
	}
}

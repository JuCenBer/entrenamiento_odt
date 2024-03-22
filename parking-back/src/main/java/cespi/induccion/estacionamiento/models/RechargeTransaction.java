package cespi.induccion.estacionamiento.models;

import cespi.induccion.estacionamiento.DTO.RechargeTransactionDTO;
import jakarta.persistence.Entity;

@Entity
public class RechargeTransaction extends Transaction{
	
	
	public RechargeTransaction() {
		super();
	}
	
	public RechargeTransaction(double monto, String operation) {
		super(monto, operation);
	}
	
	public RechargeTransactionDTO getDTO() {
		return new RechargeTransactionDTO(this.getDate(), this.getAmount(), this.getOperation());
	}

}

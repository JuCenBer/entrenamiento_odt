package cespi.induccion.estacionamiento.models;

import cespi.induccion.estacionamiento.DTO.RechargeTransactionDTO;
import jakarta.persistence.Entity;

@Entity
public class RechargeTransaction extends Transaction{
	
	
	public RechargeTransaction() {
		super();
	}
	
	public RechargeTransaction(double monto, double newBalance, String operation) {
		super(monto, newBalance, operation);
	}
	
	public RechargeTransactionDTO getDTO() {
		return new RechargeTransactionDTO(this.getDate(), this.getAmount(), this.getNewBalance(), this.getOperation());
	}

}

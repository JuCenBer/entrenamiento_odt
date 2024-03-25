package cespi.induccion.estacionamiento.DTO;

import java.time.LocalDate;

public class RechargeTransactionDTO extends TransactionDTO{

	public RechargeTransactionDTO(LocalDate date, double amount, double newBalance, String operation) {
		super(date, amount, newBalance, operation, "recharge");
	}
	
	public RechargeTransactionDTO() {
		
	}
}

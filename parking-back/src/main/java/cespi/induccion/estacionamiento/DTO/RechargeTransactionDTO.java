package cespi.induccion.estacionamiento.DTO;

import java.time.LocalDate;

public class RechargeTransactionDTO extends TransactionDTO{

	public RechargeTransactionDTO(LocalDate date, double amount, String operation) {
		super(date, amount, operation, "recharge");
	}
	
	public RechargeTransactionDTO() {
		
	}
}

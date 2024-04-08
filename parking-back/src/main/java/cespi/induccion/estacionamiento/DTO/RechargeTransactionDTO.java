package cespi.induccion.estacionamiento.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RechargeTransactionDTO extends TransactionDTO{

	public RechargeTransactionDTO(LocalDateTime date, double amount, double newBalance, String operation) {
		super(date, amount, newBalance, operation, "recharge");
	}
	
	public RechargeTransactionDTO() {
		
	}
}

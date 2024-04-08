package cespi.induccion.estacionamiento.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConsumptionTransactionDTO extends TransactionDTO{
	
	public ConsumptionTransactionDTO(LocalDateTime date, double amount, double newBalance, String operation) {
		super(date, amount, newBalance, operation, "consumption");
	}
	
	public ConsumptionTransactionDTO() {
		
	}
	
	

}

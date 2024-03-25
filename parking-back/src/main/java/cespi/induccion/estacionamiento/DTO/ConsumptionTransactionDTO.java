package cespi.induccion.estacionamiento.DTO;

import java.time.LocalDate;

public class ConsumptionTransactionDTO extends TransactionDTO{
	
	public ConsumptionTransactionDTO(LocalDate date, double amount, double newBalance, String operation) {
		super(date, amount, newBalance, operation, "consumption");
	}
	
	public ConsumptionTransactionDTO() {
		
	}
	
	

}

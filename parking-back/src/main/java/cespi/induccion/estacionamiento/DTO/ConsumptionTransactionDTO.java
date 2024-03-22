package cespi.induccion.estacionamiento.DTO;

import java.time.LocalDate;

public class ConsumptionTransactionDTO extends TransactionDTO{
	
	public ConsumptionTransactionDTO(LocalDate date, double amount, String operation) {
		super(date, amount, operation, "consumption");
	}
	
	public ConsumptionTransactionDTO() {
		
	}
	
	

}

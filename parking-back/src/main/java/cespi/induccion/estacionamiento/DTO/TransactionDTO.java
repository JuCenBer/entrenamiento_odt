package cespi.induccion.estacionamiento.DTO;

import java.time.LocalDate;

public class TransactionDTO {

	private LocalDate date;
	private double amount;
	private String operation;
	private String type;
	
	public TransactionDTO() {
	}
	
	public TransactionDTO(LocalDate date, double amount, String operation, String type) {
		this.date = date;
		this.amount = amount;
		this.operation = operation;
		this.type = type;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}

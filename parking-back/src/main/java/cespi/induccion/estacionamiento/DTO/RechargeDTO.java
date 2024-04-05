package cespi.induccion.estacionamiento.DTO;

public class RechargeDTO {
	
	private String cellphone;
	private double amount;
	
	public RechargeDTO(String cellphone, double amount) {
		this.cellphone = cellphone;
		this.amount = amount;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}

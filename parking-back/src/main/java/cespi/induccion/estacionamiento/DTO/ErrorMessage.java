package cespi.induccion.estacionamiento.DTO;

public class ErrorMessage {
		
	private int status;
	private String message;
	
	public ErrorMessage() {
	}

	public ErrorMessage(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getStatus() {
		return status;
	}
}


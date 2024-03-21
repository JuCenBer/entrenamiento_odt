package cespi.induccion.estacionamiento.DTO;

public class VehiculoDTO {

	private String licensePlate;
	
	public VehiculoDTO() {
	}
	
	public VehiculoDTO(String licensePlate) {
		this.licensePlate = licensePlate.toUpperCase();
	}
	
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate.toUpperCase();
	}
	
	
	
}

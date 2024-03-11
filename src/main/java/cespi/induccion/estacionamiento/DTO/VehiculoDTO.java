package cespi.induccion.estacionamiento.DTO;

public class VehiculoDTO {

	private long id;
	private String licensePlate;
	
	public VehiculoDTO() {
	}
	
	public VehiculoDTO(long id, String licensePlate) {
		this.id = id;
		this.licensePlate = licensePlate.toUpperCase();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate.toUpperCase();
	}
	
	
	
}

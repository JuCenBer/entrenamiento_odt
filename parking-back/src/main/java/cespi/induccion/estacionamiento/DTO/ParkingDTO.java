package cespi.induccion.estacionamiento.DTO;

public class ParkingDTO {

	private String parkedCarLicensePlate;
	private boolean parked;
	
	public ParkingDTO(boolean isParked, String parkedCarLicensePlate) {
		this.parked = isParked;
		this.parkedCarLicensePlate = parkedCarLicensePlate;
	}
	
	public ParkingDTO() {
		
	}

	public String getParkedCarLicensePlate() {
		return parkedCarLicensePlate;
	}

	public void setParkedCarLicensePlate(String parkedCarLicensePlate) {
		this.parkedCarLicensePlate = parkedCarLicensePlate;
	}

	public boolean isParked() {
		return parked;
	}

	public void setParked(boolean isParked) {
		this.parked = isParked;
	}
	
}

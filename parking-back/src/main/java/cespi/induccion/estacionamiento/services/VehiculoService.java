package cespi.induccion.estacionamiento.services;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;

@Service
@Transactional
@DependsOn(value = "importService")
public class VehiculoService {

	private ImportService importService;
	private String localPlateFormatRegex;
	private int localPlateFormatLength;
	private String mercosurPlateFormatRegex;
	private int mercosurPlateFormatLength;
	
	
	public VehiculoService(ImportService importService) {
		this.importService = importService;
	}
	
	@PostConstruct
	public void setParameters() {
		String localPlateFormat = this.importService.getLocalPlateFormat();
		String mercosurPlateFormat = this.importService.getMercosurPlateFormat();
		
		this.localPlateFormatRegex = this.obtenerRegexDesdeFormato(localPlateFormat);
		this.mercosurPlateFormatRegex = this.obtenerRegexDesdeFormato(mercosurPlateFormat);
		this.localPlateFormatLength = localPlateFormat.length();
		this.mercosurPlateFormatLength = mercosurPlateFormat.length();
	}
	
	public boolean checkLicensePlateFormatRegex(String licensePlate) {
		return (isLocalLicensePlateFormatRegex(licensePlate) || isMercosurLicensePlateFormatRegex(licensePlate));
	}
	
	private boolean isLocalLicensePlateFormatRegex(String licensePlate) {
		if(this.checkFormat(licensePlate, this.localPlateFormatRegex) && (licensePlate.length() == this.localPlateFormatLength)) {
			System.out.println("Formato local valido");
			return true;
		}
		return false;
	}
	
	private boolean isMercosurLicensePlateFormatRegex(String licensePlate) {
		if(this.checkFormat(licensePlate, this.mercosurPlateFormatRegex) && (licensePlate.length() == this.mercosurPlateFormatLength)) {
			System.out.println("Formato Mercosur valido");
			return true;
		}
	    return false;
	}
	
	private String obtenerRegexDesdeFormato(String formato) {
        return formato.replaceAll("L", "[A-Za-z]") // Letras (mayusculas o minusculas)
                .replaceAll("N", "[0-9]");    // Numeros
	}
	
	private boolean checkFormat(String licensePlate, String regexFormat) {
		Pattern pattern = Pattern.compile(regexFormat);
		Matcher matcher = pattern.matcher(licensePlate);
		boolean matchFound = matcher.find();
		return matchFound;
	}
}

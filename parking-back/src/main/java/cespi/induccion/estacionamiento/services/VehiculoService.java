package cespi.induccion.estacionamiento.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehiculoService {

//	@Autowired
//	VehiculoRepository vehiculoRepository;
	
	public boolean checkLicensePlateFormatRegex(String licensePlate) {
		return (isArgentineLicensePlateFormatRegex(licensePlate) || isMercosurLicensePlateFormatRegex(licensePlate));
	}
	
	private boolean isArgentineLicensePlateFormatRegex(String licensePlate) {
		//Chequea por el formato de patente 'AAA999'
		if(licensePlate.length() == 6) {			
			Pattern pattern = Pattern.compile("[a-zA-Z]{3}[0-9]{3}");
			Matcher matcher = pattern.matcher(licensePlate);
			boolean matchFound = matcher.find();
			if(matchFound) {
				System.out.println("Formato argentino valido");
				return true;
			}
		}
		return false;
	}
	
	private boolean isMercosurLicensePlateFormatRegex(String licensePlate) {
		//Chequea por el formato de patente 'AA000AA'
		if(licensePlate.length() == 7) {			
			Pattern pattern = Pattern.compile("[a-zA-Z]{2}[0-9]{3}[a-zA-Z]{2}");
			Matcher matcher = pattern.matcher(licensePlate);
			boolean matchFound = matcher.find();
			if(matchFound) {
				System.out.println("Formato Mercosur valido");
				return true;
			}
		}
	    return false;
	}
}

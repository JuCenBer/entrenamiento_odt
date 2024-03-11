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
	
	//ALGORITMO PARA VERIFICAR FORMATO DE PATENTE (NO TESTEADO)
	
//	public boolean checkLicensePlateFormat(String licensePlate) {
//		return (isArgentineLicensePlateFormat(licensePlate) || isMercosurLicensePlateFormat(licensePlate));
//	}
//	
//	private boolean isArgentineLicensePlateFormat(String licensePlate) {
//		//Chequea por el formato de patente 'AAA999'
//		if(licensePlate.length() == 6) {
//			char licensePlateArray[] = licensePlate.toCharArray();
//			for (int i = 0; i < 3; i++) {
//				if (!Character.isLetter(licensePlateArray[i])) {
//					return false;
//				}
//			}
//			for (int i = 3; i < (licensePlateArray.length); i++) {
//				if(!Character.isDigit(licensePlateArray[i])) {
//					return false;
//				}
//			}
//			return true;			
//		}
//		else return false;
//	}
//	
//	private boolean isMercosurLicensePlateFormat(String licensePlate) {
//		//Chequea por el formato de patente 'AA000AA'
//		if(licensePlate.length() == 7) {
//			char licensePlateArray[] = licensePlate.toCharArray();
//			for (int i = 0; i < 2; i++) {
//				if (!(Character.isLetter(licensePlateArray[i]) ||
//					  Character.isLetter(licensePlateArray[(licensePlateArray.length-1)-i]))){
//					return false;
//				}
//			}
//			for (int i = 2; i < 5; i++) {
//				if (!Character.isDigit(licensePlateArray[i])) {
//					return false;
//				}
//			}
//			return true;			
//		}
//		else return false;
//	}
	
}

package cespi.induccion.estacionamiento.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VehiculoService {

//	@Autowired
//	VehiculoRepository vehiculoRepository;
	private String localPlateFormatRegex;
	private String mercosurPlateFormatRegex;
	
	
	public VehiculoService() {
		Properties properties = new Properties();
		ClassLoader classLoader = getClass().getClassLoader();
		Path path = null;
		String localPlateFormat = null;
		String mercosurPlateFormat = null;
		URL resourceUrl = classLoader.getResource("instanceParameters.properties");
		try {
			path = Paths.get(resourceUrl.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}	
		try (InputStream input = Files.newInputStream(path)) {
            properties.load(input);
            localPlateFormat = properties.getProperty("localPlateFormat");
            mercosurPlateFormat = properties.getProperty("mercosurPlateFormat");
        } catch (IOException ex) {
            System.err.println("Error al cargar el archivo de propiedades: " + ex.getMessage());
            ex.printStackTrace();
        }
		this.localPlateFormatRegex = this.obtenerRegexDesdeFormato(localPlateFormat);
		this.mercosurPlateFormatRegex = this.obtenerRegexDesdeFormato(mercosurPlateFormat);
	}
	
	
	public boolean checkLicensePlateFormatRegex(String licensePlate) {
		return (isLocalLicensePlateFormatRegex(licensePlate) || isMercosurLicensePlateFormatRegex(licensePlate));
	}
	
	private boolean isLocalLicensePlateFormatRegex(String licensePlate) {
		//Chequea por el formato de patente 'AAA999'
		if(licensePlate.length() == 6) {			
			Pattern pattern = Pattern.compile(this.localPlateFormatRegex);
			Matcher matcher = pattern.matcher(licensePlate);
			boolean matchFound = matcher.find();
			if(matchFound) {
				System.out.println("Formato local valido");
				return true;
			}
		}
		return false;
	}
	
	private boolean isMercosurLicensePlateFormatRegex(String licensePlate) {
		//Chequea por el formato de patente 'AA000AA'
		if(licensePlate.length() == 7) {			
			Pattern pattern = Pattern.compile(this.mercosurPlateFormatRegex);
			Matcher matcher = pattern.matcher(licensePlate);
			boolean matchFound = matcher.find();
			if(matchFound) {
				System.out.println("Formato Mercosur valido");
				return true;
			}
		}
	    return false;
	}
	
	private String obtenerRegexDesdeFormato(String formato) {
        return formato.replaceAll("L", "[A-Za-z]") // Letras (mayusculas o minusculas)
                .replaceAll("N", "[0-9]");    // Numeros
	}
}

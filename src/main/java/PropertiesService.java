import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesService {

	private static final String PROPERTIES_FILE_PATH = "src/main/resources/application.properties";

	public static String getProperty(String key) {
		String localFolderPath = "";
		
		try (InputStream output = new FileInputStream(PROPERTIES_FILE_PATH)) {
			Properties prop = new Properties();
			prop.load(output);
			localFolderPath = prop.getProperty(key);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return localFolderPath;
	}
}

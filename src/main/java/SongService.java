import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SongService {

	private static final String MP3 = ".mp3";
	String songsFilePath = PropertiesService.getProperty(PropertiesService.REPOSITORY_FILE_PATH);

	public List<String> getSongsNames(String path) {
		File songsFolder = new File(path);
		List<String> songsNames = new ArrayList<>();
		
		for (File file : songsFolder.listFiles()) {
			if (file.isDirectory()) {
				songsNames.addAll(getSongsNames(file.getPath()));
			}
			
			if(file.getName().endsWith(MP3)) {
				songsNames.add(file.getName());
			}
		}
		
		return songsNames;
	}
	
	public void appendContentToFile(String textToAppend) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(songsFilePath, true));	
		writer.write(textToAppend);
		writer.close();
		System.out.println("Musicas adicionadas: " + textToAppend);
	}

	public String getFileContent() throws IOException {
		File file = new File(songsFilePath);
		
		if (!file.exists()) {
			return "";
		}
		
		return new String(Files.readAllBytes(Paths.get(songsFilePath)));
	}
}

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SongService {

	private static final String MP3 = ".mp3";
	private static final String SONGS_FILE_PATH = "src\\main\\resources\\songs.csv";

	public List<String> getAllSongsNames(String path) {
		File songsFolder = new File(path);
		List<String> songsNames = new ArrayList<>();
		
		for (File file : songsFolder.listFiles()) {
			if (file.isDirectory()) {
				songsNames.addAll(getAllSongsNames(file.getPath()));
			}
			
			if(file.getName().endsWith(MP3)) {
				songsNames.add(file.getName());
			}
		}
		
		return songsNames;
	}
	
	public void appendContentToFile(String textToAppend) throws IOException {
		BufferedWriter writer = new BufferedWriter(
									new FileWriter(SONGS_FILE_PATH, true));	
		writer.write(textToAppend);
		writer.close();
	}

	public String getFileContent() throws IOException {
		File file = new File(SONGS_FILE_PATH);
		
		if (!file.exists()) {
			return "";
		}
		
		return new String(Files.readAllBytes(Paths.get(SONGS_FILE_PATH)));
	}
}

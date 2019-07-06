import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SongReader {

	public List<String> getAllSongsNames(String path) {
		File songsFolder = new File(path);
		List<String> songsNames = new ArrayList<>();
		
		for (File file : songsFolder.listFiles()) {
			if (file.isDirectory()) {
				songsNames.addAll(getAllSongsNames(file.getPath()));
			}
			
			if(file.getName().endsWith(".mp3")) {
				songsNames.add(file.getName());
			}
		}
		
		return songsNames;
	}
}

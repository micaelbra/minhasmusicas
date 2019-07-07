import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongsComparator {

	private static final String LOCAL_FOLDER_PATH = "C:\\Users\\Usuário\\Music";
	private static final String SEPARATOR = ";";
	private static SongService service = new SongService();
	
	public static void main(String[] args) {
		List<String> localSongs = service.getAllSongsNames(LOCAL_FOLDER_PATH);
		List<String> repositorySongs = getRepositorySongs(); 

		List<String> diffSongsRepository = getDiffSongs(localSongs, repositorySongs);
		List<String> diffSongsLocal = getDiffSongs(repositorySongs, localSongs);
		
		System.out.println("MUSICAS QUE VOCE NÃO TEM LOCALMENTE");
		System.out.println();
		
		for (String song : diffSongsLocal) {
			System.out.println(song);
		}
		
		System.out.println("\n-------------------------\n");
		
		System.out.println("MUSICAS QUE O REPOSITORIO NÃO TEM");
		System.out.println();
		
		for (String song : diffSongsRepository) {
			System.out.println(song);
		}

		addSongsToRepositoryFile(diffSongsRepository);
	}

	private static void addSongsToRepositoryFile(List<String> diffSongsRepository) {
		if (diffSongsRepository.size() > 0) {
			String formatedSongsNames = "";
			
			for (String song : diffSongsRepository) {
				formatedSongsNames += song + SEPARATOR;
			}
			
			try {
				service.appendContentToFile(formatedSongsNames);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static List<String> getDiffSongs(List<String> songList1, List<String> songList2) {
		List<String> diffSongs = new ArrayList<>();
		
		for (String song1 : songList1) {
			String songTemp = "";
			
			for (String song2 : songList2) {
				if (song1.equals(song2)) {
					songTemp = song1;
				}
			}
			
			if (songTemp.isEmpty()) {
				diffSongs.add(song1);
			}
		}
		return diffSongs;
	}

	private static List<String> getRepositorySongs() {
		List songsList = new ArrayList<>();

		try {
			String repositorySongs = service.getFileContent();
			String[] splittedSongs = repositorySongs.split(SEPARATOR);
			
			if (splittedSongs.length > 0) {
				songsList = Arrays.asList(splittedSongs);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return songsList;
	}

}

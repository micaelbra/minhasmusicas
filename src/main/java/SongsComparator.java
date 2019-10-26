import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SongsComparator {

	private static final int SIM = 1;
	private static final String SEPARATOR = ";";
	private static SongService songService = new SongService();
	
	public static void main(String[] args) {
		List<String> localSongs = songService.getSongsNames(PropertiesService.getProperty("local.folder.path"));
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
			System.out.println("Deseja adicionar as musicas ao repositório?");
			System.out.println("1-Sim ou 2-Não");
			Scanner sc = new Scanner(System.in);
			int input = sc.nextInt();
			
			if (input == SIM) {
				String formatedSongsNames = "";
				
				for (String song : diffSongsRepository) {
					formatedSongsNames += song + SEPARATOR;
				}
				
				try {
					songService.appendContentToFile(formatedSongsNames);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
		try {
			String repositorySongs = songService.getFileContent();
			String[] splittedSongs = repositorySongs.split(SEPARATOR);
			
			if (splittedSongs.length > 0) {
				return asList(splittedSongs);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<>();
	}

}

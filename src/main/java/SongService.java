import static java.util.Arrays.asList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SongService {

	private static final String MP3 = ".mp3";
	private static final int SIM = 1;
	public static final String SEPARATOR = ";";
	
	private String songsFilePath = PropertiesService.getProperty(PropertiesService.REPOSITORY_FILE_PATH);
	
	public List<String> getRepositorySongs() {
		try {
			String repositorySongs = getFileContent();
			String[] splittedSongs = repositorySongs.split(SEPARATOR);
			
			if (splittedSongs.length > 0) {
				return asList(splittedSongs);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<>();
	}

	public List<String> getLocalSongsNames(String path) {
		File songsFolder = new File(path);
		List<String> songsNames = new ArrayList<>();
		
		for (File file : songsFolder.listFiles()) {
			if (file.isDirectory()) {
				songsNames.addAll(getLocalSongsNames(file.getPath()));
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
	
	public List<String> getDiffSongs(List<String> songList1, List<String> songList2) {
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
	
	@SuppressWarnings("resource")
	public void addSongsToRepositoryFile(List<String> diffSongsRepository) {
		if (diffSongsRepository.size() > 0) {
			System.out.println("Deseja adicionar as musicas ao repositório?");
			System.out.println("1-Sim ou 2-Não");
			Scanner sc = new Scanner(System.in);
			int input = sc.nextInt();
			
			if (input == SIM) {
				String formatedSongsNames = "";
				
				for (String song : diffSongsRepository) {
					formatedSongsNames += song + SongService.SEPARATOR;
				}
				
				try {
					appendContentToFile(formatedSongsNames);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<String> getDiffRepositorySongs() {
		List<String> localSongs = getLocalSongsNames(PropertiesService.getProperty("local.folder.path"));
		List<String> repositorySongs = getRepositorySongs(); 

		return getDiffSongs(localSongs, repositorySongs);
	}

	public List<String> getDiffLocalSongs() {
		List<String> localSongs = getLocalSongsNames(PropertiesService.getProperty("local.folder.path"));
		List<String> repositorySongs = getRepositorySongs(); 

		return getDiffSongs(repositorySongs, localSongs);
	}
}

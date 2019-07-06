import java.util.ArrayList;
import java.util.List;

public class Run {

	private static SongReader reader = new SongReader();
	
	public static void main(String[] args) {
		List<String> repositorySongs = reader.getAllSongsNames("C:\\Users\\Usuário\\Music");
		List<String> localSongs = getSongs2(repositorySongs);

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

	private static List<String> getSongs2(List<String> songs) {
		List songs2 = new ArrayList<>();
		songs2.addAll(songs);
	
		for (int i = 0; i < 4; i++) {
			songs2.add("Nome de musica_" + i + ".mp3");
		}
		
		return songs2;
	}

}

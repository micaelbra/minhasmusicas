import java.util.List;

public class SongsComparator {

	private static SongService songService = new SongService();
	
	public static void main(String[] args) {
		List<String> diffSongsRepository = songService.getDiffRepositorySongs();
		List<String> diffSongsLocal = songService.getDiffLocalSongs();
		
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
		
		songService.addSongsToRepositoryFile(diffSongsRepository);
	}
}

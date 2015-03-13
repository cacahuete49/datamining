/**
 * 
 */
package myPackage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author quentin
 *
 */
public class Parser {
	
	public static String LISTE_BASE="/Data/Scenarii/Liste Bases/";
	public static String LISTE_ENTREPRISE="/Data/Scenarii/Liste Entreprises/";

	private static Parser instance = null;
	
	public static Parser getInstance(){
		if ( instance ==  null)
			instance = new Parser();
		
		return instance;
	}
	/**
	 * Avec un nom de fichier valorise une base
	 * 
	 * @param nameFile
	 * @return base
	 * @throws IOException
	 */
	
	public Base parseBase(String nameFile) throws IOException{
		String chemin = System.getProperty("user.dir");
		Path file = FileSystems.getDefault().getPath(chemin + "/Data/Bases/"+nameFile);
		List<String> contenu = Files.readAllLines(file, StandardCharsets.UTF_8);
		Base base = new Base( Integer.parseInt(contenu.get(0)),Integer.parseInt(nameFile.replaceAll("\\D+","")),contenu.subList(2,contenu.size()));
		return base;
	}
	
	/**
	 * A partir d'un nom de fichier return une liste d'entreprise
	 * @param nameFile
	 * @param type
	 * @return
	 * @throws IOException
	 */
	
	public  List<String> parseListe( String nameFile , String type ) throws IOException {
		String chemin = System.getProperty("user.dir");
		Path file = FileSystems.getDefault().getPath(chemin +type+nameFile);
		List<String> contenu = Files.readAllLines(file, StandardCharsets.UTF_8);
		return contenu.subList(1, contenu.size());
	}
	
	/**
	 * Génère la liste des bases
	 * @param nameFile
	 * @return
	 * @throws IOException
	 */
	public  List<Base> parseListeBase( String nameFile) throws IOException {
//		Lecture liste base
		String chemin = System.getProperty("user.dir");
		Path file = FileSystems.getDefault().getPath(chemin +LISTE_BASE+nameFile);
		List<String> contenu = Files.readAllLines(file, StandardCharsets.UTF_8);
		contenu.remove(0);
		
//		Création et ajout des bases
		List<Base> listeBase = new ArrayList<Base>();
		for (String s : contenu) {
			listeBase.add(parseBase("Bases-"+s));
		}
		
		return listeBase;
	}
}

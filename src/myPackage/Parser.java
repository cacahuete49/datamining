/**
 * 
 */
package myPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author quentin
 *
 */
public class Parser {

    public static String LISTE_BASE = "/Data/Scenarii/Liste_Bases/";
    public static String LISTE_ENTREPRISE = "/Data/Scenarii/Liste_Entreprises/";
    public static String BASE = "/Data/Bases/";
    private static Parser instance = null;

    public static Parser getInstance() {
	if (instance == null)
	    instance = new Parser();

	return instance;
    }

    private Parser() {
    }

    /**
     * Avec un nom de fichier valorise une base
     * 
     * @param nameFile
     * @return base
     * @throws IOException
     * @throws URISyntaxException
     */

    public Base parseBase(String nameFile) throws IOException,
	    URISyntaxException {
	// Path file = Paths.get(this.getClass().getResource("/Data/Bases/" +
	// nameFile).toURI());
	BufferedReader reader = new BufferedReader(new InputStreamReader(
		getClass().getResourceAsStream(BASE + nameFile)));
	List<String> contenu = new ArrayList<String>();
	String line;
	while ((line = reader.readLine()) != null) {
	    contenu.add(line);
	}

	Base base = new Base(Integer.parseInt(contenu.get(0)),
		Integer.parseInt(nameFile.replaceAll("\\D+", "")),
		contenu.subList(2, contenu.size()));
	return base;
    }

    /**
     * A partir d'un nom de fichier return une liste d'entreprise
     * 
     * @param nameFile
     * @param type
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */

    public List<String> parseListe(String nameFile, String type)
	    throws IOException, URISyntaxException {

	BufferedReader reader = new BufferedReader(new InputStreamReader(
		getClass().getResourceAsStream(LISTE_ENTREPRISE + nameFile)));
	List<String> contenu = new ArrayList<String>();
	String line;
	while ((line = reader.readLine()) != null) {
	    contenu.add(line);
	}
	return contenu.subList(1, contenu.size());
    }
	
    /**
     * Génère la liste des bases
     * 
     * @param nameFile
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public List<Base> parseListeBase(String nameFile) throws IOException,
	    URISyntaxException {
	// Lecture liste base
	BufferedReader reader = new BufferedReader(new InputStreamReader(
		getClass().getResourceAsStream(LISTE_BASE + nameFile)));
	List<String> contenu = new ArrayList<String>();
	String line;
	while ((line = reader.readLine()) != null) {
	    contenu.add(line);
	}
	contenu.remove(0);
	contenu.remove(contenu.size() - 1);

	// Création et ajout des bases
	List<Base> listeBase = new ArrayList<Base>();
	for (String s : contenu) {
	    listeBase.add(parseBase("Bases-" + s));
	}

	return listeBase;
    }

    public void buffer() {

	buffer("/Data/Bases/Bases-Base 1.txt");
	// buffer("/Data/Scenarii/Liste_Bases/Liste_Bases1.txt");
    }

    public void buffer(String name) {

	try {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
		    getClass().getResourceAsStream(name)));
	    String line;
	    while ((line = reader.readLine()) != null) {
	    	System.out.println(line);
	    }
	    System.out.println("Build :=> " + name);
		}
		catch (Exception e) {
			System.out.println("FAILED :=> " + name);

		}

    }
}    

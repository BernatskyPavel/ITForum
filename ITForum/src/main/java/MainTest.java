import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class MainTest {

    public static void main(String[] args) throws IOException {
	String text = "<p>Писюн. ****. Блядь!</p>\r\n";
	Set<String> censoredWords = new HashSet<String>();
	String[] localcensoredWords = new String[0];
	FileReader input;
	try {
	    input = new FileReader("C:\\Users\\UnknowNPC\\Desktop\\censored.txt");
	    BufferedReader bufRead = new BufferedReader(input);
	    String myLine = null;

	    while ((myLine = bufRead.readLine()) != null) {
		localcensoredWords = myLine.split(",");
	    }
	    bufRead.close();
	} catch (IOException e) {
	    localcensoredWords = new String[0];
	    e.printStackTrace();
	}
	
	for (int i = 0; i < localcensoredWords.length; i++) {
	    censoredWords.add(localcensoredWords[i]);
	}
	
	String[] words = text.split("[\\p{Punct}\\s]+");
	for (int i = 0; i < words.length; i++) {
	    System.out.println(words[i]);
	    if (censoredWords.contains(words[i].toLowerCase())) {
		System.out.println('-'+words[i]);
		text = Pattern.compile("\\b(?i)"+words[i]+"\\b", 
			    Pattern.UNICODE_CASE).matcher(text).replaceAll("*".repeat(words[i].length()));	
	    }
	}
	System.out.println(text);
    }

}

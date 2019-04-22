package services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationService implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8940023800682087943L;
    private final String CENSOREDFILEPATH = "C:\\Users\\UnknowNPC\\Desktop\\censored.txt";
    private final String[] EMPTY_ARRAY = new String[0];
    private String[] censoredWords;
    private List<String> censoredList;

    @PostConstruct
    public void init() {
	this.censoredWords = this.EMPTY_ARRAY;
	try {
	    BufferedReader bufRead = new BufferedReader(
			   new InputStreamReader(
		                      new FileInputStream(this.CENSOREDFILEPATH), "UTF8"));
	    String myLine = null;	    
	    
	    while ((myLine = bufRead.readLine()) != null) {
		this.censoredWords = myLine.split(",");
		this.censoredList = new ArrayList<String>(Arrays.asList(this.censoredWords));
	    }
	    bufRead.close();
	} catch (IOException e) {
	    this.censoredWords = this.EMPTY_ARRAY;
	    e.printStackTrace();
	}
	
    }

    public String censoredText(String text) {
	String[] words = text.split("[\\p{Punct}\\s]+");
	for (int i = 0; i < words.length; i++) {
	    System.out.println('|'+words[i]+'|');
	    if (this.censoredList.contains(words[i].toLowerCase()) || this.censoredList.contains(words[i])) {
		System.out.println('['+words[i]+']');
		text = Pattern.compile("\\b(?i)"+words[i]+"\\b", 
			    Pattern.UNICODE_CASE).matcher(text).replaceAll("*".repeat(words[i].length()));	
	    }
	}
	return text;
    }

}

package services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.omnifaces.util.Faces;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import model.Category;
import model.Thread;

@ApplicationScoped
public class ApplicationService implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8940023800682087943L;
    private final String CENSOREDFILEPATH = Faces.getRealPath("/") + "/resources/Censored.txt";
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

    
    public MenuModel getBreadcrumbPath(Category category) {
	MenuModel result = new DefaultMenuModel();
	Stack<DefaultMenuItem> tempstack = new Stack<DefaultMenuItem>();
	DefaultMenuItem item;
	Category testcat = category;
	if (null != testcat) {
	    item = new DefaultMenuItem();
	    item.setUrl(Faces.getRequestBaseURL()
		    + "v2/category.xhtml?id="
		    + testcat.getId().intValue());
	    item.setValue(testcat.getName());
	    item.setTitle(testcat.getName());
	    tempstack.push(item);
	    while (testcat.getCategory() != null) {
		testcat = testcat.getCategory();
		item = new DefaultMenuItem();
		item.setUrl(Faces.getRequestBaseURL()
			+ "v2/category.xhtml?id="
			+ testcat.getId().intValue());
		item.setValue(testcat.getName().length() > 20
			? testcat.getName().substring(0, 20) + "..."
			: testcat.getName());
		item.setTitle(testcat.getName());
		tempstack.push(item);
	    }
	    item = new DefaultMenuItem();
	    item.setUrl(Faces.getRequestBaseURL() + "v2/");
	    item.setValue("Главная");
	    item.setTitle("Главная");
	    result.addElement(item);
	    while (!tempstack.isEmpty())
		result.addElement(tempstack.pop());

	}
	return result;
    }

    public MenuModel getBreadcrumbPath(Thread thread) {
	MenuModel result = new DefaultMenuModel();
	Stack<DefaultMenuItem> tempstack = new Stack<DefaultMenuItem>();
	DefaultMenuItem item;
	item = new DefaultMenuItem();
	item.setUrl(Faces.getRequestBaseURL() + "v2/thread.xhtml?id="
		+ thread.getId().intValue());
	item.setValue(thread.getSubject().length() > 20
		? thread.getSubject().substring(0, 20) + "..."
		: thread.getSubject());
	item.setTitle(thread.getSubject());
	tempstack.push(item);
	Category testcat = thread.getCategoryBean();
	if (null != testcat) {
	    item = new DefaultMenuItem();
	    item.setUrl(Faces.getRequestBaseURL()
		    + "v2/category.xhtml?id="
		    + testcat.getId().intValue());
	    item.setValue(testcat.getName().length() > 20
		    ? testcat.getName().substring(0, 20) + "..."
		    : testcat.getName());
	    item.setTitle(testcat.getName());
	    tempstack.push(item);
	    while (testcat.getCategory() != null) {
		testcat = testcat.getCategory();
		item = new DefaultMenuItem();
		item.setUrl(Faces.getRequestBaseURL()
			+ "v2/category.xhtml?id="
			+ testcat.getId().intValue());
		item.setValue(testcat.getName().length() > 20
			? testcat.getName().substring(0, 20) + "..."
			: testcat.getName());
		item.setTitle(testcat.getName());
		tempstack.push(item);
	    }
	    item = new DefaultMenuItem();
	    item.setUrl(Faces.getRequestBaseURL() + "v2/");
	    item.setValue("Главная");
	    item.setTitle("Главная");
	    result.addElement(item);
	    while (!tempstack.isEmpty())
		result.addElement(tempstack.pop());

	}
	return result;
    }
}

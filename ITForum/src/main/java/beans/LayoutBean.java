package beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

@Named
@ApplicationScoped
public class LayoutBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 748270878507244119L;

    public void viewLoginForm() {
	Map<String, Object> options = new HashMap<String, Object>();
	options.put("modal", true);
	options.put("width", 560);
	options.put("height", 220);
	options.put("contentWidth", "100%");
	options.put("contentHeight", "100%");

	PrimeFaces.current().dialog().openDynamic("login", options,
		null);
    }
    
}

package services;

import javax.faces.application.FacesMessage;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.primefaces.PrimeFaces;

@Path("/code")
public class CodeService {
    @POST
    public void RunCode(String code) {
	PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage("Code", "Code completed!"));
    }
}

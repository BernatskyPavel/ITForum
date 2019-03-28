package beans;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Section;
import services.SectionService;
import services.StatusService;

@Named
@ViewScoped
public class HomeBean implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -3090403837405679066L;
    private String hello = "Hello World!";
    private List<Section> sections;
    @Inject
    private SectionService section_service;
    @PostConstruct
    public void init() {
        sections = section_service.getAll();
    }
    
    public String getURLWithContextPath() {
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
    
    public List<Section> getActiveSections() {
	return sections.stream().filter(x->StatusService.Statuses.APPROVED.getId() == x.getStatusBean().getId().intValue()).collect(Collectors.toList());
    }
    
    /**
     * @return the hello
     */
    public String getHello() {
	return hello;
    }

    /**
     * @param hello the hello to set
     */
    public void setHello(String hello) {
	this.hello = hello;
    }

    /**
     * @return the sections
     */
    public List<Section> getSections() {
        return sections;
    }

    /**
     * @param sections the sections to set
     */
    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
    
}

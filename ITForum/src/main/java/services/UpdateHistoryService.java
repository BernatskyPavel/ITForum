package services;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UpdateHistoryService {
    private static final String STARTHASH = "B078FFD28DB767C502AC367053F6E0AC";
    private String lastGlobalThreadUpdateHash;
    private String lastGlobalHomeUpdateHash;
    private String lastGlobalCategoryUpdateHash;
    
    public void init() {
	setLastGlobalThreadUpdateHash(STARTHASH);
	setLastGlobalHomeUpdateHash(STARTHASH);
	setLastGlobalCategoryUpdateHash(STARTHASH);
    }

    

    /**
     * @return the starthash
     */
    public static String getStarthash() {
	return STARTHASH;
    }



    /**
     * @return the lastGlobalThreadUpdateHash
     */
    public String getLastGlobalThreadUpdateHash() {
	return lastGlobalThreadUpdateHash;
    }



    /**
     * @param lastGlobalThreadUpdateHash the lastGlobalThreadUpdateHash to set
     */
    public void setLastGlobalThreadUpdateHash(
	    String lastGlobalThreadUpdateHash) {
	this.lastGlobalThreadUpdateHash = lastGlobalThreadUpdateHash;
    }



    /**
     * @return the lastGlobalHomeUpdateHash
     */
    public String getLastGlobalHomeUpdateHash() {
	return lastGlobalHomeUpdateHash;
    }



    /**
     * @param lastGlobalHomeUpdateHash the lastGlobalHomeUpdateHash to set
     */
    public void setLastGlobalHomeUpdateHash(
	    String lastGlobalHomeUpdateHash) {
	this.lastGlobalHomeUpdateHash = lastGlobalHomeUpdateHash;
    }



    /**
     * @return the lastGlobalCategoryUpdateHash
     */
    public String getLastGlobalCategoryUpdateHash() {
	return lastGlobalCategoryUpdateHash;
    }



    /**
     * @param lastGlobalCategoryUpdateHash the lastGlobalCategoryUpdateHash to set
     */
    public void setLastGlobalCategoryUpdateHash(
	    String lastGlobalCategoryUpdateHash) {
	this.lastGlobalCategoryUpdateHash = lastGlobalCategoryUpdateHash;
    }
}

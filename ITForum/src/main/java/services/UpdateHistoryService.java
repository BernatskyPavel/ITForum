package services;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

@ApplicationScoped
//@Singleton
public class UpdateHistoryService {
    private static final String STARTHASH = "B078FFD28DB767C502AC367053F6E0AC";
    private String lastGlobalThreadUpdateHash;
    private String lastGlobalHomeUpdateHash;
    private String lastGlobalCategoryUpdateHash;
    
    private Int2ObjectMap<String> CFTHashes;
    private Int2ObjectMap<String> UFEHashes;
    private Int2ObjectMap<String> UFLHashes;
    
    @PostConstruct
    public void init() {
	setLastGlobalThreadUpdateHash(STARTHASH);
	setLastGlobalHomeUpdateHash(STARTHASH);
	setLastGlobalCategoryUpdateHash(STARTHASH);
	this.CFTHashes = new Int2ObjectArrayMap<String>();
	this.UFEHashes = new Int2ObjectArrayMap<String>();
	this.UFLHashes = new Int2ObjectArrayMap<String>();
    }

    public void setCFTHash(int id, String value) {
	if(this.CFTHashes.containsKey(id)) {
	    this.CFTHashes.replace(id, value);
	}else{
	    this.CFTHashes.put(id, value);
	}
    }

    public String getCFTHash(int id) {
	return this.CFTHashes.getOrDefault(id, STARTHASH);
    }
    
    public void setUFEHash(int id, String value) {
	if(this.UFEHashes.containsKey(id)) {
	    this.UFEHashes.replace(id, value);
	}else{
	    this.UFEHashes.put(id, value);
	}
    }

    public String getUFEHash(int id) {
	return this.UFEHashes.getOrDefault(id, STARTHASH);
    }
    
    public void setUFLHash(int id, String value) {
	if(this.UFLHashes.containsKey(id)) {
	    this.UFLHashes.replace(id, value);
	}else{
	    this.UFLHashes.put(id, value);
	}
    }

    public String getUFLHash(int id) {
	return this.UFLHashes.getOrDefault(id, STARTHASH);
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

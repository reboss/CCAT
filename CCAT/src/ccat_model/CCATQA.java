
package ccat_model;

/**
 *
 * @author John
 */
public abstract class CCATQA {
    
    private String objectText;
    private final Integer objectId;
    private final Integer parentId;
    
    public CCATQA(String objectText, Integer objectId, Integer parentId){
        
        this.objectId = objectId;
        this.objectText = objectText;
        this.parentId = parentId;
        
    }
    
    public String getText(){
        return objectText;
    }
    
    public Integer getId(){
        return objectId;
    }
    
    public Integer getParentId(){
        return parentId;
    }
    
    public void setText(String text){
        objectText = text;
    }
}

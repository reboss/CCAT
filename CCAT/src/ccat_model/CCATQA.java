/**
 * Copyright (C) John Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_model;

/**
 *
 * @author John
 */
public abstract class CCATQA {

    private String objectText;
    private final Integer objectId;
    private final Integer parentId;

    /**
     *
     * @param objectText
     * @param objectId
     * @param parentId
     */
    public CCATQA(String objectText, Integer objectId, Integer parentId) {

        this.objectId = objectId;
        this.objectText = objectText;
        this.parentId = parentId;

    }

    /**
     *
     * @return
     */
    public String getText() {
        return objectText;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return objectId;
    }

    /**
     *
     * @return
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     *
     * @param text
     */
    public void setText(String text) {
        objectText = text;
    }
    
    public String getType(){
        return null;
    }
}

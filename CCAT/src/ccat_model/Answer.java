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
public class Answer extends CCATQA {

    private String answer;

    /**
     * 
     * @param objectText
     * @param objectId
     * @param parentId 
     */
    public Answer(String objectText, Integer objectId, Integer parentId) {
        super(objectText, objectId, parentId);
        answer = "";
    }

    /**
     * 
     * @param answer 
     */
    public void setSelectedToggleValue(String answer) {
        this.answer = answer;
    }

    /**
     * 
     * @return 
     */
    public String getSelectedToggle() {
        return answer;
    }

}

/**
 * Copyright (C) John Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author John
 */
public class Header extends CCATQA {

    private final List<Question> questions;

    public Header(String objectText, Integer objectId, Integer parentId) {
        super(objectText, objectId, parentId);
        questions = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public List<Question> getChildren() {
        return Collections.unmodifiableList(questions);
    }

    /**
     *
     * @param question
     */
    public void addChild(Question question) {
        questions.add(question);
    }

}

/**
 * Copyright (C) John Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_view.MainMenu;

/**
 *
 * @author Elliott
 */
public class UserInfo {

    private String UName;
    private String UType;

    /**
     *
     */
    public UserInfo() {
    }

    /**
     *
     * @param UName
     * @param UType
     */
    public UserInfo(String UName, String UType) {
        this.UName = UName;
        this.UType = UType;
    }

    /**
     *
     * @return
     */
    public String getUName() {
        return UName;
    }

    /**
     *
     * @return
     */
    public String getUType() {
        return UType;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccat_view.MainMenu;

/**
 *
 * @author Elliott
 */
public class UserInfo {
    
    private String UName;
    private String UType;

    public UserInfo() {
    }

    public UserInfo(String UName, String UType) {
        this.UName = UName;
        this.UType = UType;
    }

    public String getUName() {
        return UName;
    }

    public String getUType() {
        return UType;
    }
    
}

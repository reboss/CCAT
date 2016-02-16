/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccat_model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.security.MessageDigest;

/**
 *
 * @author JRebo_000
 */
public class Account {

    private final File accounts;
    private String uname;
    private String pass;
    private final String salt;

    public Account() throws FileNotFoundException, IOException {
        this.salt = "M5@aG9:[2cY0";
        this.accounts = new File("accounts.txt");
    }

    public boolean validate(String uname, String pass) throws FileNotFoundException {
        FileReader reader = new FileReader(accounts);
        Scanner scanner = new Scanner(reader);
        String[] validator;
        // MD5 of password + salt to compare to
        String MD5 = MD5(pass + this.salt);
        while (scanner.hasNext()) {
            validator = scanner.nextLine().split(" ");
            if (uname.equals(validator[0]) && MD5.equals(validator[1])) {
                return true;
            }
        }
        return false;
    }

    public void create(String uname, String passwd) {
        this.uname = uname;
        this.pass = passwd;

        // MD5 hash + salt to passwd
        String MD5 = MD5(passwd + this.salt);
        try {
            String filename = "accounts.txt";
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            fw.write(uname + " " + passwd + "\n"); //appends the string to the file
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    private String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(s.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

/**
 *
 * @author Moham
 */
public class Validator {
    private final String userRegex = "^[A-Za-z][A-Za-z0-9_]{7,29}$" ;
    private final String passRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{5,20}$";
       public boolean validatePassword(String password, String confirmedPassword) {
        return password.matches(passRegex) && !password.isEmpty();
    }
    public  boolean validateUserName(String userName) {
        return !userName.isEmpty() && userName.matches(userRegex);
       
    }
    public  boolean matchpass(String password, String confirmedPassword) {
        return password.equals(confirmedPassword) ;
       
    }

 
}

package Main.controller.UserAdmin;

import Main.entity.User_Admin;
import java.util.ArrayList;

public class ViewUserController {
    User_Admin userAdmin = new User_Admin();

    /* Used for:
    * 1. editTableConstruction() - 2b method in UserAdminPageUI.java
    * 2. suspendTableConstruction() - 4b method in UserAdminPageUI.java
    */
    public String[][] getUserInfo(){
        return userAdmin.getUserInfoFromDB();
    }

    public String [][] searchByUsername(String usernameKeyedIn, String searchStatus){
        return userAdmin.searchAccount(usernameKeyedIn, searchStatus);
    }
}

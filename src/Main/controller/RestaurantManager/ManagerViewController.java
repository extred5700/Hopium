package Main.controller.RestaurantManager;

import Main.entity.Restaurant_Manager;

public class ManagerViewController {
    Restaurant_Manager restaurant_manager = new Restaurant_Manager();

    // Display all Menu Items
    public String [][] displayMenuItems(){
        return restaurant_manager.viewMenuItems();
    }

    // Display all Coupons
    public String [][] displayCoupons(){
        return restaurant_manager.viewCoupons();
    }
}

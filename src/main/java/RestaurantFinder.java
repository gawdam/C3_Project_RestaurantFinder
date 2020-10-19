import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RestaurantFinder {
    Calendar calendar;
    private List<Restaurant> restaurants = new ArrayList<Restaurant>();
    
    public void searchRestaurantByName(String restaurantName) throws restaurantNotFoundException {
        Restaurant foundRestaurant = null;
        for (Restaurant restaurant : restaurants) {
            if(restaurant.getName().equals(restaurantName)) {
                foundRestaurant = restaurant;
            }
        }
        if(foundRestaurant == null){
            throw new restaurantNotFoundException(restaurantName);
        }
        if(!isRestaurantOpen(foundRestaurant)){
            System.out.println("Restaurant is closed");
        }
        else{
            foundRestaurant.getDetails();
        }
    }
    
    public void addRestaurant(String name, String location, int openingTime, int closingTime){
        Restaurant newRestaurant = new Restaurant(name,location,openingTime,closingTime);
        restaurants.add(newRestaurant);
    }
    public boolean isRestaurantOpen(Restaurant restaurant){
        if(calendar.HOUR_OF_DAY>restaurant.openingTime && calendar.HOUR_OF_DAY<restaurant.closingTime){
            return true;
        }
        return false;
    }
    
}

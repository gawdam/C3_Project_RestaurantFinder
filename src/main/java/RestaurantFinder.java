import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RestaurantFinder {
    private List<Restaurant> restaurants = new ArrayList<Restaurant>();

    public void searchForRestaurant(String name) throws restaurantNotFoundException {
        Restaurant foundRestaurant = findRestaurantByName(name);

        if(foundRestaurant == null){
            throw new restaurantNotFoundException(name);
        }
        else if(!isRestaurantOpen(foundRestaurant)){
            System.out.println("Restaurant is closed");
        }
        else{
            foundRestaurant.getDetails();
        }
    }

    public Restaurant findRestaurantByName(String restaurantName){
        for (Restaurant restaurant : restaurants) {
            if(restaurant.getName().equals(restaurantName)) {
                return restaurant;
            }
        }
        return null;
    }

    public void addRestaurant(String name, String location, int openingTime, int closingTime){
        Restaurant newRestaurant = new Restaurant(name,location,openingTime,closingTime);
        restaurants.add(newRestaurant);
    }

    public void removeRestaurant(String restaurantName) throws restaurantNotFoundException{
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        if(restaurantToBeRemoved == null)
            throw new restaurantNotFoundException(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
    }

    public boolean isRestaurantOpen(Restaurant restaurant){
        return Calendar.HOUR_OF_DAY > restaurant.openingTime && Calendar.HOUR_OF_DAY < restaurant.closingTime;
    }

}

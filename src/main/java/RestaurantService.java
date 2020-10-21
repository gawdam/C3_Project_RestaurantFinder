import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant searchForRestaurant(String name) throws restaurantNotFoundException {
        Restaurant foundRestaurant = findRestaurantByName(name);

        if (foundRestaurant == null) {
            throw new restaurantNotFoundException(name);
        }
        else {
            return foundRestaurant;
        }
    }

    public Restaurant findRestaurantByName(String restaurantName) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(restaurantName)) {
                return restaurant;
            }
        }
        return null;
    }

    public Restaurant addRestaurant(String name, String location, int openingTime, int closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        if (restaurantToBeRemoved == null)
            throw new restaurantNotFoundException(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public static boolean isRestaurantOpen(Restaurant restaurant, int hour) {
        return (hour >= restaurant.openingTime && hour <= restaurant.closingTime);
    }

    public int getCurrentHour(){
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
}

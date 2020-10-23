import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;


class RestaurantServiceTest {
    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    @Test
    @BeforeEach
    public void BeforeEach(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @Test
    public void searching_for_existing_restaurant_should_not_return_null() {
        Restaurant searchedRestaurant = service.findRestaurantByName("Amelie's cafe");
        assertNotNull(searchedRestaurant);
    }
    @Test
    public void searching_for_non_existing_restaurant_should_return_null() {
        Restaurant searchedRestaurant = service.findRestaurantByName("Pantry d'or");
        assertNull(searchedRestaurant);
    }

    @Test
    public void calculateOrderTotal_should_throw_exception_if_item_not_found(){
        assertThrows(itemNotFoundException.class,
                ()->restaurant.calculateOrderTotal("French fries","Vegetable lasagne"));
    }
    @Test
    public void calculateOrderTotal_should_return_388_when_lasagna_and_soup_are_ordered() throws itemNotFoundException {
    assertEquals(388,restaurant.calculateOrderTotal("Sweet corn soup","Vegetable lasagne"));
    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        assertTrue(restaurant.isRestaurantOpen());
    }
    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        Restaurant newRestaurant= new Restaurant("Saravana Bhavan","KK Nagar",openingTime,closingTime);

        Restaurant spiedRestaurant = Mockito.spy(newRestaurant);
        LocalTime midnight = LocalTime.parse("00:00:00");
        doReturn(midnight).when(spiedRestaurant).getCurrentTime();

        assertFalse(spiedRestaurant.isRestaurantOpen());
    }



    //------------------------------------------------------------------------------


    //ADMIN ROLE
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }
    
    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        Restaurant restaurant = service.findRestaurantByName("Amelie's cafe");
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //-------------------------------------------------------
}
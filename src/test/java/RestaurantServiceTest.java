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
    //-------------------------------------------------------
}
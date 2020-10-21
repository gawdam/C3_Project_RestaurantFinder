import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {
    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    @Test
    @BeforeEach
    public void BeforeEach(){
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",10,21);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @Test
    public void searching_for_existing_restaurant_should_not_return_null() throws restaurantNotFoundException {
        Restaurant searchedRestaurant = service.findRestaurantByName("Amelie's cafe");
        assertNotNull(searchedRestaurant);
    }
    @Test
    public void searching_for_non_existing_restaurant_should_return_null() throws restaurantNotFoundException {
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

        assertTrue(service.isRestaurantOpen(restaurant));
    }
    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant earlyClose = Mockito.mock(Restaurant.class);
        Mockito.when(earlyClose.getClosingTime()).thenReturn(12);
        Mockito.when(earlyClose.getOpeningTime()).thenReturn(10);
        assertFalse(service.isRestaurantOpen(earlyClose));
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
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = service.getRestaurants().size();
        Restaurant newRestaurant = service.addRestaurant("Pumpkin Tales","Chennai",12,22);
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
    public void removing_item_that_does_not_exist_should_throw_exception() throws itemNotFoundException {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //-------------------------------------------------------
}
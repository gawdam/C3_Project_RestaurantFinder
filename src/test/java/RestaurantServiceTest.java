import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {
    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    @Test
    public void BeforeEach(){
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",10,21);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    @Test
    public void searching_for_existing_restaurant_should_not_return_null() throws restaurantNotFoundException {
        Restaurant searchedRestaurant = service.searchForRestaurant("Amelie's cafe");
        assertNotNull(searchedRestaurant);
    }
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.searchForRestaurant("Pantry d'or"));
    }


    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        service.removeRestaurant("Amelie's cafe");
        assertEquals(0, service.getRestaurants().size());
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = service.getRestaurants().size();
        Restaurant newRestaurant = service.addRestaurant("Pumpkin Tales","Chennai",12,22);
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //----------------------------------------------------

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Mockito.when(service.getCurrentHour()).thenReturn((restaurant.closingTime+ restaurant.openingTime)/2);
        assertTrue(service.isRestaurantOpen(restaurant,service.getCurrentHour()));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Mockito.when(service.getCurrentHour()).thenReturn((restaurant.closingTime+1));
        assertFalse(service.isRestaurantOpen(restaurant, service.getCurrentHour()));
    }
    @Test
    public void getCurrentHour_should_return_the_current_hour(){
        assertEquals(15,service.getCurrentHour());
    }
    //----------------------------------------------------
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        Restaurant restaurant = service.findRestaurantByName("Amelie's cafe");
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Water bottle",189);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        Restaurant restaurant = service.findRestaurantByName("Amelie's cafe");
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(0,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() throws itemNotFoundException {
        Restaurant restaurant = service.findRestaurantByName("Amelie's cafe");
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //-------------------------------------------------------
}
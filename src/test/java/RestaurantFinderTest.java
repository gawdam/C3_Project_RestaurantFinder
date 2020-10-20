import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantFinderTest {
    RestaurantFinder service = new RestaurantFinder();

    @Test
    public void BeforeEach(){
        Restaurant restaurant = service.addRestaurant("Amelie's cafe","Chennai",10,21);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    @Test
    public void searching_for_existing_restaurant_should_not_return_null() throws restaurantNotFoundException {
        Restaurant restaurant = service.searchForRestaurant("Amelie's cafe");
        assertNotNull(restaurant);
    }
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.searchForRestaurant("Pantry d'or"));
    }
    @Test
    public void find_restaurant_by_name_should_return_null_if_restaurant_does_not_exist(){
        Restaurant restaurant = service.findRestaurantByName("Pantry d'or");
        assertNull(restaurant);
    }

    //----------------------------------------------------
    /*
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        Restaurant removedRestaurant = service.removeRestaurant("Amelie's cafe");
        assertEquals(0,service.getRestaurants().size());
    }
    */

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = service.getRestaurants().size();
        Restaurant newRestaurant = service.addRestaurant("Pumpkin Tales","Chennai",12,22);
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //----------------------------------------------------

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant restaurant = service.addRestaurant("Pumpkin Tales","Chennai", 12,22);
        assertTrue(service.isRestaurantOpen(restaurant));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant restaurant = service.addRestaurant("Pumpkin Tales","Chennai", 12,20);
        assertFalse(service.isRestaurantOpen(restaurant));
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
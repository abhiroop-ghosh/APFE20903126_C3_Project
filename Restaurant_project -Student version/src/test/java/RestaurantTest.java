import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    // This method executes before each test case and create a default restaurant
    public void initialize_restaurant_prior_to_each_test()
    {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    // This method returns the number of menu available in the restaurant
    public int getMenuSize()
    {
        return restaurant.getMenu().size();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        //Test 1 : To check if the restaurant is open at 1:00pm

        //Arrange
        Restaurant mockedRestaurant =  Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.of(13,00));

        //Act
        boolean isRestaurantOpen = mockedRestaurant.isRestaurantOpen();

        //Assert
        assertTrue(isRestaurantOpen);

        //Test 2 : To check if the restaurant is open at 10:31 am

        //Arrange
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.of(10,31));

        //Act
        isRestaurantOpen = mockedRestaurant.isRestaurantOpen();

        //Assert
        assertTrue(isRestaurantOpen);

        //Test 3 : To check if the restaurant is open at 21:59 pm

        //Arrange
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.of(21,59));

        //Act
        isRestaurantOpen = mockedRestaurant.isRestaurantOpen();

        //Assert
        assertTrue(isRestaurantOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        //Test 1: To test if the restaurant is open at 11:00pm

        //Arrange
        Restaurant mockedRestaurant =  Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.of(23,00));

        //Act
        boolean isRestaurantOpen = mockedRestaurant.isRestaurantOpen();

        //Assert
        assertFalse(isRestaurantOpen);

        //Test 2: To test if the restaurant is open at 10:00am

        //Arrange
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.of(10,00));

        //Act
        isRestaurantOpen = mockedRestaurant.isRestaurantOpen();

        //Assert
        assertFalse(isRestaurantOpen);

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = getMenuSize();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,getMenuSize());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = getMenuSize();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,getMenuSize());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>CALCULATING TOTAL PRICE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void when_no_item_is_selected_the_total_amount_should_be_0()
    {
        //Arrange
        List<String> items = getMenu(0);

        //Act
        int totalAmount = restaurant.getTotalAmount(items);

        //Assert
        assertEquals(0,totalAmount);
    }

    @Test
    public void when_a_single_item_is_selected_the_total_amount_should_be_the_price_of_the_item()
    {
        //Arrange
        List<String> items = getMenu(1);

        //Act
        int totalAmount = restaurant.getTotalAmount(items);

        //Assert
        assertEquals(119,totalAmount);
    }

    @Test
    public void when_multiple_items_are_selected_the_total_amount_should_be_total_of_all_items()
    {
        //Arrange
        List<String> items = getMenu(2);

        //Act
        int totalAmount = restaurant.getTotalAmount(items);

        //Assert
        assertEquals(388,totalAmount);
    }

    public List<String> getMenu(int numberOfItems) {
        ArrayList<String> menuItems = new ArrayList<String>();
        switch (numberOfItems) {
            case 1:
                menuItems.add("Sweet corn soup");
                return menuItems;
            case 2:
                menuItems.add("Sweet corn soup");
                menuItems.add("Vegetable lasagne");
                return menuItems;
            default:
                return menuItems;
        }
    }
    //<<<<<<<<<<<<<<<<<<<<CALCULATING TOTAL PRICE>>>>>>>>>>>>>>>>>>>>>>>>>>
}
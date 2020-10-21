import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    private int openingTime;
    private int closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, int openingTime, int closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public List<Item> getMenu() {

        return Collections
                .unmodifiableList(menu);
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }


    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void getDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public int calculateOrderTotal(String...itemNames) throws itemNotFoundException {
        int total = 0;
        for(String itemName: itemNames){
            Item item = findItemByName(itemName);
            if(item == null)
                throw new itemNotFoundException(itemName);
            total = total + item.getPrice();
        }
        return total;
    }

    public String getName() {
        return name;
    }
    public int getOpeningTime(){
        return openingTime;
    }
    public int getClosingTime(){
        return closingTime;
    }
}

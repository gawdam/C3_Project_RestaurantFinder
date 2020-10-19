public class Item {
    private String name;
    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getname() {
        return name;
    }
    @Override
    public String toString(){
        return "{" +
                "item=" + name +
                ", price='" + price +
                '}';
    }
}

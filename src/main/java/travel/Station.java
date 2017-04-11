package travel;

public class Station extends Place {

    private int uid;
    private String name;
    private String address;
    private int number;
    private int bikes;
    private int bike_racks;
    private int free_racks;
    private boolean maintenance;
    private String bike_numbers;

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getNumber() {
        return number;
    }

    public int getBikes() {
        return bikes;
    }

    public int getBike_racks() {
        return bike_racks;
    }

    public int getFree_racks() {
        return free_racks;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public String getBike_numbers() {
        return bike_numbers; //TODO zwracać tablicę intów
    }
    //TODO biketypes

}

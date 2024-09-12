package coms309.snacks;

/**
 * Provides the Definition/Structure for the bakery row
 *
 * @author Vivek Bengre
 */

public class Fromagerie {

    private String name;

    private String location;

    private String[] inventory;

    private String telephone;

    private String[] winePairings;

    public Fromagerie() {

    }

    public Fromagerie(String name, String location, String[] inventory, String telephone, String[] winePairings) {
        this.name = name;
        this.location = location;
        this.inventory = inventory;
        this.telephone = telephone;
        this.winePairings = winePairings;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getInventory() {
        return this.inventory;
    }

    public void setInventory(String[] inventory) {
        this.inventory = inventory;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String[] getWinePairings() {
        return this.winePairings;
    }

    public void setWinePairings(String[] winePairings) {
        this.winePairings = winePairings;
    }

    @Override
    public String toString() {
        return name + " "
                + location + " "
                + inventory + " "
                + telephone + " " + winePairings;
    }
}

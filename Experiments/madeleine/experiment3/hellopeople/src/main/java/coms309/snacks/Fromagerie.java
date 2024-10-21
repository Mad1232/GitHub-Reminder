package coms309.snacks;

/**
 * Provides the Definition/Structure for the Fromagerie row
 *
 * @author Madeleine Carydis
 */

public class Fromagerie extends SnackPlace {

    private String[] winePairings;

    public Fromagerie() {
    }

    public Fromagerie(String name, String location, String[] inventory, String telephone, String[] winePairings) {
        super(name, location, inventory, telephone);
        this.winePairings = winePairings;
    }

    public String[] getWinePairings() {
        return this.winePairings;
    }

    public void setWinePairings(String[] winePairings) {
        this.winePairings = winePairings;
    }

    @Override
    public String toString() {
        return super.toString() + " " + winePairings;
    }
}

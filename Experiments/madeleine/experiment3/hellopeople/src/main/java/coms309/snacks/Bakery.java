package coms309.snacks;

/**
 * Provides the Definition/Structure for the bakery row
 *
 * @author Madeleine Carydis
 */

public class Bakery extends SnackPlace{

    public Bakery() {
    }

    private String hasViennoise;

    public Bakery(String name, String location, String[] inventory, String telephone, String hasViennoise) {
        super(name, location, inventory, telephone);
        this.hasViennoise = hasViennoise;
    }

    public String getHasViennoise() {
        return this.hasViennoise;
    }

    public void setHasViennoise(String hasViennoise) {
        this.hasViennoise = hasViennoise;
    }

    @Override
    public String toString() {
        return super.toString() + " " + hasViennoise;
    }
}

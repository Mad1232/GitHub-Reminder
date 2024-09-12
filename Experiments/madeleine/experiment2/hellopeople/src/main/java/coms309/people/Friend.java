package coms309.people;

/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class Friend {

    private String firstName;

    private String lastName;

    private String handle;

    private String pet;

    private String allergy;
    
    public Friend() {

    }

    public Friend(String firstName, String lastName, String handle, String pet) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.handle = handle;
        this.pet = pet;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHandle() {
        return this.handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getPet() {
        return this.pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getAllergy() {
        return this.allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    @Override
    public String toString() {
        return firstName + " "
                + lastName + " "
                + handle + " "
                + pet + " " + allergy;
    }
}

import java.util.Objects;

public final class Person {

    private int id;
    private final String givenName;
    private final String familyName;
    private final Phone number;

    public Person(String givenName, String familyName, Phone number){

        this.givenName = givenName;
        this.familyName = familyName;
        this.number = number;
    }

    public Person(int id, String givenName, String familyName, Phone number){
        this.givenName = givenName;
        this.familyName = familyName;
        this.number = number;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public String getGivenName(){
        return this.givenName;
    }

    public String getFamilyName(){
        return this.familyName;
    }

    public String getFullName(){
        return this.givenName + " " + this.familyName;
    }

    public Phone getPhone(){
        return this.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return getId() == person.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGivenName(), getFamilyName(), number);
    }

    public String toString(){
        return givenName + " " + familyName + ", number : "+number;
    }
}

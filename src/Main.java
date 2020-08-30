import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args){
        /*BTree<String,Integer> k = new BTree<>(4);
        k.insert("Liam Pierce",1);
        k.insert("Alok",2);
        k.insert("Anthony",3);
        k.insert("Gracie",4);
        k.insert("Jamie",5);
        k.insert("Joann Jones",6);
        k.insert("Lisa",7);
        k.insert("David Elmaleh",8);
        k.insert("Catharine Pierce",9);
        k.insert("Toni",10);*/
        if (true) {
            PhoneBook k = new PhoneBook();

            List<Person> people = Arrays.asList(
                    new Person("Liam", "Pierce", new Phone("6174854595")),
                    new Person("Liam", "Pierce", new Phone("6174854594")),
                    new Person("Alok", "", new Phone("1239374938")),
                    new Person("Anthony", "", new Phone("6172938229")),
                    new Person("Gracie", "", new Phone("61729382938")),
                    new Person("Jamie", "", null),
                    new Person("Joann", "Jones", null),
                    new Person("Lisa", "", null),
                    new Person("David", "Elmaleh", null),
                    new Person("Catharine", "Pierce", null),
                    new Person("Toni", "", null),
                    new Person("Mindy", "Davis", null),
                    new Person("William", "Pierce", null),
                    new Person("Liam", "Durbin", null),
                    new Person("Zacchia", "Albin", null),
                    new Person("Laura", "Damann", null)
            );

            for (Person m : people) {
                k.add(m);
            }

            System.out.println(k.search("Joann Jones"));
            System.out.println(k.search("Liam Pierce"));
        }

        if (false) {
            BTree<Integer, String> tree = new BTree<>();

            for (int i = 0; i < 2000; i++) {
                tree.insert((int) (Math.random() * 100), "Help" + i);
            }

            tree.visualize();

            System.out.println(tree.get(70));
        }
    }
}

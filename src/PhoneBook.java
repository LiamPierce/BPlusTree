import java.util.ArrayList;
import java.util.List;

public class PhoneBook {
    private List<Person> registry;

    private BTree<String, Integer> nameIndex;
    private BTree<String, Integer> phoneIndex;

    public PhoneBook(){
        this.registry = new ArrayList<>();
        this.nameIndex = new BTree<>();
        this.phoneIndex = new BTree<>();
    }

    public void add(Person entry){
        entry.setId(registry.size());
        registry.add(entry);
        nameIndex.insert(entry.getFullName(),entry.getId());
        phoneIndex.insert((entry.getPhone() != null ? entry.getPhone() : "").toString(),entry.getId());
        System.out.println(entry.getId());

        nameIndex.visualize();
    }

    public List<Person> search(String name){
        List<Person> results = new ArrayList<>();
        List<Integer> ids = nameIndex.getAll(name);

        if (ids.size() == 0){
            return results;
        }

        for (Integer k : ids){
            results.add(registry.get(k));
        }

        return results;
    }

    public List<Person> search(Phone number){
        List<Person> results = new ArrayList<>();
        List<Integer> ids = phoneIndex.getAll(number.toString());

        if (ids.size() == 0){
            return results;
        }

        for (Integer k : ids){
            results.add(registry.get(k));
        }

        return results;
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Liam Pierce

public class BTree <K extends Comparable<? super K>,V> {

    protected Node root;
    protected int length;

    public BTree(){
        this.length = 4;

        this.root = new LeafNode();
    }

    public BTree(int length){
        this.length = length;

        this.root = new LeafNode();
    }

    public void insert(K key, V value){
        System.out.println("Inserting "+key+":"+value);
        this.root.insert(key, value);
    }

    public V get(K key){
        return this.root.get(key);
    }

    public List<V> getAll(K key){
        return this.root.getAll(key);
    }

    public void delete(K key){
        this.root.delete(key);
    }

    public void visualize(){
        System.out.println("Visualizing tree...");
        this.root.visualize(0);
    }

    abstract class Node{
        protected List<K> keys;

        protected InternalNode parent;
        protected Node leftNeighbor;
        protected Node rightNeighbor;

        protected boolean isLeafNode = false;

        public boolean isLeaf() {
            return this.isLeafNode;
        }

        public boolean isFull(){
            return keys.size() >= length;
        }

        public void setParent(InternalNode parent){
            this.parent = parent;
        }

        public Node getParent(){
            return this.parent;
        }

        abstract public void visualize(int depth);

        //Get insertion position
        public Integer getPosition(K key){
            for (int i = 0;i < keys.size(); i++){
                if (this.keys.get(i).compareTo(key) >= 0){
                    return i;
                }
            }
            return keys.size();
        }

        //Get position of element.
        public Integer getLocation(K key) {
            for (int i = 0; i < keys.size(); i++) {
                if (this.keys.get(i).compareTo(key) > 0) {
                    return i;
                }
            }
            return keys.size();
        }

        public K minimum(){
            return Collections.min(this.keys);
        }

        public Node(){
            this.keys = new ArrayList<>();
        }

        abstract public void insert(K key, V value);
        abstract public V get(K key);
        abstract public List<V> getAll(K key);
        abstract public boolean delete(K key);
    }

    class InternalNode extends Node{
        protected List<BTree.Node> pointers;

        public InternalNode(){
            super();

            this.pointers = new ArrayList<>();
        }

        public InternalNode(LeafNode left, LeafNode right) {
            super();

            this.pointers = new ArrayList<>();
            this.pointers.add(0, left);
            this.pointers.add(1, right);

            this.keys.add(right.minimum());
        }

        public InternalNode(List<K> keys, List<BTree.Node> pointers){
            this.keys = new ArrayList<>(keys);
            this.pointers = new ArrayList<>(pointers);
        }

        public void reparent(){
            for (Node k : this.pointers){
                k.setParent(this);
            }
        }

        public void refactor(){

            reparent();

            if (this.keys.size() == length){ //Split on pointers instead of keys, maintain n pointers max.

                InternalNode split = new InternalNode(
                        this.keys.subList((int) Math.ceil(this.keys.size() / 2)+1,this.keys.size()),
                        this.pointers.subList((int) Math.ceil(this.keys.size() / 2)+1,this.pointers.size())
                );

                split.leftNeighbor = this;
                split.rightNeighbor = this.rightNeighbor;
                split.refactor();

                if (this.rightNeighbor != null){
                    this.rightNeighbor.leftNeighbor = split;
                }

                this.rightNeighbor = split;



                if (this.parent == null){
                    this.parent = new InternalNode(
                            Arrays.asList(split.minimum()),
                            new ArrayList<>(Arrays.asList(this,split))
                    );

                    split.parent = this.parent;

                    this.parent.refactor();
                }else{
                    this.parent.link(split);
                }

                this.keys = this.keys.subList(0,(int) Math.floor(this.keys.size() / 2));
                this.pointers = this.pointers.subList(0,(int) Math.floor(this.pointers.size() / 2)+1);
            }

            if (this.leftNeighbor == null && this.rightNeighbor == null && this.parent == null) {
                root = this;
            }
        }

        public void link(Node n){

            K minimum = n.minimum();
            int pos = this.getPosition(minimum);
            this.keys.add(pos,minimum);
            this.pointers.add(pos+1,n);

            n.setParent(this);

            this.refactor();
        }

        @Override
        public K minimum(){
            return (K) this.pointers.get(0).minimum();
        }

        @Override
        public void insert(K key, V value){
            this.pointers.get(getPosition(key)).insert(key,value);
        }

        @Override
        public V get(K key){
            return (V) this.pointers.get(getLocation(key)).get(key);
        }

        @Override
        public List<V> getAll(K key){
            return this.pointers.get(getLocation(key)).getAll(key);
        }

        @Override
        public boolean delete(K key) {
            return this.pointers.get(getPosition(key)).delete(key);
        }

        @Override
        public void visualize(int depth){
            System.out.println("\t".repeat(depth) + this);
            for (Node k : pointers){
                k.visualize(depth + 1);
            }
        }

        public String toString(){
            return keys.toString();
        }
    }

    class LeafNode extends Node{
        protected List<V> values;

        public LeafNode(){
            super();
            super.isLeafNode = true;

            this.values = new ArrayList<>();
        }

        public LeafNode(List<K> keys, List<V> values) {
            super();
            super.isLeafNode = true;

            this.keys = new ArrayList<>(keys);
            this.values = new ArrayList<>(values);
        }

        @Override
        public boolean delete(K key){
            Integer pos = this.getLocation(key);
            if (pos == null){
                return false;
            }

            this.keys.remove(pos);
            this.values.remove(pos);

            return true;
        }

        @Override
        public void insert(K key, V value) {
            int pos = this.getPosition(key);
            this.keys.add(pos,key);
            this.values.add(pos,value);

            if (this.isFull()){
                LeafNode split = new LeafNode(
                        this.keys.subList((int) Math.floor((length) / 2),length),
                        this.values.subList((int) Math.floor((length) / 2),length)
                );

                this.keys = this.keys.subList(0, (int) Math.floor((length) / 2));
                this.values = this.values.subList(0, (int) Math.floor((length) / 2));

                split.leftNeighbor = this;
                split.rightNeighbor = this.rightNeighbor;

                if (this.rightNeighbor != null){
                    this.rightNeighbor.leftNeighbor = split;
                }

                this.rightNeighbor = split;


                if (this.parent == null){
                    InternalNode k = new InternalNode(this, split);
                    k.refactor();
                }else{
                    this.parent.link(split);
                }


            }
        }

        @Override
        public V get(K key){
            int pos = getPosition(key);

            if (pos == this.keys.size()){
                return null;
            }

            return this.values.get(pos);
        }

        @Override
        public List<V> getAll(K key){
            return this.getAll(key,true,false);
        }

        public List<V> getAll(K key, boolean primary, boolean direction){
            List<V> results = new ArrayList<>();

            for (int i = (primary || direction ? 0 : this.keys.size() - 1);(primary || direction ? i < this.keys.size() : i >= 0);) {
                if (key.equals(this.keys.get(i))) {

                    results.add(this.values.get(i));
                    if ((primary || !direction) && this.leftNeighbor != null && i == 0){
                        results.addAll(((LeafNode) this.leftNeighbor).getAll(key,false,false));
                    }

                    if ((primary || direction) && this.rightNeighbor != null && i == this.keys.size() - 1){
                        results.addAll(((LeafNode) this.rightNeighbor).getAll(key, false, true));
                    }


                } else if (results.size() > 0) {
                    break;
                }

                if (primary || direction){
                    i++;
                }else{
                    i--;
                }
            }


            return results;
        }

        @Override
        public void visualize(int depth){
            System.out.println("\t".repeat(depth) + "______");
            for (int i = 0;i<keys.size();i++){
                System.out.println("\t".repeat(depth)+keys.get(i)+"=>"+values.get(i));
            }
        }
    }

}

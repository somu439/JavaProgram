package Collections;

import java.util.HashSet;

public class Set {
    public static void main(String[] args) {
        HashSet h= new HashSet();
        h.add("one");
        h.add("two");
        h.add("two");
        h.add("three");
        System.out.println("Set before: "+h);
        h.remove("two");
        System.out.println("set after: "+h);


    }
}

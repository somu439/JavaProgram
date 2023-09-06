package Collections;

import java.util.ArrayList;
import java.util.LinkedList;

public class List {
    public static void main(String[] args) {
        LinkedList<String> list =new LinkedList();
        list.add("one");
        list.add("two");
        list.add("three");
        System.out.println(list.size());
    }
}

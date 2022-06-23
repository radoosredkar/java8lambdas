package ch4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class IterableTest implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        List<String> tList = new ArrayList<>();
        tList.add("a");
        tList.add("b");
        tList.add("c");
        return tList.iterator();
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        for (String t : this){
            action.accept(t);
        }
    }

    @Override
    public Spliterator<String> spliterator() {
        return Iterable.super.spliterator();
    }
}

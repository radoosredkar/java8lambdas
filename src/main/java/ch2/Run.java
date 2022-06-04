package ch2;

import javax.swing.text.DateFormatter;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public class Run {
    public static void main(String[] args) {
        Runnable noArguments = () -> System.out.println("Hello World");
        BinaryOperator<Long> add = (x, y) -> x + y;
        noArguments.run();
        System.out.println(add.apply(1L, 2L).toString());

        String name = "Rado";
        Runnable sayName = () -> System.out.println(String.format("Hello %s", name));
        sayName.run();

        Predicate<Integer> atLeast5 = x -> x > 5;
        System.out.println(atLeast5.test(5));
        System.out.println(atLeast5.test(6));

        //ThreadSafe date formatter
        ThreadLocal<DateFormatter> localFormatter =
            ThreadLocal.withInitial(()->new DateFormatter());

    }
}

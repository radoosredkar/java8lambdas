package ch4;

import ch1.SampleData;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public class Run {

    public static void main(String[] args) {
        printWithSupplier(() -> "New supplier message");

        var trackLengthStats =
            SampleData.albums.findFirst().get().getTracks()
                .mapToInt(t -> t.getLength())
                .summaryStatistics();

        System.out.printf(
            "\nMax: %d, \nMin %d, \nAve %f, \nSum %d"
            , trackLengthStats.getMax()
            , trackLengthStats.getMin()
            , trackLengthStats.getAverage()
            , trackLengthStats.getSum()
        );

        calc(22, 2, (n1, n2) -> n1 + n2);
        calc(22, 2, (n1, n2) -> n1 * n2);

        System.out.println("Stayed on page 46");
    }

    private static Integer calc(Integer n1, Integer n2, BinaryOperator<Integer> b) {
        Integer result = b.apply(n1, n2);
        System.out.println();
        System.out.printf("result %d", result);
        return result;
    }

    private static void printWithSupplier(Supplier<String> message) {
        System.out.print("Starting supplier");
        System.out.print(message.get());
        System.out.print("Ending supplier");
    }
}
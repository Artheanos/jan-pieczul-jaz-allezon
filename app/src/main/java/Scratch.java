import com.sun.mail.imap.protocol.INTERNALDATE;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Scratch {

    static ArrayList<Integer> string_to_ints(String input) {

        ArrayList<Integer> result = new ArrayList<>();

        for (String string_number : input.split("[^0-9]"))
            if (!string_number.isEmpty())
                result.add(Integer.parseInt(string_number));

        return result;
    }

    static double average(int[] numbers) {
        return (double) IntStream.of(numbers).sum() / numbers.length;
    }

    static double average(ArrayList<Integer> numbers) {
        return average(numbers.stream().mapToInt(i -> i).toArray());
    }

    public static void main(String[] args) {
        String input = "1,2,2''6::2";

        System.out.println(
        );
    }
}


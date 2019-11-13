package pl.edu.pjwstk.jaz.average;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.IntStream;

@WebServlet("hello")
public class AverageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(200);
        resp.setContentType("text/plain");

        PrintWriter respWriter = resp.getWriter();

        ArrayList<Integer> input_ints = AverageTools.string_to_ints(req.getParameter("average"));

        if (input_ints.size() == 0)
            respWriter.println("Could not calculate average\nNo ints were found");
        else
            respWriter.println(String.format(
                    "Average of (%s) is %s",
                    AverageTools.join_ints(", ", input_ints),
                    AverageTools.average(input_ints)
            ));
    }
}


class AverageTools {

    public static ArrayList<Integer> string_to_ints(String input) {

        ArrayList<Integer> result = new ArrayList<>();

        for (String string_number : input.split("[^0-9]"))
            if (!string_number.isEmpty())
                result.add(Integer.parseInt(string_number));

        return result;
    }

    public static double average(int[] numbers) {
        return (double) IntStream.of(numbers).sum() / numbers.length;
    }

    public static double average(ArrayList<Integer> numbers) {
        return average(numbers.stream().mapToInt(i -> i).toArray());
    }

    public static String join_ints(String joint, ArrayList<Integer> ints) {
        StringBuilder result_builder = new StringBuilder();
        Iterator<Integer> iter = ints.iterator();
        while (iter.hasNext()) {
            result_builder.append(iter.next());
            if (iter.hasNext())
                result_builder.append(joint);
        }
        return result_builder.toString();
    }
}

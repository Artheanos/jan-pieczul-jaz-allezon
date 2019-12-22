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

        String stringInput = req.getParameter("average");

        if (stringInput == null){
            respWriter.println("Parameter `average` was not found");
            return;
        }

        ArrayList<Integer> inputInts = AverageTools.stringToInts(stringInput);

        if (inputInts.size() == 0)
            respWriter.println("Could not calculate average\nNo ints were found");
        else
            respWriter.println(String.format(
                    "Average of (%s) is %s",
                    AverageTools.joinInts(", ", inputInts),
                    AverageTools.average(inputInts)
            ));
    }
}


class AverageTools {

    public static ArrayList<Integer> stringToInts(String input) {

        ArrayList<Integer> result = new ArrayList<>();

        for (String stringNumber : input.split("[^0-9]"))
            if (!stringNumber.isEmpty())
                result.add(Integer.parseInt(stringNumber));

        return result;
    }

    public static double average(int[] numbers) {
        return (double) IntStream.of(numbers).sum() / numbers.length;
    }

    public static double average(ArrayList<Integer> numbers) {
        return average(numbers.stream().mapToInt(i -> i).toArray());
    }

    public static String joinInts(String joint, ArrayList<Integer> ints) {
        StringBuilder resultBuilder = new StringBuilder();
        Iterator<Integer> iter = ints.iterator();
        while (iter.hasNext()) {
            resultBuilder.append(iter.next());
            if (iter.hasNext())
                resultBuilder.append(joint);
        }
        return resultBuilder.toString();
    }
}

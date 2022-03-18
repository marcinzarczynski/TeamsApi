import java.util.*;

public class FunWithString {



    public String change(String string){
        List<String> words = new ArrayList<>(Arrays.asList(string.split(" ")));
        Collections.reverse(words);

        String x = " ";
        for (String y: words){
            x=x+" "+ y;
        }

        return x;
    }
}

package TES;

import TES.SimCommands.AddTrack;
import java.util.HashMap;


public class Parser {

    public Object[] parse(String input) {

        HashMap<String, String> keyValue = new HashMap<>();
        String[] words = input.split(" ");

        String commandName = words[0];


        //Argumanları yanlış verirse exception
        for (int i = 1; i < words.length; i+=2) {
                keyValue.put(words[i],words[i+1]);
        }

        return new Object[]{commandName,keyValue};
    }




}

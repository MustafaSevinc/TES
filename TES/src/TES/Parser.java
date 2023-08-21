package TES;

import TES.SimCommands.AddTrack;
import java.util.HashMap;


public class Parser {

    public HashMap parse(String input) {

        if (input.equals("addtrack")) {
            System.out.println("Track Added");
        }
        System.out.println("Command Not Found");
        return null;
    }



}

package TES.Server.Input;

import TES.Server.Datas.CommandData;

import java.util.HashMap;


public class Parser {

    public CommandData parse(String input) {

        if(input == null){
            return null;
        }

        HashMap<String, String> keyValue = new HashMap<>();
        String[] words = input.split(" ");

        String commandName = words[0];


        if(words.length %2 == 0){
            System.out.println("Input format: commandName arg1 val1 arg2 val2....");
            return null;
        }


        for (int i = 1; i < words.length; i+=2) {
                keyValue.put(words[i],words[i+1]);
        }

        CommandData cmd = new CommandData(commandName,keyValue);

        return cmd;
    }




}

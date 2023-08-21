package TES;

import TES.SimCommands.AddTrack;
import TES.SimCommands.CommandBase;
import TES.util.ClassUtils;

import java.util.List;


public class Parser {

    public CommandBase parse(String input) {

        List commandBase = getCommandClasses();



        if (input.equals("addtrack")) {
            System.out.println("Track Added");
            return new AddTrack();
        }
        System.out.println("Command Not Found");
        return null;
    }


    private List<Class<?>> getCommandClasses() {
        String directoryName = "C:\\Users\\stj.msevinc\\Desktop\\TES\\TES\\src\\TES.SimCommands";
        Class<?> baseClass = CommandBase.class;
        List<Class<?>> extendingClasses = null;
        try {
            extendingClasses = ClassUtils.getClassesExtendingBaseClass(directoryName, "SimCommands", baseClass);
        } catch (Exception ex) {
            System.out.println("Class Not Found");
        }
        return extendingClasses;
    }
}

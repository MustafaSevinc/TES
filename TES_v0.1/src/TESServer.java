import java.util.UUID;

public class TESServer {
    InputManager inputManager;
    Simulator simulator;
    Parser parser;
    boolean isWorking;


    public TESServer() {
        isWorking = true;
    }


    public void start() {
        this.start(null);
    }

    public void start(String filePath) {

        inputManager = new InputManager();
        simulator = new Simulator(0, 500);
        parser = new Parser();

        if (filePath != null) {
            inputManager.setInputFromFile(filePath);
        }
        String inputText;
        while (isWorking) {
            inputText = inputManager.readNext();
            if(inputText == null ) {inputManager.setInputFromTerminal();continue;}
            else if ( inputText == "") {continue;}

            else if (inputText.equals("exit")) {
                isWorking = false;
                close();
                return;
            //else if kısmı test amaçlı silinir sonra:
            } else if (inputText.equals("showSim")) {
                System.out.println("ACTIVE OBJECTS:");
                simulator.printSimObjectNames();
            }
            CommandData cmd = parser.parse(inputText);
            if(cmd != null)
                simulator.execute(cmd);
        }
    }

    public void close() {
        inputManager.close();
        simulator.close();
    }





    private static String file1 = "C:\\Users\\stj.msevinc\\Desktop\\TES\\LETSGO.txt";
    private static String file2 = "C:\\Users\\stj.msevinc\\Desktop\\TES\\LETSGO 2.txt";
    public static void main(String[] args) {
        TESServer tesServer = new TESServer();
        tesServer.start(file1);
    }

}

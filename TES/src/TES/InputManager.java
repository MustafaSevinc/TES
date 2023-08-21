package TES;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputManager {

    Scanner scanner;

    public InputManager() {
        scanner = new Scanner(System.in);
    }


    public void setInputFromFile(String filePath) {
        scanner.close();
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void setInputFromTerminal() {
        scanner.close();
        scanner = new Scanner(System.in);
    }


    public String readNext() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }


    public void close() {
        scanner.close();
    }
}

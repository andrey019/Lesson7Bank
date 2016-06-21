import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DbHandler dbHandler = new DbHandler();
        MainMenu mainMenu = new MainMenu(dbHandler, scanner);
        mainMenu.start();


    }
}

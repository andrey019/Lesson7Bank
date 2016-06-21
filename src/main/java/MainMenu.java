import java.util.Scanner;

public class MainMenu extends Thread {
    private DbHandler dbHandler;
    private Scanner scanner;

    public MainMenu(DbHandler dbHandler, Scanner scanner) {
        this.dbHandler = dbHandler;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        mainMenu();
    }

    private void mainMenu() {
        instructionsOnScreen();
        while (!isInterrupted()) {
            switch (scanner.nextLine()) {
                case "1":
                    addClient();
                    break;
                case "2":
                    //delete();
                    break;
                case "3":
                    //
                    break;
                case "4":
                    //
                    break;
                case "5":
                    //
                    break;
                case "6":
                    //
                    break;
                case "7":
                    //
                    break;
                case "8":
                    System.exit(0);
                    break;
            }
        }
    }

    private String getStringInput() {
        String string = scanner.nextLine();
        while (string.isEmpty()) {
            System.out.println("Field can't be empty! Try again");
            string = scanner.nextLine();
        }
        return string;
    }

    private int getIntInput() {
        int integer = -1;
        while (integer == -1) {
            try {
                integer = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Wrong type! Try again");
            }
        }
        return integer;
    }

    private double getDoubleInput() {
        double number = -1;
        while (number == -1) {
            try {
                number = Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Wrong type! Try again");
            }
        }
        return number;
    }

    private static void instructionsOnScreen() {
        System.out.println("1) Add client");
        System.out.println("2) Delete dish by id");
        System.out.println("3) Find dish by name");
        System.out.println("4) Show dishes with price range from-to");
        System.out.println("5) Show all dishes with discount");
        System.out.println("6) Show all dishes");
        System.out.println("7) Show random dishes set with total weight no more then");
        System.out.println("8) Exit");
        System.out.println();
    }

    private void addClient() {
        System.out.print("Type in client name: ");
        String name = getStringInput();
        System.out.print("Type in client surname: ");
        String surname = getStringInput();
        Client client = new Client(name, surname);
        if (dbHandler.addEntityToDB(client)) {
            System.out.println("Added successfully!\r\n");
        } else {
            System.out.println("Internal error!\r\n");
        }
    }

    private void addAccount() {
        System.out.print("Type in owner ID: ");
        int clientId = getIntInput();
        //get client
        System.out.print("Type in currency ID: ");
        int currencyId = getIntInput();
        //get rate
        Account account = new Account();
        if (dbHandler.addEntityToDB(account)) {
            System.out.println("Added successfully!\r\n");
        } else {
            System.out.println("Internal error!\r\n");
        }
    }
//
//    private void delete() {
//        System.out.print("Type in dish Id: ");
//        int id = getIntInput();
//        Menu menu = (Menu) dbHandler.getEntityById(Menu.class, id);
//        if (menu == null) {
//            System.out.println("No dish with this id!\r\n");
//            return;
//        } else {
//            if (dbHandler.deleteEntityFromBD(menu)) {
//                System.out.println("Deleted successfully!\r\n");
//            } else {
//                System.out.println("Internal error!\r\n");
//            }
//        }
//    }
}

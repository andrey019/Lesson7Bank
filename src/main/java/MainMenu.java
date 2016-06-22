import java.util.Date;
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
                    addAccount();
                    break;
                case "3":
                    addCurrency();
                    break;
                case "4":
                    addTransaction();
                    break;
                case "5":
                    getAllClientMoney();
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
        System.out.println("2) Add account");
        System.out.println("3) Add currency");
        System.out.println("4) Add transaction (transfer money between accounts)");
        System.out.println("5) All client money");
        System.out.println("6) ");
        System.out.println("7) ");
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
        Client client = (Client) dbHandler.getEntityById(Client.class, clientId);
        System.out.print("Type in currency ID: ");
        int currencyId = getIntInput();
        System.out.print("Type in initial amount of money: ");
        double money = getDoubleInput();
        Rate rate = (Rate) dbHandler.getEntityById(Rate.class, currencyId);
        long number = dbHandler.getUniqueAccountNumber();
        Account account = new Account(number, money, client, rate);
        if (dbHandler.addEntityToDB(account)) {
            System.out.println("Added successfully!\r\n");
        } else {
            System.out.println("Internal error!\r\n");
        }
    }

    private void addCurrency() {
        System.out.print("Type in currency name: ");
        String name = getStringInput();
        System.out.print("Type in buy rate to national currency: ");
        double buyRate = getDoubleInput();
        System.out.print("Type in sell rate to national currency: ");
        double sellRate = getDoubleInput();
        Rate rate = new Rate(name, buyRate, sellRate);
        if (dbHandler.addEntityToDB(rate)) {
            System.out.println("Added successfully!\r\n");
        } else {
            System.out.println("Internal error!\r\n");
        }
    }

    private void addTransaction() {
        System.out.print("Type in giver's account ID or number: ");
        int giver = getIntInput();
        Account giverAcc = getAccountByIdOrNumber(giver);
        if (giverAcc == null) {
            System.out.println("No such account!");
            return;
        }
        System.out.print("Type in receiver's account ID or number: ");
        int receiver = getIntInput();
        Account receiverAcc = getAccountByIdOrNumber(receiver);
        if (receiverAcc == null) {
            System.out.println("No such account!");
            return;
        }
        System.out.print("Type in amount of money to transfer: ");
        double initialSum = getDoubleInput();
        TransactionBank transaction = new TransactionBank(giverAcc, receiverAcc, initialSum, new Date());
        if (transaction.setFinalSum()) {
            giverAcc.subMoney(initialSum);
            receiverAcc.addMoney(transaction.getFinalSum());
            String giverResult = "Account \"" + giverAcc.getNumber() + "\": withdrawn " +
                    transaction.getInitialSum() + " " + giverAcc.getRate().getCurrency();
            String receiverResult = "Account \"" + receiverAcc.getNumber() + "\": deposited " +
                    transaction.getFinalSum() + " " + receiverAcc.getRate().getCurrency();
            if (dbHandler.addTransaction(transaction, giverAcc, receiverAcc)) {
                System.out.println("Transfered successfully!");
                System.out.println(giverResult);
                System.out.println(receiverResult);
            } else {
                System.out.println("Internal error!\r\n");
            }
        } else {
            System.out.println("Not enough money!");
        }
    }

    private void getAllClientMoney() {
        System.out.print("Type in client's ID: ");
        int clientId = getIntInput();
        Client client = (Client) dbHandler.getEntityById(Client.class, clientId);
        if (client == null) {
            System.out.println("No such client!\r\n");
            return;
        }
        double allMoney = 0;
        System.out.println("Client = " + client.getName() + " " + client.getSurname());
        for (Account account : client.getAccounts()) {
            System.out.println("Account " + account.getNumber() + " = " +
                    account.getMoney() + " " + account.getRate().getCurrency());
            allMoney += account.getMoney() * account.getRate().getBuyRate();
        }
        System.out.println("Total in national currency = " + allMoney + "\r\n");
    }

    private Account getAccountByIdOrNumber(long value) {
        if (value < 100000000) {
            return (Account) dbHandler.getEntityById(Account.class, value);
        } else {
            return (Account) dbHandler.getByEqualUnique(Account.class, "number", value);
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

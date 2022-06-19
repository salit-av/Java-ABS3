package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menus {

    private Scanner scanner = new Scanner(System.in);

    public int printMenuAndReturnChoice() {
        int choice = 0;
        System.out.println("-------------------HELLO---------------------");
        System.out.println("Please choose what you want from the menu: ");
        System.out.println("1. load xml file");
        System.out.println("2. Print all loans and their status");
        System.out.println("3. Print all customers and their info");
        System.out.println("4. Load money to account");
        System.out.println("5. Withdrawal money from account");
        System.out.println("6. Activation");
        System.out.println("7. Promoting yaz and providing payments");
        System.out.println("8. EXIT");
        System.out.println("---------------------------------------------");

        return giveInt();
    }

    public void printGoodbye() {
        System.out.println("--------------------GOODBYE--------------------");
    }

    public void printNoFileUploadedToSystem() {
        System.out.println("-----------------------------------Error!--------------------------------------");
        System.out.println("No file uploaded to the system, please upload one and then choose this option");
        System.out.println("-------------------------------------------------------------------------------");

    }

    public int printInvestmentBalance(int balance) {
        System.out.println("Please enter a total amount for the investment:");
        System.out.println("(An integer between 1 and "+ balance + ", or 0 to cancel)");
        int invesment = giveInt();

        while(invesment > balance){
            System.out.println("You're confused... you do not have that much money in the account");
            invesment = giveInt();
        }
        return invesment;
    }

    public int printMinimumInterestAndReturnAnswer() {
        System.out.println("Please enter integer - minimum interest for a single yaz you are willing to receive:");
        System.out.println("(and 0 if you don't want this filter)");
        return giveInt();
    }

    public int printMinimumYazForAllLoanAndReturnAnswer() {
        System.out.println("Please enter integer - minimum yaz for all loan:");
        System.out.println("(and 0 if you don't want this filter)");
        return giveInt();}

    public int giveInt() {
        while(true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("----------Error!----------");
                System.out.println("Please enter a correct number!");
                scanner.nextLine();
            }
        }
    }
}

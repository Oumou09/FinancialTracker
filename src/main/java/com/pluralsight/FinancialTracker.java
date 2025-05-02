package com.pluralsight;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class FinancialTracker {

    private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                LocalDate date = LocalDate.parse(parts[0], DATE_FORMATTER);
                LocalTime time = LocalTime.parse(parts[1], DateTimeFormatter.ofPattern(TIME_FORMAT));
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);
                transactions.add(new Transaction(date, time, vendor, description, amount));
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error reading file: " + fileName);
        }
    }

    private static void addDeposit(Scanner scanner) {

        System.out.print("Enter the date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
        System.out.print("Enter the time (HH:mm:ss): ");
        LocalTime time = LocalTime.parse(scanner.nextLine(), TIME_FORMATTER);
        System.out.print("Enter product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter product vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter product amount: ");
        double amount = Double.parseDouble(scanner.nextLine());


        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive number.");
            return;
        }
        Transaction newTransaction = new Transaction(date, time, vendor, description, amount);
        transactions.add(newTransaction);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.newLine();
            writer.write(newTransaction.toString());
            System.out.println("Deposit added successfully!");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void addPayment(Scanner scanner) {
        System.out.print("Enter the date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
        System.out.print("Enter the time (HH:mm:ss): ");
        LocalTime time = LocalTime.parse(scanner.nextLine(), TIME_FORMATTER);
        System.out.print("Enter product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter product vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter product amount: ");
        double amount = Double.parseDouble(scanner.nextLine()) * -1;

        Transaction newTransaction = new Transaction(date, time, vendor, description, amount);
        transactions.add(newTransaction);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.newLine();
            writer.write(newTransaction.toString());
            System.out.println(" added successfully!");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) A`ll");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }
    private static void displayLedger() {
        System.out.println(" DATE    |     TIME        |          DESCRIPTION    |     VENDOR              | AMOUNT               ");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
            System.out.println();
        }
    }
    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions` ArrayList.
        System.out.println(" DATE    |      TIME   |          DESCRIPTION    |   VENDOR              | AMOUNT                ");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {
                System.out.println(transaction);
            }

        }
    }
    private static void displayPayments() {
        System.out.println(" DATE    |       TIME       |          DESCRIPTION    |   VENDOR            | AMOUNT               ");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }

        }
    }
    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();


            switch (input) {
                case "1":
                    filterTransactionsByDate("1");
                    break;
                case "2":
                    filterTransactionsByDate("2");
                    break;
                case "3":
                    filterTransactionsByDate("3");
                    break;
                case "4":
                    filterTransactionsByDate("4");
                    break;
                case "5":
                    filterTransactionsByVendor();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }
    private static void filterTransactionsByDate(String option) {
        Month currMonth = LocalDate.now().getMonth();
        int getYear = LocalDate.now().getYear();
        Month prevMonth = currMonth.minus(1);
        int prevYear = getYear - 1;
        for (Transaction transaction : transactions) {
            switch (option) {
                case "1":
                    if (transaction.getDate().getMonth().equals(currMonth) && transaction.getDate().getYear() == getYear) {
                        System.out.println(transaction);
                    }
                    break;
                case "2":
                    if (transaction.getDate().getMonth().equals(prevMonth)  && transaction.getDate().getYear() == getYear) {
                        System.out.println(transaction);
                    }
                    break;
                case "3":
                    if (transaction.getDate().getYear() == getYear) {
                        System.out.println(transaction);
                    }
                    break;
                case "4":
                    if (transaction.getDate().getYear() == prevYear) {
                        System.out.println(transaction);
                    }
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }
        private static void filterTransactionsByVendor (){
            System.out.println("Write the name of a vendor: ");
            Scanner scanner = new Scanner(System.in);
            String vendor = scanner.nextLine();
            for (Transaction transaction : transactions) {
                if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                    System.out.println(transaction);
                }
            }

        }
}


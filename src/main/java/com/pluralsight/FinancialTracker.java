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


        public static void loadTransactions (String fileName){
            try {
                BufferedReader br = new BufferedReader(new FileReader("transactions.csv"));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    LocalDate date = LocalDate.parse(parts[0], DateTimeFormatter.ofPattern(DATE_FORMAT));
                    LocalTime time = LocalTime.parse(parts[1], DateTimeFormatter.ofPattern(TIME_FORMAT));
                    String vendor = parts[2];
                    String description = parts[3];
                    double amount = Double.parseDouble(parts[4]);
                    transactions.add(new Transaction(date, time, vendor, description, amount));
                }
            } catch (Exception e) {
                System.err.println("Error reading file: " + fileName);
            }
            // This method should load transactions from a file with the given file name.
            // If the file does not exist, it should be created.
            // The transactions should be stored in the `transactions` ArrayList.
            // Each line of the file represents a single transaction in the following format:
            // <date>|<time>|<description>|<vendor>|<amount>
            // For example: 2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
            // After reading all the transactions, the file should be closed.
            // If any errors occur, an appropriate error message should be displayed.
        }

        private static void addDeposit (Scanner scanner) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter the date (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(input.nextLine(), DATE_FORMATTER);
            input.nextLine();
            System.out.print("Enter the time (HH:mm:ss): ");
            LocalTime time = LocalTime.parse(input.nextLine(), TIME_FORMATTER);
            System.out.print("Enter product description: ");
            String description = input.nextLine();
            System.out.print("Enter product vendor: ");
            String vendor = input.nextLine();
            System.out.print("Enter product amount: ");
            double amount = Double.parseDouble(input.nextLine());


            if (amount <= 0) {
                System.out.println("Invalid amount. Please enter a positive number.");
            }
            Transaction newTransaction = new Transaction(date, time, vendor, description, amount);
            transactions.add(newTransaction);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true));
                writer.newLine();
                writer.write(newTransaction.toString());
                System.out.println("Deposit added successfully!");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();}
            }


        private static void addPayment (Scanner scanner) {

            try {
                // This method should prompt the user to enter the date, time, description, vendor, and amount of a payment.
                // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
                Scanner input = new Scanner(System.in);
                System.out.print("Enter the date (yyyy-MM-dd): ");
                LocalDate date = LocalDate.parse(input.nextLine(), DATE_FORMATTER);
                input.nextLine();
                System.out.print("Enter the time (HH:mm:ss): ");
                LocalTime time = LocalTime.parse(input.nextLine(), TIME_FORMATTER);
                System.out.print("Enter product description: ");
                String description = input.nextLine();
                System.out.print("Enter product vendor: ");
                String vendor = input.nextLine();
                System.out.print("Enter product amount: ");
                double amount = Double.parseDouble(input.nextLine() ) * -1;

                Transaction newTransaction = new Transaction(date, time, vendor, description, amount);
                transactions.add(newTransaction);
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true));
                    writer.newLine();
                    writer.write(newTransaction.toString());
                    System.out.println(" added successfully!");
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();}
        }


        private static void ledgerMenu (Scanner scanner){
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
        private static void displayLedger () {
            System.out.println(" DATE    | TIME   |          DESCRIPTION    | VENDOR              | AMOUNT                ");
            System.out.println("------------------------------------------------------------------------------------------");
            // This method should display a table of all transactions in the `transactions` ArrayList.
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
            // The table should have columns for date, time, description, vendor, and amount.
        }

        private static void displayDeposits () {
            // This method should display a table of all deposits in the `transactions` ArrayList.
            try {


            } catch (Exception e) {
                System.out.println("Invalid Entry");
            } // The table should have columns for date, time, description, vendor, and amount.
        }

        private static void displayPayments () {
            // This method should display a table of all payments in the `transactions` ArrayList.
            // The table should have columns for date, time, description, vendor, and amount.
        }

        private static void reportsMenu (Scanner scanner){
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
                        // Generate a report for all transactions within the current month,
                        // including the date, time, description, vendor, and amount for each transaction.
                    case "2":
                        // Generate a report for all transactions within the previous month,
                        // including the date, time, description, vendor, and amount for each transaction.
                    case "3":
                        // Generate a report for all transactions within the current year,
                        // including the date, time, description, vendor, and amount for each transaction.

                    case "4":
                        // Generate a report for all transactions within the previous year,
                        // including the date, time, description, vendor, and amount for each transaction.
                    case "5":
                        // Prompt the user to enter a vendor name, then generate a report for all transactions
                        // with that vendor, including the date, time, description, vendor, and amount for each transaction.
                    case "0":
                        running = false;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
        }


        private static void filterTransactionsByDate (LocalDate startDate, LocalDate endDate){
            // This method filters the transactions by date and prints a report to the console.
            // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
            // The method loops through the transactions list and checks each transaction's date against the date range.
            // Transactions that fall within the date range are printed to the console.
            // If no transactions fall within the date range, the method prints a message indicating that there are no results.
        }

        private static void filterTransactionsByVendor (String vendor){
            // This method filters the transactions by vendor and prints a report to the console.
            // It takes one parameter: vendor, which represents the name of the vendor to filter by.
            // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
            // Transactions with a matching vendor name are printed to the console.
            // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.
        }


    }


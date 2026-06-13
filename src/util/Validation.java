/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import controller.BusList;
import java.util.Scanner;
import controller.PassengerList;

/**
 *
 * @author LAPTOP
 */
public class Validation {

    private static final Scanner sc = new Scanner(System.in);

    public static String inputString(String msg) {
        while (true) {
            System.out.print(msg);
            String input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty!");
        }
    }

    public static int inputInt(String msg, int min, int max) {
        while (true) {
            try {
                System.out.print(msg);
                int input = Integer.parseInt(sc.nextLine().trim());

                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Input must be in range " + min + " to " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Input must be Integer!");
            }
        }
    }

    public static double inputDouble(String msg, double min, double max) {
        while (true) {
            try {
                System.out.print(msg);
                double n = Double.parseDouble(sc.nextLine().trim());

                if (n >= min && n <= max) {
                    return n;
                }

                System.out.println("Input must be from " + min + " to " + max);
            } catch (NumberFormatException e) {
                System.out.println("Input must be Double!");
            }
        }
    }

    //validate bus
    public static String inputBusCode(String msg, BusList list) {
        while (true) {
            String code = inputString(msg);

            if (!list.isCodeExist(code)) {
                return code;
            }

            System.out.println("Bus code already exists!");
        }
    }

    public static String inputArriveStation(String msg, String dstation) {
        while (true) {
            String astation = inputString(msg);

            if (!astation.equalsIgnoreCase(dstation)) {
                return astation;
            }

            System.out.println("Arriving station must be different from departing station!");
        }
    }

    //validate passenger
    public static String inputPassengerID(String msg, PassengerList list) {
        while (true) {
            String id = inputString(msg);

            if (!list.isIDExist(id)) {
                return id;
            }

            System.out.println("Passenger ID already exists!");
        }
    }

    public static String inputPassengerName(String msg) {
        while (true) {
            String name = inputString(msg);

            if (name.matches("[a-zA-Z ]+")) {
                return name;
            }

            System.out.println("Passenger name must contain only letters and spaces!");
        }
    }

    public static String inputPhoneNumber(String msg) {
        while (true) {
            String phone = inputString(msg);

            if (phone.matches("\\d{10}")) {
                return phone;
            }

            System.out.println("Phone number must be 10 digits!");
        }
    }

    public static int inputCCCD(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                int cccd = Integer.parseInt(sc.nextLine().trim());
                if (cccd > 0) {
                    return cccd;
                }
                System.out.println("CCCD must be a positive integer!");
            } catch (NumberFormatException e) {
                System.out.println("CCCD must be an integer!");
            }
        }
    }

    public static String inputPassengerCode(String msg, PassengerList list) {
        while (true) {
            String code = inputString(msg);

            if (list.isCodeExist(code)) {
                System.out.println("Passenger code already exists!");
            } else {
                return code;
            }
        }
    }
}

package controller;

import java.io.*;
import model.Node;
import model.Passenger;
import util.Validation;

public class PassengerList {

    Node<Passenger> head, tail;

    public void menu() {
        int choice;

        do {
            System.out.println("\n========== PASSENGER LIST ==========");
            System.out.println("1. Load data from file");
            System.out.println("2. Input & add to the end");
            System.out.println("3. Display data");
            System.out.println("4. Save passenger list to file");
            System.out.println("5. Search by pcode");
            System.out.println("6. Delete by pcode");
            System.out.println("7. Search by name");
            System.out.println("8. Search buses by pcode");
            System.out.println("0. Back");

            choice = Validation.inputInt("Enter your choice: ", 0, 8);

            switch (choice) {
                case 1:
                    loadFromFile("passengers.txt");
                    break;

                case 2:
                    inputAndAddLast();
                    break;

                case 3:
                    display();
                    break;

                case 4:
                    saveToFile("passengers.txt");
                    break;

                case 5:
                    searchByPcode();
                    break;

                case 6:
                    deleteByPcode();
                    break;

                case 7:
                    searchByName();
                    break;

                case 8:
                    searchBusesByPcode();
                    break;

                case 0:
                    System.out.println("Back to main menu...");
                    break;
            }

        } while (choice != 0);
    }

    boolean isEmpty() {
        return head == null;
    }

    public boolean isIDExist(String id) {
        Node<Passenger> p = head;

        while (p != null) {
            if (p.info.getPcode().equalsIgnoreCase(id)) {
                return true;
            }

            p = p.next;
        }

        return false;
    }

    public boolean isCodeExist(String pcode) {
        Node<Passenger> p = head;

        while (p != null) {
            if (p.info.getPcode().equalsIgnoreCase(pcode)) {
                return true;
            }
            p = p.next;
        }

        return false;
    }

    public Passenger inputInforPassenger() {
        String pcode = Validation.inputPassengerCode("Enter passenger code: ", this);
        String name = Validation.inputString("Enter passenger name: ");
        String phone = Validation.inputString("Enter phone: ");

        return new Passenger(pcode, name, phone);
    }

    public void addLast(Passenger x) {
        Node<Passenger> p = new Node<>(x);

        if (head == null) {
            head = tail = p;
        } else {
            tail.next = p;
            tail = p;
        }
    }

    public void inputAndAddLast() {
        addLast(inputInforPassenger());
        System.out.println("Add passenger successfully!");
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Passenger list is empty!");
            return;
        }

        System.out.printf("%-10s %-20s %-15s\n",
                "Pcode", "Name", "Phone");

        Node<Passenger> p = head;

        while (p != null) {
            System.out.println(p.info);
            p = p.next;
        }
    }

    public Node<Passenger> searchByPcode(String pcode) {
        Node<Passenger> p = head;

        while (p != null) {
            if (p.info.getPcode().equalsIgnoreCase(pcode)) {
                return p;
            }
            p = p.next;
        }

        return null;
    }

    public Passenger findPassengerById(String pcode) {
        Node<Passenger> p = head;

        while (p != null) {
            if (p.info.getPcode().equalsIgnoreCase(pcode)) {
                return p.info;
            }

            p = p.next;
        }

        return null;
    }

    public void searchByPcode() {
        String pcode = Validation.inputString("Enter pcode to search: ");

        Node<Passenger> p = searchByPcode(pcode);

        if (p == null) {
            System.out.println("Passenger not found!");
        } else {
            System.out.print("Passenger found: ");
            System.out.println(p.info);
        }
    }

    public void deleteByPcode() {
        if (isEmpty()) {
            System.out.println("Passenger list is empty!");
            return;
        }

        String pcode = Validation.inputString("Enter pcode to delete: ");

        if (head.info.getPcode().equalsIgnoreCase(pcode)) {
            head = head.next;

            if (head == null) {
                tail = null;
            }

            System.out.println("Delete passenger successfully!");
            return;
        }

        Node<Passenger> prev = head;
        Node<Passenger> curr = head.next;

        while (curr != null) {
            if (curr.info.getPcode().equalsIgnoreCase(pcode)) {
                prev.next = curr.next;

                if (curr == tail) {
                    tail = prev;
                }

                System.out.println("Delete passenger successfully!");
                return;
            }

            prev = curr;
            curr = curr.next;
        }

        System.out.println("Passenger not found!");
    }

    public void searchByName() {
        if (isEmpty()) {
            System.out.println("Passenger list is empty!");
            return;
        }

        String name = Validation.inputString("Enter name to search: ");
        boolean found = false;

        Node<Passenger> p = head;

        while (p != null) {
            if (p.info.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(p.info);
                found = true;
            }

            p = p.next;
        }

        if (!found) {
            System.out.println("Passenger not found!");
        }
    }

    public void saveToFile(String filename) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(filename));

            Node<Passenger> p = head;

            while (p != null) {
                Passenger x = p.info;

                pw.println(
                        x.getPcode() + "|"
                        + x.getName() + "|"
                        + x.getPhone()
                );

                p = p.next;
            }

            pw.close();
            System.out.println("Save passenger list successfully!");

        } catch (IOException e) {
            System.out.println("Error saving file!");
        }
    }

    public void loadFromFile(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            head = tail = null;

            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length == 3) {
                    String pcode = parts[0];
                    String name = parts[1];
                    String phone = parts[2];

                    Passenger p = new Passenger(pcode, name, phone);
                    addLast(p);
                }
            }

            br.close();
            System.out.println("Load passenger list successfully!");

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file!");
        }
    }

    public void searchBusesByPcode() {
        String pcode = Validation.inputString("Enter pcode to search: ");

        Node<Passenger> p = searchByPcode(pcode);

        if (p == null) {
            System.out.println("Passenger not found!");
        } else {
            System.out.print("Passenger found: ");
            System.out.println(p.info);

            System.out.println("List buses booked by this passenger:");
            System.out.println("This function needs BookingList to complete.");
        }
    }
}

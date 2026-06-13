package controller;

import java.io.*;
import model.Bus;
import model.Node;
import util.Validation;

public class BusList {

    Node<Bus> head, tail;

    public void menu() {
        int choice;

        do {
            System.out.println("\n========== BUS LIST ==========");
            System.out.println("1. Load data from file");
            System.out.println("2. Input & add to the end");
            System.out.println("3. Display data");
            System.out.println("4. Save bus list to file");
            System.out.println("5. Search by bcode");
            System.out.println("6. Delete by bcode");
            System.out.println("7. Sort by bcode");
            System.out.println("8. Input & add to beginning");
            System.out.println("9. Add after position k");
            System.out.println("10. Delete position k");
            System.out.println("11. Search by passenger name");
            System.out.println("12. Search booked by bcode");
            System.out.println("0. Back");

            choice = Validation.inputInt("Enter your choice: ", 0, 12);

            switch (choice) {
                case 1:
                    loadFromFile("buses.txt");
                    break;

                case 2:
                    inputAndAddLast();
                    break;

                case 3:
                    display();
                    break;

                case 4:
                    saveToFile("buses.txt");
                    break;

                case 5:
                    searchByBcode();
                    break;

                case 6:
                    //deleteByBcode();
                    break;

                case 7:
                    sortAscendingByBcode();
                    break;

                case 8:
                    inputAndAddFirst();
                    break;

                case 9:
                    addAfterPositionK();
                    break;

                case 10:
                    //deletePositionK();
                    break;

                case 11:
                    //searchByPassengerName();
                    break;

                case 12:
                    //searchBookedByBcode();
                    break;

                case 0:
                    System.out.println("Back to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }

    boolean isEmpty() {
        if (head == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCodeExist(String code) {
        Node<Bus> p = head;

        while (p != null) {
            if (p.info.getBcode().equalsIgnoreCase(code)) {
                return true;
            }
            p = p.next;
        }

        return false;
    }

    public Bus inputInforBus() {
        String bcode = Validation.inputBusCode("Enter bus code: ", this);
        String bnum = Validation.inputString("Enter bus number: ");
        String dstation = Validation.inputString("Enter departing station: ");
        String astation = Validation.inputArriveStation("Enter arriving station: ", dstation);

        double dtime = Validation.inputDouble("Enter departing time: ", 0, 24);

        int seat = Validation.inputInt("Enter number of seats: ", 1, Integer.MAX_VALUE);

        int booked = Validation.inputInt("Enter booked seats: ", 0, seat);

        double atime = Validation.inputDouble("Enter arriving time: ", dtime, 24);

        return new Bus(bcode, bnum, dstation, astation, dtime, seat, booked, atime);
    }

    public void addLast(Bus b) {
        Node<Bus> p = new Node<>(b);

        if (head == null) {
            head = tail = p;
        } else {
            tail.next = p;
            tail = p;
        }
    }

    public void inputAndAddFirst() {
        addFirst(inputInforBus());
        System.out.println("Add bus to first list successfully!");
    }

    public void inputAndAddLast() {
        addLast(inputInforBus());
        System.out.println("Add bus to last list successfully!");
    }

    public void display() {
        if (head == null) {
            System.out.println("Bus list is empty!");
            return;
        }

        System.out.printf("%-10s %-10s %-15s %-15s %-8s %-6s %-6s %-8s\n",
                "Code", "Number", "Depart", "Arrive",
                "DTime", "Seat", "Booked", "ATime");

        Node<Bus> p = head;

        while (p != null) {
            System.out.println(p.info);
            p = p.next;
        }
    }

    public Node<Bus> searchByBcode(String bcode) {
        Node<Bus> p = head;

        while (p != null) {
            if (p.info.getBcode().equalsIgnoreCase(bcode)) {
                return p;
            }

            p = p.next;
        }

        return null;
    }

    public void searchByBcode() {
        String bcode = Validation.inputString("Enter bcode to search: "); //********

        Node<Bus> p = searchByBcode(bcode);

        if (p == null) {
            System.out.println("Bus not found!");
        } else {
            System.out.print("Bus found: ");
            System.out.println(p.info);
        }
    }

    public void deleteByBcode() {
        if (isEmpty()) {
            System.out.println("Bus list is empty!");
            return;
        }

        String bcode = Validation.inputString("Enter bcode to delete: ");

        if (head.info.getBcode().equalsIgnoreCase(bcode)) {
            head = head.next;

            if (head == null) {
                tail = null;
            }

            System.out.println("Delete successfully!");
            return;
        }

        Node<Bus> prev = head;
        Node<Bus> curr = head.next;

        while (curr != null) {
            if (curr.info.getBcode().equalsIgnoreCase(bcode)) {
                prev.next = curr.next;

                if (curr == tail) {
                    tail = prev;
                }

                System.out.println("Delete successfully!");
                return;
            }

            prev = curr;
            curr = curr.next;
        }

        System.out.println("Bus not found!");
    }

    void set(Node p, Bus x) {
        if (p != null) {
            p.info = x;
        }
    }

    void swap(Node<Bus> p, Node<Bus> q) {
        Bus tmp = p.info;
        set(p, q.info);
        set(q, tmp);
    }

    public void sortAscendingByBcode() {
        if (isEmpty()) {
            System.out.println("List is empty!");
            System.out.println("You should add Bus to list first.");
        } else {
            for (Node<Bus> i = head; i.next != null; i = i.next) {
                for (Node<Bus> j = i.next; j != null; j = j.next) {
                    if (i.info.getBcode().compareToIgnoreCase(j.info.getBcode()) > 0) {
                        swap(i, j);
                    }
                }
            }

            System.out.println("Sorted successfully!");
        }
    }

    public void addFirst(Bus b) {
        Node<Bus> p = new Node<>(b);

        if (head == null) {
            head = tail = p;
        } else {
            p.next = head;
            head = p;
        }
    }

    public int size() {
        Node p = head;
        int cnt = 0;
        while (p != null) {
            p = p.next;
            cnt++;
        }

        return cnt;
    }

    public Node<Bus> get(int k) {

        Node<Bus> p = head;
        int pos = 1;
        while (pos != k) {
            p = p.next;
            pos++;
        }

        return p;
    }

    public void addAfterPositionK() {
        if (isEmpty()) {
            System.out.println("List is empty!");
            System.out.println("You should add Bus to list first!");
            return;
        }

        int k = Validation.inputInt("Enter position k: ", 1, size());

        Node<Bus> busK = get(k);

        Node<Bus> newNodeBus = new Node<>(inputInforBus());

        newNodeBus.next = busK.next;
        busK.next = newNodeBus;

        if (k == size()) {
            tail = newNodeBus;
        }

        System.out.println("Add after position " + k + " successfully!");
    }

    public void searchBookedByBcode() {
        String bcode = Validation.inputString("Enter bcode to search booked: ");

        Node<Bus> p = searchByBcode(bcode);

        if (p == null) {
            System.out.println("Bus not found!");
        } else {
            System.out.println("Booked seats: " + p.info.getBooked());
        }
    }

    public void saveToFile(String fileName) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(fileName));

            Node<Bus> p = head;

            while (p != null) {
                Bus b = p.info;

                pw.println(
                        b.getBcode() + "|"
                        + b.getBnum() + "|"
                        + b.getDstation() + "|"
                        + b.getAstation() + "|"
                        + b.getDtime() + "|"
                        + b.getSeat() + "|"
                        + b.getBooked() + "|"
                        + b.getAtime()
                );

                p = p.next;
            }

            pw.close();
            System.out.println("Save bus list to file successfully!");

        } catch (IOException e) {
            System.out.println("Error saving file!");
        }
    }

    public void loadFromFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            head = tail = null;

            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length == 8) {
                    String bcode = parts[0];
                    String bnum = parts[1];
                    String dstation = parts[2];
                    String astation = parts[3];
                    double dtime = Double.parseDouble(parts[4]);
                    int seat = Integer.parseInt(parts[5]);
                    int booked = Integer.parseInt(parts[6]);
                    double atime = Double.parseDouble(parts[7]);

                    Bus b = new Bus(bcode, bnum, dstation, astation,
                            dtime, seat, booked, atime);

                    addLast(b);
                }
            }

            br.close();
            System.out.println("Load bus list from file successfully!");

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Error reading file!");
        } catch (NumberFormatException e) {
            System.out.println("File data format is invalid!");
        }
    }
}

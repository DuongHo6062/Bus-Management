package controller;

import model.Node;
import model.Booking;
import model.Bus;
import model.Passenger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Date;

public class BookingList {
    private Node<Booking> head;
    private Node<Booking> tail;

    public BookingList() {
        head = null;
        tail = null;
    }
    
    public boolean isEmpty() {
        return head == null;
    }

    public void addLast(Booking booking) {
        Node<Booking> newNode = new Node<>(booking);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    // --- 3.1. Load data from file ---
    public void loadFromFile(String filename) {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line;
            
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("")) {
                    continue;
                }
                
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String bcode = parts[0].trim();
                    String pcode = parts[1].trim();
                    Date odate = new Date(); 
                    int paid = Integer.parseInt(parts[3].trim());
                    int seat = Integer.parseInt(parts[4].trim());
                    
                    Booking b = new Booking(bcode, pcode, odate, paid, seat);
                    this.addLast(b);
                }
            }
            br.close();
            fr.close();
            System.out.println("Load data tu file thanh cong!");
        } catch (Exception e) {
            System.out.println("Loi doc file: " + e.getMessage());
        }
    }

    // --- 3.2. Book bus  ---
    public void bookBus(BusList busController, Object passengerController) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Nhap ma xe buyt (bcode): ");
        String bcode = sc.nextLine();
        System.out.print("Nhap ma hanh khach (pcode): ");
        String pcode = sc.nextLine();

        
        Node<Bus> busNode = busController.searchByBcode(bcode); 
        if (busNode == null) {
            System.out.println("Loi: Ma xe buyt khong ton tai!");
            return;
        }
        Bus foundBus = busNode.info;

        
        PassengerList pList = (PassengerList) passengerController;
        Passenger foundPassenger = pList.findPassengerById(pcode); 
        if (foundPassenger == null) {
            System.out.println("Loi: Ma hanh khach khong ton tai!");
            return;
        }
        
        System.out.print("Nhap so luong ghe muon dat: ");
        int seatsToBook = Integer.parseInt(sc.nextLine());
        if (seatsToBook <= 0) { 
            System.out.println("Loi: So ghe dat phai lon hon 0!");
            return;
        }

        
        int availableSeats = foundBus.getSeat() - foundBus.getBooked();
        if (seatsToBook > availableSeats) { 
            System.out.println("Loi: So ghe dat vuot qua so ghe con trong cua xe buyt!");
            return;
        }

        
        Booking newBooking = new Booking(bcode, pcode, new Date(), 0, seatsToBook);
        this.addLast(newBooking);
        
 
        int updatedBooked = foundBus.getBooked() + seatsToBook;
        foundBus.setBooked(updatedBooked);

        System.out.println("Dat ve thanh cong!");
    }

    // --- 3.3. Display data ---
    public void display() {
        if (isEmpty()) {
            System.out.println("Danh sach dat ve trong!");
            return;
        }
        
        System.out.println("Ma xe | Ma khach | Ngay dat | Trang thai | So ghe");
        System.out.println("--------------------------------------------------");
        
        Node<Booking> current = head;
        while (current != null) {
            Booking b = current.info;
            System.out.println(b.getBcode() + " | " + b.getPcode() + " | " + b.getOdate() + " | " + b.getPaid() + " | " + b.getSeat());
            current = current.next;
        }
    }

    // --- 3.4. Save booking list to file ---
    public void saveToFile(String filename) {
        try {
            FileWriter fw = new FileWriter(filename);
            PrintWriter pw = new PrintWriter(fw);
            
            Node<Booking> current = head;
            while (current != null) {
                pw.println(current.info.toString());
                current = current.next;
            }
            
            pw.close();
            fw.close();
            System.out.println("Da luu danh sach vao file " + filename);
        } catch (Exception e) {
            System.out.println("Loi ghi file: " + e.getMessage());
        }
    }

    // --- 3.5. Sort by bcode  ---
    public void sortByBcodeAndPcode() {
        if (isEmpty() || head.next == null) {
            return; 
        }

        boolean swapped;
        do {
            swapped = false;
            Node<Booking> current = head;
            
            while (current.next != null) {
                Booking b1 = current.info;
                Booking b2 = current.next.info;

                int compareBcode = b1.getBcode().compareTo(b2.getBcode());
                
                if (compareBcode > 0 || (compareBcode == 0 && b1.getPcode().compareTo(b2.getPcode()) > 0)) {
                    Booking temp = current.info;
                    current.info = current.next.info;
                    current.next.info = temp;
                    
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
        
        System.out.println("Da sap xep danh sach tang dan theo bcode + pcode.");
    }

    // --- 3.6. Pay booking by bcode  ---
    public void payBooking() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma xe buyt can thanh toan: ");
        String bcode = sc.nextLine();
        System.out.print("Nhap ma hanh khach can thanh toan: ");
        String pcode = sc.nextLine();

        Node<Booking> current = head;
        boolean isFound = false;

        while (current != null) {
            if (current.info.getBcode().equals(bcode) && current.info.getPcode().equals(pcode)) {
                isFound = true;
                if (current.info.getPaid() == 0) {
                    current.info.setPaid(1); 
                    System.out.println("Thanh toan thanh cong! (Trang thai da chuyen sang 1)");
                } else {
                    System.out.println("Ve nay da duoc thanh toan tu truoc roi.");
                }
                break;
            }
            current = current.next;
        }

        if (!isFound) {
            System.out.println("Khong tim thay don dat ve hop le!");
        }
    }

    public void menu(BusList busController, Object passengerController) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n========== BOOKING LIST ==========");
            System.out.println("3.1. Load data from file");
            System.out.println("3.2. Book bus");
            System.out.println("3.3. Display data");
            System.out.println("3.4. Save booking list to file");
            System.out.println("3.5. Sort by bcode + pcode");
            System.out.println("3.6. Pay booking by bcode + pcode");
            System.out.println("0. Back to main menu");
            System.out.print("Choose: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    loadFromFile("bookings.txt"); 
                    break;
                case 2:
                    bookBus(busController, passengerController); 
                    break;
                case 3:
                    display(); 
                    break;
                case 4:
                    saveToFile("bookings.txt"); 
                    break;
                case 5:
                    sortByBcodeAndPcode(); 
                    display();
                    break;
                case 6:
                    payBooking(); 
                    break;
                case 0:
                    System.out.println("Quay lai Main Menu...");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (choice != 0);
    }
}
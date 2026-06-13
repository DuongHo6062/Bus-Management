    package view;

    import controller.BusList;
    import controller.PassengerList;
    import controller.BookingList;
    import java.util.Scanner;

    public class Main {

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);

            BusList busList = new BusList();
            PassengerList passengerList = new PassengerList();
            BookingList bookingList = new BookingList();

            int choice;

            do {
                System.out.println("========== BUS BOOKING SYSTEM ==========");
                System.out.println("1. Bus list");
                System.out.println("2. Passenger list");
                System.out.println("3. Booking list");
                System.out.println("0. Exit");
                System.out.print("Choose: ");

                choice = Integer.parseInt(sc.nextLine());
                
                switch (choice) {
                    case 1:
                        busList.menu();
                        break;
                    case 2:
                        passengerList.menu();
                        break;
                    case 3:
                        bookingList.menu(busList, passengerList);
                        break;
                }
            } while (choice != 0);
        }
    }

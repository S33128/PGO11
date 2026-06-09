import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();
        students.add(new Student("S001","Zbigniew Wodecki","12c",120));
        students.add(new Student("S002","Michał Wiśniewski","11a",40));
        students.add(new Student("S003","Zenon Martyniuk","10b",0));

        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(new LaptopSet("E001","Lenovo ThinkPad Lab",80,32,true));
        equipmentList.add(new LaptopSet("E002","Dell XPS Demo",100,16,false));
        equipmentList.add(new CameraKit("E003","Sony Conent Kit",90,3,true));
        equipmentList.add(new CameraKit("E004","Canon Interview Kit",70,1,true));

        DiscountPolicy discountPolicy = new LoyaltyDiscountPolicy();
        ReservationService service = new ReservationService(students,equipmentList,discountPolicy);
        int choice;
        do{
            printMenu();
            System.out.println("\nWybor: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Podaj poprawny numer");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> showStudents(service);
                case 2 -> showEquipment(service);
                case 3 -> createReservation(service,scanner);
                case 4 -> returnEquipment(service,scanner);
                case 5 -> service.printActiveReservations();
                case 6 -> service.printReport();
                case 0 -> System.out.println("\nZakonczono program");
                default -> System.out.println("\nNiepoprawna opcja");
            }
        }while (choice != 0);
        scanner.close();
    }
    private static void printMenu() {
        System.out.println("\n==============================");
        System.out.println("MEDIA LAB");
        System.out.println("==============================");
        System.out.println("1. Wyswietl studentow");
        System.out.println("2. Wyswietl sprzet");
        System.out.println("3. Utworz rezerwacje");
        System.out.println("4. Zwroc sprzet");
        System.out.println("5. Aktywne rezerwacje");
        System.out.println("6. Raport");
        System.out.println("0. Zakoncz");
    }
    private static void showStudents(ReservationService service) {
        System.out.println();
        System.out.println("Lista studentow");
        for(Student student : service.getStudents()) {
            System.out.println(student);
        }
    }
    private static void showEquipment(ReservationService service) {
        System.out.println();
        System.out.println("Lista sprzetu");
        for(Equipment equipment : service.getEquipmentsList()){
            System.out.println(equipment.getDisplayText());
        }
    }
    private static void createReservation(ReservationService service, Scanner scanner) {
        System.out.println("\nPodaj id studenta: ");
        String studentId = scanner.nextLine();
        System.out.println("\nPodaj id sprzetu: ");
        String equipmentId = scanner.nextLine();
        System.out.println("Podaj liczbe dni: ");
        while(!scanner.hasNextInt()){
            System.out.println("Podaj poprawna liczbe");
            scanner.next();
        }
        int days = scanner.nextInt();
        scanner.nextLine();
        service.createReservation(studentId,equipmentId,days);
    }
    private static void returnEquipment(ReservationService service, Scanner scanner) {
        System.out.println("\nPodaj id rezerwacji: ");
        String reservationId = scanner.nextLine();
        service.returnEquipment(reservationId);
    }
}
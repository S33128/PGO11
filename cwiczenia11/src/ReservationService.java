import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class ReservationService {
    private List<Student> students;
    private List<Equipment> equipmentList;
    private List<Reservation> reservations;
    private DiscountPolicy discountPolicy;
    private int reservationCounter = 1;
    public ReservationService(List<Student> students, List<Equipment> equipmentsList, DiscountPolicy discountPolicy) {
        this.students = students;
        this.equipmentList = equipmentsList;
        this.discountPolicy = discountPolicy;
        reservations = new ArrayList<>();
    }
    public List<Student> getStudents() {
        return students;
    }
    public List<Equipment> getEquipmentsList() {
        return equipmentList;
    }
    public List<Reservation> getReservations() {
        return reservations;
    }
    public Reservation createReservation(String studentId, String equipmentId, int days) {
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Nie znaleziono studenta");
            return null;
        }
        Equipment equipment = findEquipmentById(equipmentId);
        if (equipment == null) {
            System.out.println("Nie znaleziono sprzęt");
            return null;
        }
        if (!equipment.isAvailable()){
            System.out.println("Sprzęt " + equipmentId + " nie jest dostępny.");
            return null;
        }
        if (days < 1 || days > 14){
            System.out.println("Liczba dni musi być z zakresu 1-14.");
            return null;
        }
        String reservationId = String.format("R%03d", reservationCounter++);
        Reservation reservation = new Reservation(reservationId,student,equipment,days,ReservationStatus.ACTIVE);
        equipment.setAvailable(false);
        reservations.add(reservation);
        double cost = reservation.calculateTotalCost(discountPolicy);
        System.out.println();
        System.out.println("Utworzono rezerwacje" + reservationId);
        System.out.println("Sprzet: " + equipment.getName());
        System.out.println("Koszt:" + cost +  "PLN");
        System.out.println("Status: " + reservation.getStatus());
        return reservation;
    }
    public void returnEquipment(String reservationId) {
        Reservation reservation = findReservationById(reservationId);
        if (reservation == null) {
            System.out.println("Nie znaleziono sprzetu");
            return;
        }
        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            System.out.println("Rezerwacja nie jest aktywna");
            return;
        }
        reservation.setStatus(ReservationStatus.RETURNED);
        reservation.getEquipment().setAvailable(true);
        double totalCost = reservation.calculateTotalCost(discountPolicy);
        int points = (int)(totalCost / 10);
        reservation.getStudent().addLoyaltyPoints(points);
        System.out.println();
        System.out.println("Zwrocono sprzet");
        System.out.println("Student otrzymal: " + points + "punktow lojanlosciowych.");
    }
    public Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }
        return null;
    }
    public Equipment findEquipmentById(String id) {
        for (Equipment equipment : equipmentList) {
            if (equipment.getId().equalsIgnoreCase(id)) {
                return equipment;
            }
        }
        return null;
    }
    public Reservation findReservationById(String id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equalsIgnoreCase(id)) {
                return reservation;
            }
        }
        return null;
    }
    public List<Equipment> findAvaibleEquipment() {
        List<Equipment> result = new ArrayList<>();
        for (Equipment equipment : equipmentList) {
            if(equipment.isAvailable()){
                result.add(equipment);
            }
        }
        return result;
    }
    public void printActiveReservations() {
        System.out.println();
        System.out.println("Aktywne rezerwacje");
        boolean found = false;
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.ACTIVE) {
                System.out.println(reservation.getDisplayText());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Brak aktywnych rezerwacji");
        }
    }
    public void printReport(){
        System.out.println();
        System.out.println("RAPORT");
        double revenue = 0;
        boolean found = false;
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.RETURNED) {
                System.out.println(reservation.getDisplayText());
                revenue += reservation.calculateTotalCost(discountPolicy);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Brak zakonczonych rezerwacji");
        }
        System.out.println("Łączny przychod: "+ revenue + " PLN");
        Student bestStudent = students.stream().max(Comparator.comparingInt(Student::getLoyaltyPoints)).orElse(null);
        if (bestStudent != null) {
            System.out.println("Najwiecej punktow posiada:");
            System.out.println(bestStudent.getFullName() + " (" + bestStudent.getLoyaltyPoints() + " pkt)");
        }
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class ReservationService {
    private List<Student> students;
    private List<Equipment> equipmentsList;
    private List<Reservation> reservations;
    private DiscountPolicy discountPolicy;
    private int reservationCounter = 1;
    public ReservationService(List<Student> students, List<Equipment> equipmentsList, DiscountPolicy discountPolicy) {
        this.students = students;
        this.equipmentsList = equipmentsList;
        this.discountPolicy = discountPolicy;
        reservations = new ArrayList<>();
    }
    public List<Student> getStudents() {
        return students;
    }
    public List<Equipment> getEquipmentsList() {
        return equipmentsList;
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
        if (equipment == null) {
            System.out.println("Nie znaleziono sprzęt");
            return null;
        }
        if (!equipment.isAvaible()){
            System.out.println("Sprzęt " + equipmentId + " nie jest dostępny.");
            return null;
        }
        if (days < 1 || days > 14){
            System.out.println("Liczba dni musi być z zakresu 1-14.");
            return null;
        }
        String reservationId = String.format("R%03d", reservationCounter++);
        Reservation reservation = new Reservation(reservationId,student,equipment,days,ReservationStatus.ACTIVE);
        equipment.setAvaible(false);
        reservations.add(reservation);
        double cost = reservation.calculateTotalCost(discountPolicy);
        System.out.println();
        System.out.println("Utworzono rezerwacje" + reservationId);
        System.out.println("Sprzet: " + equipment.getName());
        return reservation;
    }
    public void returnEquipment(String reservationId) {}
}

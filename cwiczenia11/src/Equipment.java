public abstract class Equipment implements Displayable{
    private String id;
    private String name;
    private double baseDailyPrice;
    private boolean avaible;

    public Equipment(String id, String name, double baseDailyPrice, boolean avaible) {
        this.id = id;
        this.name = name;
        this.baseDailyPrice = baseDailyPrice;
        this.avaible = true;
    }
    public abstract double calculateDailyPrice();
    public abstract String getDetails();
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getBaseDailyPrice() {
        return baseDailyPrice;
    }
    public boolean isAvailable() {
        return avaible;
    }
    public void setAvailable(boolean avaible) {
        this.avaible = avaible;
    }
    @Override
    public String getDisplayText() {
        return String.format("%s | %s | %s | %.2f PLM/dzień | %s | %s",
                id, name, getClass().getSimpleName(),calculateDailyPrice(),
                avaible ? "Dostępny" : "Niedostępny",
                getDetails()
        );
    }
}

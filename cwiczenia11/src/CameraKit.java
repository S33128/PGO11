public class CameraKit extends Equipment{
    private int lensCount;
    private boolean hasTripod;

    public CameraKit(String id, String name, double baseDailyPrice, int lensCount, boolean hasTripod) {
        super(id, name, baseDailyPrice);
        this.lensCount = lensCount;
        this.hasTripod = hasTripod;
    }
    @Override
    public double calculatedPrice(){
        return getBaseDailyPrice() + (lensCount * 10) + (hasTripod ? 15 : 0);
    }
    @Override
    public String getDetails(){
        return lensCount + " Obiektyw, " + (hasTripod ? "statyw" : "bez statywu");
    }
}

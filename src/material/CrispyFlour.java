package material;
import java.time.LocalDate;

public class CrispyFlour extends Material {
    private int quantity;

    public CrispyFlour(String id, String name, LocalDate manufacturingDate, int cost, int quantity) {
        super(id, name, manufacturingDate, cost);
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getAmount() {
        return (double) quantity * getCost();
    }

    @Override
    public LocalDate getExpiryDate() {
        return getManufacturingDate().plusDays(1);
    }


    @Override
    public double getRealMoney() {
       LocalDate today = LocalDate.now();
       LocalDate expiryDate = getExpiryDate();
       double discountRate = 0;
       int daysLeft = expiryDate.getDayOfYear() - today.getDayOfYear();
       if(expiryDate.getYear() > today.getYear()) {
           daysLeft += (today.isLeapYear()? 366 : 365);
       }
       if (daysLeft < 0){
           discountRate = 0;
       } else if (daysLeft <= 60){ // Còn <= 2 tháng (ước lượng)
           discountRate = 0.40; //  Giảm 40%
       } else if (daysLeft <= 120){
           discountRate = 0.20;
       } else {
           discountRate = 0.05;
       }
       return getAmount() * (1 - discountRate);
    }

    @Override
    public String toString() {
        return super.toString()+
                "|" +
                "|" + quantity +
                "|" + getExpiryDate() +
                "|" + getAmount() +
                "|" + getRealMoney() +
                '|';
    }
}

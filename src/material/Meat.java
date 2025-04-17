package material;

import java.time.LocalDate;

public class Meat extends Material {
    private double weight;

    public Meat(String id, String name, LocalDate manufacturingDate, int cost,double weight) {
        super(id, name, manufacturingDate, cost);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public double getAmount() {
        return weight * getCost();
    }

    @Override
    public LocalDate getExpiryDate() {
        return getManufacturingDate().plusDays(7);
    }

    @Override
    public double getRealMoney() {
        LocalDate today = LocalDate.now();
        LocalDate expiryDate = getExpiryDate();
        double discountRate = 0;
        int dayLeft = expiryDate.getDayOfYear() - today.getDayOfYear();
        if (expiryDate.getYear() > today.getYear()) {
            dayLeft += (today.isLeapYear() ? 366 : 365);
        }
        if (dayLeft < 0){
            dayLeft = 0;
        } else if (dayLeft <= 5){
            discountRate = 0.30;
        }else {
            discountRate = 0.10;
        }
        return getAmount() * (1 - discountRate);
    }

    @Override
    public String toString() {
        return super.toString()+
                "CrispyFlour{" +
                ", weight=" + weight +
                ", expiryDate=" + getExpiryDate() +
                ",amount=" + getAmount() +
                ", realMoney=" + getRealMoney() +
                '}';
    }
}

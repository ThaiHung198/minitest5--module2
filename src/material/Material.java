package material;

import discount.Discount;

import java.time.LocalDate;


public abstract class Material implements Discount {
    private String id;
    private String name;
    private LocalDate manufacturingDate;
    private int cost;

    public Material(String id, String name, LocalDate manufacturingDate, int cost) {
        this.id = id;
        this.name = name;
        this.manufacturingDate = manufacturingDate;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    public abstract double getAmount();

    public abstract LocalDate getExpiryDate();

    @Override
    public abstract double getRealMoney();

    @Override
    public String toString() {
        return
                "Sản phẩm |" +
                "|" + id + '\'' +
                "|'" + name + '\'' +
                "|" + manufacturingDate +
                "|" + cost +
                '|';
    }

}

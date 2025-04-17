package manager;

import material.CrispyFlour;
import material.Material;
import material.Meat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List; // Sử dụng List và ArrayList là cơ bản

public class MaterialManager {
    // Danh sách để lưu trữ các đối tượng Material
    private List<Material> materials;

    // Constructor: Khởi tạo danh sách và dữ liệu mẫu
    public MaterialManager() {
        materials = new ArrayList<>();
        initializeSampleData(); // Gọi hàm tạo dữ liệu mẫu
    }

    private void initializeSampleData() {
        materials.add(new CrispyFlour("F001", "Bột chiên giòn A", LocalDate.now().minusMonths(11), 15000, 10));
        materials.add(new CrispyFlour("F002", "Bột mỳ đa dụng B", LocalDate.now().minusMonths(7), 12000, 20));
        materials.add(new CrispyFlour("F003", "Bột gạo C", LocalDate.now().minusMonths(9), 10000, 15));
        materials.add(new CrispyFlour("F004", "Bột năng D", LocalDate.now().minusMonths(6), 18000, 5));
        materials.add(new CrispyFlour("F005", "Bột chiên xù E", LocalDate.now().minusMonths(10), 20000, 12));

        materials.add(new Meat("M001", "Thịt heo ba chỉ", LocalDate.now().minusDays(3), 150000, 1.5));
        materials.add(new Meat("M002", "Thịt bò thăn", LocalDate.now().minusDays(1), 250000, 0.8));
        materials.add(new Meat("M003", "Thịt gà đùi", LocalDate.now().minusDays(5), 80000, 2.0));
        materials.add(new Meat("M004", "Thịt vịt", LocalDate.now().minusDays(0), 90000, 1.2));
        materials.add(new Meat("M005", "Cá hồi fillet", LocalDate.now().minusDays(6), 350000, 0.5));
        System.out.println("Đã khởi tạo dữ liệu mẫu.");
    }

    public void addMaterial(Material material) {
        // Kiểm tra đầu vào cơ bản
        if (material == null || material.getId() == null || material.getId().isEmpty()) {
            System.out.println("Lỗi: Thông tin vật liệu không hợp lệ.");
            return;
        }

        // Kiểm tra ID trùng lặp bằng vòng lặp đơn giản
        boolean idExists = false;
        for (Material existingMaterial : materials) {
            // So sánh ID bằng phương thức equals() của String
            if (existingMaterial != null && material.getId().equals(existingMaterial.getId())) {
                idExists = true;
                break;
            }
        }

        if (idExists) {
            System.out.println("Lỗi: Vật liệu với ID '" + material.getId() + "' đã tồn tại.");
        } else {
            materials.add(material); // Thêm vào danh sách
            System.out.println("Đã thêm thành công: " + material.getName());
        }
    }

    public void editMaterial(String id, Material updatedMaterialData) {
        // Kiểm tra dữ liệu đầu vào
        if (id == null || id.isEmpty() || updatedMaterialData == null || !id.equals(updatedMaterialData.getId())) {
            System.out.println("Lỗi: ID cần sửa hoặc dữ liệu cập nhật không hợp lệ.");
            return;
        }

        int indexToEdit = -1; // Biến lưu vị trí cần sửa
        for (int i = 0; i < materials.size(); i++) {
            Material current = materials.get(i);
            if (current != null && id.equals(current.getId())) {
                indexToEdit = i; // Lưu lại index
                break;
            }
        }

        if (indexToEdit != -1) { // Nếu tìm thấy
            Material existingMaterial = materials.get(indexToEdit);

            // Chỉ cập nhật nếu loại đối tượng giống nhau
            if (existingMaterial.getClass().equals(updatedMaterialData.getClass())) {
                // Cập nhật thông tin chung dùng setters
                existingMaterial.setName(updatedMaterialData.getName());
                existingMaterial.setManufacturingDate(updatedMaterialData.getManufacturingDate());
                existingMaterial.setCost(updatedMaterialData.getCost());

                // Cập nhật thông tin riêng dùng instanceof và ép kiểu
                if (existingMaterial instanceof CrispyFlour) {
                    ((CrispyFlour) existingMaterial).setQuantity(((CrispyFlour) updatedMaterialData).getQuantity());
                } else if (existingMaterial instanceof Meat) {
                    ((Meat) existingMaterial).setWeight(((Meat) updatedMaterialData).getWeight());
                }
                System.out.println("Đã cập nhật vật liệu ID: " + id);
            } else {
                System.out.println("Lỗi: Không thể sửa đổi loại vật liệu (ví dụ: từ Bột sang Thịt).");
            }
        } else {
            System.out.println("Lỗi: Không tìm thấy vật liệu với ID '" + id + "' để sửa.");
        }
    }
// Hàm xóa
    public void deleteMaterial(String id) {
        if (id == null || id.isEmpty()) {
            System.out.println("Lỗi: ID cần xóa không hợp lệ.");
            return;
        }

        int indexToDelete = -1; // Tìm vị trí cần xóa
        for (int i = 0; i < materials.size(); i++) {
            Material current = materials.get(i);
            if (current != null && id.equals(current.getId())) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete != -1) { // Nếu tìm thấy
            materials.remove(indexToDelete); // Xóa khỏi danh sách bằng index
            System.out.println("Đã xóa vật liệu ID: " + id);
        } else { // Nếu không tìm thấy
            System.out.println("Lỗi: Không tìm thấy vật liệu với ID '" + id + "' để xóa.");
        }
    }
// Phương thức hiển thị
    public void displayAllMaterials() {
        System.out.println("\n--- DANH SÁCH VẬT LIỆU ---");
        System.out.println("|Loại |\t ID |\t Name |\t ManufacturingDate |\t Cost |\t Quantity |\t Amount |\t Real Money |\n");
        System.out.println("--------------------------------------------------------------------------------------------------");
        if (materials.isEmpty()) {
            System.out.println("Danh sách hiện đang trống.");
            return;
        }
        for (Material material : materials) {
            if (material != null) {
                // In thông tin bằng cách gọi toString() của đối tượng
                // toString() trong lớp con của bạn đã bao gồm cả tiền chiết khấu
                System.out.println(material);
            }
        }
        System.out.println("-------------------------");
    }
// phương thức tính chenh lệch
    public double calculateTotalDiscountDifferenceToday() {
        double totalAmount = 0;        // Tổng tiền gốc
        double totalRealMoney = 0;     // Tổng tiền sau chiết khấu

        // Dùng vòng lặp for-each để tính tổng
        for (Material material : materials) {
            if (material != null) {
                totalAmount += material.getAmount();      // Gọi getAmount()
                totalRealMoney += material.getRealMoney(); // Gọi getRealMoney()
            }
        }
        return totalAmount - totalRealMoney;
    }

    // Phương thức lấy danh sách
    public List<Material> getMaterials() {
        return materials;
    }
}
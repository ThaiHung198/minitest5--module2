package main;// Đặt trong package phù hợp, ví dụ: package main;
// Hoặc bỏ dòng package nếu bạn không dùng package

import manager.MaterialManager;
import material.CrispyFlour;
import material.Material;
import material.Meat;

import java.time.LocalDate;
import java.time.format.DateTimeParseException; // Để bắt lỗi parse ngày
import java.util.InputMismatchException;     // Để bắt lỗi nhập sai kiểu số
import java.util.Scanner;

public class Main {
    // Khai báo static để các hàm trong Main dùng chung
    private static MaterialManager manager = new MaterialManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            displayMenu();             // Hiển thị menu
            choice = getUserChoice();    // Lấy lựa chọn
            processUserChoice(choice); // Xử lý lựa chọn
        } while (choice != 0);

        System.out.println("Đóng ứng dụng.");
        scanner.close(); // Đóng Scanner
    }

    private static void displayMenu() {
        System.out.println("\n===== MENU QUẢN LÝ VẬT LIỆU =====");
        System.out.println("1. Hiển thị danh sách vật liệu");
        System.out.println("2. Thêm vật liệu mới");
        System.out.println("3. Sửa thông tin vật liệu");
        System.out.println("4. Xóa vật liệu");
        System.out.println("5. Tính tổng chênh lệch chiết khấu hôm nay");
        System.out.println("0. Thoát");
        System.out.println("===================================");
        System.out.print("Nhập lựa chọn của bạn: ");
    }
// phương thúc lấy lựa chọn
    private static int getUserChoice() {
        int choice = -1; // Giá trị mặc định nếu nhập sai
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Lỗi: Vui lòng nhập một số nguyên.");
        } finally {
            scanner.nextLine(); // Luôn đọc bỏ dòng mới sau nextInt() hoặc khi lỗi
        }
        return choice;
    }

    // Phương thức xử lý lựa chọn
    private static void processUserChoice(int choice) {
        switch (choice) {
            case 1:
                manager.displayAllMaterials(); // Gọi hàm hiển thị của manager
                break;
            case 2:
                handleAddMaterial(); // Gọi hàm xử lý thêm
                break;
            case 3:
                handleEditMaterial(); // Gọi hàm xử lý sửa
                break;
            case 4:
                handleDeleteMaterial(); // Gọi hàm xử lý xóa
                break;
            case 5:
                displayTotalDiscountDifference(); // Gọi hàm tính và hiển thị chênh lệch
                break;
            case 0:
                System.out.println("Đang thoát...");
                break;
            case -1: // Lỗi nhập liệu từ getUserChoice
                // Không cần làm gì, vòng lặp sẽ hiển thị lại menu
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                break;
        }
    }

    //Hàm xử lý thêm vật liệu
    private static void handleAddMaterial() {
        try {
            System.out.println("--- Thêm Vật Liệu Mới ---");
            System.out.print("Chọn loại vật liệu (1: Bột, 2: Thịt): ");
            int type = Integer.parseInt(scanner.nextLine()); // Đọc cả dòng rồi parse

            System.out.print("Nhập ID: ");
            String id = scanner.nextLine();
            System.out.print("Nhập Tên: ");
            String name = scanner.nextLine();
            System.out.print("Hạn sử dụng (DD-MM-YYYY): ");
            LocalDate manuDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Nhập Giá (cost): ");
            int cost = Integer.parseInt(scanner.nextLine());

            Material newMaterial = null;
            if (type == 1) { // Bột
                System.out.print("Nhập Số lượng (quantity): ");
                int quantity = Integer.parseInt(scanner.nextLine());
                newMaterial = new CrispyFlour(id, name, manuDate, cost, quantity);
            } else if (type == 2) { // Thịt
                System.out.print("Nhập Khối lượng (weight): ");
                double weight = Double.parseDouble(scanner.nextLine());
                newMaterial = new Meat(id, name, manuDate, cost, weight);
            } else {
                System.out.println("Loại vật liệu không hợp lệ.");
                return;
            }

            manager.addMaterial(newMaterial); // Gọi manager để thêm

        } catch (NumberFormatException e) { // Lỗi khi nhập số
            System.out.println("Lỗi nhập liệu: Giá, số lượng, hoặc khối lượng phải là số hợp lệ.");
        } catch (DateTimeParseException e) { // Lỗi khi nhập ngày
            System.out.println("Lỗi nhập liệu: Ngày phải đúng định dạng YYYY-MM-DD.");
        } catch (Exception e) { // Các lỗi khác
            System.out.println("Đã xảy ra lỗi khi thêm: " + e.getMessage());
        }
    }

    //  Hàm xử lý sửa vật liệu
    private static void handleEditMaterial() {
        try {
            System.out.println("--- Sửa Vật Liệu ---");
            System.out.print("Nhập ID vật liệu cần sửa: ");
            String idToEdit = scanner.nextLine();

            // Tìm vật liệu hiện có trong danh sách của manager
            Material existingMaterial = null;
            // Dùng for-each để tìm
            for (Material mat : manager.getMaterials()) {
                if (mat != null && mat.getId().equals(idToEdit)) {
                    existingMaterial = mat;
                    break;
                }
            }

            if (existingMaterial == null) { // Nếu không tìm thấy
                System.out.println("Không tìm thấy vật liệu với ID: " + idToEdit);
                return;
            }

            System.out.println("Nhập thông tin mới (Để trống nếu không muốn thay đổi):");

            // Nhập tên mới
            System.out.print("Tên mới [" + existingMaterial.getName() + "]: ");
            String newName = scanner.nextLine();
            if (newName.trim().isEmpty()) newName = existingMaterial.getName(); // Giữ tên cũ nếu trống

            // Nhập ngày mới
            System.out.print("Ngày sản xuất mới (YYYY-MM-DD) [" + existingMaterial.getManufacturingDate() + "]: ");
            String dateStr = scanner.nextLine();
            LocalDate newManuDate = existingMaterial.getManufacturingDate(); // Giữ ngày cũ
            if (!dateStr.trim().isEmpty()) newManuDate = LocalDate.parse(dateStr); // Parse nếu có nhập

            // Nhập giá mới
            System.out.print("Giá mới [" + existingMaterial.getCost() + "]: ");
            String costStr = scanner.nextLine();
            int newCost = existingMaterial.getCost(); // Giữ giá cũ
            if (!costStr.trim().isEmpty()) newCost = Integer.parseInt(costStr); // Parse nếu có nhập

            // Tạo đối tượng tạm để chứa dữ liệu mới, ID giữ nguyên
            Material updatedData = null;

            // Nhập thuộc tính riêng dựa trên loại của đối tượng "hiện có"
            if (existingMaterial instanceof CrispyFlour) {
                CrispyFlour existingFlour = (CrispyFlour) existingMaterial;
                System.out.print("Số lượng mới [" + existingFlour.getQuantity() + "]: ");
                String quantStr = scanner.nextLine();
                int newQuantity = existingFlour.getQuantity(); // Giữ số lượng cũ
                if (!quantStr.trim().isEmpty()) newQuantity = Integer.parseInt(quantStr);
                updatedData = new CrispyFlour(idToEdit, newName, newManuDate, newCost, newQuantity); // Tạo đối tượng mới với dữ liệu cập nhật
            } else if (existingMaterial instanceof Meat) {
                Meat existingMeat = (Meat) existingMaterial;
                System.out.print("Khối lượng mới [" + existingMeat.getWeight() + "]: ");
                String weightStr = scanner.nextLine();
                double newWeight = existingMeat.getWeight(); // Giữ khối lượng cũ
                if (!weightStr.trim().isEmpty()) newWeight = Double.parseDouble(weightStr);
                updatedData = new Meat(idToEdit, newName, newManuDate, newCost, newWeight); // Tạo đối tượng mới
            } else {
                System.out.println("Lỗi: Không thể xác định loại vật liệu.");
                return;
            }

            // Gọi hàm sửa của manager
            manager.editMaterial(idToEdit, updatedData);

        } catch (NumberFormatException e) {
            System.out.println("Lỗi nhập liệu: Vui lòng nhập đúng định dạng số.");
        } catch (DateTimeParseException e) {
            System.out.println("Lỗi nhập liệu: Vui lòng nhập ngày đúng định dạng YYYY-MM-DD.");
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi khi sửa: " + e.getMessage());
        }
    }

    // Hàm xử lý xóa vật liệu
    private static void handleDeleteMaterial() {
        try {
            System.out.println("--- Xóa Vật Liệu ---");
            System.out.print("Nhập ID vật liệu cần xóa: ");
            String idToDelete = scanner.nextLine();
            manager.deleteMaterial(idToDelete); // Gọi manager để xóa
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi khi xóa: " + e.getMessage());
        }
    }

    //  Hàm hiển thị tổng chênh lệch chiết khấu
    private static void displayTotalDiscountDifference() {
        try {
            System.out.println("\n--- [5] Tổng Chênh Lệch Chiết Khấu Hôm Nay ---");
            double difference = manager.calculateTotalDiscountDifferenceToday();
            System.out.printf("Tổng số tiền chiết khấu áp dụng hôm nay: %,.0f\n", difference);
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi khi tính toán: " + e.getMessage());
        }
    }
}
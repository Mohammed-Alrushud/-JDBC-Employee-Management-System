import java.sql.*;
import java.util.Scanner;

public class Employees {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String url = "jdbc:mariadb://localhost:3306/Project2";
        String user = "root";
        String pwd = "";

        try {
            Connection con = DriverManager.getConnection(url, user, pwd);
            Statement stmt = con.createStatement();

            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1) Insert a new employee");
                System.out.println("2) Display all the employees");
                System.out.println("3) Give yearly raises");
                System.out.println("4) Exit");
                System.out.print("Choose an operation: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    while (true) {
                        System.out.println("\nInserting a new employee:");

                        System.out.print("EmployeeID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Employee name: ");
                        String name = scanner.nextLine();

                        System.out.print("Salary: ");
                        int salary = scanner.nextInt();

                        System.out.print("Sales: ");
                        int sales = scanner.nextInt();
                        scanner.nextLine();

                        String insertSQL = "INSERT INTO EMPLOYEES VALUES(" + id + ", '" + name + "', " + salary + ", " + sales + ")";
                        stmt.executeUpdate(insertSQL);

                        System.out.print("Insert another record (Y/N)?: ");
                        String again = scanner.nextLine();
                        if (!again.equalsIgnoreCase("Y")) break;
                    }

                } else if (choice == 2) {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");

                    System.out.println("\nAll Employees:");
                    System.out.println("EmployeeID  | Name | Salary | Sales");

                    while (rs.next()) {
                        int id = rs.getInt("EmployeeID");
                        String name = rs.getString("Name");
                        double salary = rs.getDouble("Salary");
                        double sales = rs.getDouble("Sales");
                        System.out.println(id + " | " + name + " | " + salary + " | " + sales);
                    }

                } else if (choice == 3) {
                    System.out.print("Enter sales goal: ");
                    double goal = scanner.nextDouble();
                    scanner.nextLine();

                    ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");

                    System.out.println("\nYearly Raises:");
                    System.out.println("EmployeeID  | Name  | Old Salary  | New Salary");

                    while (rs.next()) {
                        int id = rs.getInt("EmployeeID");
                        String name = rs.getString("Name");
                        double oldSalary = rs.getDouble("Salary");
                        double sales = rs.getDouble("Sales");

                        double newSalary = (sales >= goal) ? oldSalary * 1.10 : oldSalary * 1.05;

                        String updateSQL = "UPDATE EMPLOYEES SET Salary = " + newSalary + " WHERE EmployeeID = " + id;
                        stmt.executeUpdate(updateSQL);

                        System.out.println(id + " | " + name + " | " + oldSalary + " | " + newSalary);
                    }

                } else if (choice == 4) {
                    System.out.println("Exiting program...");
                    break;
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            }

            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


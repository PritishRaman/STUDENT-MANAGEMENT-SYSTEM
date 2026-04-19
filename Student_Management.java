package std_mgmt;
import java.util.*;
import java.sql.*;

public class StudentMgmt {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/students",
                "root", "root"
            );

            Statement stmt = con.createStatement();
            Scanner sc = new Scanner(System.in);

            int ans = 1, ch;
            String first_name, last_name, course_major, phone_no, dob;
            double gpa;
            int roll_no;
            String query = "";

            do {
                System.out.println("\n1. Insert");
                System.out.println("2. Delete");
                System.out.println("3. Update");
                System.out.println("4. Display");
                System.out.println("5. Exit");
                System.out.println("Enter choice:");

                ch = sc.nextInt();

                switch (ch) {

                case 1:
                    System.out.println("Enter roll number:");
                    roll_no = sc.nextInt();

                    System.out.println("Enter first name:");
                    first_name = sc.next();

                    System.out.println("Enter last name:");
                    last_name = sc.next();

                    System.out.println("Enter course major:");
                    course_major = sc.next();

                    System.out.println("Enter DOB (yyyy-mm-dd):");
                    dob = sc.next();

                    System.out.println("Enter phone number:");
                    phone_no = sc.next();

                    System.out.println("Enter GPA:");
                    gpa = sc.nextDouble();

                    query = "INSERT INTO students VALUES (" + roll_no + ",'" + first_name + "','" 
                            + last_name + "','" + course_major + "','" + dob + "','" 
                            + phone_no + "'," + gpa + ")";

                    stmt.executeUpdate(query);
                    System.out.println("Inserted...");
                    break;

                case 2:
                    System.out.println("Enter roll number:");
                    roll_no = sc.nextInt();

                    query = "DELETE FROM students WHERE roll_no=" + roll_no;
                    stmt.executeUpdate(query);

                    System.out.println("Deleted...");
                    break;

                case 3:
                    PreparedStatement ps = con.prepareStatement(
                        "UPDATE students SET first_name=?, last_name=?, gpa=? WHERE roll_no=?"
                    );

                    System.out.println("Enter roll number:");
                    roll_no = sc.nextInt();

                    System.out.println("Enter new first name:");
                    first_name = sc.next();

                    System.out.println("Enter new last name:");
                    last_name = sc.next();

                    System.out.println("Enter new GPA:");
                    gpa = sc.nextDouble();

                    ps.setString(1, first_name);
                    ps.setString(2, last_name);
                    ps.setDouble(3, gpa);
                    ps.setInt(4, roll_no);

                    ps.executeUpdate();
                    System.out.println("Updated...");
                    break;

                case 4:
                    ResultSet rs = stmt.executeQuery("SELECT * FROM students");

                    while (rs.next()) {
                        System.out.println(
                            rs.getInt("roll_no") + " " +
                            rs.getString("first_name") + " " +
                            rs.getString("last_name") + " " +
                            rs.getString("course_major") + " " +
                            rs.getDate("dob") + " " +
                            rs.getString("phone_no") + " " +
                            rs.getDouble("gpa")
                        );
                    }
                    break;

                case 5:
                    ans = 0;
                    break;

                default:
                    System.out.println("Invalid choice");
                }

            } while (ans == 1);

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
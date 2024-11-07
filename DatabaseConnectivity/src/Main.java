import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	public static void main(String[] args)throws SQLException {
		String url = "jdbc:mysql://localhost:3306/demo";
		String username = "root";
		String password = "Mahesh@2006";
		Connection con = DriverManager.getConnection(url,username,password);
		if(con !=null) {
			System.out.println("Connection Established");
		}
		else {
			System.out.println("Connection Not Established");
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the user Details:");
		String name = sc.nextLine();
		String pass = sc.nextLine();
		int id = sc.nextInt();
		
		String sql = "Insert into users(username,password,userid) values(?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,name);
		ps.setString(2,pass);
		ps.setInt(3,id);
		
		int res = ps.executeUpdate();
		if(res > 0) {
			System.out.println("A new user created");
		}
		String query = "select * from users"; 
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			System.out.println("Username: "+rs.getString("username"));
			System.out.println("Password: "+rs.getString("Password"));
			System.out.println("UserId: "+rs.getString("userid"));
			
		}
		
		// Update user information
        System.out.println("Enter the User ID of the user you want to update:");
        int updateId = sc.nextInt();
        sc.nextLine(); // consume the newline
        System.out.print("Enter the new username: ");
        String newUsername = sc.nextLine();
        System.out.print("Enter the new password: ");
        String newPassword = sc.nextLine();

        String sqlUpdate = "UPDATE users SET username = ?, password = ? WHERE userid = ?";
        PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
        psUpdate.setString(1, newUsername);
        psUpdate.setString(2, newPassword);
        psUpdate.setInt(3, updateId);

        int updateRes = psUpdate.executeUpdate();
        if (updateRes > 0) {
            System.out.println("User updated successfully.");
        } else {
            System.out.println("User not found.");
        }

        // Delete user
        System.out.println("Enter the User ID of the user you want to delete:");
        int deleteId = sc.nextInt();

        String sqlDelete = "DELETE FROM users WHERE userid = ?";
        PreparedStatement psDelete = con.prepareStatement(sqlDelete);
        psDelete.setInt(1, deleteId);

        int deleteRes = psDelete.executeUpdate();
        if (deleteRes > 0) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found.");
        }

        sc.close();
        con.close();
	}
}

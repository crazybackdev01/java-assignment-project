import  java.sql.*;


public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/ebookshop";
    private static final String user = "root";

    private static final String password = "10#14__RU#14#10";
    public static void main(String[] args) {
        try {
        Class.forName("com.mysql.cj.jdbc.Driver"); // Loading Driver for MySQL
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " + rs.getString("title"));
            }
            rs.close();
            stmt.close();
            conn.close();


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }
}
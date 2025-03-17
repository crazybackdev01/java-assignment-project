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
            Statement readStmt = conn.createStatement();
            ResultSet readResult = readStmt.executeQuery("SELECT * FROM books");
            while (readResult.next()) {
                System.out.println(readResult.getInt("id") + " - " + readResult.getString("title"));
            }
            readResult.close();
            readStmt.close();
            conn.close();


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }
}
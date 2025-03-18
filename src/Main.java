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
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String preparedInsertQuery = "INSERT INTO books(id, title, author, price, qty) VALUES(?, ?, ?, ?, ?)"; // SQL query is compiling only once when using PreparedStatement object
            PreparedStatement preparedStatement = connection.prepareStatement(preparedInsertQuery);

            preparedStatement.setInt(1, 1005);
            preparedStatement.setString(2,"Master Microservices");
            preparedStatement.setString(3, "Jack Murphy");
            preparedStatement.setFloat(4, 13.4f);
            preparedStatement.setInt(5, 14);

            int rowsAffected = preparedStatement.executeUpdate();
            //String readQuery =  "SELECT * FROM books";
            //String insertQuery = String.format("INSERT INTO books(id, title, author, price, qty) VALUES(%o,'%s', '%s', %f, %o)",5, "Master Microservices", "Jack sparrow", 111.2, 14);
            //String updateQuery = String.format("UPDATE books SET author = '%s' WHERE id = %d", "Jack daaku", 5);
            //String deleteQuery = "DELETE FROM books WHERE id = 5";
//            ResultSet readResult = statement.executeQuery(readQuery);
//            while (readResult.next()) {
//                System.out.println(readResult.getInt("id") + " - " + readResult.getString("title"));
//            }
//            readResult.close();
//            int insertRowsAffected = statement.executeUpdate(insertQuery);
//            int updateRowsAffected = statement.executeUpdate(updateQuery);
//            int deletedRowsAffected = statement.executeUpdate(deleteQuery);
                if(rowsAffected > 0){
                System.out.println("data inserted successfully");
                }else {
                System.out.println("Data not inserted");
                }
                statement.close();
                connection.close();


                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }


    }
}
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.Scanner;


public class Assignment {
    private static final String url = "jdbc:mysql://localhost:3306/ebookshop";
    private static final String user = "root";

    private static final String password = "10#14__RU#14#10";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void retrieveBooks(Connection connection){
        try {
            String query = "SELECT * FROM books";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                System.out.println("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title") +
                        ", Author: " + rs.getString("author") + ", Price: " + rs.getFloat("price") +
                        ", Quantity: " + rs.getInt("qty"));
            }
            rs.close();
            statement.close();
        }catch(SQLException error){
            error.printStackTrace();
            System.out.println(error.getMessage());
        }
    }
    public static void insertBook(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book price: ");
        float price = scanner.nextFloat();
        System.out.print("Enter book quantity: ");
        int qty = scanner.nextInt();

        String query = "INSERT INTO books (id, title, author, price, qty) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setFloat(4, price);
            pstmt.setInt(5, qty);
            pstmt.executeUpdate();
            System.out.println("Book inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBook(Connection connection){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book ID to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter new price: ");
        float price = scanner.nextFloat();
        System.out.print("Enter new quantity: ");
        int qty = scanner.nextInt();

        String query = "UPDATE books SET price = ?, qty = ? WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, price);
            preparedStatement.setInt(2, qty);
            preparedStatement.setInt(3, id);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book updated successfully!");
                retrieveBookById(connection,id);
            } else {
                System.out.println("Book ID not found!");
            }
        }catch(SQLException error){
            error.printStackTrace();
        }
    }

    public static void retrieveBookById(Connection connection, int id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                System.out.println("Updated Record - ID: " + rs.getInt("id") + ", Title: " + rs.getString("title") +
                        ", Author: " + rs.getString("author") + ", Price: " + rs.getFloat("price") +
                        ", Quantity: " + rs.getInt("qty"));
            }
        } catch(SQLException error){
            error.printStackTrace();
        }
    }

    public static void deleteBook(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book ID to delete: ");
        int id = scanner.nextInt();
        String query = "DELETE FROM books WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Book deleted successfully!");
            } else {
                System.out.println("Book ID not found!");
            }
            retrieveBooks(connection);
        } catch (SQLException error){
            error.printStackTrace();
        }
    }

    public static void useCachedRowSet(Connection connection) {
        try {
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet crs = factory.createCachedRowSet();
            crs.setUrl(url);
            crs.setUsername(user);
            crs.setPassword(password);
            crs.setCommand("SELECT * FROM books");
            crs.execute();

            while (crs.next()) {
                System.out.println("ID: " + crs.getInt("id") + ", Title: " + crs.getString("title") +
                        ", Author: " + crs.getString("author") + ", Price: " + crs.getFloat("price") +
                        ", Quantity: " + crs.getInt("qty"));
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter book ID to update price: ");
            int id = scanner.nextInt();
            System.out.print("Enter new price: ");
            float newPrice = scanner.nextFloat();

            crs.beforeFirst();
            while (crs.next()) {
                if (crs.getInt("id") == id) {
                    crs.updateFloat("price", newPrice);
                    crs.updateRow();
                    System.out.println("Book price updated successfully!");
                    break;
                }
            }

            crs.acceptChanges(getConnection());
            retrieveBooks(connection);
        }catch (SQLException error){
            error.printStackTrace();
        }
    }

    public static void useFilteredRowSet() {
        try {
            RowSetFactory rsf = RowSetProvider.newFactory();
            FilteredRowSet frs = rsf.createFilteredRowSet();
            frs.setUrl(url);
            frs.setUsername(user);
            frs.setPassword(password);
            frs.setCommand("SELECT * FROM books");
            frs.execute();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter minimum price to filter books: ");
            float minPrice = scanner.nextFloat();

            frs.beforeFirst();
            while (frs.next()) {
                if (frs.getFloat("price") > minPrice) {
                    System.out.println("ID: " + frs.getInt("id") + ", Title: " + frs.getString("title") +
                            ", Author: " + frs.getString("author") + ", Price: " + frs.getFloat("price") +
                            ", Quantity: " + frs.getInt("qty"));
                }
            }
        }catch(SQLException error){
            error.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try{
            Connection connection = getConnection();
//            System.out.println("Database connection successfull"); // Question-1
            retrieveBooks(connection); // Question-2
            insertBook(connection);   // Question-3
            updateBook(connection);   // Question-4
            deleteBook(connection);   // Question-5
            useCachedRowSet(connection); // Question-6
            useFilteredRowSet(); // Question-7
            connection.close();
            System.out.println("Database connection closed");
        }catch(SQLException error){
            error.printStackTrace();
            System.out.println(error.getMessage());
        }
    }
}

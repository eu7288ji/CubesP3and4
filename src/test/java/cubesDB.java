import java.sql.*;

public class cubesDB {

static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/cubes";
    static final String USER = "cj";
    static final String PASSWORD = "password";

    public static void main(String[] args) {

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class;" +
                    " check you have drivers and classpath configured correctly");
            cnfe.printStackTrace();
            System.exit(-1);
        }

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
                Statement statement = conn.createStatement()){

            String createTableSQL = "CREATE TABLE IF NOT EXISTS cubes (cube_solver varchar(50), how_long double)";
            statement.executeUpdate(createTableSQL);
            System.out.println("Created cubes table");

            String addDataSQL = "INSERT INTO cubes VALUES ('Cubestormer II robot', 5.270)";
            statement.executeUpdate(addDataSQL);

            addDataSQL = "INSERT INTO cubes VALUES ('Fakhri Raihaan (using his feet)', 27.93)";
            statement.executeUpdate(addDataSQL);

            addDataSQL = "INSERT INTO cubes VALUES ('Ruxin Liu (age 3)', 99.33)";
            statement.executeUpdate(addDataSQL);

            addDataSQL = "INSERT INTO cubes VALUES ('Mats Valk (human record holder)', 6.27)";
            statement.executeUpdate(addDataSQL);

            String fetchAllDataSQL = "SELECT * FROM cubes";
            ResultSet rs = statement.executeQuery(fetchAllDataSQL);

            while (rs.next()) {
                String cubeSolver = rs.getString("cube_solver");
                double howLong = rs.getDouble("how_long");
                System.out.println("Cube solver name = " + cubeSolver + "Time to complete = " + howLong);
            }

            String blah = "grant select, insert, update, delete, create, drop on vet.* to 'cj'@'localhost'";
            

            rs.close();
            statement.close();
            conn.close();

        } catch (SQLException se){
            se.printStackTrace();
        }

        System.out.println("Goodbye");
    }
}

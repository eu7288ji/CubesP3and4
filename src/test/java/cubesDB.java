import java.sql.*;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;

public class cubesDB {

static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/cubes";
    static final String USER = "cj";
    static final String PASSWORD = "password";


    public void addEntry (Connection connection) {
        try {
            while (true) {
                ArrayList<String> name = new ArrayList<>();
                ArrayList<Double> time = new ArrayList<>();
                String newEntry = getString("Enter 'Y' to add entry, 'N' for update.");
                if (newEntry.equalsIgnoreCase("y")) {
                    String newName = getString("What was the name of the Cube Solver?");
                    Double newDouble = getDouble("What was their best time?");

                    name.add(newName);
                    time.add(newDouble);

                    String prep = "Insert into cubes values (? , ?)";


                    PreparedStatement insert = connection.prepareStatement(prep);
                    insert.setString(1, newName);
                    insert.setDouble(2, newDouble);
                    insert.executeUpdate();

                } else if (newEntry.equalsIgnoreCase("n"));
                {
                    String newUpdate = getString("Would you like to update? Y or N");

                    if (newUpdate.equalsIgnoreCase("y")) {


                        String upd = "UPDATE cubes SET how_long = ? WHERE cube_solver = ?";

                        String newNameUpdate = getString("What name would you like to enter?");
                        Double newTimeUpdate = getDouble("What new time would you like to enter?");

                        PreparedStatement update = connection.prepareStatement(upd);
                        update.setDouble(1, newTimeUpdate);
                        update.setString(2, newNameUpdate);
                        update.executeUpdate();
                    }
                    else {
                        return;
                    }
                }

            }
        } catch (SQLDataException sqle) {
            System.out.println("Error with the query.");
            System.out.println(sqle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{

        cubesDB cubes = new cubesDB();

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

            //String refresh = "Drop table cubes";
           // statement.executeUpdate(refresh);

            cubes.addEntry(conn);

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

    public static String getString (String question) {
        Scanner input = new Scanner(System.in);

        if (question != null){
            System.out.println(question);
        }
        String entry = input.nextLine();
        return entry;
    }

    public static Double getDouble (String question) {
        Scanner inputDouble = new Scanner(System.in);

        if (question != null) {
            System.out.println(question);
        }
        while (true) {
            try {
                String stringInput = inputDouble.nextLine();
                double enterTime = Double.parseDouble(stringInput);
                if (enterTime >= 0) {
                    return enterTime;
                } else {
                    System.out.println("Please enter positive double");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please type positive number");
            }
        }
    }
}

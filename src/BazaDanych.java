import java.sql.*;

public class BazaDanych {

    // Stałe z parametrami połączenia do bazy danych MySQL
    private static final String DRIVER = "com.mysql.jdbc.Driver";  // Nazwa sterownika JDBC
    private static final String DB_URL = "jdbc:mysql://localhost:3306/AplikacjaFitness"; // URL bazy danych
    private static final String DB_USERNAME = "root";  // Nazwa użytkownika bazy
    private static final String DB_PASSWORD = "";      // Hasło do bazy (puste w tym przypadku)

    private static Connection connection;  // Obiekt połączenia z bazą danych
    private static Statement statement;    // Obiekt do wykonywania zapytań SQL

    // Metoda nawiązująca połączenie z bazą danych
    public static Connection ConnectToDataBase(){
        try{
            // Ładowanie sterownika JDBC
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Próba połączenia do bazy z podanymi parametrami
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            return connection;  // Zwracamy obiekt połączenia
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Jeśli połączenie się nie powiedzie, zwracamy null
    }

    // Metoda wykonująca zapytania SELECT i zwracająca ResultSet z wynikami
    public static ResultSet Zapytanie(String sql){
        try{
            // Jeśli nie ma połączenia, nawiązujemy je i tworzymy obiekt statement
            if(connection == null){
                connection = ConnectToDataBase();
                statement = connection.createStatement();
            }

            // Wykonujemy zapytanie i zwracamy wyniki
            return statement.executeQuery(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;  // W przypadku błędu zwracamy null
    }

    // Metoda wykonująca polecenia SQL typu INSERT, UPDATE, DELETE
    public static void Polecenie(String sql){
        try{
            // Jeśli nie ma połączenia, nawiązujemy je i tworzymy obiekt statement
            if(connection == null){
                connection = ConnectToDataBase();
                statement = connection.createStatement();
            }

            // Wykonujemy polecenie SQL
            statement.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

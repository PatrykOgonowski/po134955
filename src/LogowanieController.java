import java.sql.ResultSet;
import java.sql.SQLException;

public class LogowanieController {
    LogowanieGUI gui; // Referencja do GUI logowania, skąd pobieramy dane i gdzie wyświetlamy komunikaty

    public LogowanieController(LogowanieGUI gui){
        this.gui = gui;
    }

    // Metoda odpowiedzialna za logowanie użytkownika do aplikacji
    public void Zaloguj(){
        // Pobieramy login i hasło wpisane przez użytkownika
        String login = gui.getLogin();
        String haslo = gui.getHaslo();

        // Sprawdzamy, czy pola nie są puste
        if(!login.equals("") && !haslo.equals("")){
            // Tworzymy zapytanie SQL, które wyszukuje użytkownika z podanym loginem i hasłem
            String zapytanie = "SELECT * FROM klienci WHERE login='"+login+"' AND haslo='"+haslo+"';";
            ResultSet rs = BazaDanych.Zapytanie(zapytanie);

            try {
                // Sprawdzamy, czy zapytanie zwróciło jakieś wyniki (czy istnieje taki użytkownik)
                if(rs.isBeforeFirst()){
                    rs.next();  // Przechodzimy do pierwszego wyniku

                    // Pobieramy dane użytkownika z wyników zapytania
                    int id = rs.getInt("id");
                    String imie = rs.getString("imie");
                    int max_kalorie = rs.getInt("max_kalorie");
                    int max_wegle = rs.getInt("max_wegle");
                    int max_tluszcze = rs.getInt("max_tluszcze");
                    int max_bialko = rs.getInt("max_bialko");

                    // Tworzymy obiekt użytkownika na podstawie pobranych danych
                    Uzytkownik uzytkownik = new Uzytkownik(id,imie,max_kalorie,max_wegle,max_tluszcze,max_bialko);

                    // Otwieramy główne GUI aplikacji, przekazując zalogowanego użytkownika
                    new AplikacjaGUI(uzytkownik);

                    // Zamykamy okno logowania
                    gui.dispose();
                }
                else{
                    // Jeśli brak wyników (niepoprawne dane), wyświetlamy komunikat o błędzie
                    gui.UstawTekst("Podane dane są nieprawidłowe!");
                }
            } catch (SQLException e) {
                // W przypadku problemów z bazą danych rzucamy wyjątek
                throw new RuntimeException(e);
            }
        }
        else{
            // Jeśli któreś pole jest puste, wyświetlamy ten sam komunikat błędu
            gui.UstawTekst("Podane dane są nieprawidłowe!");
        }
    }
}

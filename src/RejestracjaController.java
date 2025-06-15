import java.sql.ResultSet;
import java.sql.SQLException;

public class RejestracjaController {
    RejestracjaGUI gui; // Referencja do GUI rejestracji - pobieramy z niego dane i ustawiamy komunikaty

    public RejestracjaController(RejestracjaGUI gui){
        this.gui = gui;
    }

    // Główna metoda do obsługi rejestracji nowego użytkownika
    public void Zarejestruj(){
        // Pobieramy dane wpisane przez użytkownika z formularza GUI
        String login = gui.getLogin();
        String haslo = gui.getHaslo();
        String imie = gui.getImie();
        String wzrost = gui.getWzrost();
        String waga = gui.getWaga();
        String wiek = gui.getWiek();

        // Sprawdzamy, czy wszystkie pola są wypełnione i czy wybrano płeć oraz cel
        if(!login.equals("") && !haslo.equals("") && !imie.equals("") && !wzrost.equals("") && !waga.equals("") && !wiek.equals("")
                && !gui.getGroupPlec().isSelected(null) && !gui.getGroupCel().isSelected(null)){

            gui.setInfoText(""); // Czyszczenie komunikatu info

            // Sprawdzamy, czy login jest dostępny (nie istnieje już w bazie)
            if (CzyLoginWolny(login)) {

                // Obliczamy dzienne zapotrzebowanie kaloryczne i rozkład makroskładników
                int max_kalorie = ObliczKalorie(wzrost,waga,wiek);
                int max_bialko = (int) (max_kalorie * 0.25 / 4);    // 25% kalorii na białko, 1g białka = 4 kcal
                int max_tluszcze = (int) (max_kalorie * 0.25 / 9);  // 25% kalorii na tłuszcze, 1g tłuszczu = 9 kcal
                int max_wegle = (int) (max_kalorie * 0.50 / 4);     // 50% kalorii na węglowodany, 1g węgli = 4 kcal

                // Budujemy polecenie SQL do wstawienia nowego użytkownika do tabeli klienci
                String polecenie = "INSERT INTO klienci(login,haslo,imie,max_kalorie,max_bialko,max_tluszcze,max_wegle) " +
                        "VALUES('"+login+"','"+haslo+"','"+imie+"'," + max_kalorie + "," + max_bialko +"," + max_tluszcze + "," + max_wegle + ");";

                BazaDanych.Polecenie(polecenie);  // Wykonujemy polecenie w bazie danych

                new LogowanieGUI();  // Otwieramy GUI logowania po udanej rejestracji
                gui.dispose();       // Zamykamy okno rejestracji

            }
            else{
                gui.setInfoText("Podany login jest już zajęty!"); // Informujemy, że login jest niedostępny
            }
        }
        else
        {
            gui.setInfoText("Wprowadzone dane są niepoprawne!"); // Komunikat o niepełnych lub błędnych danych
        }

    }

    // Metoda sprawdzająca czy login nie jest już zajęty przez innego użytkownika
    public boolean CzyLoginWolny(String login){
        String zapytanie = "SELECT * FROM klienci WHERE login='"+login+"';";
        ResultSet rs = BazaDanych.Zapytanie(zapytanie);

        try {
            // rs.isBeforeFirst() zwraca true jeśli jest co najmniej jeden wynik
            // Zwracamy true jeśli nie ma wyników (login wolny), false jeśli jest już taki login
            if(!rs.isBeforeFirst()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);  // Rzucamy wyjątek dalej, jeśli coś poszło nie tak z bazą
        }
    }

    // Metoda obliczająca dzienne zapotrzebowanie kaloryczne na podstawie wzrostu, wagi i wieku
    public int ObliczKalorie(String wzrost, String waga, String wiek){
        int plec;
        int cel;
        int wiekInt = Integer.parseInt(wiek);       // Parsujemy wiek do int
        double wzrostDouble = Double.parseDouble(wzrost); // Parsujemy wzrost (w cm) do double
        double wagaDouble = Double.parseDouble(waga);     // Parsujemy wagę do double
        double cpm;  // całkowita przemiana materii (CPM) - podstawowe zapotrzebowanie kaloryczne

        // Sprawdzamy, która płeć została zaznaczona w GUI
        if(gui.getMezczyznaButton().isSelected()){
            plec = 1;  // 1 = mężczyzna
        }
        else{
            plec = 0;  // 0 = kobieta
        }

        // Sprawdzamy, jaki cel został zaznaczony (schudnąć czy utrzymać wagę)
        if(gui.getSchudnacButton().isSelected()){
            cel = 1;
        }
        else{
            cel = 0;
        }

        // Obliczamy CPM według wzoru Mifflina-St Jeor z mnożnikiem aktywności 1.375 (lekka aktywność)
        if(plec == 1){
            cpm = (10 * wagaDouble + 6.25 * wzrostDouble - 5 * wiekInt + 5) * 1.375;
        }
        else{
            cpm = (10 * wagaDouble + 6.25 * wzrostDouble - 5 * wiekInt - 161) * 1.375;
        }

        // Jeśli celem jest schudnięcie, zmniejszamy CPM o 20%
        if(cel == 1){
            double kalorie = cpm * 0.8;
            return (int) kalorie;
        }

        // Jeśli celem jest utrzymanie wagi lub inny, zwiększamy CPM o 10%
        double kalorie = cpm * 1.1;
        return (int) kalorie;

    }

}

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AplikacjaController {

    private final AplikacjaGUI gui;
    Uzytkownik uzytkownik;
    LocalDate data;  // Przechowuje aktualnie wybrany dzień, dla którego wyświetlamy posiłki
    int licznikDniDoTylu = 0;  // Licznik dni cofniętych w historii (do obsługi nawigacji w kalendarzu)

    AplikacjaController(AplikacjaGUI gui, Uzytkownik uzytkownik){
        this.gui = gui;
        this.uzytkownik = uzytkownik;
        this.data = LocalDate.now();  // Domyślnie ustawiamy dzień na dzisiaj
    }

    // Zwraca tekst z sumą spożytych kalorii i limitem użytkownika
    public String iloscKalorii(){
        int suma = 0;
        String zapytanie = "SELECT SUM(kalorie) as suma_kalorii FROM posilki WHERE klient_id=" + uzytkownik.id + " AND data = '" + data + "'";
        ResultSet rs = BazaDanych.Zapytanie(zapytanie);

        try {
            if(rs.next()){
                suma = rs.getInt("suma_kalorii");  // Pobieramy sumę kalorii z wyniku zapytania
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Zwracamy format "spożyto / limit"
        return suma + "/" + uzytkownik.max_kalorie;
    }

    // Podobnie jak iloscKalorii(), ale dla węglowodanów
    public String iloscWegli(){
        int suma = 0;
        String zapytanie = "SELECT SUM(wegle) as suma_wegli FROM posilki WHERE klient_id=" + uzytkownik.id + " AND data = '" + data + "'";
        ResultSet rs = BazaDanych.Zapytanie(zapytanie);

        try {
            if(rs.next()){
                suma = rs.getInt("suma_wegli");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return suma + "/" + uzytkownik.max_wegle;
    }

    // Pobiera sumę tłuszczów spożytych danego dnia
    public String iloscTluszczy(){
        int suma = 0;
        String zapytanie = "SELECT SUM(tluszcze) as suma_tluszczy FROM posilki WHERE klient_id=" + uzytkownik.id + " AND data = '" + data + "'";
        ResultSet rs = BazaDanych.Zapytanie(zapytanie);

        try {
            if(rs.next()){
                suma = rs.getInt("suma_tluszczy");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return suma + "/" + uzytkownik.max_tluszcze;
    }

    // Pobiera sumę białka spożytego danego dnia
    public String iloscBialka(){
        int suma = 0;
        String zapytanie = "SELECT SUM(bialko) as suma_bialka FROM posilki WHERE klient_id=" + uzytkownik.id + " AND data = '" + data + "'";
        ResultSet rs = BazaDanych.Zapytanie(zapytanie);

        try {
            if(rs.next()){
                suma = rs.getInt("suma_bialka");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return suma + "/" + uzytkownik.max_bialko;
    }

    // Przesuwa wyświetlany dzień o jeden wstecz (cofa się w historii)
    public void CofnijDzien(){
        data = data.minusDays(1); // Odejmij jeden dzień od aktualnej daty
        licznikDniDoTylu++;       // Zwiększ licznik dni do tyłu
        OdswiezCaleGui();         // Odśwież interfejs, by pokazać dane dla nowej daty
    }

    // Przesuwa wyświetlany dzień o jeden do przodu (jeśli to możliwe)
    public void DzienDalej(){
        data = data.plusDays(1);  // Dodaj jeden dzień do aktualnej daty
        licznikDniDoTylu--;       // Zmniejsz licznik dni do tyłu
        OdswiezCaleGui();         // Odśwież GUI
    }

    // Obsługa kliknięcia przycisku dodawania posiłku
    public void DodajPosilekButton(){
        new DodawaniePosilkowGUI(this.uzytkownik, data);  // Otwórz nowe okno dodawania posiłku
        gui.dispose();  // Zamknij obecne okno aplikacji
    }

    // Usuwa wybrany posiłek z bazy i listy GUI
    public void UsunPosilekButton(){
        int index = gui.getListaPosilkow().getSelectedIndex(); // Pobierz indeks zaznaczonego posiłku
        int id = gui.getListModel().get(index).GetId();        // Pobierz id posiłku z modelu

        // Zapytanie SQL do usunięcia posiłku z bazy
        String polecenie = "DELETE FROM posilki WHERE id = " + id + " AND data = '" + data + "'";
        BazaDanych.Polecenie(polecenie);

        // Usuń element z modelu listy, żeby odświeżyć widok
        gui.getListModel().remove(index);

        // Aktualizuj podsumowanie kalorii i makroskładników
        gui.UstawKalorie();
        gui.UstawMakro();
    }

    // Odświeża listę posiłków w GUI na podstawie aktualnie wybranego dnia
    public void OdswiezListePosilkow(){
        gui.getListModel().clear(); // Wyczyść obecną listę posiłków

        List<Posilek> lista = PobierzPosilkiZBazy();  // Pobierz posiłki z bazy
        for(Posilek posilek : lista){
            gui.getListModel().addElement(posilek); // Dodaj każdy posiłek do modelu listy
        }
    }

    // Pobiera listę posiłków z bazy danych dla użytkownika i wybranej daty
    public List<Posilek> PobierzPosilkiZBazy(){
        List<Posilek> lista = new ArrayList<>();

        String zapytanie = "SELECT * FROM posilki WHERE klient_id = " + uzytkownik.id + " AND data = '" + data +"'";
        ResultSet rs = BazaDanych.Zapytanie(zapytanie);

        try {
            while (rs.next()) {
                String nazwa = rs.getString("nazwa");
                int kalorie = rs.getInt("kalorie");
                int bialko = rs.getInt("bialko");
                int tluszcze = rs.getInt("tluszcze");
                int weglowodany = rs.getInt("wegle");
                int id = rs.getInt("id");

                // Utwórz obiekt posiłku z danymi z bazy i aktualną datą
                Posilek p = new Posilek(nazwa, kalorie, weglowodany, tluszcze, bialko, data,id);
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Odświeża cały GUI: listę posiłków, sumy kalorii i makroskładników, oraz opis dnia
    public void OdswiezCaleGui(){
        OdswiezListePosilkow();  // Aktualizuje listę posiłków
        gui.UstawKalorie();      // Aktualizuje wyświetlanie kalorii
        gui.UstawMakro();        // Aktualizuje wyświetlanie makroskładników

        // Jeśli cofamy się w czasie, pokazujemy konkretną datę i włączamy przycisk "Dzień dalej"
        if(licznikDniDoTylu != 0){
            gui.setDzienText(data.toString());
            gui.getDzienDalejButton().setEnabled(true);
        }
        else{
            // Jeśli jesteśmy na dzisiejszym dniu, pokazujemy "Dzisiaj" i wyłączamy przycisk "Dzień dalej"
            gui.setDzienText("Dzisiaj");
            gui.getDzienDalejButton().setEnabled(false);
        }
    }

}

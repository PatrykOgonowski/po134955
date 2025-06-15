import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DodawaniePosilkowController {
    private DodawaniePosilkowGUI gui;
    private Uzytkownik uzytkownik;
    private LocalDate data;

    public DodawaniePosilkowController(DodawaniePosilkowGUI gui, Uzytkownik uzytkownik, LocalDate data){
        this.gui = gui;
        this.uzytkownik = uzytkownik;
        this.data = data;
    }

    public void OdswiezListePosilkow(){
        String sql = "SELECT * FROM zapisaneposilki WHERE klient_id=" + uzytkownik.id;
        ResultSet rs = BazaDanych.Zapytanie(sql);
        DefaultListModel<Posilek> listModel = gui.GetListModel();

        try{
            while(rs.next()){
                String nazwa = rs.getString("nazwa");
                int kalorie = Integer.parseInt(rs.getString("kalorie"));
                double bialko = Double.parseDouble(rs.getString("bialko"));
                double tluszcze = Double.parseDouble(rs.getString("tluszcze"));
                double wegle = Double.parseDouble(rs.getString("wegle"));
                int id = Integer.parseInt(rs.getString("id"));

                //Utwórz obiekt posiłku z pobranych danych oraz przypisaną datą
                Posilek posilek = new Posilek(nazwa,kalorie,wegle,tluszcze,bialko,data,id);

                //Dodaj posiłek do modelu listy w GUI
                listModel.addElement(posilek);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void DodajPosilek(){
        //Pobierz wybrany posiłek z GUI
        Posilek p = gui.GetWybranyPosilek();

        //Dodaj posiłek do bazy danych
        DodajPosilekDoBazyDanych(p);

        //Otwórz główny ekran aplikacji i zamknij aktualne okno
        new AplikacjaGUI(uzytkownik);
        gui.dispose();
    }

    public void DodajPosilekDoBazyDanych(Posilek p){
        //Tworzy polecenie SQL do dodania posiłku do tabeli posilki z przypisaniem do użytkownika i daty
        String polecenie = "INSERT INTO posilki(klient_id, nazwa, kalorie, bialko, wegle, tluszcze, data) VALUES(" + uzytkownik.id + ",'" + p.nazwa
                + "'," + p.kalorie + "," + p.bialko + "," + p.weglowodany + "," + p.tluszcze + ",'" + data + "')";

        //Wykonaj polecenie SQL w bazie danych
        BazaDanych.Polecenie(polecenie);
    }
}

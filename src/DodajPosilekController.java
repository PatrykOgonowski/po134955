import java.time.LocalDate;

public class DodajPosilekController {
    private final DodajPosilekGUI gui;
    Uzytkownik uzytkownik;
    LocalDate data;

    public DodajPosilekController(DodajPosilekGUI gui, Uzytkownik uzytkownik, LocalDate data){
        this.gui = gui;
        this.uzytkownik = uzytkownik;
        this.data = data;
    }

    public void NowyPosilek(){
        String nazwa = gui.getNazwa();
        String kalorieText = gui.getKalorie();
        String wegleText = gui.getWeglowodany();
        String tluszczeText = gui.getTluszcze();
        String bialkoText = gui.getBialko();

        if(nazwa.equals("")||kalorieText.equals("")||wegleText.equals("")||tluszczeText.equals("")||bialkoText.equals("")){
            gui.UstawInfoLabel("Pola nie mogą być puste!");
        }
        else{
            try {
                int kalorie = Integer.parseInt(kalorieText);
                double wegle = Double.parseDouble(wegleText);
                double tluszcze = Double.parseDouble(tluszczeText);
                double bialko = Double.parseDouble(bialkoText);

                if(kalorie<0||wegle<0||tluszcze<0||bialko<0){
                    gui.UstawInfoLabel("Wartości nie mogą być ujemne!");
                    return;
                }

                //Utwórz obiekt posiłku z danymi użytkownika i datą
                Posilek p = new Posilek(nazwa,kalorie,wegle,tluszcze,bialko,this.data,0);
                DodajPosilekDoBazyDanych(p);
                new AplikacjaGUI(uzytkownik);
                gui.dispose();

            } catch (NumberFormatException e) {
                gui.UstawInfoLabel("Wprowadź poprawne liczby!");
            }
        }
    }

    public void DodajPosilekDoBazyDanych(Posilek p){
        //Wstaw posiłek do tabeli posilki powiązanej z użytkownikiem i datą
        String polecenie = "INSERT INTO posilki(klient_id, nazwa, kalorie, bialko, wegle, tluszcze, data) VALUES(" + uzytkownik.id + ",'" + p.nazwa
                + "'," + p.kalorie + "," + p.bialko + "," + p.weglowodany + "," + p.tluszcze + ",'" + data + "')";

        //Wstaw posiłek do tabeli zapisaneposilki
        String polecenie2 = "INSERT INTO zapisaneposilki(klient_id, nazwa, kalorie, bialko, wegle, tluszcze) VALUES(" + uzytkownik.id + ",'" + p.nazwa
                + "'," + p.kalorie + "," + p.bialko + "," + p.weglowodany + "," + p.tluszcze + ")";

        BazaDanych.Polecenie(polecenie);
        BazaDanych.Polecenie(polecenie2);
    }
}

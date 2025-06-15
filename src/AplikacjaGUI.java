import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AplikacjaGUI extends GUI{
    private JLabel welcomeText;
    private JPanel aplikacjaPanel;
    private JList listaPosilkow;
    private DefaultListModel<Posilek> modelListy;
    private JButton dodaj;
    private JButton usun;
    private JLabel kalorieText;
    private JLabel wegleText;
    private JLabel tluszczeText;
    private JLabel bialkoText;
    private JButton dzienWstecz;
    private JButton dzienDalej;
    private JLabel dzienText;
    private String login;
    AplikacjaController controller;

    public AplikacjaGUI(Uzytkownik uzytkownik){
        super("Fitness App", 400,300);
        this.login = login;
        this.controller = new AplikacjaController(this, uzytkownik);

        UstawWelcomeText(uzytkownik.imie);
        UstawKalorie();
        UstawMakro();

        this.modelListy = new DefaultListModel<Posilek>();
        listaPosilkow.setModel(modelListy);
        controller.OdswiezListePosilkow();

        this.setContentPane(aplikacjaPanel);
        this.setVisible(true);

        dzienWstecz.addActionListener(e -> controller.CofnijDzien());
        dzienDalej.addActionListener(e -> controller.DzienDalej());
        dodaj.addActionListener(e -> controller.DodajPosilekButton());
        usun.addActionListener(e -> controller.UsunPosilekButton());
    }

    public void UstawWelcomeText(String imie){
        this.welcomeText.setText("Witaj, " + imie +"!");
    }

    public void UstawKalorie(){
        String kalorie = controller.iloscKalorii();

        this.kalorieText.setText(kalorie);
    }

    public void UstawMakro(){
        String wegle = controller.iloscWegli();
        String tluszcze = controller.iloscTluszczy();
        String bialko = controller.iloscBialka();

        this.wegleText.setText(wegle);
        this.tluszczeText.setText(tluszcze);
        this.bialkoText.setText(bialko);
    }

    public JList getListaPosilkow(){
        return this.listaPosilkow;
    }

    public DefaultListModel<Posilek> getListModel(){
        return this.modelListy;
    }

    public JButton getDzienDalejButton(){
        return this.dzienDalej;
    }

    public JButton getDzienWsteczButton(){
        return this.dzienWstecz;
    }

    public void setDzienText(String tekst){
        this.dzienText.setText(tekst);
    }





}

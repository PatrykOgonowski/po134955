import javax.swing.*;
import java.time.LocalDate;

public class DodajPosilekGUI extends GUI{
    private JTextField nazwaField;
    private JTextField kalorieField;
    private JTextField weglowodanyField;
    private JTextField tluszczeField;
    private JTextField bialkoField;
    private JButton dodajButton;
    private JPanel dodajPosilekPanel;
    private JLabel infoLabel;
    private DodajPosilekController controller;

    public DodajPosilekGUI(Uzytkownik uzytkownik, LocalDate data){
        super("Dodaj Posiłek", 600,300);
        this.controller = new DodajPosilekController(this, uzytkownik, data);

        this.setContentPane(dodajPosilekPanel);
        this.setVisible(true);

        dodajButton.addActionListener(e -> controller.NowyPosilek());
    }

    public String getNazwa(){
        return this.nazwaField.getText();
    }

    public String getKalorie(){
        return this.kalorieField.getText();
    }

    public String getWeglowodany(){
        return this.weglowodanyField.getText();
    }

    public String getTluszcze(){
        return this.tluszczeField.getText();
    }

    public void UstawInfoLabel(String tekst){
        this.infoLabel.setText(tekst);
    }

    public String getBialko(){
        return this.bialkoField.getText();
    }
}

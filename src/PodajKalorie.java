import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PodajKalorie extends GUI{
    private JTextField textField1;
    private JButton akceptujButton;
    private JPanel panelKalorie;
    private JLabel infoText;
    private String login;

    PodajKalorie(String login){
        super("Cel kaloryczny",200,150);
        this.setContentPane(panelKalorie);
        this.setVisible(true);
        Przycisk(login);
    }

    void Przycisk(String login){
        akceptujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String kalorieText = textField1.getText();

                if(!kalorieText.equals("")){
                    int kalorie = Integer.parseInt(textField1.getText());


                    String polecenie = "UPDATE klienci SET max_kalorie="+kalorie+" WHERE login='"+login+"';";
                    BazaDanych.Polecenie(polecenie);

                    new LogowanieGUI();
                    PodajKalorie.this.dispose();
                }
                else{
                    infoText.setText("Podaj cel kaloryczny!");
                }

            }
        });
    }


}

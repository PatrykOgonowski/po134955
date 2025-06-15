import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogowanieGUI extends GUI{

    private JPanel panelLogowania;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton zalogujButton;
    private JLabel rejestracjaLabel;
    private JLabel infoText;
    private LogowanieController controller;

    LogowanieGUI(){
        super("Logowanie", 200,200);
        this.controller = new LogowanieController(this);

        this.zalogujButton.addActionListener(e -> controller.Zaloguj());

        rejestracjaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LogowanieGUI.this.dispose();
                new RejestracjaGUI();
            }
        });

        this.setContentPane(panelLogowania);
        this.setVisible(true);
    }

    public String getLogin(){
        return this.textField1.getText();
    }

    public String getHaslo(){
        return this.passwordField1.getText();
    }

    public void UstawTekst(String tekst){
        this.infoText.setText(tekst);
    }





}

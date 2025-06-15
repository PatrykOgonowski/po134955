import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class DodawaniePosilkowGUI extends GUI{
    private JList posilkiList;
    private DefaultListModel<Posilek> listModel;
    private JPanel panel;
    private JButton dodajButton;
    private JLabel stworzPosilekLabel;
    private DodawaniePosilkowController controller;

    public DodawaniePosilkowGUI(Uzytkownik uzytkownik, LocalDate data){
        super("Dodaj Posiłek!", 400, 400);
        this.controller = new DodawaniePosilkowController(this,uzytkownik,data);

        listModel = new DefaultListModel<>();
        posilkiList.setModel(listModel);
        controller.OdswiezListePosilkow();

        this.setContentPane(panel);
        this.setVisible(true);

        dodajButton.addActionListener(e -> controller.DodajPosilek());

        stworzPosilekLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DodawaniePosilkowGUI.this.dispose();
                new DodajPosilekGUI(uzytkownik,data);
            }
        });
    }

    public DefaultListModel<Posilek> GetListModel(){
        return this.listModel;
    }

    public Posilek GetWybranyPosilek(){
        return (Posilek) posilkiList.getSelectedValue();
    }
}

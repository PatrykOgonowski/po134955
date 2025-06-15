import javax.swing.*;

public class RejestracjaGUI extends GUI{
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton zarejestrujButton;
    private JPanel panelRejestracji;
    private JTextField imieField;
    private JLabel infoText;
    private JRadioButton mezczyznaRadioButton;
    private JRadioButton kobietaRadioButton;
    private JTextField wzrostField;
    private JRadioButton przytycRadioButton;
    private JRadioButton schudnacRadioButton;
    private JTextField wagaField;
    private JTextField wiekField;
    private RejestracjaController controller;
    private ButtonGroup plec;
    private ButtonGroup cel;

    RejestracjaGUI(){
        super("Rejestracja",400,400);
        this.controller = new RejestracjaController(this);
        this.plec = new ButtonGroup();
        this.cel = new ButtonGroup();
        this.plec.add(this.mezczyznaRadioButton);
        this.plec.add(this.kobietaRadioButton);
        this.cel.add(this.przytycRadioButton);
        this.cel.add(this.schudnacRadioButton);
        this.setContentPane(panelRejestracji);
        this.setVisible(true);

        zarejestrujButton.addActionListener(e -> controller.Zarejestruj());
    }

    public String getLogin(){
        return this.loginField.getText();
    }

    public String getHaslo(){
        return this.passwordField.getText();
    }

    public String getImie(){
        return this.imieField.getText();
    }

    public String getWiek(){
        return this.wiekField.getText();
    }

    public String getWaga(){
        return this.wagaField.getText();
    }

    public String getWzrost(){
        return this.wzrostField.getText();
    }

    public JRadioButton getMezczyznaButton(){
        return this.mezczyznaRadioButton;
    }

    public JRadioButton getSchudnacButton(){
        return this.schudnacRadioButton;
    }

    public void setInfoText(String tekst) {
        this.infoText.setText(tekst);
    }

    public ButtonGroup getGroupPlec(){
        return this.plec;
    }

    public ButtonGroup getGroupCel(){
        return this.cel;
    }
}



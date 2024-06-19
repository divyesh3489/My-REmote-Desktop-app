import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SetPassword extends  JFrame implements  ActionListener {
    static String port = "5000";
    private String ValueOfPassword = "";
    JButton Submit;
    JPanel panel;
    JTextField textFieldSetPasword, textField2;
    JLabel labelSetPasword,labelSpace,label2;
    public SetPassword()
    {
        labelSetPasword = new JLabel();
        labelSetPasword.setText("Enter Your password Password");
        textFieldSetPasword = new JTextField(15);
        labelSpace = new JLabel();
        this.setLayout(new BorderLayout());
        Submit = new JButton("Submit");
        panel = new JPanel(new GridLayout(2,1));
        panel.add(labelSetPasword);
        panel.add(textFieldSetPasword);
        panel.add(labelSpace);
        panel.add(Submit);
        add(panel,BorderLayout.CENTER);
        Submit.addActionListener(this);
        setTitle("Set Your Password For Connection");
    }

    public void actionPerformed(ActionEvent e)
    {
        ValueOfPassword = textFieldSetPasword.getText();
        System.out.println(ValueOfPassword);
        dispose();
        new InitConnection(Integer.parseInt(port),ValueOfPassword);
    }
    public String getValueOfPassword()
    {
        return ValueOfPassword;
    }
    public static void main(String[] args) {
        SetPassword setpassowrd = new SetPassword();
        setpassowrd.setSize(300,80);
        setpassowrd.setLocation(500,300);
        setpassowrd.setVisible(true);
    }

}

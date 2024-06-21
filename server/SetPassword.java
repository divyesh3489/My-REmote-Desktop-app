import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class SetPassword extends JFrame implements ActionListener {
    static String port = "5000";
    private String ValueOfPassword = "";
    JButton Submit;
    JPanel panel;
    JPasswordField passwordField;
    JLabel labelSetPassword, labelSpace;

    public SetPassword() {
        labelSetPassword = new JLabel("Enter Your Password:");
        passwordField = new JPasswordField(15);
        labelSpace = new JLabel(" ");

        this.setLayout(new BorderLayout());
        Submit = new JButton("Submit");
        panel = new JPanel(new GridLayout(3, 1));
        panel.add(labelSetPassword);
        panel.add(passwordField);
        panel.add(labelSpace);
        panel.add(Submit);
        add(panel, BorderLayout.CENTER);
        Submit.addActionListener(this);
        setTitle("Set Your Password For Connection");
    }

    public void actionPerformed(ActionEvent e) {
        ValueOfPassword = new String(passwordField.getPassword());
        System.out.println(ValueOfPassword);
        dispose();
        new ConnectionStatusFrame(Integer.parseInt(port), ValueOfPassword);
    }

    public String getValueOfPassword() {
        return ValueOfPassword;
    }

    public static void main(String[] args) {
        SetPassword setPassword = new SetPassword();
        setPassword.setSize(300, 150);
        setPassword.setLocation(500, 300);
        setPassword.setVisible(true);
    }
}

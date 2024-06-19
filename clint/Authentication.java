
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;

public class Authentication extends JFrame implements ActionListener {
    private Socket cSocket = null;
    DataOutputStream  checkPass = null;
    DataInputStream verification = null;
    boolean  verify;
    JButton submit;
    JPanel panel;
    JLabel labelPassword,labelSpace;
    String width = "";
    String height = "";
    JTextField textfieldPassword;
    public Authentication(Socket cSocket)
    {
        this.cSocket = cSocket;
        labelPassword = new JLabel();
        labelPassword.setText("Enter Password");
        textfieldPassword = new JTextField(15);
        labelSpace = new JLabel();
        labelSpace.setText("");
        this.setLayout(new BorderLayout());
        submit = new JButton("Submit");
        panel = new JPanel(new GridLayout(2,1));
        panel.add(labelPassword);
        panel.add(textfieldPassword);
        panel.add(labelSpace);  
        panel.add(submit);
        add(panel,BorderLayout.CENTER);
        submit.addActionListener(this);
        setTitle("Authnticate your self");



    }
    public  void actionPerformed(ActionEvent Passwordaction)
    {   
        String getPasswordvalue = textfieldPassword.getText();
        try{
            checkPass = new DataOutputStream(cSocket.getOutputStream());
            verification = new DataInputStream(cSocket.getInputStream());
            checkPass.writeUTF(getPasswordvalue);
            verify = verification.readBoolean();    
            System.out.println(checkPass);
            System.out.println(verify);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        if (verify)
        {
            try{
                width = verification.readUTF();
                height = verification.readUTF();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            CreateFrame frame = new CreateFrame(cSocket,width,height);
            dispose();
        }
        else
        {
            System.out.println("Please Enter Valid password");
            JOptionPane.showMessageDialog(this,"Password is invalid, Please Enter Valid password","Error",JOptionPane.ERROR);
            dispose();
        }
    }   

}

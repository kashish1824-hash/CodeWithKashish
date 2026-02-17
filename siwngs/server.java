package siwngs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;

public class server extends JFrame implements ActionListener {

    JTextField t;
    static JPanel p1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    server() {
        setSize(400, 500);
        setLocation(100, 100);
        getContentPane().setBackground(Color.pink);
        setLayout(null);

       
        JPanel p = new JPanel();
        p.setBackground(Color.BLACK);
        p.setBounds(0, 0, 400, 50);
        p.setLayout(null);
        add(p);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        JLabel l1 = new JLabel(new ImageIcon(i2));
        l1.setBounds(0, 20, 25, 25);
        p.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/k.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 40, Image.SCALE_DEFAULT);
        JLabel l2 = new JLabel(new ImageIcon(i5));
        l2.setBounds(40, 10, 50, 40);
        p.add(l2);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        JLabel l3 = new JLabel(new ImageIcon(i8));
        l3.setBounds(350, 20, 30, 30);
        p.add(l3);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(25, 30, Image.SCALE_DEFAULT);
        JLabel l4 = new JLabel(new ImageIcon(i11));
        l4.setBounds(300, 20, 25, 30);
        p.add(l4);

        JLabel name = new JLabel("Isha");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 30, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.PLAIN, 10));
        p.add(status);

        
        p1 = new JPanel();
        p1.setBounds(3, 52, 379, 377);
        p1.setLayout(new BorderLayout());
        add(p1);

        
        t = new JTextField();
        t.setBounds(3, 430, 285, 40);
        t.setBackground(Color.pink);
        t.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(t);

       
        JButton b = new JButton("Send");
        b.setBounds(290, 430, 93, 40);
        b.setForeground(Color.black);
        b.setBackground(Color.pink);
        b.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        b.addActionListener(this);
        add(b);

        setVisible(true);
    }

   
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = t.getText().trim();
            if (out.isEmpty()) return;

            JPanel p3 = formatLabel(out);

            JPanel right = new JPanel(new BorderLayout());
            right.add(p3, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(10));

            p1.add(vertical, BorderLayout.PAGE_START);

           
            if (dout != null) {
                dout.writeUTF(out);
            } else {
                System.out.println("Client not connected yet!");
            }

            t.setText("");

            repaint();
            validate();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(Color.lightGray);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new server();

        try {
            ServerSocket skt = new ServerSocket(6001);
            System.out.println(" Server started. Waiting for client...");
            Socket s1 = skt.accept();
            System.out.println(" Client connected!");

            DataInputStream d = new DataInputStream(s1.getInputStream());
            dout = new DataOutputStream(s1.getOutputStream());

            while (true) {
                String msg = d.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                f.validate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

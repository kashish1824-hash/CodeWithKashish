package chatapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import javax.swing.border.*;
import java.util.*;
import java.text.*;

public class client  implements ActionListener {

    JTextField t;
    static JPanel p1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();

    client() {

      
        
        f.setSize(400, 500);
        f.setLocation(100, 100);
        f.getContentPane().setBackground(Color.pink);
        f.setLayout(null);

        
        JPanel p = new JPanel();
        p.setBackground(Color.BLACK);
        p.setBounds(0, 0, 400, 50);
        p.setLayout(null);
        f.add(p);

       
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        JLabel l1 = new JLabel(new ImageIcon(i2));
        l1.setBounds(5, 13, 25, 25);
        p.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

       
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/a.png"));
        Image i5 = i4.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        JLabel l2 = new JLabel(new ImageIcon(i5));
        l2.setBounds(40, 5, 40, 40);
        p.add(l2);

       
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        JLabel l3 = new JLabel(new ImageIcon(i8));
        l3.setBounds(320, 10, 30, 30);
        p.add(l3);

     
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        JLabel l4 = new JLabel(new ImageIcon(i11));
        l4.setBounds(360, 10, 25, 25);
        p.add(l4);

        
        JLabel name = new JLabel("Aisha");
        name.setBounds(100, 8, 150, 20);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(100, 28, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        p.add(status);

       
        p1 = new JPanel();
        p1.setBounds(3, 52, 379, 377);
       
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        f.add(p1);

       
        t = new JTextField();
        t.setBounds(3, 430, 285, 40);
        t.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(t);

       
        JButton b = new JButton("Send");
        b.setBounds(290, 430, 90, 40);
        b.setBackground(Color.pink);
        b.setForeground(Color.BLACK);
        b.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        b.addActionListener(this);
       f.add(b);

       
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String out = t.getText().trim();
            if (out.isEmpty()) return;

            JPanel p3 = formatLabel(out);

            JPanel right = new JPanel(new BorderLayout());
            right.add(p3, BorderLayout.LINE_END);

            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            p1.add(vertical, BorderLayout.PAGE_START);
            p1.revalidate();
            p1.repaint();

            if (dout != null) {
                dout.writeUTF(out);
            }

            t.setText("");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(255, 182, 193));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel(sdf.format(cal.getTime()));
        time.setFont(new Font("Tahoma", Font.PLAIN, 10));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new client();

        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true) {
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);

                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                p1.add(vertical, BorderLayout.PAGE_START);
                p1.revalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


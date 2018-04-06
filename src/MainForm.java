import javax.swing.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainForm extends DataBase {
    private JPanel panel1;
    private JButton button1;
    private JComboBox comboBox1;
    private JTable table1;
    private JButton statusButton;

    public MainForm() throws SQLException {
        DataBase dataBase = new DataBase();
        table1.setModel(showData("SELECT o.id, o.town,  o.representation, o.route,  a.name AS address, g.name AS goods, c.name AS client, t.name AS transport, st.name AS store, s.name AS status  " +
                "FROM orders o INNER JOIN addresses a ON o.address = a.id INNER JOIN goods g ON o.goods = g.id INNER JOIN clients c ON o.client = c.id  INNER JOIN transport t ON o.transport = t.id  " +
                " INNER JOIN stores st ON o.store = st.id INNER JOIN status s ON o.status = s.id"));
        fillCombobox("SELECT status.name FROM status", comboBox1);

        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


            }
        });
    }

    public static void main(String [] args) throws SQLException {
        JFrame frame = new JFrame("App"); //работа с формой
        frame.setContentPane(new MainForm().panel1); // отобразить контент элемента панель
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        MainForm mainForm = new MainForm();

    }
}




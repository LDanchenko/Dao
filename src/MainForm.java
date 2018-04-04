import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainForm extends DataBase {
    private JPanel panel1;
    private JButton button1;
    private JTable table1;

    public MainForm() throws SQLException {
        DataBase dataBase = new DataBase();
        table1.setModel(showData());
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




import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DataBase extends  AbstractController <String, Integer>{


    public DataBase() {


    }

    @Override
    public ResultSet getData(String query) {
        ResultSet rs = null;
        PreparedStatement ps = getPrepareStatement(query);
        try {
           rs = ps.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //closePrepareStatement(ps);
        }
        return rs;

    }

    public DefaultTableModel showData(String query) throws SQLException {
        ResultSet resultSet = getData(query);
        ResultSetMetaData metaData = resultSet.getMetaData();

        DefaultTableModel tableModel = new DefaultTableModel() {
            //чтобы нельзя было редактировать первую колонку с id
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column != 0) {
                    return true;
                } else return false;
            }
        };
        int columnCount = metaData.getColumnCount();
        //добавляем столбы в модель таблицы
        for (int i = 1; i <= columnCount; i++) {
            tableModel.addColumn(metaData.getColumnName(i));
        }
        //добавляем строки в модель таблицы
        String[] row = new String[columnCount];
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = resultSet.getString(i);
            }
            tableModel.addRow(row);
        }
        resultSet.close();

        return tableModel;
    }

    public void fillCombobox (String query, JComboBox comboBox) throws SQLException {
        ResultSet resultSet = getData(query);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
//из резалт сет значения передаем в список
        ArrayList<String> al = new ArrayList<String>();
        while (resultSet.next()) {
            ArrayList<String> record = new ArrayList<String>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String value = resultSet.getString(i);
                al.add(value);

            }

        }
//удаляем повторы
        List<String> deduped = al.stream().distinct().collect(Collectors.toList());
        for (int i = 1; i <= deduped.size(); i++) {
            comboBox.addItem(deduped.get(i - 1));
        }
    }

    public void Filter (JComboBox comboBox, JTable  jTable){

        String parametr = comboBox.getSelectedItem().toString();
        try {
            String s = null;
            ResultSet resultSet = getData("SELECT id FROM status WHERE status.name = '" + parametr + "'");
            if (resultSet.next()) {
                s = resultSet.getString(1);
                //  JOptionPane.showMessageDialog(null, resultSet.getString(1));
            }
            jTable.setModel(showData("SELECT o.id, o.town,  o.representation, o.route,  a.name AS address, g.name AS goods, c.name AS client, t.name AS transport, st.name AS store, s.name AS status  " +
                    "FROM orders o INNER JOIN addresses a ON o.address = a.id INNER JOIN goods g ON o.goods = g.id INNER JOIN clients c ON o.client = c.id  INNER JOIN transport t ON o.transport = t.id  " +
                    " INNER JOIN stores st ON o.store = st.id INNER JOIN status s ON o.status = s.id WHERE o.status = " + s + " "));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(String entity) {
        return false;
    }



}

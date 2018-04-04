import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DataBase extends  AbstractController <String, Integer>{

    public static final String SELECT_ALL_USERS = "SELECT * FROM clients";

    public DataBase() {


    }

    @Override
    public ResultSet getAll() {
        ResultSet rs = null;
        PreparedStatement ps = getPrepareStatement(SELECT_ALL_USERS);
        try {
           rs = ps.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //closePrepareStatement(ps);
        }
        return rs;

    }

    public DefaultTableModel showData() throws SQLException {
        ResultSet resultSet = getAll();
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

    @Override
    public String update(String entity) {
        return null;
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

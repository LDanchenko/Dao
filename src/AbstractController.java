//тут все методы работы с бд
//PREPARE

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;

public abstract class AbstractController<E, K> {
    private Connection connection;
    private final String USERNAME = "root";
    private final String PASSWORD = "llddvvcc5678";
    private final String URL = "jdbc:mysql://localhost:3306/logistic?useSSL=false";

    public AbstractController() {
        Driver driver = null;
        try { //еслинету драйвера
            driver = new FabricMySQLDriver();
        } catch (SQLException ex) {
            System.out.println("Ошибка при создании драйвера!");
            System.exit(0);
        }

        try { //регистрируем драйвер
            DriverManager.registerDriver(driver);
        } catch (SQLException ex) {
            System.out.println("Не удалось зарегистрировать драйвер!");
            System.exit(0);
        }

        try {// пробуем соед
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);


            DatabaseMetaData metaData =  connection.getMetaData(); //проверяем, есть ли таблицы, если нет, создать
            Statement statement = connection.createStatement();


        } catch (SQLException ex) {
            System.out.println("Не удалось создать соединение :(");
            System.exit(0);
        }


    }
    // Возвращения экземпляра Connection в пул соединений
   // public void returnConnection() {
    //    connection = returnConnection(connection);
    //}

    public abstract ResultSet getData(String query);
    public abstract E getEntityById(K id);
    public abstract boolean delete(K id);
    public abstract boolean create(E entity);



    // Получение экземпляра PrepareStatement
    public PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    // Закрытие PrepareStatement
    public void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

import config.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQL {
    private static Connection connection;

    public MySQL() {
        try {
            connection = DatabaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS links (id INTEGER AUTO_INCREMENT, link TEXT, PRIMARY KEY (id));"
            );
            ps.execute();

            ps = connection.prepareStatement(
                            "CREATE TABLE IF NOT EXISTS report (id INTEGER AUTO_INCREMENT, date VARCHAR(255), PRIMARY KEY (id));"
            );
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void write(String link) {
        String sql = "INSERT INTO links (link) VALUES (?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, link);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> read() {
        List<String> links = new ArrayList<>();
        String sql = "SELECT link FROM links";
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT link FROM links");
            ResultSet rs =  ps.executeQuery();
            while (rs.next()) {
                links.add(rs.getString("link"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return links;
    }

    public static class DatabaseConnection {
        public static Connection getConnection() throws SQLException {
            //connection = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(Config.getDbHost(), Config.getDbUser(), Config.getDbPassword());
            } catch (ClassNotFoundException e) {
                System.out.println("Could not connect to database!");
                e.printStackTrace();
            }
            return connection;
        }
    }
}

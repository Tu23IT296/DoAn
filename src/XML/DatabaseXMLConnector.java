package XML;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseXMLConnector {

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/TKPLAYER";
            String userName = "root";
            String password = ""; 
            connection = DriverManager.getConnection(url, userName, password);
            statement = connection.createStatement();

            String sql = "SELECT * FROM TAIKHOAN";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String TenNguoiChoi = resultSet.getString("username");
                String MatKhau = resultSet.getString("password");
                int score = resultSet.getInt("score");

                // Tạo thư mục để lưu trữ tệp XML, nếu chưa tồn tại
                File directory = new File("T");
                if (!directory.exists()) {
                    directory.mkdir(); // Tạo thư mục mới
                }

                // Tạo tên tệp riêng cho mỗi người chơi trong thư mục "T"
                String fileName = "T/" + TenNguoiChoi + ".xml";
                
                // Ghi thông tin người chơi vào tệp riêng biệt
                FileWriter writer = new FileWriter(fileName);
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                writer.write("<player>\n");
                writer.write("\t<username>" + TenNguoiChoi + "</username>\n");
                writer.write("\t<password>" + MatKhau + "</password>\n");
                writer.write("\t<score>" + score + "</score>\n");
                writer.write("</player>");
                writer.close();

                System.out.println("Đã lưu thông tin người chơi vào tệp " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

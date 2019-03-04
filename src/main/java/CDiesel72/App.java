package CDiesel72;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {

    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/mydb?serverTimezone=Europe/Kiev";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "14041707";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();

        ConnectionFactory factory = new ConnectionFactory(
                DB_CONNECTION, DB_USER, DB_PASSWORD
        );

        try (Connection conn = factory.getConnection()) {
            QuarterDAO dao = new QuarterDAO(conn, "quarters");

            while (true) {
                System.out.println("1: Создать таблицу");
                System.out.println("2: Добавить квартиру");
                System.out.println("3: Удалить квартиру");
                System.out.println("4: Все квартиры");
                System.out.println("5: Поиск по району");
                System.out.println("6: Поиск по площади");
                System.out.println("7: Поиск по количеству комнат");
                System.out.println("8: Поиск по цене");
                System.out.print("-> ");

                String s = sc.nextLine();
                System.out.println("------");
                switch (s) {
                    case "1":
                        dao.createTable();
                        break;
                    case "2":
                        dao.add(menu.newQuarter());
                        dao.getAll(Quarter.class);
                        break;
                    case "3":
                        Quarter q = menu.delQuarter(dao);
                        if (q != null) {
                            dao.delete(q, int.class);
                        }
                        dao.getAll(Quarter.class);
                        break;
                    case "4":
                        dao.getAll(Quarter.class);
                        break;
                    case "5":
                        dao.getEquals(Quarter.class, "region", menu.selRegion());
                        break;
                    case "6":
                        List<String> lA = menu.selArea();
                        dao.getRange(Quarter.class, "area", lA.get(0), lA.get(1));
                        break;
                    case "7":
                        dao.getEquals(Quarter.class, "countRooms", menu.selCountRooms());
                        break;
                    case "8":
                        List<String> lP = menu.selPrice();
                        dao.getRange(Quarter.class, "price", lP.get(0), lP.get(1));
                        break;
                    default:
                        return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package CDiesel72;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Diesel on 03.03.2019.
 */
public class Menu {
    private Scanner sc = new Scanner(System.in);

    private String scNotEmpty() {
        String st = null;
        while ((st == null) || (st.isEmpty())) {
            st = sc.nextLine();
        }
        return st;
    }

    public Quarter newQuarter() {
        System.out.println("Введите параметры квартиры:");
        System.out.print("район: ");
        String region = scNotEmpty();
        System.out.print("адрес: ");
        String address = scNotEmpty();
        System.out.print("площадь квартиры: ");
        int area = sc.nextInt();
        System.out.print("количество комнат: ");
        int countRooms = sc.nextInt();
        System.out.print("цена: ");
        int price = sc.nextInt();
        System.out.println("------");
        return new Quarter(region, address, area, countRooms, price);
    }

    public Quarter delQuarter(QuarterDAO dao) {
        System.out.print("Введите ID квартиры: ");
        int id = sc.nextInt();
        System.out.println("------");
        List<Quarter> list = dao.getSelect(Quarter.class, " WHERE id = \"" + id + "\"");
        for (Quarter q : list) {
            if (id == q.getId()) {
                return q;
            }
        }
        return null;
    }

    public String selRegion() {
        System.out.print("Введите название района: ");
        String res = scNotEmpty();
        System.out.println("------");
        return res;
    }

    public List<String> selArea() {
        List<String> list = new ArrayList<>();
        System.out.print("Введите минимальную площадь: ");
        Integer min = sc.nextInt();
        System.out.print("Введите максимальную площадь: ");
        Integer max = sc.nextInt();
        System.out.println("------");
        list.add(min.toString());
        list.add(max.toString());
        return list;
    }

    public String selCountRooms() {
        System.out.print("Введите количество комнат: ");
        Integer res = sc.nextInt();
        System.out.println("------");
        return res.toString();
    }

    public List<String> selPrice() {
        List<String> list = new ArrayList<>();
        System.out.print("Введите минимальную цену: ");
        Integer min = sc.nextInt();
        System.out.print("Введите максимальную цену: ");
        Integer max = sc.nextInt();
        System.out.println("------");
        list.add(min.toString());
        list.add(max.toString());
        return list;
    }
}

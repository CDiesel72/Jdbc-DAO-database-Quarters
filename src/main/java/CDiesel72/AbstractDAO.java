package CDiesel72;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<K, T> {
    private final Connection conn;
    private final String table;

    public AbstractDAO(Connection conn, String table) {
        this.conn = conn;
        this.table = table;
    }

    public Connection getConn() {
        return conn;
    }

    public String getTable() {
        return table;
    }

    public void add(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();

            StringBuilder names = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (Field f : fields) {
                f.setAccessible(true);

                names.append(f.getName()).append(',');
                values.append('"').append(f.get(t)).append("\",");
            }
            names.deleteCharAt(names.length() - 1); // last ','
            values.deleteCharAt(values.length() - 1); // last ','

            String sql = "INSERT INTO " + table + "(" + names.toString() +
                    ") VALUES(" + values.toString() + ")";

            try (Statement st = conn.createStatement()) {
                st.execute(sql.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update(T t, Class<K> k) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = null;

            for (Field f : fields) {
                if (f.isAnnotationPresent(Id.class)) {
                    id = f;
                    id.setAccessible(true);
                    break;
                }
            }
            if (id == null) {
                throw new RuntimeException("No Id field");
            }

            if (id.getType() != k) {
                throw new RuntimeException("Incorrect class Id field");
            }

            StringBuilder set = new StringBuilder();

            for (Field f : fields) {
                if (id.getName() != f.getName()) {
                    f.setAccessible(true);

                    set.append(f.getName()).append(" = \"").append(f.get(t)).append("\",");
                }
            }
            set.deleteCharAt(set.length() - 1);

            String sql = "UPDATE " + table + " SET " + set
                    + " WHERE " + id.getName() + " = \"" + id.get(t) + "\"";

            try (Statement st = conn.createStatement()) {
                st.execute(sql.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete(T t, Class<K> k) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = null;

            for (Field f : fields) {
                if (f.isAnnotationPresent(Id.class)) {
                    id = f;
                    id.setAccessible(true);
                    break;
                }
            }
            if (id == null) {
                throw new RuntimeException("No Id field");
            }

            if (id.getType() != k) {
                throw new RuntimeException("Incorrect class Id field");
            }

            String sql = "DELETE FROM " + table + " WHERE " + id.getName() +
                    " = \"" + id.get(t) + "\"";

            try (Statement st = conn.createStatement()) {
                st.execute(sql.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delAll() {
        try {
            String sql = "DELETE FROM " + table;

            try (Statement st = conn.createStatement()) {
                st.execute(sql.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<T> getSelect(Class<T> cls, String where) {
        List<T> res = new ArrayList<>();

        try {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT * FROM " + table + where)) {
                    ResultSetMetaData md = rs.getMetaData();

                    while (rs.next()) {
                        T t = (T) cls.newInstance();

                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            String columnName = md.getColumnName(i);

                            Field field = cls.getDeclaredField(columnName);
                            field.setAccessible(true);

                            field.set(t, rs.getObject(columnName));
                        }

                        res.add(t);
                    }
                }
            }

            return res;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void getAll(Class<T> cls) {
        toScreen(getSelect(cls, ""));
    }

    public void getEquals(Class<T> cls, String name, String value) {
        toScreen(getSelect(cls, " WHERE " + name + " = \"" + value + "\""));
    }

    public void getRange(Class<T> cls, String name, String min, String max) {
        toScreen(getSelect(cls, " WHERE " + name + " >= \"" + min + "\" AND " +
                name + " <= \"" + max + "\""));
    }

    private void toScreen(List<T> list) {
        if (list.size() > 0) {
            for (T t : list) {
                System.out.println(t);
            }
        } else {
            System.out.println("Нет записей");
        }
        System.out.println("------");
    }
}

package CDiesel72;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Bios on 29.11.2017.
 */
public class QuarterDAO extends AbstractDAO<Integer, Quarter> {
    public QuarterDAO(Connection conn, String table) {
        super(conn, table);
    }

    public void createTable() {
        try (Statement st = getConn().createStatement()) {
            st.execute("DROP TABLE IF EXISTS " + getTable());
            st.execute("CREATE TABLE " + getTable() +
                    " (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    " region VARCHAR(20) NOT NULL," +
                    " address VARCHAR(20) NOT NULL," +
                    " area INT NOT NULL," +
                    " countRooms INT NOT NULL," +
                    " price INT)");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}

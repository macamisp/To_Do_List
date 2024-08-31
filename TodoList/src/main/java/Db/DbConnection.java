package Db;

import model.TODOtask;

import java.util.ArrayList;
import java.util.List;

public class DbConnection {
    private static DbConnection instance;
    private List<TODOtask> connection;
    private DbConnection() {
        connection = new ArrayList<>();
    }
    public List<TODOtask> getConnection() {
        return connection;
    }
    public static DbConnection getInstance() {
        return null == instance ? instance = new DbConnection() : instance;
    }
}

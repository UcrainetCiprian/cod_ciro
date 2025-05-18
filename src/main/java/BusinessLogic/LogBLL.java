package BusinessLogic;

import DataAccess.LogDAO;
import Model.Log;

import java.util.List;

public class LogBLL {
    private final LogDAO logDAO = new LogDAO();

    public void insertLog(Log l){
        logDAO.insert(l);
    }

    public Log getLog(int id) {
        return logDAO.findById(id);
    }
    public List<Log> getAllLogs() {
        return logDAO.findAll();
    }
}

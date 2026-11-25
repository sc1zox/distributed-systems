package exercise6;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataBaseImpl implements DataBase {
    private final ConcurrentHashMap<Integer, String> database = new ConcurrentHashMap<>();

    @Override
    public String getRecord(int index) throws RemoteException {
        return database.get(index);
    }

    @Override
    public void addRecord(int index, String record) throws RemoteException {
        database.put(index, record);
    }

    @Override
    public int getSize() throws RemoteException {
        return database.size();
    }

    @Override
    public int getIndex(String val) throws RemoteException {
        return database.entrySet().stream()
                .filter(e -> e.getValue().equals(val))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
    }

    @Override
    public DBResult getRecordObj(int index) throws RemoteException {
        String val = database.get(index);
        return new DBResult(index, val);
    }
}

package exercise6;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static exercise6.DataBaseServer.registryName;

public class DataBaseClient {

    public static void main(String[] args) throws Exception {
        DataBase stub = readStubFromFile(registryName);

        Map<Integer, String> records = new LinkedHashMap<>();
        records.put(4101, "Appen");
        records.put(4102, "Ahrensburg");
        records.put(4103, "Wedel");
        records.put(4104, "Aumuhle");
        records.put(4105, "Seevetal");
        records.put(4106, "Quickborn");

        for (Map.Entry<Integer, String> entry : records.entrySet()) {
            stub.addRecord(entry.getKey(), entry.getValue());
            System.out.println("addRecord(" + entry.getKey() + ", " + entry.getValue() + ")");
        }

        System.out.println("getRecord(4103) = " + stub.getRecord(4103));
        System.out.println("getRecord(4107) = " + stub.getRecord(4107));

        System.out.println("getSize() = " + stub.getSize() + " (erwartet: " + records.size() + ")");
        System.out.println("getIndex(\"Wedel\") = " + stub.getIndex("Wedel"));
        System.out.println("getIndex(\"Hamburg\") = " + stub.getIndex("Hamburg"));

        DBResult r = stub.getRecordObj(4103);
        System.out.println("getRecordObj(4103) = " + r.getKey() + " -> " + r.getValue());
        DBResult missing = stub.getRecordObj(4107);
        System.out.println("getRecordObj(4107) = " + missing.getKey() + " -> " + missing.getValue());
    }

    private static DataBase readStubFromFile(String fileName)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        /* Deserialize stub using Java Serialization */
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fis);
        DataBase remoteObj = (DataBase) in.readObject();
        in.close();
        return remoteObj;
    }
}

package exercise6;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

// Aufgabe 1b
public class DataBaseServer {
    // static damit GC nicht aufräumt da keine registriy im einsatz sondern nur file
    public static DataBase db = new DataBaseImpl();
    public static final String registryName = "registryMock";

    public static void main(String[] args) throws Exception {
        int port = 1337;
        Remote stub = UnicastRemoteObject.exportObject(db, port);
        writeStubToFile(registryName, stub);
        System.out.println("running on port " + port);
    }

    private static void writeStubToFile(String fileName, Remote stub)
            throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        out.writeObject(stub);
        out.close();
    }
}

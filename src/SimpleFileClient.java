import java.io.*;
import java.net.Socket;

public class SimpleFileClient {

    public final static int SOCKET_PORT = 13267;
    public final static String SERVER = "127.0.0.1";
    public final static String FILE_TO_RECEIVE = "C:/Temp/downloaded_example.json";

    public final static int FILE_SIZE = 6022386;

    public static void main(String[] args) throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        Socket socket = null;

        try{
            socket = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Connecting ...");
            byte[] myFileBytes = new byte[FILE_SIZE];
            InputStream inputStream = socket.getInputStream();
            fileOutputStream = new FileOutputStream(FILE_TO_RECEIVE);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bytesRead = inputStream.read(myFileBytes, 0, myFileBytes.length);
            current = bytesRead;

            do{
                bytesRead = inputStream.read(myFileBytes, current, (myFileBytes.length-current));
                if(bytesRead >= 0){
                    current += bytesRead;
                }
            } while(bytesRead > -1);

            bufferedOutputStream.write(myFileBytes, 0, current);
            bufferedOutputStream.flush();
            System.out.println("File " + FILE_TO_RECEIVE + " downloaded (" + current + " bytes read)");
        }
        finally{
            if(fileOutputStream != null) fileOutputStream.close();
            if(bufferedOutputStream != null) bufferedOutputStream.close();
            if(socket != null) socket.close();
        }
    }
}

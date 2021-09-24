import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleFileServer {

    public final static int SOCKET_PORT = 13267;
    public final static String FILE_TO_SEND = "C:/Temp/example.json";

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        ServerSocket serverSocket = null;
        Socket socket = null;

        try{
            serverSocket = new ServerSocket(SOCKET_PORT);
            while(true){
                System.out.println("Waiting ...");
                try{
                    socket = serverSocket.accept();
                    System.out.println("Accepted connection: " + socket);

                    File myFile = new File(FILE_TO_SEND);
                    byte[] myFileBytes = new byte[(int)myFile.length()];
                    fileInputStream = new FileInputStream(myFile);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    bufferedInputStream.read(myFileBytes, 0, myFileBytes.length);
                    outputStream = socket.getOutputStream();
                    System.out.println("Sending " + FILE_TO_SEND + "(" + myFileBytes.length + " bytes)");
                    outputStream.write(myFileBytes, 0, myFileBytes.length);
                    outputStream.flush();
                    System.out.println("Done.");
                }
                finally{
                    if(bufferedInputStream != null) bufferedInputStream.close();
                    if(outputStream != null) outputStream.close();
                    if(socket != null) socket.close();
                }
            }
        }
        finally{
            if(serverSocket != null) serverSocket.close();
        }
    }
}

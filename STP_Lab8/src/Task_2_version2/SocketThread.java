package Task_2_version2;

import java.io.*;
import java.net.Socket;


// этот поток мы создаем, потому что сервак должен все выполнять синхронизированно: надо обрабатывать много потоков — сокетов
public class SocketThread extends Thread {
    // всё будем соединять через сокеты - они нужны чтобы передавать данные между компами
    private Socket socket;
    // также нам нужны инпутстрим (потока для ввода клиентом данных)
    private PrintStream output;
    // и аутпутстрим (поток для вывода данных сервером)
    private BufferedReader input;

    public SocketThread(Socket socket) throws IOException {
        this.socket = socket;
        output = new PrintStream(socket.getOutputStream());
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                String message = input.readLine();
                if (message.equals("stop")) {
                    break;
                }
                System.out.println(socket.getInetAddress().getHostName() + ": " + message);
                send(message);
            }

        }
        catch (IOException e) {
            System.err.println("Disconnect");
        }
        finally
        {
            try {
                System.out.println(socket.getInetAddress() + " disconnected");
                Server.socketThreads.remove(socket);
                socket.close();
                output.close();
                input.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // чтобы подтвердить что все на уровне инпутов аутпутов работает,выводим в консоль сообщение из аутпута (=сообщение инпута)
    private void send(String message) throws IOException {
        for (var socketThread : Server.socketThreads)
            socketThread.output.println(message);
    }
}
package com.quick.project.io;

import com.quick.project.util.io.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

@Slf4j
public class Server{

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(IOUtils.DEFAULT_PORT)){
            log.info("服务器端启动了....");
            HandlerExecutorPool pool = new HandlerExecutorPool(50, 1000);
            while (true) {
                Socket socket = serverSocket.accept();
                pool.execute(new ServerHandler(socket));
            }
        }
    }

    private static class ServerHandler implements Runnable {
        private Socket socket;

        public ServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)){
                while (true) {
                    String info = bufferedReader.readLine();
                    if(info == null){
                        break;
                    }
                    log.info("客户端发送的消息：" + info);
                    printWriter.println("服务器端响应了客户端请求....");
                    log.info("服务器端响应了客户端请求....");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class HandlerExecutorPool {

        private ExecutorService executor;

        public HandlerExecutorPool(int maxSize, int queueSize) {
            this.executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxSize, 120L,
                    TimeUnit.SECONDS,new ArrayBlockingQueue<>(queueSize),new CustomizableThreadFactory(new
                    AlternativeJdkIdGenerator().generateId().toString()),new ThreadPoolExecutor.AbortPolicy());
        }

        public void execute(ServerHandler serverHandler) {
            executor.execute(serverHandler);
        }
    }
}

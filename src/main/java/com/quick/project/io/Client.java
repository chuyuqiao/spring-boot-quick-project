package com.quick.project.io;

import com.quick.project.util.io.IOUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class Client {

    public static void main(String[] args) throws IOException {

        try (Socket socket = new Socket(IOUtils.DEFAULT_HOST, IOUtils.DEFAULT_PORT);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            log.info("客户端启动了...");
            printWriter.println("客户端请求了服务器...");
            log.info("客户端请求了服务器...");
            String response = bufferedReader.readLine();
            log.info("服务端返回的消息：{}",response);
        }
    }

}

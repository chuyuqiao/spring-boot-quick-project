package com.quick.project.io.aio;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

@Slf4j
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {

    @Override
    public void completed(AsynchronousSocketChannel result, Server attachment) {
        attachment.channel.accept(attachment,this);
        read(result);
    }

    private void read(AsynchronousSocketChannel result) {
        //读取数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer resultSize, ByteBuffer attachment) {
                attachment.flip();
                System.out.println("Server->" + "收到客户端发送的数据长度为：" + resultSize);
                String data = new String(buffer.array()).trim();
                System.out.println("Server->" + "收到客户端发送的数据为：" + data);
                String response = "服务器端响应了客户端。。。。。。";
                write(result, response);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                log.warn(exc.getMessage(),exc);
            }
        });
    }

    private void write(AsynchronousSocketChannel result, String response) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(response.getBytes());
            buffer.flip();
            result.write(buffer).get();
        } catch (Exception e) {
            log.warn(e.getMessage(),e);
        }
    }

    @Override
    public void failed(Throwable exc, Server attachment) {
        log.warn(exc.getMessage(),exc);
    }
}

package com.quick.project.io.aio;

import com.quick.project.util.io.IOUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Server {
    private ExecutorService executorService;
    private AsynchronousChannelGroup  channelGroup;
    public  AsynchronousServerSocketChannel channel;

    public Server(int port){
        try {
            executorService= Executors.newCachedThreadPool();
            channelGroup=AsynchronousChannelGroup.withCachedThreadPool(executorService,1);
            channel=AsynchronousServerSocketChannel.open(channelGroup);
            channel.bind(new InetSocketAddress(port));
            log.info("Server start,port[{}]",port);
            channel.accept(this, new ServerCompletionHandler());
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Server server=new Server(IOUtils.DEFAULT_PORT);
    }
}

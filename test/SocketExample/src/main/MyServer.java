package main;

import java.util.Timer;
import java.util.TimerTask;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MyServer {
	static final int port = 8081;

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		try {
			TimerTask timertask = new MyTimer();
			Timer timer = new Timer();
			//timer.schedule(timertask, 0, 30 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ServerChatInitializer());

			b.bind(port).sync().channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
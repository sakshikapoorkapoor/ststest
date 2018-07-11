package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {
	/*static int port = 7705;
	static String host = "192.168.2.17";*/
	static int port = 8081;
	static String host = "localhost";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String data = "$$My|name|is|sakshi|kapoor#";

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();

			bootstrap.group(group).channel(NioSocketChannel.class).handler(new ClientChatInitializer());
			Channel ch = bootstrap.connect(host, port).sync().channel();

			ChannelFuture lastWriteFuture = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			for (;;) {

				String line = in.readLine();
				if (line == null) {
					break;
				}
				System.out.println("Data Send loop: " + line + "==" + ch.remoteAddress());
				lastWriteFuture = ch.write(line + "\r\n");
				ch.flush();
				System.out.println("In for loop");
				if ("bye".equals(line.toLowerCase())) {
					ch.closeFuture().sync();
					break;
				}

			}
			if (lastWriteFuture != null) {
				lastWriteFuture.sync();

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// The connection is closed automatically on shutdown.
			group.shutdownGracefully();
		}
	}

	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * EventLoopGroup workerGroup = new NioEventLoopGroup();
	 * 
	 * try { Bootstrap b = new Bootstrap(); // (1) b.group(workerGroup); // (2)
	 * b.channel(NioSocketChannel.class); // (3)
	 * b.option(ChannelOption.SO_KEEPALIVE, true); // (4) b.handler(new
	 * ChannelInitializer<SocketChannel>() {
	 * 
	 * @Override public void initChannel(SocketChannel ch) throws Exception {
	 * ch.pipeline().addLast(new ClientChatInitializer()); } });
	 * 
	 * // Start the client. ChannelFuture f = b.connect(host, port).sync(); //
	 * (5)
	 * 
	 * // Wait until the connection is closed. f.channel().closeFuture().sync();
	 * 
	 * // for(;;){ // BufferedReader in = new BufferedReader(new
	 * InputStreamReader(System.in)); // String line = in.readLine(); // if
	 * (line == null) { // break; // } // System.out.println("Data Send loop: "
	 * +line+"=="+f.channel().remoteAddress()); // f =
	 * f.channel().write(line+"\r\n"); // f.channel().flush(); ////
	 * System.out.println("In for loop"); // // }
	 * 
	 * } finally { workerGroup.shutdownGracefully(); } }
	 */

}

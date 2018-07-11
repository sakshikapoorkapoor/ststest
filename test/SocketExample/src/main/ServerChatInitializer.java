package main;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ServerChatInitializer extends ChannelInitializer<Channel> {
	 public ServerChatInitializer() {
	      //  this.sslCtx = sslCtx;
	    }
	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast(new StringDecoder());
		pipeline.addLast(new StringEncoder());
		pipeline.addLast(new ServerHandler());
	}

}

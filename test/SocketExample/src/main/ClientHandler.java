package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("msg from server: "+msg);
	}
	 @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        cause.printStackTrace();
	        ctx.close();
	}
	 
	 @Override
		public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
			// TODO Auto-generated method stub
			super.channelRegistered(ctx);
			
			
			
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			// TODO Auto-generated method stub
			super.channelActive(ctx);
/*//			for(;;){
            	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            	String line = in.readLine();
//				if (line == null) {
//					break;
//				}
				System.out.println("Data Send loop: "+line+"=="+ctx.channel().remoteAddress());
				ctx.write(line+"\r\n");
				ctx.flush();*/
				 
//            }
		}
		
		
}

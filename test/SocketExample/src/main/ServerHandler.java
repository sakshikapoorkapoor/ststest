package main;

import java.nio.ByteBuffer;
import java.security.spec.EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.DocFlavor.STRING;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.DBCollection;

import database.DbConnection;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import parser.AvlParser;
import parser.GpsCardParser;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

	static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("msg: " + msg);
		String socketAddress = "" + ctx.channel().localAddress();
		String[] ipAndport = socketAddress.split(":");
		System.out.println("port:" + ipAndport[1]);
		// parseTCP(msg);
		// saveToMongo(msg);
		if (msg.contains("IWAP") && msg.contains("#")) {
		   /* String commandword = msg.substring(2, 6);
			if(commandword.equals("AP00"))
			{
				Constants.imei = msg.substring(6, msg.indexOf("#"));
			}*/
			
			/*GpsCardParser cardParser = new GpsCardParser();
			String data = "*"+Constants.imei+"*"+msg;
			System.out.println("data-----------------------"+data);
			String code = data.substring(data.indexOf("AP"), data.indexOf("AP") + 4);
			System.out.println("data-----------------------" + commandword);
			*/
			
			GpsCardParser cardParser = new GpsCardParser();
			String code = msg.substring(msg.indexOf("AP"), msg.indexOf("AP") + 4);
			System.out.println("data-----------------------" + code);
		String	data = "*357653050454151*IWAP02,zh_cn,0,7,404,10,520|3192|57,520|3193|58,520|3191|46,520|37526|34,520|18366|32,517|48148|32,517|64852|29#";
			
			switch (code) {
			case "AP00":
				// data = "IWAP00353456789012345";
				// String imei = data.substring(6);
				break;
			case "AP01":
				cardParser.insertaData(cardParser.parsedata(data.substring(0,data.indexOf("#"))));
				break;
			case "AP02":		
				cardParser.insertaData(cardParser.parsedata(data.substring(0,data.indexOf("#"))));
				break;
			case "AP10":
				cardParser.insertaData(cardParser.parsedata(data.substring(0,data.indexOf("#"))));
				break;
			case "AP03":
			
				break;
			case "AP04":
			
				break;
			case "AP53":
		
				break;
			default:
			}

			byte[] response = null;
			String str = "";
			
			switch (code) {
			case "AP00":
				Constants.imei  = msg.substring(6, msg.indexOf("#"));
				Date currdate = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String serverdateTime = format.format(currdate);

			     str = "IWBP00"+","+serverdateTime+","+"5.30#\r\n";
				response = str.getBytes();
				break;
			case "AP01":
				str = "IWBP01#\r\n";
				response = str.getBytes();

				break;
			case "AP02":
				String arr[] = msg.split(",");
				String isreplyAddress = arr[2];
				str = "IWBP02#\r\n";
				//response = str.getBytes();
				if (isreplyAddress.equals("0")) {
					str = "IWBP02#\r\n";
					response = str.getBytes();

				} else {

				}
				break;

			case "AP10":
				String arr1[] = msg.split(",");
				String isreplyWDAddress = arr1[7].substring(0, 1);
				String ismobileHyperlink = arr1[7].substring(1);
				str = "BP10";
				//response = str.getBytes();
				if (isreplyWDAddress.equals("1")) {
					response = str.getBytes();
				}
				if (ismobileHyperlink.equals("1")) {
					response = str.getBytes();
				}
				break;
			case "AP03":
				str = "IWBP03#\r\n";
				response = str.getBytes();
				break;
			case "AP04":
				str = "IWBP04#\r\n";
				response = str.getBytes();
				break;
			case "AP53":
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String dateTime = dateFormat.format(date);
				str = "IWBP53#" + dateTime + "\r\n";
				response = str.getBytes();
				break;
			default:
				break;
			}
			//byte[] response = null;
			if(response!=null)
			{ ByteBuf out =
			  ctx.alloc().buffer(response.length); out.writeBytes(response);
			  ctx.write(out); ctx.flush();
			}
		}
		// response to client
		/*
		 * byte[] response = null; String str = "hellofghhfjjghjgjjgjghkoo\r\n";
		 * response = str.getBytes(); ByteBuf out =
		 * ctx.alloc().buffer(response.length); out.writeBytes(response);
		 * ctx.write(out); ctx.flush();
		 */

		// for (Channel c : channels) {
		// if (c != ctx.channel()) {
		// c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg +
		// '\n');
		// } else {
		// c.writeAndFlush("[you] " + msg + '\n');
		// }
		// }

		// Close the connection if the client has sent 'bye'.
		// if ("bye".equals(msg.toLowerCase())) {
		// ctx.close();
		// }

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
		/*
		 * String str = "ping"; final ByteBuf byteBufMsg =
		 * ctx.alloc().buffer(str.length());
		 * byteBufMsg.writeBytes(str.getBytes()); ctx.writeAndFlush(byteBufMsg);
		 */

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
		cause.printStackTrace();
		ctx.close();
	}

	void saveToMongo(String data) {
		if (DbConnection.getMongoClient() == null || DbConnection.getDb() == null) {
			DbConnection.makeConnection();
			System.out.println("makeConnection");
		}
		DBCollection collection = DbConnection.getDb().getCollection("col_data");
		BulkWriteOperation bulkWriteOperation = collection.initializeOrderedBulkOperation();
		if (data.startsWith("$$") && data.endsWith("#")) {
			int end_index = data.indexOf("#");
			// int start_index = data.lastIndexOf("$");
			String actual_data = data.substring(2, end_index);
			// System.out.println(actual_data);
			String[] result = actual_data.split("\\|");
			BasicDBObject basicDBObject = new BasicDBObject();
			for (int i = 0; i < result.length; i++) {
				// System.out.println(result[i]);
				basicDBObject.put(String.valueOf(i + 1), result[i]);
			}
			bulkWriteOperation.insert(basicDBObject);
			bulkWriteOperation.execute();

		}

	}

	void parseUDP(String msg) {
		if (msg.length() > 30) {
			System.out.println("length :" + msg.length());
			String datalength = msg.substring(0, 4);
			String packet_Id = msg.substring(4, 8);
			String packet_Type = msg.substring(8, 10);
			String imei_ength = msg.substring(10, 14);
			String actual_imei = msg.substring(14, 44);
			String Code_id = msg.substring(44, 46);
			String Number_of_data = msg.substring(46, 48);
			String Timestamp = msg.substring(48, 64);
			String Priority = msg.substring(64, 66);
			String GPS_data = msg.substring(68, 98);
			String lat = msg.substring(68, 77);
			String ln = msg.substring(77, 86);
			String speed = msg.substring(97, 102);
			/*
			 * Codec id: 08 Number of data: 01 Timestamp: 0000013febdd19c8
			 * Priority: 00 GPS data: 0f0e9ff0209a718000690000120000
			 */
			System.out.println("datalength :" + datalength + " packet_Id :" + packet_Id + " packet_Type :" + packet_Type
					+ " imei_ength :" + imei_ength + " actual_imei :" + actual_imei);
			System.out.println("GPS_data :" + GPS_data);
			System.out.println("lat :" + lat);
			System.out.println("ln :" + ln);
			System.out.println("speed :" + speed);
		}
	}

	void parseTCP(String msg) {
		if (msg.length() > 30 && msg.startsWith("$$") && msg.indexOf("GPRMC") != -1) {
			AvlParser avlParser = new AvlParser();
			avlParser.parserData(msg);
		} else if (msg.startsWith("IWAP") && msg.endsWith("#")) {
			GpsCardParser cardParser = new GpsCardParser();
			cardParser.parsedata(msg.substring(0, msg.indexOf("#")));
			String commandword = msg.substring(2, 6);
			switch (commandword) {
			case "AP00":

				break;
			case "AP01":
				/*
				 * byte[] response = null; String str = "IWBP01#"; response =
				 * str.getBytes(); ByteBuf out =
				 * ctx.alloc().buffer(response.length);
				 * out.writeBytes(response); ctx.write(out); ctx.flush();
				 */
				/*
				 * String rs = "IWBP01#"; response = rs.getBytes(); ByteBuf out
				 * = ctx.alloc().buffer(response.length);
				 * out.writeBytes(response); ctx.write(out); ctx.flush();
				 */
				break;
			case "AP02":

				break;

			case "AP10":

				break;
			case "AP03":

				break;
			case "AP04":

				break;
			case "AP53":

				break;
			default:
				break;
			}
		} else if (msg.length() > 30) {
			System.out.println("length :" + msg.length());
			String datalength = msg.substring(8, 16);
			/*
			 * String packet_Id = msg.substring(4, 8); String packet_Type =
			 * msg.substring(8, 10); String imei_ength = msg.substring(10, 14);
			 * String actual_imei = msg.substring(14, 44);
			 */
			String Code_id = msg.substring(16, 18);
			String Number_of_data = msg.substring(18, 20);
			String Timestamp = msg.substring(20, 36);
			String Priority = msg.substring(36, 38);
			String GPS_data = msg.substring(38, 68);
			String lat = msg.substring(38, 46);
			String ln = msg.substring(46, 55);
			String speed = msg.substring(55, 60);
			/*
			 * Codec id: 08 Number of data: 01 Timestamp: 0000013febdd19c8
			 * Priority: 00 GPS data: 0f0e9ff0209a718000690000120000
			 */
			System.out.println("datalength :" + datalength);
			System.out.println("Number_of_data :" + Number_of_data);
			System.out.println("Timestamp :" + Timestamp);
			System.out.println("Code_id" + Code_id);
			System.out.println("GPS_data :" + GPS_data);
			System.out.println("lat :" + lat);
			System.out.println("ln :" + ln);
			System.out.println("speed :" + speed);
		}
	}
}

package main;

import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.omg.CORBA.REBIND;

class NioClient {
	public static long startTime, endTime;
	public static int counterData;
	public static boolean stat;

	public static void main(String argv[]) throws Exception {

		// String a="e:/tmp/test.txt";
		// ("s")
		// System.out.print(a.lastIndexOf("/"));

		for (int i = 101; i < 102; i++) {
			try {
				Socket soc = new Socket("localhost", 8081);

				Thread ml = new TCPline(soc, i);
				ml.start();

			} catch (ConnectException ex) {
				System.out.println("::::Server stop working due to some technical fault::::" + ex);
			}
		}
	}
}

class TCPline extends Thread {
	private int counter;
	public boolean status = true;
	Socket clientSocket;
	private String sentence;
	String modifiedSentence;

	public TCPline(Socket soc, int i) {
		this.counter = i;
		try {

			this.clientSocket = soc;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {


		new Thread(new Runnable() {

			@Override
			public void run() {
				int c = 0;
				while (true) {

					sentence = "hjhkjhkhjl\r\n";
					DataOutputStream outToServer;
					try {
						outToServer = new DataOutputStream(clientSocket.getOutputStream());
						// outToServer.writeBytes(sentence);
						outToServer.write(sentence.getBytes());
						System.out.println("Send: " + sentence);

						BufferedReader inFromServer = new BufferedReader(
								new InputStreamReader(clientSocket.getInputStream()));
						String recData = inFromServer.readLine();
						System.out.println("Rec: " + recData);

						Thread.sleep(10000);
					} catch (Exception e) {
						System.out.println(System.currentTimeMillis());
						break;
					}
				}
			}
		}).start();

		/*
		 * DataOutputStream outToServer; try { outToServer = new
		 * DataOutputStream( clientSocket.getOutputStream());
		 * outToServer.writeBytes(sentence); System.out.println("Send--:" +
		 * sentence); // Thread.sleep(1000); } catch (Exception e) {
		 *
		 * } // SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm"); //
		 * Date date = new Date(); // String time = sdf.format(date); // int
		 * tim=Integer.parseInt(time.substring(6, 8)); // tim=tim-3; //
		 * time=time.substring(0, 6)+tim+time.substring(8); //
		 * sentence="imei:359710044306158,tracker,"
		 * +time+",,F,030037.000,A,1910.4081,N,07252.0166,E,0.00,0\r\n";
		 *
		 * // if (clientSocket.isConnected()) { // DataOutputStream outToServer;
		 * // try { // System.out.println("Send--:"+sentence); // outToServer =
		 * new DataOutputStream( // clientSocket.getOutputStream()); //
		 * outToServer.writeBytes(sentence); // try { // Thread.sleep(10000); //
		 * } catch (Exception e) { // System.out.println(""+e); // } // } catch
		 * (IOException e2) { // // TODO Auto-generated catch block //
		 * System.out.println("timeou  " + e2); // continue; // }
		 *
		 * try { BufferedReader inFromServer = new BufferedReader( new
		 * InputStreamReader(clientSocket.getInputStream())); String recData =
		 * inFromServer.readLine(); System.out.println("Rec: " + recData); }
		 * catch (IOException e1) { System.out.println("error" + e1); } try {
		 * Thread.sleep(6000); } catch (Exception e) { System.out.println("" +
		 * e); }
		 */
		// // break;
		// //// continue;
		// }
		// }
	}
}

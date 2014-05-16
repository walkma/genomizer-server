package command;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import response.Response;

public class PostTransferCommand {

	private HttpExchange exchange;

	public PostTransferCommand(HttpExchange exchange) {
		this.exchange = exchange;
	}

	public boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}


	public void execute() {

		BufferedInputStream bis = new BufferedInputStream(exchange.getRequestBody());

		String path = exchange.getRequestURI().toString();

		String[] split = path.split("=");
		path = split[split.length -1];

		System.out.println("path = " + path);

		File file = new File(path);

		try {
			FileOutputStream fos = new FileOutputStream(file);


		    int len = Integer.parseInt(exchange.getRequestHeaders().getFirst("Content-Length"));
		    byte[] buffer = new byte[len];
		    System.out.println("Lenght = " + len);
		    bis.read(buffer, 0, len);
		    fos.write(buffer);

		    String qry = new String(buffer, "UTF-8");

		    System.out.println("MESSAGE START:\n\n");
		    System.out.println(qry + "\n");

		    OutputStream bos = exchange.getResponseBody();


		    exchange.sendResponseHeaders(200, 0);

		    bos.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
		    bos.write("Content-Length: 0\r\n".getBytes("UTF-8"));
		    bos.write("\r\n".getBytes("UTF-8"));

            bos.flush();

            fos.close();
		    bis.close();
		    bos.close();



		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executor;

import response.GetTransferResponse;
import response.MinimalResponse;
import response.Response;
import response.StatusCode;
import sun.misc.IOUtils;
import authentication.Authenticate;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import command.CommandHandler;
import command.CommandType;
import command.GetTransferCommand;

public class Doorman {

	private HttpServer httpServer;
	private CommandHandler commandHandler;

	public Doorman(CommandHandler commandHandler, int port) throws IOException {

		this.commandHandler = commandHandler;

		httpServer = HttpServer.create(new InetSocketAddress(port),0);
		httpServer.createContext("/login", createHandler());
		httpServer.createContext("/experiment", createHandler());
		httpServer.createContext("/annotation", createHandler());
		httpServer.createContext("/file", createHandler());
		httpServer.createContext("/search", createHandler());
		httpServer.createContext("/user", createHandler());
		httpServer.createContext("/process", createHandler());
		httpServer.createContext("/sysadm", createHandler());
		httpServer.createContext("/geo", createHandler());
		httpServer.createContext("/transfer", createHandler());

		httpServer.setExecutor(new Executor() {
			@Override
			public void execute(Runnable command) {

				try {
				new Thread(command).start();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void start() {
		httpServer.start();
	}

	HttpHandler createHandler() {
		return new HttpHandler() {
			@Override
			public void handle(HttpExchange exchange) throws IOException {
				System.out.println("\n-----------------\nNEW EXCHANGE: " + exchange.getHttpContext().getPath());
				switch(exchange.getRequestMethod()) {
				case "GET":
					switch(exchange.getHttpContext().getPath()) {
					case "/experiment":
						exchange(exchange, CommandType.RETRIEVE_EXPERIMENT_COMMAND);
						break;
					case "/file":
						exchange(exchange, CommandType.GET_FILE_FROM_EXPERIMENT_COMMAND);
						break;
					case "/search":
						exchange(exchange, CommandType.SEARCH_FOR_EXPERIMENTS_COMMAND);
						break;
					case "/annotation":
						exchange(exchange, CommandType.GET_ANNOTATION_INFORMATION_COMMAND);
						break;
					case "/sysadm":
						exchange(exchange, CommandType.GET_ANNOTATION_PRIVILEGES_COMMAND);
						break;
					case "/transfer":
						exchange(exchange, CommandType.GET_TRANSFER_COMMAND);
						break;
					}
					break;


				case "PUT":
					switch(exchange.getHttpContext().getPath()) {
					case "/experiment":
						exchange(exchange, CommandType.UPDATE_EXPERIMENT_COMMAND);
						break;
					case "/file":
						exchange(exchange, CommandType.UPDATE_FILE_IN_EXPERIMENT_COMMAND);
						break;
					case "/user":
						exchange(exchange, CommandType.UPDATE_USER_COMMAND);
						break;
					case "/process":
						System.out.println("found process RESTful");
						exchange(exchange, CommandType.PROCESS_COMMAND);
						break;
					case "/annotation":
						exchange(exchange, CommandType.ADD_ANNOTATION_VALUE_COMMAND);
						break;
					case "/sysadm":
						exchange(exchange, CommandType.UPDATE_ANNOTATION_PRIVILEGES_COMMAND);
						break;
					}
					break;


				case "POST":
					switch(exchange.getHttpContext().getPath()) {
					case "/login":
						exchange(exchange, CommandType.LOGIN_COMMAND);
						break;
					case "/experiment":
						exchange(exchange, CommandType.ADD_EXPERIMENT_COMMAND);
						break;
					case "/file":
						exchange(exchange, CommandType.ADD_FILE_TO_EXPERIMENT_COMMAND);
						break;
					case "/user":
						exchange(exchange, CommandType.CREATE_USER_COMMAND);
						break;
					case "/annotation":
						exchange(exchange, CommandType.ADD_ANNOTATION_FIELD_COMMAND);
						break;
					case "/transfer":
						exchange(exchange, CommandType.POST_TRANSFER_COMMAND);
						break;
					case "/geo":
						System.out.println("GEO!");
						exchange(exchange, CommandType.GET_GEO_ID);
						break;
					}
					break;


				case "DELETE":
					switch(exchange.getHttpContext().getPath()) {
					case "/login":
						exchange(exchange, CommandType.LOGOUT_COMMAND);
						break;
					case "/experiment":
						exchange(exchange, CommandType.REMOVE_EXPERIMENT_COMMAND);
						break;
					case "/file":
						exchange(exchange, CommandType.DELETE_FILE_FROM_EXPERIMENT_COMMAND);
						break;
					case "/user":
						exchange(exchange, CommandType.DELETE_USER_COMMAND);
						break;
					case "/annotation":
						exchange(exchange, CommandType.REMOVE_ANNOTATION_FIELD_COMMAND);
						break;


					}
					break;
				}
			}
		};
	}

	private void exchange(HttpExchange exchange, CommandType type) {

		//TEMP

		//ENDTEMP

		InputStream bodyStream = exchange.getRequestBody();
		Scanner scanner = new Scanner(bodyStream);
		String body = "";

		String uuid = null;
		String username = null;
		System.out.println("Exchange: " + type);

		if(type != CommandType.LOGIN_COMMAND) {
			try {
				//uuid =  exchange.getRequestHeaders().get("Authorization").get(0);
				//Set Content-type to application/octet-stream if command type is a GET transfer
				if(type == CommandType.GET_TRANSFER_COMMAND) {
					Headers h = exchange.getResponseHeaders();
					h.set("Content-Type", "application/octet-stream");
					System.out.println("Like a boss!");
				}
			} catch(NullPointerException e) {
				System.out.println("Unauthorized request!");
				Response errorResponse = new MinimalResponse(StatusCode.UNAUTHORIZED);
				try {
					respond(exchange, errorResponse);
					scanner.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				scanner.close();
				return;
			}
		} else {
			System.out.println("FOUND LOGIN COMMAND.");
		}
		if(type == CommandType.POST_TRANSFER_COMMAND) {
			body = parseFileFromBody(bodyStream, exchange);
		} else {
			while(scanner.hasNext()) {
				body = body.concat(" " + scanner.next());
			}
		}
		scanner.close();

		Response response = null;

		try {
		username = Authenticate.getUsername(uuid);
		} catch(Exception e ) {
			e.printStackTrace();
		}
		System.out.println("BEFORE PROCESS COMMAND...");
		try {
			System.out.println("Jodu, det va den biten " + exchange.getRequestURI().toString());
			response = commandHandler.processNewCommand(body, exchange.getRequestURI().toString(), username, type);
		} catch(Exception e ) {
			e.printStackTrace();
		}
		System.out.println("AFTER PROCESS COMMAND.");

		//TODO Should there be some error checking?


		try {
			respond(exchange, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private void respond(HttpExchange exchange, Response response) throws IOException {
		String body = null;
		if(response.getBody() == null || response.getBody().equals("")) {
			exchange.sendResponseHeaders(response.getCode(), 0);

		} else {


			OutputStream os = exchange.getResponseBody();

			if(response instanceof GetTransferResponse) {
				exchange.sendResponseHeaders(response.getCode(), ((GetTransferResponse)response).getFileSize());
				FileInputStream fis = ((GetTransferResponse)response).getFileBody();
				byte[] buffer = new byte[1024];
			    int bytesRead;
			    while ((bytesRead = fis.read(buffer)) != -1)
			    {
			        os.write(buffer, 0, bytesRead);
			    }

			} else {

				body = response.getBody();
				exchange.sendResponseHeaders(response.getCode(), body.getBytes().length);
				body = response.getBody();
				os.write(body.getBytes());


			}
			os.flush();
			os.close();


		}
		System.out.println("END OF EXCHANGE\n------------------");
	}

	/**
	 * Parses data from a multipart/form-data and saves the content to a file.
	 * @param inputStream The stream containing the body of the multipart/form-data.
	 * @param exchange HttpExchange object.
	 * @return A string with the response code.
	 */
	private String parseFileFromBody(InputStream inputStream, HttpExchange exchange) {

		String filePath = exchange.getRequestURI().toString().split("=")[1];

		System.out.println("filepath = " + filePath);
		String length = exchange.getRequestHeaders().getFirst("Content-length");
		BufferedInputStream bis = new BufferedInputStream(inputStream);

		String body = null;
		byte[] byteRead = new byte[1];
		byte[] byteArr = new byte[1024];
		byte[] fileArr = null;
		int i = 0;
		int nrOfBytes = 0;
		boolean first = true;

		File file = new File(filePath);
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			body = "400";
			return body;
		}

		try {
			while(bis.read(byteRead, 0, 1) != -1) {
				if(byteRead[0] == '\r') {

					String line = new String(byteArr);
					byteArr = new byte[1024];

					if(line.indexOf("Content-") == -1 && !first) {
						i = 0;
						bis.read(byteRead, 0, 1);

						int len = Integer.parseInt(length) - nrOfBytes;

						int bytes = 4096;
						fileArr = new byte[bytes];

						while(len > 0) {;
							if(len > bytes) {
								fileArr = new byte[bytes];
								int temp = 0;
								int tempBytes = bytes;
								//Makes sure 'bytes' number of bytes are read.
								while((temp = bis.read(fileArr, temp, tempBytes)) != tempBytes) {
									tempBytes -= temp;
								}
								len -= bytes;

							} else {
								fileArr = new byte[len];
								bis.read(fileArr, 0, len);
								len = -1;
							}

							try {
								fos.write(fileArr);
							} catch (FileNotFoundException e) {
								body = "404";
								return body;
							} catch (IOException e) {
								body = "400";
								return body;
							}
						}
						fos.close();
						break;
					} else {
						if(first) {
							nrOfBytes += (nrOfBytes + 8);
						}
						i = 0;
						first = false;
						nrOfBytes++;
					}
				} else {
					byteArr[i++] = byteRead[0];
					nrOfBytes++;
				}
			}

			body = new String(fileArr);

		} catch (IOException e) {
			body = "400";
			return body;
		}

		body = "200";

		return body;
	}
}

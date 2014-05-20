package server;

<<<<<<< HEAD
=======
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
>>>>>>> branch 'master' of ssh://git@github.com:22/walkma/genomizer-server.git
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executor;

import response.MinimalResponse;
import response.Response;
import response.StatusCode;
import authentication.Authenticate;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import command.CommandHandler;
import command.CommandType;

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
//						GetTransferCommand getTransferCommand = new GetTransferCommand(exchange);
//						getTransferCommand.execute();
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
//						PostTransferCommand postTransferCommand = new PostTransferCommand(exchange);
//						postTransferCommand.execute();
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
<<<<<<< HEAD
			TransferData uploadData = new TransferData();
			body = uploadData.parseFileFromBody(bodyStream, exchange);
=======
			body = getBinaryDataAsString(bodyStream, exchange);

>>>>>>> branch 'master' of ssh://git@github.com:22/walkma/genomizer-server.git
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

<<<<<<< HEAD
=======
			body = response.getBody();
			exchange.sendResponseHeaders(response.getCode(), body.getBytes().length);

>>>>>>> branch 'master' of ssh://git@github.com:22/walkma/genomizer-server.git
			OutputStream os = exchange.getResponseBody();
<<<<<<< HEAD

			if(response instanceof GetTransferResponse) {
				exchange.sendResponseHeaders(response.getCode(), ((GetTransferResponse)response).getFileSize());
				TransferData transferData = new TransferData();
				transferData.sendFile(((GetTransferResponse)response).getFileBody(), os);
			} else {
				body = response.getBody();
				exchange.sendResponseHeaders(response.getCode(), body.getBytes().length);
				body = response.getBody();
				os.write(body.getBytes());
			}
=======
			os.write(body.getBytes());
>>>>>>> branch 'master' of ssh://git@github.com:22/walkma/genomizer-server.git
			os.flush();
			os.close();
		}
		System.out.println("END OF EXCHANGE\n------------------");
<<<<<<< HEAD
=======
	}

	private String getBinaryDataAsString(InputStream inputStream, HttpExchange exchange) {
		//String length= exchange.getRequestHeaders().getFirst("length");
		String length = exchange.getRequestHeaders().getFirst("Content-length");
		String boundary = exchange.getRequestHeaders().getFirst("Boundary");
		System.out.println("boundary22: " + boundary);
		//String length = params.get(" Content-length");
		//System.out.println("lenght = " + length);
		BufferedInputStream bis = new BufferedInputStream(inputStream);
		System.out.println("length = " + length);

		String body = null;

		//int len = Integer.parseInt(length);

		byte[] byteRead = new byte[1];
		byte[] byteArr = new byte[1024];
		byte[] fileArr = null;// new byte[len];
		int bytesRead = 0;

		int i = 0;
		int nrOfBytes = 0;

		boolean first = true;

		try {
			while((bytesRead = bis.read(byteRead, 0, 1)) != -1) {
				if(byteRead[0] == '\r') {


					String line = new String(byteArr);
					byteArr = new byte[1024];

					System.out.println("string2: " + line);

					if(line.indexOf("Content-") == -1 && !first) {
						i = 0;
						System.out.println("string: " + line);
						bis.read(byteRead, 0, 1);

						int len = Integer.parseInt(length) - nrOfBytes;
						System.out.println("nrofbytes: " + nrOfBytes);
						System.out.println("length11 = " + len);
						fileArr = new byte[len];

						for(int k = 0; k < len; k++) {
							bis.read(byteRead, 0, 1);
							fileArr[k] = byteRead[0];
						}

						//System.out.println("file: " + new String(fileArr));
						break;
					} else {
						if(first) {
							System.out.println("boundary length = " + i);
							System.out.println("string calculated: " + line);
							nrOfBytes += (nrOfBytes + 4 * 2 + 1);
						}
						i = 0;
						first = false;
					}
				} else {
					byteArr[i++] = byteRead[0];
					nrOfBytes++;
				}
			}

			File file = new File("/home/c11/c11vlg/Downloads/uploadTest.txt");

			FileOutputStream fos = new FileOutputStream(file);
			fos.write(fileArr);
			fos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "hej";
>>>>>>> branch 'master' of ssh://git@github.com:22/walkma/genomizer-server.git
	}
}

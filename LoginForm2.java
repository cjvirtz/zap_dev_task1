import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginForm2 {
	private Socket socket = null;
    private ServerSocket server = null;

	public LoginForm2(int port) throws IOException{
		try
        {
            server = new ServerSocket(port);
                    
            while(true) {
            socket = server.accept();
            
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            PrintStream out = new PrintStream(socket.getOutputStream());
            
            String htmlForm = "<html>\n" +
                    "  <head>\n" +
                    "    <title>Log in</title>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <div>\n" +
                    "      <form action=\"/login\" method=\"POST\">\n" +
                    "        <label for=\"phoneNumber\">Phone</label>\n" +
                    "        <br />\n" +
                    "        <input type=\"text\" name=\"phoneNumber\" />\n" +
                    "        <br />\n" +
                    "        <label for=\"pin\">Pin</label>\n" +
                    "        <br />\n" +
                    "        <input type=\"password\" name=\"pin\" />\n" +
                    "        <br />\n" +
                    "        <button class=\"submit\">Log in</button>\n" +
                    "      </form>\n" +
                    "    </div>\n" +
                    "  </body>\n" +
                    "</html>";
            
            String htmlFormNumber = "<html>\n" +
                    "  <head>\n" +
                    "    <title>Log in</title>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <div>\n" +
                    "      <h2>Invalid Number</h2>\n" +
                    "      <form action=\"/login\" method=\"POST\">\n" +
                    "        <label for=\"phoneNumber\">Phone</label>\n" +
                    "        <br />\n" +
                    "        <input type=\"text\" name=\"phoneNumber\" />\n" +
                    "        <br />\n" +
                    "        <label for=\"pin\">Pin</label>\n" +
                    "        <br />\n" +
                    "        <input type=\"password\" name=\"pin\" />\n" +
                    "        <br />\n" +
                    "        <button class=\"submit\">Log in</button>\n" +
                    "      </form>\n" +
                    "    </div>\n" +
                    "  </body>\n" +
                    "</html>";
            
            String htmlFormPin = "<html>\n" +
                    "  <head>\n" +
                    "    <title>Log in</title>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <div>\n" +
                    "      <h2>Invalid Pin</h2>\n" +
                    "      <form action=\"/login\" method=\"POST\">\n" +
                    "        <label for=\"phoneNumber\">Phone</label>\n" +
                    "        <br />\n" +
                    "        <input type=\"text\" name=\"phoneNumber\" />\n" +
                    "        <br />\n" +
                    "        <label for=\"pin\">Pin</label>\n" +
                    "        <br />\n" +
                    "        <input type=\"password\" name=\"pin\" />\n" +
                    "        <br />\n" +
                    "        <button class=\"submit\">Log in</button>\n" +
                    "      </form>\n" +
                    "    </div>\n" +
                    "  </body>\n" +
                    "</html>";
            
            String inputLine;
            
            String outputLine = "HTTP/1.1 200 OK\n" + "Content-Type: text/html" + "\n\n" + htmlForm;
           
            String getRequest = "";
            
            String postInfo = "";
            
            while((inputLine = input.readLine()) != null)
            {	 
            	if(inputLine.length() > 5) {
	            	if (inputLine.substring(0,3).equals("POST")) {
                        getRequest = inputLine;
                        System.out.println(postInfo);	                    
	            		//System.out.println("Get Request " + getRequest);
	            		if (getRequest.contains("phoneNumber=") && getRequest.contains("pin=")) {
                        String pNumber = getRequest.substring(19,31);
                        String pass = getRequest.substring(36,42);
	            		if (Login.getCredentials(pNumber, pass).equalsIgnoreCase("success")) {
	    					ChatClient chatClient = new ChatClient("localhost", 3000, pNumber);
	    				}
	    				else if (Login.getCredentials(pNumber, pass).equalsIgnoreCase("invalid pin")) {
	    					outputLine = "HTTP/1.1 200 OK\n" + "Content-Type: text/html" + "\n\n" + htmlFormPin;
	    				}
	    				else {
	    					outputLine = "HTTP/1.1 200 OK\n" + "Content-Type: text/html" + "\n\n" + htmlFormNumber;
	    				}
	            		}
	            	}
            	}
            	
            	//System.out.println(inputLine);
            	
            	if(inputLine.isEmpty()){
                    while(input.ready()){
                        postInfo += ((char) input.read());
                    }
                    //System.out.println(postInfo);
            		break;
                }
            }

            if (!postInfo.equals("")) {
                String[] fields = new String[10];
                fields = PostGetSplitters.doPost(postInfo);
                System.out.println(Arrays.toString(fields));	                    
                //System.out.println("Get Request " + getRequest);
                String pNumber = fields[0].split("=")[1];
                String pass = fields[1].split("=")[1];
                System.out.println(pNumber);
                System.out.println(pass);
                if (Login.getCredentials(pNumber, pass).equalsIgnoreCase("success")) {
                    ChatClient chatClient = new ChatClient("localhost", 3000, pNumber);
                }
                else if (Login.getCredentials(pNumber, pass).equalsIgnoreCase("invalid pin")) {
                    outputLine = "HTTP/1.1 200 OK\n" + "Content-Type: text/html" + "\n\n" + htmlFormPin;
                }
                else {
                    outputLine = "HTTP/1.1 200 OK\n" + "Content-Type: text/html" + "\n\n" + htmlFormNumber;
                }
            }
            
            out.println(outputLine);
            
            socket.close();
            }
        }
        catch (Exception e){  
            e.printStackTrace(); 
        } 
    }

    public static void main(String args[]) throws IOException
    {
        LoginForm2 form = new LoginForm2(4000);
    }
	
	public static ArrayList<String> readFile(String path) {
        ArrayList<String> listOfLines = new ArrayList<String>();
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(path));
            String read = null;
            while ((read = in.readLine()) != null) {
                String[] split = read.split("\\|");
                for (String part : split) {
                    listOfLines.add(part);
                }
            }
        } catch (IOException e) {
            System.out.println("There was a problem: " + e);
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
            }
        }

        return listOfLines;
    }
	
	public static String getCredentials(String number, String pin) {
        ArrayList users = readFile("data/users");

        if (users.contains(number)) {
            if (users.get(users.indexOf(number)+1).toString().equals(pin) ){
            	return "success";
            }
            else {
            	return "invalid pin";
            }
        }
        else {
        	return "invalid number";
        }
    }

    public static String[] doGet(String inputLine){
        String[] fields = new String[10];
        fields = inputLine.split(" ");

        if(fields[1].length() > 1){
            fields = fields[1].split("?");
            System.out.println(fields.toString());
        }

        return fields;
    }
}

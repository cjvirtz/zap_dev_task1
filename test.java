import java.util.Arrays;

public class test {
    public test(){

    }

    public static void main (String[] args){
        //FOR GET
    //     String inputLine = "GET /login?phoneNumber=639000000001&pin=100000&balance=PHP500 HTTP/1.1";
    //     //String inputLine = "GET / HTTP/1.1";
    //     String[] fields = new String[10];
    //     fields = inputLine.split(" ");

    //     if(fields[1].length() > 1){
    //         fields = fields[1].split("\\?");
    //         System.out.println(Arrays.toString(fields));
    //         fields = fields[1].split("&");
    //         System.out.println(Arrays.toString(fields));
    //     }
    //     else{
    //         System.out.println("Success");
    //     }

        //FOR POST
        String inputLine = "phoneNumber=639000000002&pin=123";
        String[] fields = new String[10];
        fields = inputLine.split("&");
        fields = fields[0].split("=");
        System.out.println(Arrays.toString(fields));


    }
}
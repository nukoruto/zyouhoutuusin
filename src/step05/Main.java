package step05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * メインメソッド：
 * 自作したBase64の静的メソッド（encodeとdecode）の動作確認を行うメソッド
 * 標準入力した文字列を、Base64で暗号化し、その後、復号する。
 *
 * さらに、引数にサーバのホスト名とポート番号が指定された場合は、
 * Base64で暗号化した文字列をSimpleServerに送信し、応答を受け取る。
 */
public class Main {

    public static void main(String[] args){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        try {
            String str1 = reader.readLine();
            if(str1 == null){
                System.err.println("Main> 標準入力からの読み込みに失敗しました。<main>");
                return;
            }

            System.out.println("");
            String str2 = MyBase64.encode(str1);
            System.out.println("encode >> "+str2);

            System.out.println("");
            String str3 = MyBase64.decode(str2);
            System.out.println("decode >> "+str3);

            if(args.length == 0){
                return;
            }

            if(args.length != 2){
                System.err.println("Main> サーバに接続する場合は、[host port] を指定してください。<main>");
                return;
            }

            communicateWithServer(str2, args[0], args[1]);

        } catch (IOException e){
            System.out.println(e.toString()+"<main@Main>");
        }
    }

    private static void communicateWithServer(String encodedMessage, String host, String portStr){
        Charset sjis = Charset.forName("SJIS");
        int port;
        try{
            port = Integer.parseInt(portStr);
        }
        catch(NumberFormatException e){
            System.err.println("Client> ポート番号は数値で指定してください。<communicateWithServer>");
            return;
        }

        try(Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), sjis), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), sjis))){

            out.println(encodedMessage);
            out.flush();
            System.out.println("Client> サーバからの応答を待ちます。<communicateWithServer>");
            String response = in.readLine();
            if(response == null){
                System.out.println("Client> サーバからの応答が確認できませんでした。<communicateWithServer>");
                return;
            }

            try{
                String decodedResponse = MyBase64.decode(response);
                System.out.println("server response >> "+decodedResponse);
            }
            catch(IllegalArgumentException e){
                System.err.println(""+e+": サーバから受信した文字列をデコードできませんでした。<communicateWithServer>");
            }

        }
        catch(IOException e){
            System.err.println(""+e+": サーバとの通信に失敗しました。<communicateWithServer>");
        }
    }
}

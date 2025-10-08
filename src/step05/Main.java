package step05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * メインメソッド：
 * 自作したBase64の静的メソッド（encodeとdecode）の動作確認を行うメソッド
 * 標準入力した文字列を、Base64で暗号化し、その後、復号する。
 */
public class Main {

    public static void main(String[] args){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String str1 = reader.readLine();

            System.out.println("");
            String str2 = MyBase64.encode(str1);
            System.out.println("encode >> "+str2);

            System.out.println("");
            String str3 = MyBase64.decode(str2);
            System.out.println("decode >> "+str3);

        } catch (IOException e){
            System.out.println(e.toString()+"<main@Main>");
        }
    }
}

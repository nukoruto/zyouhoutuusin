package step05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 *  ライブラリを使用したBase64の変換を試すクラス
 */
public class UseBase64 {

    public static Charset charset = StandardCharsets.UTF_8;

    public static void main(String[] args){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, charset));

        try {
            String str1 = reader.readLine();

	        String str2 = Base64.getEncoder().encodeToString(str1.getBytes(charset));
	        System.out.println("encode >> "+str2);

                String str3 = new String(Base64.getDecoder().decode(str2), charset);
	        System.out.println("decode >> "+str3);
        } catch (IOException e){
            System.out.println(e.toString()+"<main@UseBase64>");
        }
	}
}

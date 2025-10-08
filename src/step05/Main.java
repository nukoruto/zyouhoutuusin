package step05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ���C�����\�b�h�F
 * ���삵��Base64�̐ÓI���\�b�h�iencode��decode�j�̓���m�F���s�����\�b�h
 * �W�����͂�����������ABase64�ňÍ������A���̌�A��������B
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

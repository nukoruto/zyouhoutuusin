package step05;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 *  自作のBase64の変換を行うクラス
 *<BR>
 *<BR>  特徴：
 *<BR>  ・変換は静的なメソッド内で行うため、オブジェクトの生成は不要。
 *<BR>
 *<BR>  管理している主なフィールド
 *<BR>  ・static String TABLE:  Base64の変換テーブル
 *<BR>
 *<BR>  管理している主なメソッド
 *<BR>  ・static String encode(String str1):  Base64のエンコード変換
 *<BR>  ・static String decode(String str1):  Base64のデコード変換
 */
public class MyBase64 {
    /** Base64の変換テーブル */
    public static String TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

/**
  エンコードするメソッド
  str1: 元の文字列
  戻り値: エンコード後の文字列(str2)

  アルゴリズム
  処理１．str1を2進数の文字列（strB）にする（1文字定数は8bitの2進数に変換）
  処理２．strBを6bit刻みで文字定数に変換し、新たな文字列（str2）を作る。
      変換にはStringクラスのオブジェクトTABLEを使う。
      6bitの2進数を整数値(10進数)にして、その値を文字列TABLEのindexに使う。
      その文字列TABLEのindex番目の値が、変換後の文字定数。
  処理３．str2の長さは4の倍数になるように調整する。不足は「=」で埋める。
*/
    public static String encode(String str1){
        StringBuilder str2Builder = new StringBuilder(); //Base64にエンコード後の文字列

        byte[] bytes = str1.getBytes(StandardCharsets.UTF_8);

        //処理１．str1を2進数の文字列（strB）にする（1文字定数は8bitの2進数に変換）
        //処理としては、1文字ずつ処理してstrBに連結させていく。
        for(int i=0; i<bytes.length;){
            int b0 = bytes[i++] & 0xFF;
            boolean hasB1 = i < bytes.length;
            int b1 = hasB1 ? bytes[i++] & 0xFF : 0;
            boolean hasB2 = i < bytes.length;
            int b2 = hasB2 ? bytes[i++] & 0xFF : 0;

            //処理２．strBを6bit刻みで文字定数に変換し、新たな文字列（str2）を作る。
            str2Builder.append(TABLE.charAt((b0 >>> 2) & 0x3F));
            str2Builder.append(TABLE.charAt(((b0 << 4) | (b1 >>> 4)) & 0x3F));
            str2Builder.append(hasB1 ? TABLE.charAt(((b1 << 2) | (b2 >>> 6)) & 0x3F) : '=');
            str2Builder.append(hasB2 ? TABLE.charAt(b2 & 0x3F) : '=');
        }

        String str2 = str2Builder.toString();

        //処理３．str2の長さが4の倍数になるように調整する。不足は「=」で埋める。【確認のみ】
        while(str2.length()%4 != 0){
            str2 +="="; //「=」パディング
        }

        return str2;
    }


/**
  デコードする関数
  str2: 元の文字列
  戻り値: デコード後の文字列(str3)

  アルゴリズム
  処理１．str2にはパディング「=」があるので除去
  処理２．str2を2進数の文字列（strB）にする（1文字定数は6bitの2進数に変換）
  処理３．strBを8bit刻みで文字定数に変換し、新たな文字列（str3）を作る
*/
    public static String decode(String str2){
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        String clean = str2.replaceAll("\\s", "");
        int len2; //パディング「=」を除去した文字の個数

        if(clean.length() % 4 != 0){
            throw new IllegalArgumentException("Invalid Base64 length: " + clean.length());
        }

        //処理１．str2のパディング「=」を除去した文字の個数を把握。len2を求める。
        len2 = clean.indexOf("=");
        if(len2<0){
            len2 = clean.length();
        }

        //処理２．str2を2進数の文字列（strB）にする（1文字定数は6bitの2進数に変換）
        for(int i=0; i<clean.length(); i+=4){
            int[] n = new int[4];
            char[] block = new char[]{clean.charAt(i), clean.charAt(i+1), clean.charAt(i+2), clean.charAt(i+3)};
            for(int j=0; j<4; j++){
                char c = block[j];
                if(c == '='){
                    n[j] = 0;
                } else {
                    n[j] = TABLE.indexOf(c); //「str2のi番目の文字」が格納されている文字列TABLEの場所（index）を求める。【１．右辺変更】
                    if(n[j] < 0){
                        throw new IllegalArgumentException("Invalid Base64 character detected: " + c);
                    }
                }
            }

            int combined = (n[0] << 18) | (n[1] << 12) | (n[2] << 6) | n[3];

            //処理３．strBを8bit刻みで文字定数に変換し、新たな文字列（str3）を作る
            byteOut.write((combined >>> 16) & 0xFF);
            if(block[2] != '='){
                byteOut.write((combined >>> 8) & 0xFF);
            }
            if(block[3] != '='){
                byteOut.write(combined & 0xFF);
            }
        }

        return new String(byteOut.toByteArray(), StandardCharsets.UTF_8);
    }

/**
 * メインメソッド：
 * 自作したBase64の動作確認を行うメソッド
 * 標準入力した文字列を、Base64で暗号化し、その後、復号する。
 */
        public static void main(String[] args) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String str1 = reader.readLine();

            System.out.println("");
            String str2 = encode(str1);
            System.out.println("encode >> "+str2);

//            System.out.println("");
//            String str3 = decode(str2);
//            System.out.println("decode >> "+str3);

        } catch (IOException e){
            System.out.println(e.toString()+"<main@MyBase64>");
        }
        }

}

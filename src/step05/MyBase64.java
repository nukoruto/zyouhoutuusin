package step05;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
    public static final String TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    /** 文字列をバイト列へ変換する際に使用する文字コード */
    private static final Charset CHARSET = StandardCharsets.UTF_8;

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
        if(str1 == null || str1.isEmpty()){
            return "";
        }

        byte[] input = str1.getBytes(CHARSET);
        StringBuilder encoded = new StringBuilder(((input.length + 2) / 3) * 4);

        for(int i = 0; i < input.length;){
            int remaining = input.length - i;

            int b0 = input[i++] & 0xff;
            int b1 = (remaining > 1) ? (input[i++] & 0xff) : 0;
            int b2 = (remaining > 2) ? (input[i++] & 0xff) : 0;

            int combined = (b0 << 16) | (b1 << 8) | b2;

            encoded.append(TABLE.charAt((combined >> 18) & 0x3f));
            encoded.append(TABLE.charAt((combined >> 12) & 0x3f));
            encoded.append(remaining > 1 ? TABLE.charAt((combined >> 6) & 0x3f) : '=');
            encoded.append(remaining > 2 ? TABLE.charAt(combined & 0x3f) : '=');
        }

        return encoded.toString();
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
        if(str2 == null || str2.isEmpty()){
            return "";
        }

        String clean = str2.replaceAll("\\s", "");
        if(clean.length() % 4 != 0){
            throw new IllegalArgumentException("Base64文字列の長さが4の倍数ではありません。");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for(int i = 0; i < clean.length(); i += 4){
            char c0 = clean.charAt(i);
            char c1 = clean.charAt(i + 1);
            char c2 = clean.charAt(i + 2);
            char c3 = clean.charAt(i + 3);

            int b0 = decodeChar(c0);
            int b1 = decodeChar(c1);
            int b2 = (c2 == '=') ? 0 : decodeChar(c2);
            int b3 = (c3 == '=') ? 0 : decodeChar(c3);

            int combined = (b0 << 18) | (b1 << 12) | (b2 << 6) | b3;

            baos.write((combined >> 16) & 0xff);
            if(c2 != '='){
                baos.write((combined >> 8) & 0xff);
            }
            if(c3 != '='){
                baos.write(combined & 0xff);
            }
        }

        return new String(baos.toByteArray(), CHARSET);
    }

    /**
     * Base64文字を数値に変換するヘルパーメソッド。
     * @param c Base64文字
     * @return 6bitの数値
     */
    private static int decodeChar(char c){
        int index = TABLE.indexOf(c);
        if(index < 0){
            throw new IllegalArgumentException("Base64に含まれない文字が指定されました: " + c);
        }
        return index;
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

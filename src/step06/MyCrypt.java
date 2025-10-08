package step06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *  自作のAESの変換を行うクラス
 *<BR>
 *<BR>  特徴：
 *<BR>  ・変換は静的なメソッド内で行うため、オブジェクトの生成は不要。
 *<BR>
 *<BR>  管理している主なフィールド
 *<BR>  ・static Charset charset:  文字コードを指定するための値
 *<BR>
 *<BR>  管理している主なメソッド
 *<BR>  ・static String encode(String str,String strK,String strV):  AESのエンコード変換
 *<BR>  ・static String decode(String str,String strK,String strV):  AESのデコード変換
 */

public class MyCrypt {
    /** チャーセット（文字コード）の指定に用いる値 */
    public static Charset charset = StandardCharsets.UTF_8;

/**
  エンコードするメソッド
  str1: 元の文字列
  戻り値: エンコード後の文字列(str2)

  アルゴリズム
　処理１．鍵の作成
　処理２．初期化ベクトルの作成
　処理３．変換器の作成
　処理４．変換器の初期設定（モード（エンコードかデコードかの情報）１と２のオブジェクトが必要）
　処理５．変換：文字列（平文）→byte配列→④を使って変換→byte配列
　処理６．⑤にBase64でエンコードして文字列（暗号文）
*/
	public static String encode(String str1,String strK,String strV) {
		try {
		    String str2 = "";

		    //処理１．【右辺変更】
			SecretKeySpec key = null;

		    //処理２．【右辺変更】
			IvParameterSpec iv = null;

		    //処理３．【右辺変更】
			Cipher cipher = null;

		    //処理４．【1行追加】


		    //処理５．【右辺変更】
			byte[] bary = null;
            System.out.print("AES >> ");
            for (byte b : bary) {
                System.out.print(String.format("%02X", b));
            }
            System.out.println(" >> new String >> "+new String(bary, charset));
            System.out.println("");

		    //処理６．【確認のみ】
            str2 = Base64.getEncoder().encodeToString(bary);

			return str2;

		} catch (Exception e) {
		    System.out.println(e.toString()+"<encode@MyCrypt>");
		}
		return null;
	}

/**
  デコードするメソッド
  str2: 元の文字列
  戻り値: デコード後の文字列(str3)

  アルゴリズム
　処理１．鍵の作成
　処理２．初期化ベクトルの作成
　処理３．変換器の作成
　処理４．変換器の初期設定（モード（エンコードかデコードかの情報）１と２のオブジェクトが必要）
　処理５．変換：文字列（平文）→byte配列→④を使って変換→byte配列
　処理６．⑤にBase64でエンコードして文字列（暗号文）
*/
	public static String decode(String str2,String strK,String strV) {
		try {
		    String str3 = "";

		    //処理１．【右辺変更】
			SecretKeySpec key = null;

		    //処理２．【右辺変更】
			IvParameterSpec iv = null;

		    //処理３．【右辺変更】
			Cipher cipher = null;

		    //処理４．【1行追加】


		    //処理５．【右辺変更】
			byte[] bary = null;

		    //処理６．【確認のみ】
			str3 = new String(bary, charset);

			return str3;
		} catch (Exception e) {
		    System.out.println(e.toString()+"<decode@MyCrypt>");
		}
		return null;
	}



/**
 * メインメソッド：
 * AESによる暗号化・復号の動作確認を行うメソッド
 * 標準入力した文字列を、暗号化し、その後、復号する。
 */
	public static void main(String[] args) {

		String strK1 = "0123012301230123"; //鍵（16bit）
		String strK2 = "kurume-seigyo-5s";
		String strV1 = "abcdefghijklmnop"; //初期化ベクトル（鍵と同じbit）
		String strV2 = "0123012301230123";
		String strV3 = " 1 2 3 4 5 6 7 8";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String str1 = reader.readLine();

            System.out.println("");
            String str2 = encode(str1,strK1,strV1);
            System.out.println("encode >> "+str2);

            String str3 = decode(str2,strK1,strV1);
            System.out.println("decode >> "+str3);
        } catch (IOException e){
            System.out.println(e.toString()+"<main@MyCrypt>");
        }
	}
}

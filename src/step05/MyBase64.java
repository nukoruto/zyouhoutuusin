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
        StringBuilder strBBuilder = new StringBuilder(); //2進数の文字列に変換後の文字列

        byte[] bytes = str1.getBytes(StandardCharsets.UTF_8);

        //処理１．str1を2進数の文字列（strB）にする（1文字定数は8bitの2進数に変換）
        //処理としては、1文字ずつ処理してstrBに連結させていく。
        for(int i=0; i<bytes.length; i++){
            String tmp = Integer.toBinaryString(bytes[i] & 0xFF); //str1を2進数の文字列に変換してtmpに格納する。【１．右辺変更】

            //tmpが8桁になっていない場合、上位ビットの場所に0を入れ8桁にする。
            //for文などを使って、文字列前方に0を入れる。（4行くらい）【２．作成】
            while(tmp.length() < 8){
                tmp = "0" + tmp;
            }

            strBBuilder.append(tmp);
        }

        //出来あたっがstrBの長さが6の倍数でない場合、末尾にゼロパディング（3行くらい）【３．作成】処理３を参考
        while(strBBuilder.length() % 6 != 0){
            strBBuilder.append('0');
        }

        String strB = strBBuilder.toString();

        //処理２．strBを6bit刻みで文字定数に変換し、新たな文字列（str2）を作る
        for(int i=0; (i+6)<=strB.length(); i+=6){
            String substr = strB.substring(i, i+6); //6桁（6ビットぶん）を取り出す。      【４．右辺変更】
            int num = Integer.parseInt(substr, 2);  //2進数の文字列を10進数の整数に変換         【５．右辺変更】
            System.out.println(substr+">>"+num);

            str2Builder.append(TABLE.charAt(num)); //変換した文字（文字列TABLEのnum番目の文字）をstr2に連結させる。【６．右辺変更】
        }

        String str2 = str2Builder.toString();

        int padding = (3 - (bytes.length % 3)) % 3;
        for(int i=0; i<padding; i++){
            str2 += "=";
        }

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
        StringBuilder strBBuilder = new StringBuilder(); //2進数の文字列に変換後の文字列
        int len2; //パディング「=」を除去した文字の個数

        //処理１．str2のパディング「=」を除去した文字の個数を把握。len2を求める。
        len2 = str2.indexOf("=");
        if(len2<0){
            len2 = str2.length();
        }

        //処理２．str2を2進数の文字列（strB）にする（1文字定数は6bitの2進数に変換）
        for(int i=0; i<len2; i++){ //「=」が来たら終了
            int n = TABLE.indexOf(str2.charAt(i)); //「str2のi番目の文字」が格納されている文字列TABLEの場所（index）を求める。【１．右辺変更】
            if(n < 0){
                throw new IllegalArgumentException("Invalid Base64 character detected: " + str2.charAt(i));
            }
            String tmp = Integer.toBinaryString(n); //数値nを2進数の文字列に変換してtmpに格納。【１．右辺変更】
            //tmpが6桁になっていない場合、上位ビットの場所に0を入れ6桁にする。
            //for文などを使って、文字列前方に0を入れる。（4行くらい）【３．作成】エンコードでほぼ同じ処理をしている。
            while(tmp.length() < 6){
                tmp = "0" + tmp;
            }

            strBBuilder.append(tmp);
        }

        String strB = strBBuilder.toString();
        int padding = str2.length() - len2;
        if(padding > 0 && strB.length() >= padding * 2){
            strB = strB.substring(0, strB.length() - padding * 2);
        }

        //処理３．strBを8bit刻みで文字定数に変換し、新たな文字列（str3）を作る
        for(int i=0; (i+8)<=strB.length(); i+=8){
            String substr = strB.substring(i, i+8); //8桁（8ビットぶん）を取り出す。【４．右辺変更】
            int num = Integer.parseInt(substr, 2); //2進数の文字列を10進数の整数に変換    【５．右辺変更】
            System.out.println(substr+">>"+num);
            byteOut.write(num); //numはアスキーコードの数字になっているので、char型に変換（キャスト）して文字列str3に連結。
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

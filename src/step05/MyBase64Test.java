package step05;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * MyBase64のエンコード・デコード処理を簡易検証するクラス。
 * 日本語（MS932）とUTF-8の両方を対象に、標準ライブラリの結果と一致するか確認する。
 */
public final class MyBase64Test {
    private MyBase64Test() {
    }

    public static void main(String[] args) {
        runDefaultCharsetTests();
        runUtf8Tests();
        System.out.println("MyBase64Test: all checks passed");
    }

    private static void runDefaultCharsetTests() {
        Charset charset = MyBase64.MESSAGE_CHARSET;
        String[] samples = {
            "", "hello", "こんにちは", "漢字とカタカナABC123", "改行\n含む", "スペース 含む"
        };
        for(String sample : samples){
            String encoded = MyBase64.encode(sample);
            String expected = Base64.getEncoder().encodeToString(sample.getBytes(charset));
            if(!encoded.equals(expected)){
                throw new AssertionError("Default charset encode mismatch: " + sample);
            }
            String decoded = MyBase64.decode(encoded);
            if(!decoded.equals(sample)){
                throw new AssertionError("Default charset decode mismatch: " + sample);
            }
        }
    }

    private static void runUtf8Tests() {
        Charset charset = StandardCharsets.UTF_8;
        String[] samples = {
            "", "emoji😀", "多言語Mix日本語とEnglish", "タブ\t入り", "改行\r\nテスト"
        };
        for(String sample : samples){
            String encoded = MyBase64.encode(sample, charset);
            String expected = Base64.getEncoder().encodeToString(sample.getBytes(charset));
            if(!encoded.equals(expected)){
                throw new AssertionError("UTF-8 encode mismatch: " + sample);
            }
            String decoded = MyBase64.decode(encoded, charset);
            if(!decoded.equals(sample)){
                throw new AssertionError("UTF-8 decode mismatch: " + sample);
            }
        }
    }
}

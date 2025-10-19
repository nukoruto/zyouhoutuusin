package step03;

//記述（import）///////////////////////////////////
import java.io.IOException;

import step02.SimpleClient;
import step05.MyBase64;



/**
 *  SimpleClientを継承したプログラム
 *<BR>
 *<BR>  役割：
 *<BR>  ・通信用プログラムとしての役割を担い、表示用プログラムとの連携を行う。
 *<BR>  ・表示用プログラムへの処理依頼はフィールドconによって行う。
 *<BR>  ・通信用プログラムの主要な記述は、継承によって実装済み。
 *<BR>  ・表示用プログラムへの処理依頼が発生するメソッドのみオーバーライドを行う。
 *<BR>
 *<BR>  管理している主なフィールド
 *<BR>  ・con:  表示用プログラムへ処理を依頼したい場合に用いるオブジェクト。
 */

/**
 * @author nakano
 *	Version: 1.00
 *	last change: Mar 2012
 */
public class SimpleClient2 extends SimpleClient {//記述（通信用プログラムの継承、パッケージstep02にある）///////////////////////////////////
	private Connector con; //中継用クラスのオブジェクト

        /**
         * コンストラクタ
         * スーパークラスのコンストラクタを呼び出すのみ。
         * @param args
         */
        public SimpleClient2(String[] args) {
                super(args);
        }

	/**
	 * 表示用プログラム（GUIPanel2）との中継を行うConnectorクラスのオブジェクトをセットする。
	 * @param con
	 */
	public void setConnector(Connector con){
		this.con = con;
	}

	/**
         * このメソッドでは、スーパークラスのフィールドoutを用いたサーバーへの送信処理を記述する。
         * このメソッドは、外部クラス（GUIPanel2）のフィールドconから、Connectorクラスのメソッドを経由して呼び出される。
         * @param con
         */
        public void sendMessage(String msg){
                if(out == null || msg == null){
                        return;
                }

                String encoded = MyBase64.encode(msg);
                out.println(encoded);
                out.flush();
        }

	/**
	 * 【Override】　スレッドの実体（サーバとの通信処理）
	 * このメソッド内では、サーバーからのメッセージを受信し、表示用プログラムにて表示させる。
         * このメソッド内では、サーバーへのメッセージの送信は行わない。
         * 受信した文字列は、フィールドconのメソッドdisplayMessageメソッドで表示用プログラム（GUIPanel2）に渡す。
         */
        public void run(){
                try{
                        String msg = null;
                        while((msg = in.readLine()) != null){
                                if(con != null){
                                        String decoded;
                                        try{
                                                decoded = MyBase64.decode(msg);
                                        }
                                        catch(IllegalArgumentException e){
                                                decoded = "<decode error> " + msg;
                                                System.err.println(""+e+":サーバから受信したデータのBase64復号に失敗しました。<run>");
                                        }
                                        con.displayMessage(decoded);
                                }
                        }
                        this.close();
                }
                catch(IOException e){
                        System.err.println(""+e+":サーバとの通信に失敗しました。<run>");
                        this.close();
                }
        }


}

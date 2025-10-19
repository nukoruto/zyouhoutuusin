package step02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import step05.MyBase64;

/**
 *  クライアントプログラムを起動させるメインプログラム
 *<BR>  
 *<BR>  役割：
 *<BR>  ・ソケット通信を行うクライアントを立ち上げる。
 *<BR>  ・サーバとの接続後は、標準入力にて文字列を入力し、サーバに送信する。
 *<BR>  ・文字列をサーバに送信した後は、サーバからの返答を待つ。
 *<BR>  ・サーバからの返答を受信したら、そのままその文字列を標準出力する。
 *<BR>
 *<BR>  管理している主なフィールド
 *<BR>  ・host:  ソケット通信を行うホスト名。初期値は""（空）。
 *<BR>  ・port1:  ソケット通信を行うポート番号。初期値は999。
 *<BR>  ・port2:  ソケット通信を行うポート番号。初期値は888。
 *<BR>  ・socket: サーバと通信するためのソケット。
 *<BR>  ・in: ソケットの接続相手から文字列を受信するオブジェクト。client_socketを基に作られる。
 *<BR>  ・out:ソケットの接続相手に文字列を送信するオブジェクト。client_socketを基に作られる。 
 *<BR>  ・std_in: 標準入力される文字列を受け取るオブジェクト。System.inを基に作られる。
 */

/**
 * @author nakano
 *	Version: 1.05
 *	last change: Oct 2014
 */
public class SimpleClient extends Thread {
	/** ホスト名 */
	private String host = "10.20.24.1"; //サーバのIPアドレス
	/** ポート番号(1000番以下【重要】) */
	private int port1 = 999; //【重要】サーバへ送信するポート
	private int port2 = 888; //【重要】サーバから受信するポート

	/** クライアントのソケット */
	private Socket socket;
	/** ソケットから文字列を受信するためのオブジェクト */
	protected BufferedReader in;
	/** ソケットから文字列を送信するためのオブジェクト */
	protected PrintWriter out;
	
	/** 標準入力から文字列を受け取るためのオブジェクト */
	private BufferedReader std_in;

/**
 *<BR> メインメソッド
 */
	public static void main(String[] args) {
		new SimpleClient(args);
	}

/**
 *<BR> 課題①－０：　コンストラクタ【確認作業】
 *<BR>   ・クライアントの処理の流れを確認すること。
 */
	public SimpleClient(String[] args) {
		super();
		
		//IPとポート番号の確定
		boolean f1 = this.analizeCommandline(args); //課題②－１
		if(!f1){
			System.exit(1);
		}
		
		//ソケットの生成とサーバへの接続
		boolean f2 = this.setSocket(); //課題②－２
		if(!f2){
			System.exit(1);
		}
		
		//サーバと接続したソケットから、入出力オブジェクトの生成
		boolean f3 = this.setIO(); //課題②－３
		if(!f3){
			System.exit(1);
		}
		
		//チャットクライアントの機能始動と終了
		if(f1 && f2 && f3){
			System.out.println("\n/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/");
			System.out.println("SimpleClient (Ver 1.00)");
			System.out.println("  connectTo: "+host+":"+port1);
			System.out.println("/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/");
			
			this.start(); //課題②－４
		}
		else{
			System.exit(1);
		}
	}
	
/**
 *<BR> 課題②－１：　コマンドライン解析処理
 *<BR>   ・APIにてIntegerクラスのparseIntメソッドを確認すること。
 *<BR>   ・引数が2つで、第一引数が文字、第二引数が数字ならば、それぞれ、ホスト名、ポート番号として設定する。
 *<BR>   ・引数が上述以外の場合には、falseを返す。
 *<BR>   ・例外発生時の処理もfalseを返す。
 */
	public boolean analizeCommandline(String[] args){
		if(args.length == 2){
			try{
				host = args[0];
				port1 = Integer.parseInt(args[1]);
			}
			catch(NumberFormatException e){
				System.err.println(""+e+":引数が数字ではありません。<analizeCommandline>");
				System.out.println("Client> ポート番号は半角数字で入力してください。<analizeCommandline>");
				return false;
			}
			
			System.out.println("Client> 接続するサーバのホスト名は"+host+"、ポート番号は"+port1+"とします。<analizeCommandline>");
			return true;
		}
		
		System.out.println("Client> 引数を2つ指定してください。<analizeCommandline>");
		System.out.println("Client>  第一引数：サーバのホスト名(IPアドレス)<analizeCommandline>");
		System.out.println("Client>  第二引数：通信用のポート番号<analizeCommandline>");
		return false;
	}
	
/**
 *<BR> 課題②－２：　ソケットの生成処理
 *<BR>   ・APIにてSocketクラスのコンストラクタ等を確認すること。
 *<BR>   ・オブジェクトの生成、ポート番号の設定をする（socketとbind）。
 *<BR>   ・例外発生時の処理はfalseを返す。
 */
        public boolean setSocket(){
                try{
                        InetAddress address = InetAddress.getByName(host);
                        socket = new Socket(address, port1);
                        System.out.println("Clien> サーバとの接続に成功しました。<setSocket>");
                        return true;
                }
                catch(Exception e){ //IOException
                        System.err.println(""+e+":サーバとの接続に失敗しました。<setSocket>");
                        return false;
                }
        }
	
/**
 *<BR> 課題②－３：　入出力オブジェクトの生成処理
 *<BR>   ・APIにてBufferedReaderクラス、PrintWriterクラスを調べること。
 *<BR>   ・文字コードはSJISを指定する。
 *<BR>   ・例外発生時の処理はfalseを返す。
 */
        public boolean setIO(){
                try{
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "SJIS"));
                        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "SJIS"), true);
                        std_in = new BufferedReader(new InputStreamReader(System.in, "SJIS"));
                        System.out.println("Client> 入出力オブジェクトを生成しました。<setIO>");
                        return true;
                }
                catch(Exception e){ //IOException
                        System.err.println(""+e+":入出力オブジェクトの生成に失敗しました。<setIO>");
                        return false;
                }
        }



/**
 *<BR> 課題②－４：　スレッドの実体（サーバとの通信処理）
 *<BR>   ・標準入力した文字列はmsg1に格納して、サーバに送信する。
 *<BR>   ・受信した文字列はmsg2に格納する。
 *<BR>   ・msg2（受信した文字列）がnullならば、通信中にエラーが起こったと判断し、通信を終了させる。
 *<BR>   ・msg2（受信した文字列）が上記以外ならば、標準出力し、以上の処理を繰り返す。
 *<BR>   ・通信終了の処理をする。（課題②－５）
 */
        public void run(){
                String msg1 = "";
                String msg2 = "";
                boolean done = false;
                try{
                        while(!done){
                                System.out.println("");
                                System.out.print("Client> サーバに送信する文字列を入力してください。<run>\n");
                                msg1 = std_in.readLine();
                                if(msg1 == null){
                                        System.out.println("Client> 入力が確認できないため終了します。<run>");
                                        done = true;
                                        continue;
                                }
                                String encodedMsg = MyBase64.encode(msg1, MyBase64.MESSAGE_CHARSET);
                                out.println(encodedMsg);
                                out.flush();
                                System.out.println("Client> サーバからの応答を待ちます。<run>");
                                msg2 = in.readLine();
                                if(msg2 == null){
                                        System.out.println("Client> サーバとの接続が切れています。<run>");
                                        done = true;
                                }
                                else{
                                        System.out.println("Client> サーバからの文字列を受け取りました。<run>");
                                        try{
                                                String decoded = MyBase64.decode(msg2, MyBase64.MESSAGE_CHARSET);
                                                System.out.println(decoded);
                                                if("bye".equals(decoded)){
                                                        done = true;
                                                }
                                        }
                                        catch(IllegalArgumentException e){
                                                System.err.println("Client> 受信データのBase64復号に失敗しました。<run>");
                                                System.err.println(e.getMessage());
                                        }
                                }
                        }

                        this.close();  //課題②－５
                }
                catch (Exception e) { //IOException
                        System.out.println(e);
                        System.exit(1);
                }
        }
	
/**
 *<BR> 課題②－５：　プログラムの終了処理
 *<BR>   ・入出力オブジェクトの終了
 *<BR>   ・スレッド(Socket)の終了
 */
	public void close(){
		try{
			in.close();
			out.close();
			std_in.close();
			
			System.out.println("Client> socketの終了させます。<close>");
			socket.close();
			System.out.println("Client> socketの終了しました。<close>");
			System.out.println("Client> プログラムを終了させます。<close>");
			System.exit(0);
		}
		catch(Exception e){
			System.err.println(""+e+":オブジェクトの終了に失敗しました。<close>");
		}
	}
}

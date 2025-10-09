package step01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  サーバプログラムを起動させるメインプログラム
 *<BR>  
 *<BR>  役割：
 *<BR>  ・ソケット通信を行うサーバを立ち上げる。
 *<BR>  ・クライアント１個のみを受け付ける。
 *<BR>  ・クライアントとの接続後は、クライアントからの文字列を受け取り、返答をする。
 *<BR>
 *<BR>  管理している主なフィールド
 *<BR>  ・port:  ソケット通信を行うポート番号。初期値は4000。
 *<BR>  ・server_socket: サーバ用のソケット。クライアントの接続を待つ。
 *<BR>  ・client_socket: サーバに接続があった際に作られるオブジェクト。
 *<BR>  ・in: ソケットの接続相手から文字列を受信するオブジェクト。client_socketを基に作られる。
 *<BR>  ・out:ソケットの接続相手に文字列を送信するオブジェクト。client_socketを基に作られる。 
 */

/**
 * @author nakano
 *	Version: 1.03
 *	last change: Mar 2012
 */
public class SimpleServer extends Thread {
	/** ポート番号(1000番以下) */
	private int port = 999; //クライアントから受信するポート
	
	/** サーバ用のソケット */
	private ServerSocket listen_socket;
	/** クライアントからサーバに接続があった際に作られるオブジェクト */
	private Socket client_socket;
	
	/** ソケットから文字列を受信するためのオブジェクト */
	private BufferedReader in;
	/** ソケットから文字列を送信するためのオブジェクト */
	private PrintWriter out;
	
/**
 *<BR> メインメソッド
 */
	public static void main(String[] args) {
		new SimpleServer(args);
	}
	
/**
 *<BR> 課題1－０：　コンストラクタ【確認作業】
 *<BR>   ・サーバの処理の流れを確認すること。
 */
	public SimpleServer(String[] args){
		super();
		
		//ポート番号の確定
		boolean f1 = this.analizeCommandline(args);//課題1－１
		if(!f1){
			System.exit(1);
		}
		
		//ソケットの生成とbind処理
		boolean f2 = this.setSocket();//課題1－２
		
		if(!f2){
			System.exit(1);
		}
		else{
			System.out.println("\n/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/");
			System.out.println("SimpleServer (Ver 1.00)");
			System.out.println("  Port : "+port);
			System.out.println("/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/\n");
		}
		
		//受動的オープン処理とaccept処理
		boolean f3 = this.waitClient();//課題1－３
		if(!f3){
			System.exit(1);
		}
		
		//クライアントと接続したソケットから、入出力オブジェクトの生成
		boolean f4 = this.setIO();//課題1－４
		if(!f4){
			System.exit(1);
		}
		
		//サーバ情報、クライアント情報の表示
		boolean f5 = this.printSocketInfo(); //課題1－５
		if(!f5){
			System.exit(1);
		}
		
		//チャットサーバの機能始動と終了
		if(f3 && f4 && f5){
			start(); //課題1－６
		}
		else{
			System.exit(1);
		}
	}
	
	
/**
 *<BR> 課題1－１：　コマンドライン解析処理【確認作業】
 *<BR>   ・APIにてIntegerクラスのparseIntメソッドを確認すること。
 *<BR>   ・引数が1つで、数字ならば、ポート番号として設定する。
 */
        public boolean analizeCommandline(String[] args){
                if(args == null || args.length == 0){
                        return true;
                }

                if(args.length != 1){
                        System.err.println("Server> コマンドライン引数の個数が正しくありません。<analizeCommandline>");
                        return false;
                }

                try{
                        int parsedPort = Integer.parseInt(args[0]);
                        if(parsedPort < 0 || parsedPort > 65535){
                                System.err.println("Server> ポート番号が範囲外です。0-65535 を指定してください。<analizeCommandline>");
                                return false;
                        }
                        port = parsedPort;
                        return true;
                }
                catch(NumberFormatException e){
                        System.err.println(""+e+":ポート番号の解析に失敗しました。<analizeCommandline>");
                        return false;
                }
        }

/**
 *<BR> 課題1－２：　サーバソケット生成処理【ソースコード追記作業】
 *<BR>   ・ServerSocketクラスのAPIを確認すること。
 *<BR>   ・例外発生時の処理はfalseを返す。
 */
        public boolean setSocket(){
                try{
                        listen_socket = new ServerSocket();
                        listen_socket.setReuseAddress(true);
                        InetSocketAddress endpoint = new InetSocketAddress(port);
                        listen_socket.bind(endpoint);
                        System.out.println("Server> サーバソケットを生成しました。<setSocket>");
                        return true;
                }
                catch(IOException e){
                        System.err.println(""+e+":サーバソケットの生成に失敗しました。<setSocket>");
                        return false;
                }
        }
	
/**
 *<BR> 課題1－３：　ソケット受付待ち処理【ソースコード追記作業】
 *<BR>   ・APIにてServerSocketクラスのメソッドを調べること。
 *<BR>   ・例外発生時の処理はfalseを返す。
 */
	public boolean waitClient(){
		try{
			System.out.println("Server> サーバソケットにアクセスがあるまで待機します。<waitClient>");
			
			
			
			
			
			System.out.println("Server> サーバソケットにアクセスがありました。<waitClient>");
			return true;
		}
		catch(Exception e){ //IOException
			System.err.println(""+e+":クライアントとの接続に失敗しました。<waitClient>");
			return false;
		}
	}
	
/**
 *<BR> 課題1－４：　入出力オブジェクトの生成処理【ソースコード追記作業】
 *<BR>   ・APIにてBufferedReaderクラス、PrintWriterクラスを調べること。
 *<BR>   ・文字コードはSJISを指定する。
 *<BR>   ・例外発生時の処理はfalseを返す。
 */
	public boolean setIO(){
		try{
			
			
			
			
			
			
			
			System.out.println("Server> 入出力オブジェクトを生成しました。<setIO>");
			return true;
		}
		catch(Exception e){ //IOException
			System.err.println(""+e+":入出力オブジェクトの生成に失敗しました。<setIO>");
			return false;
		}
	}
	
/**
 *<BR> 課題1－５：　ソケットの接続先と接続元の情報をclient_socketから取り出して標準出力。【ソースコード変更作業】
 *<BR>   ・APIにてSocketクラスを調べること。
 */
	public boolean printSocketInfo(){
		System.out.println("Server> Socketの情報を表示します。<printSocketInfo>");
		System.out.println(" 【サーバ】");
		System.out.println("   ホスト名: "                                     );
		System.out.println("   ポート番号: "                                   );
		System.out.println("   ソケットアドレス: "                             );
		System.out.println(" 【クライアント】");
		System.out.println("   ホスト名: "                                     );
		System.out.println("   ポート番号: "                                   );
		System.out.println("   ソケットアドレス: "                             );
		
		return true;
	}
	
/**
 *<BR> 課題1－６：　スレッドの実体（クライアントとの通信処理）【ソースコード追記作業】
 *<BR>   ・受信した文字列がnullならば、通信中にエラーが起こったと判断し、while文から抜ける。
 *<BR>   ・受信した文字列が「bye」ならば、通信の終了と判断し、while文から抜ける。
 *<BR>   ・受信した文字が上記以外ならば、文字列の先頭に「ECHO: 」を付与して、Clientに送信しwhile文を繰り返す。
 *<BR>   ・while文から抜け出した後、通信終了の処理を行う。（課題1－７）
 *<BR>   ・tryのスコープで例外(IOException )が発生した場合は、その時点から強制的にcatchへ飛ぶ。
 */
	public void run(){
		String msg = "";
		boolean done = false;
		try{
			while(!done){
				System.out.println("");
				msg = in.readLine();
				
				if(msg == null){
					System.out.println("Server> クライアントとの接続が切れています。<run>");
					done = true;
				}
				else if(msg.equals("bye")){
					System.out.println("Server> クライアントから接続終了の合言葉がきました。<run>");
					
					
					
				}
				else{
					System.out.println("Server> クライアントからの文字列を受け取りました。<run>");
					System.out.println(msg);
					
					
					
					
					
					
					System.out.println("Server> クライアントへメッセージを送りました。<run>");
				}
			}
			
			this.close();  //課題1－７
		}
		catch(IOException e){
			System.err.println(""+e+":クライアントとの接続に失敗しました。<run>");
		}
	}
	
/**
 *<BR> 課題1－７：　プログラムの終了処理【確認作業】
 *<BR>   ・入出力オブジェクトの終了
 *<BR>   ・スレッド(ServerSocketとSocket)の終了
 */
	public void close(){
		try{
			System.out.println("Server> 入出力用のオブジェクトinとoutを終了させます。<close>");
			in.close();
			out.close();
			System.out.println("Server> listen_socketとclient_socketを終了させました。<close>");
			
			System.out.println("Server> listen_socketとclient_socketを終了させます。<close>");
			listen_socket.close();
			client_socket.close();
			System.out.println("Server> listen_socketとclient_socketを終了させました。<close>");
			System.out.println("Server> プログラムを終了させます。<close>");
			System.exit(0);
		}
		catch(Exception e){
			System.err.println(""+e+":オブジェクトの終了に失敗しました。<close>");
			System.exit(1);
		}
	}
}

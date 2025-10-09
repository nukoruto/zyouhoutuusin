package step02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

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
	public boolean setSocket(){
		try{
			InetAddress serverAddress = InetAddress.getByName(host);
			socket = new Socket();
			socket.bind(new InetSocketAddress(port2));
			socket.connect(new InetSocketAddress(serverAddress, port1));
			System.out.println("Client> T[oƂ̐ڑɐ܂B<setSocket>");
			return true;
		}
		catch(Exception e){ //IOException
			System.err.println(""+e+":T[oƂ̐ڑɎs܂B<setSocket>");
			return false;
		}
	}

/**
 *<BR> ۑA|RF@o̓IuWFNg̐
 *<BR>   EAPIɂBufferedReaderNXAPrintWriterNX𒲂ׂ邱ƁB
 *<BR>   ER[hSJISw肷B
 *<BR>   EȌfalseԂB
 */
	public boolean setIO(){
		try{
			std_in = new BufferedReader(new InputStreamReader(System.in));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "SJIS"));
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "SJIS"), true);
			System.out.println("Client> o̓IuWFNg𐶐܂B<setIO>");
			return true;
		}
		catch(Exception e){ //IOException
			System.err.println(""+e+":o̓IuWFNg̐Ɏs܂B<setIO>");
			return false;
		}
	}

/**
 *<BR> ۑA|SF@Xbh̎́iT[oƂ̒ʐMj
 *<BR>   EW͂msg1Ɋi[āAT[oɑMB
 *<BR>   EMmsg2Ɋi[B
 *<BR>   Emsg2iMjnullȂ΁AʐMɃG[NƔfAʐMIB
 *<BR>   Emsg2iMjLȊOȂ΁AWo͂Aȏ̏JԂB
 *<BR>   EʐMȈBiۑA|Tj
 */
	public void run(){
		String msg1 = "";
		String msg2 = "";
		boolean done = false;
		try{
			while(!done){
				System.out.println("");
				System.out.print("Client> T[o֑镶͂ĂB<run>");
				msg1 = std_in.readLine();
				if(msg1 == null){
					System.out.println("Client> W͂當擾ł܂B<run>");
					done = true;
					continue;
				}
				out.println(msg1);
				out.flush();
				System.out.println("Client> T[oփbZ[W𑗂܂B<run>");
				msg2 = in.readLine();
				if(msg2 == null){
					System.out.println("Client> T[oƂ̐ڑ؂Ă܂B<run>");
					done = true;
				}
				else{
					System.out.println("Client> T[o̕󂯎܂B<run>");
					System.out.println(msg2);
					if(msg1.equals("bye")){
						System.out.println("Client> T[oƂ̒ʐMI܂B<run>");
						done = true;
					}
				}
			}
			this.close();  //ۑA|T
		}
		catch (Exception e) { //IOException
			System.out.println(e);
			System.exit(1);
		}
	}

					done = true;
				}
				else{
					System.out.println("Client> サーバからの文字列を受け取りました。<run>");
					System.out.println(msg2);
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

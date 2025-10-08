package step03;

/**
 *  表示用プログラムと通信用プログラムとの中継を行うためのプログラム
 *<BR>
 *<BR>  役割：
 *<BR>  ・表示用プログラムと通信用プログラムとの中継。
 *<BR>
 *<BR>  管理している主なフィールド
 *<BR>  ・gui:  表示用プログラムのオブジェクト。
 *<BR>  ・client:  通信用プログラムのオブジェクト。
 */

/**
 * @author nakano
 *	Version: 1.00
 *	last change: Mar 2012
 */
public class Connector {
	private GUIPanel2 gui; //表示用プログラムのオブジェクト
	private SimpleClient2 client; //通信用プログラムのオブジェクト

	/**
	 * コンストラクタ
	 *
	 * @param gui
	 * @param client
	 */
	public Connector(GUIPanel2 gui, SimpleClient2 client){
		this.gui = gui;
		this.client = client;
	}

	/**
	 * 中継用メソッド（表示用プログラム⇒通信用プログラム）
	 * @param msg
	 */
	public void sendMessage(String msg){
		client.sendMessage(msg);
	}

	/**
	 * 中継用メソッド（通信用プログラム⇒表示用プログラム）
	 * @param msg
	 */
	public void displayMessage(String msg){
		gui.displayMessage(msg);
	}

}

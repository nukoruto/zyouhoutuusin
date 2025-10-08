package step03;

import javax.swing.JFrame;

/**
 *  GUIを介してSimpleServerとの通信を行うプログラムの起動
 *<BR>
 *<BR>  役割：
 *<BR>  ・表示用プログラムのオブジェクトの生成と、表示用JFrameの設定。
 *<BR>  ・IPアドレスとポート番号の指定し、通信用プログラムのオブジェクトの生成。
 *<BR>  ・中継用プログラムのオブジェクトの生成。
 *<BR>  ・表示用プログラムと通信用プログラムのオブジェクトそれぞれに、中継用プログラムのオブジェクトを設定。
 *<BR>
 */

/**
 * @author nakano
 *	Version: 1.00
 *	last change: Mar 2012
 */
public class Main {
	/**
	 * メインメソッド
	 */
	public static void main(String[] args){
		Connector con; //中継用クラスのオブジェクト
		GUIPanel2 gui; //表示用クラスのオブジェクト
		SimpleClient2 client; //通信用クラスのオブジェクト

		//GUIの表示
		gui = new GUIPanel2();
		JFrame frame = new JFrame("SampleAppletcation");
		frame.setSize(500, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(gui);
		//パネルの再配置
		gui.revalidate();

		//クライアントの起動
		String[] data = {"127.0.0.1", "4000"}; //IPアドレスとポート番号【重要】
		client = new SimpleClient2(data);

		//GUIとクライアントを繋げる
		con = new Connector(gui, client);
		gui.setConnector(con);
		client.setConnector(con);
	}
}

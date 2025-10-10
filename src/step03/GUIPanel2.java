package step03;

//記述（import）///////////////////////////////////
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import gui.GUIPanel;
import javax.swing.JTextField;


/**
 *  GUIPanelを継承したプログラム
 *<BR>
 *<BR>  役割：
 *<BR>  ・表示用プログラムとしての役割を担い、通信用プログラムとの連携を行う。
 *<BR>  ・通信用プログラムへの処理依頼はフィールドconによって行う。
 *<BR>  ・表示用プログラムの主要な記述は、継承によって実装済み。
 *<BR>  ・通信用プログラムへの処理依頼が発生するメソッドのみオーバーライドを行う。
 *<BR>
 *<BR>  管理している主なフィールド
 *<BR>  ・con:  通信用プログラムへ処理を依頼したい場合に用いるオブジェクト。
 */

/**
 * @author nakano
 *	Version: 1.00
 *	last change: Mar 2012
 */
public class GUIPanel2 extends GUIPanel{//修正（JPanelではダメ。表示用プログラムの継承、パッケージguiにある。）///////////////////////////////////
	private Connector con; //中継用クラスのオブジェクト

	/**
	 * コンストラクタ
	 * スーパークラスのコンストラクタを呼び出すのみ。
	 */
        public GUIPanel2(){
                super();
        }

	/**
	 * 通信用プログラム（SimpleClient2）との中継を行うためのConnectorクラスのオブジェクトをセットする。
	 * @param con
	 */
	public void setConnector(Connector con){
		this.con = con;
	}

	/**
	 * このメソッドでは、スーパークラスのフィールドjtaに、引数msgの値を追記する処理を記述する。
	 * このメソッドは、外部クラス（SimpleClient2）のフィールドconから、Connectorクラスのメソッドを経由して呼び出される。
	 * @param msg
	 */
        public void displayMessage(String msg){
                if(msg == null){
                        return;
                }
                jta.append(msg + "\n");
        }

	/**
	 *<BR> 【Override】ActionEventのイベント処理
	 */
        public void actionPerformed(ActionEvent e) {
                if(e.getSource() instanceof JTextField){
                        String msg = jtf.getText();
                        if(msg != null && con != null){
                                con.sendMessage(msg);
                        }
                }
                super.actionPerformed(e);
        }

}

package gui;

/**************************************************
 *	GUIPanel.java
 *	------------------------------
 *
 *	Version: 1.00
 *	last change: Feb 2005
 *
 *	written by Akira Nakano
 *	email: nakano@kurume-nct.ac.jp
 *	copyrighted (c) by Nakano Lab.
 *
 **************************************************/

//既存のパッケージの利用
//import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

//import java.awt.*;
import java.awt.Choice;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

/**
 *  チャットクライアントのためのGUIプログラム
 *<BR>  
 *<BR>  役割：
 *<BR>  ・チャットクライアントとしてのGUIを提供する。
 *<BR>  ・テキストフィールドにて、文字列を打ち込みEnterキーを押せば、打ち込んだ文字列をテキストエリアに追記させ、同時に、サーバへ送信する。
 *<BR>  ・サーバから受け取った文字列を表示するテキストエリアを設置している。
 *<BR>
 *<BR>  管理している主なフィールド
 *<BR>  ・parent: このGUIPanelクラスのオブジェクトを生成したクラス。このオブジェクトを通じて、フィールドやメソッドを利用する。
 *<BR>  ・jta:  文字列を表示するテキストエリア。他クラスから利用できるようprotected宣言としている。
 *<BR>  ・jtf:  文字列を入力するテキストフィールド。他クラスから利用できるようprotected宣言としている。
 */
public class GUIPanel extends JPanel implements ActionListener, ItemListener{
	/** actionPerformedメソッドや、itemStateChangedで参照するのでメンバ変数として宣言 */
	protected JTextArea jta;
	
	/** actionPerformedメソッドや、itemStateChangedで参照するのでメンバ変数として宣言 */
	protected JTextField jtf;
	

/**
 *<BR> コンストラクタ
 *<BR> ・コンポーネントの配置
 *<BR> ・JButtonクラスのオブジェクトに対してイベントリスナの登録
 *<BR> ・Choiceクラスのオブジェクトに対してイベントリスナの登録
 */
	public GUIPanel(){
		super();
	
		//コンポーネント系クラスのオブジェクトの作成
		//ラベル
		JLabel jl = new JLabel("コンポーネントの連携", JLabel.CENTER);
		//ボタン
		JButton jb = new JButton("初期化");
		//テキストエリア
		jta = new JTextArea("JTextArea\n");
		//テキストフィールド
		jtf = new JTextField("JTextField");
		//ポップアップメニュー
		Choice choice = new Choice();
		choice.add("Black");
		choice.add("Red");
		choice.add("Green");
		choice.add("Blue");
		
		//コンポーネントの配置
		//レイアウトの設定
		this.setLayout(new BorderLayout());
		//配置
		this.add(jl, BorderLayout.NORTH);
		this.add(jb, BorderLayout.WEST);
		this.add(choice, BorderLayout.EAST);
		this.add(jta, BorderLayout.CENTER);
		this.add(jtf, BorderLayout.SOUTH);
		
		//ボタンにイベント
		jb.addActionListener(this);
		jtf.addActionListener(this);
		
		choice.addItemListener(this);
	}
	
/**
 *<BR> ActionEventのイベント処理
 *<BR> ・抽象クラスActionListenerに記されているメソッドactionPerformedのオーバーライド
 *<BR> ・ボタンをクリックしたら、テキストエリアに、テキストフィールドに入力されている文字を追記し、テキストフィールドの入力を空白にする。
 */
	public void actionPerformed(ActionEvent e){
		System.out.println("ActionEvent");
		if(e.getSource() instanceof JButton){
			String str = e.getActionCommand();
			System.out.println("> "+str);
			if("初期化".equals(str)){
				jta.setText("初期化しました。\n");
				jtf.setText("");
			}
		}
		else if(e.getSource() instanceof JTextField){
			System.out.println("> TextField");
			
			String msg = jtf.getText();
			jta.append(msg+"\n");
			
			jtf.setText("");
		}
		else{
			System.out.println("I don't know the compornent");
		}
	}
	
/**
 *<BR> ItemEventのイベント処理
 *<BR> ・抽象クラスItemListenerに記されているメソッドitemStateChangedのオーバーライド
 *<BR> ・チョイスの選択アイテムを選んだら、選択したアイテム（色名）にしたがって、テキストエリアの色の設定を変える。
 */
	public void itemStateChanged(ItemEvent e){
		if(e.getSource() instanceof Choice){
			String str = ((Choice)(e.getSource())).getSelectedItem();
			System.out.println("> Choice ["+str+"]");
			if("Black".equals(str)){
				jta.setForeground(Color.black);
			}
			else if("Red".equals(str)){
				jta.setForeground(Color.red);
			}
			else if("Green".equals(str)){
				jta.setForeground(Color.green);
			}
			else if("Blue".equals(str)){
				jta.setForeground(Color.blue);
			}
		}
		else{
			System.out.println("I don't know the compornent");
		}
	}

}

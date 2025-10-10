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
 *      Version: 1.00
 *      last change: Mar 2012
 */
public class Main {
        /** デフォルトのホスト名 */
        private static final String DEFAULT_HOST = "127.0.0.1";
        /** デフォルトのポート番号 */
        private static final int DEFAULT_PORT = 4000;

        /**
         * メインメソッド
         */
        public static void main(String[] args){
                Connector con; //中継用クラスのオブジェクト
                GUIPanel2 gui; //表示用クラスのオブジェクト
                SimpleClient2 client; //通信用クラスのオブジェクト

                String[] connection = analizeCommandline(args);

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
                client = new SimpleClient2(connection);

                //GUIとクライアントを繋げる
                con = new Connector(gui, client);
                gui.setConnector(con);
                client.setConnector(con);
        }

        /**
         * コマンドライン引数から接続先情報を決定する。
         * 引数が指定されない場合はデフォルト値を用いる。
         * @param args コマンドライン引数
         * @return ホスト名とポート番号の文字列配列
         */
        private static String[] analizeCommandline(String[] args){
                String host = DEFAULT_HOST;
                int port = DEFAULT_PORT;

                if(args.length == 0){
                        System.out.println("Main> 引数が指定されなかったため、デフォルト設定("+host+":"+port+")を使用します。<analizeCommandline>");
                        return new String[]{host, String.valueOf(port)};
                }

                if(args.length != 2){
                        System.out.println("Main> 引数を0個または2つ指定してください。<analizeCommandline>");
                        System.out.println("Main>  第一引数：サーバのホスト名(IPアドレス)<analizeCommandline>");
                        System.out.println("Main>  第二引数：通信用のポート番号<analizeCommandline>");
                        System.exit(1);
                }

                try{
                        host = args[0];
                        port = Integer.parseInt(args[1]);
                        System.out.println("Main> 接続するサーバのホスト名は"+host+"、ポート番号は"+port+"とします。<analizeCommandline>");
                        return new String[]{host, String.valueOf(port)};
                }
                catch(NumberFormatException e){
                        System.err.println(""+e+":引数が数字ではありません。<analizeCommandline>");
                        System.out.println("Main> ポート番号は半角数字で入力してください。<analizeCommandline>");
                        System.exit(1);
                }

                return new String[]{host, String.valueOf(port)};
        }
}

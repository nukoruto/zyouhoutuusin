package step03;

import javax.swing.JFrame;

/**
 *  GUI�����SimpleServer�Ƃ̒ʐM���s���v���O�����̋N��
 *<BR>
 *<BR>  �����F
 *<BR>  �E�\���p�v���O�����̃I�u�W�F�N�g�̐����ƁA�\���pJFrame�̐ݒ�B
 *<BR>  �EIP�A�h���X�ƃ|�[�g�ԍ��̎w�肵�A�ʐM�p�v���O�����̃I�u�W�F�N�g�̐����B
 *<BR>  �E���p�p�v���O�����̃I�u�W�F�N�g�̐����B
 *<BR>  �E�\���p�v���O�����ƒʐM�p�v���O�����̃I�u�W�F�N�g���ꂼ��ɁA���p�p�v���O�����̃I�u�W�F�N�g��ݒ�B
 *<BR>
 */

/**
 * @author nakano
 *	Version: 1.00
 *	last change: Mar 2012
 */
public class Main {
	/**
	 * ���C�����\�b�h
	 */
	public static void main(String[] args){
		Connector con; //���p�p�N���X�̃I�u�W�F�N�g
		GUIPanel2 gui; //�\���p�N���X�̃I�u�W�F�N�g
		SimpleClient2 client; //�ʐM�p�N���X�̃I�u�W�F�N�g

		//GUI�̕\��
		gui = new GUIPanel2();
		JFrame frame = new JFrame("SampleAppletcation");
		frame.setSize(500, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(gui);
		//�p�l���̍Ĕz�u
		gui.revalidate();

		//�N���C�A���g�̋N��
		String[] data = {"127.0.0.1", "4000"}; //IP�A�h���X�ƃ|�[�g�ԍ��y�d�v�z
		client = new SimpleClient2(data);

		//GUI�ƃN���C�A���g���q����
		con = new Connector(gui, client);
		gui.setConnector(con);
		client.setConnector(con);
	}
}

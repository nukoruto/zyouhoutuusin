package step03;

/**
 *  �\���p�v���O�����ƒʐM�p�v���O�����Ƃ̒��p���s�����߂̃v���O����
 *<BR>
 *<BR>  �����F
 *<BR>  �E�\���p�v���O�����ƒʐM�p�v���O�����Ƃ̒��p�B
 *<BR>
 *<BR>  �Ǘ����Ă����ȃt�B�[���h
 *<BR>  �Egui:  �\���p�v���O�����̃I�u�W�F�N�g�B
 *<BR>  �Eclient:  �ʐM�p�v���O�����̃I�u�W�F�N�g�B
 */

/**
 * @author nakano
 *	Version: 1.00
 *	last change: Mar 2012
 */
public class Connector {
	private GUIPanel2 gui; //�\���p�v���O�����̃I�u�W�F�N�g
	private SimpleClient2 client; //�ʐM�p�v���O�����̃I�u�W�F�N�g

	/**
	 * �R���X�g���N�^
	 *
	 * @param gui
	 * @param client
	 */
	public Connector(GUIPanel2 gui, SimpleClient2 client){
		this.gui = gui;
		this.client = client;
	}

	/**
	 * ���p�p���\�b�h�i�\���p�v���O�����˒ʐM�p�v���O�����j
	 * @param msg
	 */
	public void sendMessage(String msg){
		client.sendMessage(msg);
	}

	/**
	 * ���p�p���\�b�h�i�ʐM�p�v���O�����˕\���p�v���O�����j
	 * @param msg
	 */
	public void displayMessage(String msg){
		gui.displayMessage(msg);
	}

}

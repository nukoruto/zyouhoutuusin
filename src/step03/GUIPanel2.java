package step03;

//�L�q�iimport�j///////////////////////////////////
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *  GUIPanel���p�������v���O����
 *<BR>
 *<BR>  �����F
 *<BR>  �E�\���p�v���O�����Ƃ��Ă̖�����S���A�ʐM�p�v���O�����Ƃ̘A�g���s���B
 *<BR>  �E�ʐM�p�v���O�����ւ̏����˗��̓t�B�[���hcon�ɂ���čs���B
 *<BR>  �E�\���p�v���O�����̎�v�ȋL�q�́A�p���ɂ���Ď����ς݁B
 *<BR>  �E�ʐM�p�v���O�����ւ̏����˗����������郁�\�b�h�̂݃I�[�o�[���C�h���s���B
 *<BR>
 *<BR>  �Ǘ����Ă����ȃt�B�[���h
 *<BR>  �Econ:  �ʐM�p�v���O�����֏������˗��������ꍇ�ɗp����I�u�W�F�N�g�B
 */

/**
 * @author nakano
 *	Version: 1.00
 *	last change: Mar 2012
 */
public class GUIPanel2 extends JPanel{//�C���iJPanel�ł̓_���B�\���p�v���O�����̌p���A�p�b�P�[�Wgui�ɂ���B�j///////////////////////////////////
	private Connector con; //���p�p�N���X�̃I�u�W�F�N�g

	/**
	 * �R���X�g���N�^
	 * �X�[�p�[�N���X�̃R���X�g���N�^���Ăяo���̂݁B
	 */
	public GUIPanel2(){
		//�L�q///////////////////////////////////
	}

	/**
	 * �ʐM�p�v���O�����iSimpleClient2�j�Ƃ̒��p���s�����߂�Connector�N���X�̃I�u�W�F�N�g���Z�b�g����B
	 * @param con
	 */
	public void setConnector(Connector con){
		this.con = con;
	}

	/**
	 * ���̃��\�b�h�ł́A�X�[�p�[�N���X�̃t�B�[���hjta�ɁA����msg�̒l��ǋL���鏈�����L�q����B
	 * ���̃��\�b�h�́A�O���N���X�iSimpleClient2�j�̃t�B�[���hcon����AConnector�N���X�̃��\�b�h���o�R���ČĂяo�����B
	 * @param msg
	 */
	public void displayMessage(String msg){
		//�L�q///////////////////////////////////
	}

	/**
	 *<BR> �yOverride�zActionEvent�̃C�x���g����
	 */
	public void actionPerformed(ActionEvent e) {
		//�L�q///////////////////////////////////
	}

}

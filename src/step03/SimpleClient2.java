package step03;

//�L�q�iimport�j///////////////////////////////////
import java.io.IOException;



/**
 *  SimpleClient���p�������v���O����
 *<BR>
 *<BR>  �����F
 *<BR>  �E�ʐM�p�v���O�����Ƃ��Ă̖�����S���A�\���p�v���O�����Ƃ̘A�g���s���B
 *<BR>  �E�\���p�v���O�����ւ̏����˗��̓t�B�[���hcon�ɂ���čs���B
 *<BR>  �E�ʐM�p�v���O�����̎�v�ȋL�q�́A�p���ɂ���Ď����ς݁B
 *<BR>  �E�\���p�v���O�����ւ̏����˗����������郁�\�b�h�̂݃I�[�o�[���C�h���s���B
 *<BR>
 *<BR>  �Ǘ����Ă����ȃt�B�[���h
 *<BR>  �Econ:  �\���p�v���O�����֏������˗��������ꍇ�ɗp����I�u�W�F�N�g�B
 */

/**
 * @author nakano
 *	Version: 1.00
 *	last change: Mar 2012
 */
public class SimpleClient2 {//�L�q�i�ʐM�p�v���O�����̌p���A�p�b�P�[�Wstep02�ɂ���j///////////////////////////////////
	private Connector con; //���p�p�N���X�̃I�u�W�F�N�g

	/**
	 * �R���X�g���N�^
	 * �X�[�p�[�N���X�̃R���X�g���N�^���Ăяo���̂݁B
	 * @param args
	 */
	public SimpleClient2(String[] args) {
		//�L�q///////////////////////////////////
	}

	/**
	 * �\���p�v���O�����iGUIPanel2�j�Ƃ̒��p���s��Connector�N���X�̃I�u�W�F�N�g���Z�b�g����B
	 * @param con
	 */
	public void setConnector(Connector con){
		this.con = con;
	}

	/**
	 * ���̃��\�b�h�ł́A�X�[�p�[�N���X�̃t�B�[���hout��p�����T�[�o�[�ւ̑��M�������L�q����B
	 * ���̃��\�b�h�́A�O���N���X�iGUIPanel2�j�̃t�B�[���hcon����AConnector�N���X�̃��\�b�h���o�R���ČĂяo�����B
	 * @param con
	 */
	public void sendMessage(String msg){
		//�L�q///////////////////////////////////
	}

	/**
	 * �yOverride�z�@�X���b�h�̎��́i�T�[�o�Ƃ̒ʐM�����j
	 * ���̃��\�b�h���ł́A�T�[�o�[����̃��b�Z�[�W����M���A�\���p�v���O�����ɂĕ\��������B
	 * ���̃��\�b�h���ł́A�T�[�o�[�ւ̃��b�Z�[�W�̑��M�͍s��Ȃ��B
	 * ��M����������́A�t�B�[���hcon�̃��\�b�hdisplayMessage���\�b�h�ŕ\���p�v���O�����iGUIPanel2�j�ɓn���B
	 */
	public void run(){
		//�L�q///////////////////////////////////
	}


}

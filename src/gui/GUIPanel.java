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

//�����̃p�b�P�[�W�̗��p
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
 *  �`���b�g�N���C�A���g�̂��߂�GUI�v���O����
 *<BR>  
 *<BR>  �����F
 *<BR>  �E�`���b�g�N���C�A���g�Ƃ��Ă�GUI��񋟂���B
 *<BR>  �E�e�L�X�g�t�B�[���h�ɂāA�������ł�����Enter�L�[�������΁A�ł����񂾕�������e�L�X�g�G���A�ɒǋL�����A�����ɁA�T�[�o�֑��M����B
 *<BR>  �E�T�[�o����󂯎�����������\������e�L�X�g�G���A��ݒu���Ă���B
 *<BR>
 *<BR>  �Ǘ����Ă����ȃt�B�[���h
 *<BR>  �Eparent: ����GUIPanel�N���X�̃I�u�W�F�N�g�𐶐������N���X�B���̃I�u�W�F�N�g��ʂ��āA�t�B�[���h�⃁�\�b�h�𗘗p����B
 *<BR>  �Ejta:  �������\������e�L�X�g�G���A�B���N���X���痘�p�ł���悤protected�錾�Ƃ��Ă���B
 *<BR>  �Ejtf:  ���������͂���e�L�X�g�t�B�[���h�B���N���X���痘�p�ł���悤protected�錾�Ƃ��Ă���B
 */
public class GUIPanel extends JPanel implements ActionListener, ItemListener{
	/** actionPerformed���\�b�h��AitemStateChanged�ŎQ�Ƃ���̂Ń����o�ϐ��Ƃ��Đ錾 */
	protected JTextArea jta;
	
	/** actionPerformed���\�b�h��AitemStateChanged�ŎQ�Ƃ���̂Ń����o�ϐ��Ƃ��Đ錾 */
	protected JTextField jtf;
	

/**
 *<BR> �R���X�g���N�^
 *<BR> �E�R���|�[�l���g�̔z�u
 *<BR> �EJButton�N���X�̃I�u�W�F�N�g�ɑ΂��ăC�x���g���X�i�̓o�^
 *<BR> �EChoice�N���X�̃I�u�W�F�N�g�ɑ΂��ăC�x���g���X�i�̓o�^
 */
	public GUIPanel(){
		super();
	
		//�R���|�[�l���g�n�N���X�̃I�u�W�F�N�g�̍쐬
		//���x��
		JLabel jl = new JLabel("�R���|�[�l���g�̘A�g", JLabel.CENTER);
		//�{�^��
		JButton jb = new JButton("������");
		//�e�L�X�g�G���A
		jta = new JTextArea("JTextArea\n");
		//�e�L�X�g�t�B�[���h
		jtf = new JTextField("JTextField");
		//�|�b�v�A�b�v���j���[
		Choice choice = new Choice();
		choice.add("Black");
		choice.add("Red");
		choice.add("Green");
		choice.add("Blue");
		
		//�R���|�[�l���g�̔z�u
		//���C�A�E�g�̐ݒ�
		this.setLayout(new BorderLayout());
		//�z�u
		this.add(jl, BorderLayout.NORTH);
		this.add(jb, BorderLayout.WEST);
		this.add(choice, BorderLayout.EAST);
		this.add(jta, BorderLayout.CENTER);
		this.add(jtf, BorderLayout.SOUTH);
		
		//�{�^���ɃC�x���g
		jb.addActionListener(this);
		jtf.addActionListener(this);
		
		choice.addItemListener(this);
	}
	
/**
 *<BR> ActionEvent�̃C�x���g����
 *<BR> �E���ۃN���XActionListener�ɋL����Ă��郁�\�b�hactionPerformed�̃I�[�o�[���C�h
 *<BR> �E�{�^�����N���b�N������A�e�L�X�g�G���A�ɁA�e�L�X�g�t�B�[���h�ɓ��͂���Ă��镶����ǋL���A�e�L�X�g�t�B�[���h�̓��͂��󔒂ɂ���B
 */
	public void actionPerformed(ActionEvent e){
		System.out.println("ActionEvent");
		if(e.getSource() instanceof JButton){
			String str = e.getActionCommand();
			System.out.println("> "+str);
			if("������".equals(str)){
				jta.setText("���������܂����B\n");
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
 *<BR> ItemEvent�̃C�x���g����
 *<BR> �E���ۃN���XItemListener�ɋL����Ă��郁�\�b�hitemStateChanged�̃I�[�o�[���C�h
 *<BR> �E�`���C�X�̑I���A�C�e����I�񂾂�A�I�������A�C�e���i�F���j�ɂ��������āA�e�L�X�g�G���A�̐F�̐ݒ��ς���B
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

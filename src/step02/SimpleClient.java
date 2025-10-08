package step02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *  �N���C�A���g�v���O�������N�������郁�C���v���O����
 *<BR>  
 *<BR>  �����F
 *<BR>  �E�\�P�b�g�ʐM���s���N���C�A���g�𗧂��グ��B
 *<BR>  �E�T�[�o�Ƃ̐ڑ���́A�W�����͂ɂĕ��������͂��A�T�[�o�ɑ��M����B
 *<BR>  �E��������T�[�o�ɑ��M������́A�T�[�o����̕ԓ���҂B
 *<BR>  �E�T�[�o����̕ԓ�����M������A���̂܂܂��̕������W���o�͂���B
 *<BR>
 *<BR>  �Ǘ����Ă����ȃt�B�[���h
 *<BR>  �Ehost:  �\�P�b�g�ʐM���s���z�X�g���B�����l��""�i��j�B
 *<BR>  �Eport1:  �\�P�b�g�ʐM���s���|�[�g�ԍ��B�����l��999�B
 *<BR>  �Eport2:  �\�P�b�g�ʐM���s���|�[�g�ԍ��B�����l��888�B
 *<BR>  �Esocket: �T�[�o�ƒʐM���邽�߂̃\�P�b�g�B
 *<BR>  �Ein: �\�P�b�g�̐ڑ����肩�當�������M����I�u�W�F�N�g�Bclient_socket����ɍ����B
 *<BR>  �Eout:�\�P�b�g�̐ڑ�����ɕ�����𑗐M����I�u�W�F�N�g�Bclient_socket����ɍ����B 
 *<BR>  �Estd_in: �W�����͂���镶������󂯎��I�u�W�F�N�g�BSystem.in����ɍ����B
 */

/**
 * @author nakano
 *	Version: 1.05
 *	last change: Oct 2014
 */
public class SimpleClient extends Thread {
	/** �z�X�g�� */
	private String host = "10.20.24.1"; //�T�[�o��IP�A�h���X
	/** �|�[�g�ԍ�(1000�Ԉȉ��y�d�v�z) */
	private int port1 = 999; //�y�d�v�z�T�[�o�֑��M����|�[�g
	private int port2 = 888; //�y�d�v�z�T�[�o�����M����|�[�g

	/** �N���C�A���g�̃\�P�b�g */
	private Socket socket;
	/** �\�P�b�g���當�������M���邽�߂̃I�u�W�F�N�g */
	protected BufferedReader in;
	/** �\�P�b�g���當����𑗐M���邽�߂̃I�u�W�F�N�g */
	protected PrintWriter out;
	
	/** �W�����͂��當������󂯎�邽�߂̃I�u�W�F�N�g */
	private BufferedReader std_in;

/**
 *<BR> ���C�����\�b�h
 */
	public static void main(String[] args) {
		new SimpleClient(args);
	}

/**
 *<BR> �ۑ�@�|�O�F�@�R���X�g���N�^�y�m�F��Ɓz
 *<BR>   �E�N���C�A���g�̏����̗�����m�F���邱�ƁB
 */
	public SimpleClient(String[] args) {
		super();
		
		//IP�ƃ|�[�g�ԍ��̊m��
		boolean f1 = this.analizeCommandline(args); //�ۑ�A�|�P
		if(!f1){
			System.exit(1);
		}
		
		//�\�P�b�g�̐����ƃT�[�o�ւ̐ڑ�
		boolean f2 = this.setSocket(); //�ۑ�A�|�Q
		if(!f2){
			System.exit(1);
		}
		
		//�T�[�o�Ɛڑ������\�P�b�g����A���o�̓I�u�W�F�N�g�̐���
		boolean f3 = this.setIO(); //�ۑ�A�|�R
		if(!f3){
			System.exit(1);
		}
		
		//�`���b�g�N���C�A���g�̋@�\�n���ƏI��
		if(f1 && f2 && f3){
			System.out.println("\n/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/");
			System.out.println("SimpleClient (Ver 1.00)");
			System.out.println("  connectTo: "+host+":"+port1);
			System.out.println("/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/");
			
			this.start(); //�ۑ�A�|�S
		}
		else{
			System.exit(1);
		}
	}
	
/**
 *<BR> �ۑ�A�|�P�F�@�R�}���h���C����͏���
 *<BR>   �EAPI�ɂ�Integer�N���X��parseInt���\�b�h���m�F���邱�ƁB
 *<BR>   �E������2�ŁA�������������A�������������Ȃ�΁A���ꂼ��A�z�X�g���A�|�[�g�ԍ��Ƃ��Đݒ肷��B
 *<BR>   �E��������q�ȊO�̏ꍇ�ɂ́Afalse��Ԃ��B
 *<BR>   �E��O�������̏�����false��Ԃ��B
 */
	public boolean analizeCommandline(String[] args){
		if(args.length == 2){
			try{
				host = args[0];
				port1 = Integer.parseInt(args[1]);
			}
			catch(NumberFormatException e){
				System.err.println(""+e+":�����������ł͂���܂���B<analizeCommandline>");
				System.out.println("Client> �|�[�g�ԍ��͔��p�����œ��͂��Ă��������B<analizeCommandline>");
				return false;
			}
			
			System.out.println("Client> �ڑ�����T�[�o�̃z�X�g����"+host+"�A�|�[�g�ԍ���"+port1+"�Ƃ��܂��B<analizeCommandline>");
			return true;
		}
		
		System.out.println("Client> ������2�w�肵�Ă��������B<analizeCommandline>");
		System.out.println("Client>  �������F�T�[�o�̃z�X�g��(IP�A�h���X)<analizeCommandline>");
		System.out.println("Client>  �������F�ʐM�p�̃|�[�g�ԍ�<analizeCommandline>");
		return false;
	}
	
/**
 *<BR> �ۑ�A�|�Q�F�@�\�P�b�g�̐�������
 *<BR>   �EAPI�ɂ�Socket�N���X�̃R���X�g���N�^�����m�F���邱�ƁB
 *<BR>   �E�I�u�W�F�N�g�̐����A�|�[�g�ԍ��̐ݒ������isocket��bind�j�B
 *<BR>   �E��O�������̏�����false��Ԃ��B
 */
	public boolean setSocket(){
		try{
			
			
			
			
			
			
			
			
			
			System.out.println("Clien> �T�[�o�Ƃ̐ڑ��ɐ������܂����B<setSocket>");
			return true;
		}
		catch(Exception e){ //IOException
			System.err.println(""+e+":�T�[�o�Ƃ̐ڑ��Ɏ��s���܂����B<setSocket>");
			return false;
		}
	}
	
/**
 *<BR> �ۑ�A�|�R�F�@���o�̓I�u�W�F�N�g�̐�������
 *<BR>   �EAPI�ɂ�BufferedReader�N���X�APrintWriter�N���X�𒲂ׂ邱�ƁB
 *<BR>   �E�����R�[�h��SJIS���w�肷��B
 *<BR>   �E��O�������̏�����false��Ԃ��B
 */
	public boolean setIO(){
		try{
			
			
			
			
			
			
			
			
			
			
			
			
			System.out.println("Client> ���o�̓I�u�W�F�N�g�𐶐����܂����B<setIO>");
			return true;
		}
		catch(Exception e){ //IOException
			System.err.println(""+e+":���o�̓I�u�W�F�N�g�̐����Ɏ��s���܂����B<setIO>");
			return false;
		}
	}



/**
 *<BR> �ۑ�A�|�S�F�@�X���b�h�̎��́i�T�[�o�Ƃ̒ʐM�����j
 *<BR>   �E�W�����͂����������msg1�Ɋi�[���āA�T�[�o�ɑ��M����B
 *<BR>   �E��M�����������msg2�Ɋi�[����B
 *<BR>   �Emsg2�i��M����������j��null�Ȃ�΁A�ʐM���ɃG���[���N�������Ɣ��f���A�ʐM���I��������B
 *<BR>   �Emsg2�i��M����������j����L�ȊO�Ȃ�΁A�W���o�͂��A�ȏ�̏������J��Ԃ��B
 *<BR>   �E�ʐM�I���̏���������B�i�ۑ�A�|�T�j
 */
	public void run(){
		String msg1 = "";
		String msg2 = "";
		boolean done = false;
		try{
			while(!done){
				System.out.println("");
				
				
				
				
				
				
				
				
				if(msg2 == null){
					System.out.println("Client> �T�[�o�Ƃ̐ڑ����؂�Ă��܂��B<run>");
					done = true;
				}
				else{
					System.out.println("Client> �T�[�o����̕�������󂯎��܂����B<run>");
					System.out.println(msg2);
				}
			}
			
			this.close();  //�ۑ�A�|�T
		}
		catch (Exception e) { //IOException
			System.out.println(e);
			System.exit(1);
		}
	}
	
/**
 *<BR> �ۑ�A�|�T�F�@�v���O�����̏I������
 *<BR>   �E���o�̓I�u�W�F�N�g�̏I��
 *<BR>   �E�X���b�h(Socket)�̏I��
 */
	public void close(){
		try{
			in.close();
			out.close();
			std_in.close();
			
			System.out.println("Client> socket�̏I�������܂��B<close>");
			socket.close();
			System.out.println("Client> socket�̏I�����܂����B<close>");
			System.out.println("Client> �v���O�������I�������܂��B<close>");
			System.exit(0);
		}
		catch(Exception e){
			System.err.println(""+e+":�I�u�W�F�N�g�̏I���Ɏ��s���܂����B<close>");
		}
	}
}

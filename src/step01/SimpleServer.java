package step01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  �T�[�o�v���O�������N�������郁�C���v���O����
 *<BR>  
 *<BR>  �����F
 *<BR>  �E�\�P�b�g�ʐM���s���T�[�o�𗧂��グ��B
 *<BR>  �E�N���C�A���g�P�݂̂��󂯕t����B
 *<BR>  �E�N���C�A���g�Ƃ̐ڑ���́A�N���C�A���g����̕�������󂯎��A�ԓ�������B
 *<BR>
 *<BR>  �Ǘ����Ă����ȃt�B�[���h
 *<BR>  �Eport:  �\�P�b�g�ʐM���s���|�[�g�ԍ��B�����l��4000�B
 *<BR>  �Eserver_socket: �T�[�o�p�̃\�P�b�g�B�N���C�A���g�̐ڑ���҂B
 *<BR>  �Eclient_socket: �T�[�o�ɐڑ����������ۂɍ����I�u�W�F�N�g�B
 *<BR>  �Ein: �\�P�b�g�̐ڑ����肩�當�������M����I�u�W�F�N�g�Bclient_socket����ɍ����B
 *<BR>  �Eout:�\�P�b�g�̐ڑ�����ɕ�����𑗐M����I�u�W�F�N�g�Bclient_socket����ɍ����B 
 */

/**
 * @author nakano
 *	Version: 1.03
 *	last change: Mar 2012
 */
public class SimpleServer extends Thread {
	/** �|�[�g�ԍ�(1000�Ԉȉ�) */
	private int port = 999; //�N���C�A���g�����M����|�[�g
	
	/** �T�[�o�p�̃\�P�b�g */
	private ServerSocket listen_socket;
	/** �N���C�A���g����T�[�o�ɐڑ����������ۂɍ����I�u�W�F�N�g */
	private Socket client_socket;
	
	/** �\�P�b�g���當�������M���邽�߂̃I�u�W�F�N�g */
	private BufferedReader in;
	/** �\�P�b�g���當����𑗐M���邽�߂̃I�u�W�F�N�g */
	private PrintWriter out;
	
/**
 *<BR> ���C�����\�b�h
 */
	public static void main(String[] args) {
		new SimpleServer(args);
	}
	
/**
 *<BR> �ۑ�1�|�O�F�@�R���X�g���N�^�y�m�F��Ɓz
 *<BR>   �E�T�[�o�̏����̗�����m�F���邱�ƁB
 */
	public SimpleServer(String[] args){
		super();
		
		//�|�[�g�ԍ��̊m��
		boolean f1 = this.analizeCommandline(args);//�ۑ�1�|�P
		if(!f1){
			System.exit(1);
		}
		
		//�\�P�b�g�̐�����bind����
		boolean f2 = this.setSocket();//�ۑ�1�|�Q
		
		if(!f2){
			System.exit(1);
		}
		else{
			System.out.println("\n/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/");
			System.out.println("SimpleServer (Ver 1.00)");
			System.out.println("  Port : "+port);
			System.out.println("/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/\n");
		}
		
		//�󓮓I�I�[�v��������accept����
		boolean f3 = this.waitClient();//�ۑ�1�|�R
		if(!f3){
			System.exit(1);
		}
		
		//�N���C�A���g�Ɛڑ������\�P�b�g����A���o�̓I�u�W�F�N�g�̐���
		boolean f4 = this.setIO();//�ۑ�1�|�S
		if(!f4){
			System.exit(1);
		}
		
		//�T�[�o���A�N���C�A���g���̕\��
		boolean f5 = this.printSocketInfo(); //�ۑ�1�|�T
		if(!f5){
			System.exit(1);
		}
		
		//�`���b�g�T�[�o�̋@�\�n���ƏI��
		if(f3 && f4 && f5){
			start(); //�ۑ�1�|�U
		}
		else{
			System.exit(1);
		}
	}
	
	
/**
 *<BR> �ۑ�1�|�P�F�@�R�}���h���C����͏����y�m�F��Ɓz
 *<BR>   �EAPI�ɂ�Integer�N���X��parseInt���\�b�h���m�F���邱�ƁB
 *<BR>   �E������1�ŁA�����Ȃ�΁A�|�[�g�ԍ��Ƃ��Đݒ肷��B
 *<BR>   �E��������q�ȊO�̏ꍇ�ɂ́A�|�[�g�ԍ��͏����l�̂܂܂Ƃ���B
 */
	public boolean analizeCommandline(String[] args){
		if(args.length > 0){
			try{
				port = Integer.parseInt(args[0]);
			}
			catch(NumberFormatException e){
				System.err.println(""+e+":�����������ł͂���܂���B<analizeCommandline>");
				System.out.println("Server> ���͖͂������܂��B<analizeCommandline>");
			}
		}
		
		System.out.println("Server> �|�[�g�ԍ���"+port+"�Ƃ��܂��B<analizeCommandline>");
		return true;
	}
	
/**
 *<BR> �ۑ�1�|�Q�F�@�T�[�o�\�P�b�g�̐��������y�\�[�X�R�[�h�ǋL��Ɓz
 *<BR>   �EAPI�ɂ�ServerSocket�N���X�̃R���X�g���N�^�����m�F���邱�ƁB
 *<BR>   �E�I�u�W�F�N�g�̐����A�|�[�g�ԍ��̐ݒ������isocket��bind�j�B
 *<BR>   �E��O�������̏�����false��Ԃ��B
 */
	public boolean setSocket(){
		try{
			
			
			
			
			
			System.out.println("Server> �T�[�o�\�P�b�g�̐����ɐ������܂����B<setSocket>");
			return true;
		}
		catch(Exception e){ //IOException: �\�P�b�g�̐����Ɏ��s
			System.err.println(""+e+":�T�[�o�\�P�b�g�̐����Ɏ��s���܂����B<setSocket>");
			return false;
		}
	}
	
/**
 *<BR> �ۑ�1�|�R�F�@�\�P�b�g��t�҂������y�\�[�X�R�[�h�ǋL��Ɓz
 *<BR>   �EAPI�ɂ�ServerSocket�N���X�̃��\�b�h�𒲂ׂ邱�ƁB
 *<BR>   �E��O�������̏�����false��Ԃ��B
 */
	public boolean waitClient(){
		try{
			System.out.println("Server> �T�[�o�\�P�b�g�ɃA�N�Z�X������܂őҋ@���܂��B<waitClient>");
			
			
			
			
			
			System.out.println("Server> �T�[�o�\�P�b�g�ɃA�N�Z�X������܂����B<waitClient>");
			return true;
		}
		catch(Exception e){ //IOException
			System.err.println(""+e+":�N���C�A���g�Ƃ̐ڑ��Ɏ��s���܂����B<waitClient>");
			return false;
		}
	}
	
/**
 *<BR> �ۑ�1�|�S�F�@���o�̓I�u�W�F�N�g�̐��������y�\�[�X�R�[�h�ǋL��Ɓz
 *<BR>   �EAPI�ɂ�BufferedReader�N���X�APrintWriter�N���X�𒲂ׂ邱�ƁB
 *<BR>   �E�����R�[�h��SJIS���w�肷��B
 *<BR>   �E��O�������̏�����false��Ԃ��B
 */
	public boolean setIO(){
		try{
			
			
			
			
			
			
			
			System.out.println("Server> ���o�̓I�u�W�F�N�g�𐶐����܂����B<setIO>");
			return true;
		}
		catch(Exception e){ //IOException
			System.err.println(""+e+":���o�̓I�u�W�F�N�g�̐����Ɏ��s���܂����B<setIO>");
			return false;
		}
	}
	
/**
 *<BR> �ۑ�1�|�T�F�@�\�P�b�g�̐ڑ���Ɛڑ����̏���client_socket������o���ĕW���o�́B�y�\�[�X�R�[�h�ύX��Ɓz
 *<BR>   �EAPI�ɂ�Socket�N���X�𒲂ׂ邱�ƁB
 */
	public boolean printSocketInfo(){
		System.out.println("Server> Socket�̏���\�����܂��B<printSocketInfo>");
		System.out.println(" �y�T�[�o�z");
		System.out.println("   �z�X�g��: "                                     );
		System.out.println("   �|�[�g�ԍ�: "                                   );
		System.out.println("   �\�P�b�g�A�h���X: "                             );
		System.out.println(" �y�N���C�A���g�z");
		System.out.println("   �z�X�g��: "                                     );
		System.out.println("   �|�[�g�ԍ�: "                                   );
		System.out.println("   �\�P�b�g�A�h���X: "                             );
		
		return true;
	}
	
/**
 *<BR> �ۑ�1�|�U�F�@�X���b�h�̎��́i�N���C�A���g�Ƃ̒ʐM�����j�y�\�[�X�R�[�h�ǋL��Ɓz
 *<BR>   �E��M����������null�Ȃ�΁A�ʐM���ɃG���[���N�������Ɣ��f���Awhile�����甲����B
 *<BR>   �E��M���������񂪁ubye�v�Ȃ�΁A�ʐM�̏I���Ɣ��f���Awhile�����甲����B
 *<BR>   �E��M������������L�ȊO�Ȃ�΁A������̐擪�ɁuECHO: �v��t�^���āAClient�ɑ��M��while�����J��Ԃ��B
 *<BR>   �Ewhile�����甲���o������A�ʐM�I���̏������s���B�i�ۑ�1�|�V�j
 *<BR>   �Etry�̃X�R�[�v�ŗ�O(IOException )�����������ꍇ�́A���̎��_���狭���I��catch�֔�ԁB
 */
	public void run(){
		String msg = "";
		boolean done = false;
		try{
			while(!done){
				System.out.println("");
				msg = in.readLine();
				
				if(msg == null){
					System.out.println("Server> �N���C�A���g�Ƃ̐ڑ����؂�Ă��܂��B<run>");
					done = true;
				}
				else if(msg.equals("bye")){
					System.out.println("Server> �N���C�A���g����ڑ��I���̍����t�����܂����B<run>");
					
					
					
				}
				else{
					System.out.println("Server> �N���C�A���g����̕�������󂯎��܂����B<run>");
					System.out.println(msg);
					
					
					
					
					
					
					System.out.println("Server> �N���C�A���g�փ��b�Z�[�W�𑗂�܂����B<run>");
				}
			}
			
			this.close();  //�ۑ�1�|�V
		}
		catch(IOException e){
			System.err.println(""+e+":�N���C�A���g�Ƃ̐ڑ��Ɏ��s���܂����B<run>");
		}
	}
	
/**
 *<BR> �ۑ�1�|�V�F�@�v���O�����̏I�������y�m�F��Ɓz
 *<BR>   �E���o�̓I�u�W�F�N�g�̏I��
 *<BR>   �E�X���b�h(ServerSocket��Socket)�̏I��
 */
	public void close(){
		try{
			System.out.println("Server> ���o�͗p�̃I�u�W�F�N�gin��out���I�������܂��B<close>");
			in.close();
			out.close();
			System.out.println("Server> listen_socket��client_socket���I�������܂����B<close>");
			
			System.out.println("Server> listen_socket��client_socket���I�������܂��B<close>");
			listen_socket.close();
			client_socket.close();
			System.out.println("Server> listen_socket��client_socket���I�������܂����B<close>");
			System.out.println("Server> �v���O�������I�������܂��B<close>");
			System.exit(0);
		}
		catch(Exception e){
			System.err.println(""+e+":�I�u�W�F�N�g�̏I���Ɏ��s���܂����B<close>");
			System.exit(1);
		}
	}
}

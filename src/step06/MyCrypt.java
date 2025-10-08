package step06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *  �����AES�̕ϊ����s���N���X
 *<BR>
 *<BR>  �����F
 *<BR>  �E�ϊ��͐ÓI�ȃ��\�b�h���ōs�����߁A�I�u�W�F�N�g�̐����͕s�v�B
 *<BR>
 *<BR>  �Ǘ����Ă����ȃt�B�[���h
 *<BR>  �Estatic Charset charset:  �����R�[�h���w�肷�邽�߂̒l
 *<BR>
 *<BR>  �Ǘ����Ă����ȃ��\�b�h
 *<BR>  �Estatic String encode(String str,String strK,String strV):  AES�̃G���R�[�h�ϊ�
 *<BR>  �Estatic String decode(String str,String strK,String strV):  AES�̃f�R�[�h�ϊ�
 */

public class MyCrypt {
    /** �`���[�Z�b�g�i�����R�[�h�j�̎w��ɗp����l */
    public static Charset charset = StandardCharsets.UTF_8;

/**
  �G���R�[�h���郁�\�b�h
  str1: ���̕�����
  �߂�l: �G���R�[�h��̕�����(str2)

  �A���S���Y��
�@�����P�D���̍쐬
�@�����Q�D�������x�N�g���̍쐬
�@�����R�D�ϊ���̍쐬
�@�����S�D�ϊ���̏����ݒ�i���[�h�i�G���R�[�h���f�R�[�h���̏��j�P�ƂQ�̃I�u�W�F�N�g���K�v�j
�@�����T�D�ϊ��F������i�����j��byte�z�񁨇C���g���ĕϊ���byte�z��
�@�����U�D�D��Base64�ŃG���R�[�h���ĕ�����i�Í����j
*/
	public static String encode(String str1,String strK,String strV) {
		try {
		    String str2 = "";

		    //�����P�D�y�E�ӕύX�z
			SecretKeySpec key = null;

		    //�����Q�D�y�E�ӕύX�z
			IvParameterSpec iv = null;

		    //�����R�D�y�E�ӕύX�z
			Cipher cipher = null;

		    //�����S�D�y1�s�ǉ��z


		    //�����T�D�y�E�ӕύX�z
			byte[] bary = null;
            System.out.print("AES >> ");
            for (byte b : bary) {
                System.out.print(String.format("%02X", b));
            }
            System.out.println(" >> new String >> "+new String(bary, charset));
            System.out.println("");

		    //�����U�D�y�m�F�̂݁z
            str2 = Base64.getEncoder().encodeToString(bary);

			return str2;

		} catch (Exception e) {
		    System.out.println(e.toString()+"<encode@MyCrypt>");
		}
		return null;
	}

/**
  �f�R�[�h���郁�\�b�h
  str2: ���̕�����
  �߂�l: �f�R�[�h��̕�����(str3)

  �A���S���Y��
�@�����P�D���̍쐬
�@�����Q�D�������x�N�g���̍쐬
�@�����R�D�ϊ���̍쐬
�@�����S�D�ϊ���̏����ݒ�i���[�h�i�G���R�[�h���f�R�[�h���̏��j�P�ƂQ�̃I�u�W�F�N�g���K�v�j
�@�����T�D�ϊ��F������i�����j��byte�z�񁨇C���g���ĕϊ���byte�z��
�@�����U�D�D��Base64�ŃG���R�[�h���ĕ�����i�Í����j
*/
	public static String decode(String str2,String strK,String strV) {
		try {
		    String str3 = "";

		    //�����P�D�y�E�ӕύX�z
			SecretKeySpec key = null;

		    //�����Q�D�y�E�ӕύX�z
			IvParameterSpec iv = null;

		    //�����R�D�y�E�ӕύX�z
			Cipher cipher = null;

		    //�����S�D�y1�s�ǉ��z


		    //�����T�D�y�E�ӕύX�z
			byte[] bary = null;

		    //�����U�D�y�m�F�̂݁z
			str3 = new String(bary, charset);

			return str3;
		} catch (Exception e) {
		    System.out.println(e.toString()+"<decode@MyCrypt>");
		}
		return null;
	}



/**
 * ���C�����\�b�h�F
 * AES�ɂ��Í����E�����̓���m�F���s�����\�b�h
 * �W�����͂�����������A�Í������A���̌�A��������B
 */
	public static void main(String[] args) {

		String strK1 = "0123012301230123"; //���i16bit�j
		String strK2 = "kurume-seigyo-5s";
		String strV1 = "abcdefghijklmnop"; //�������x�N�g���i���Ɠ���bit�j
		String strV2 = "0123012301230123";
		String strV3 = " 1 2 3 4 5 6 7 8";

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String str1 = reader.readLine();

            System.out.println("");
            String str2 = encode(str1,strK1,strV1);
            System.out.println("encode >> "+str2);

            String str3 = decode(str2,strK1,strV1);
            System.out.println("decode >> "+str3);
        } catch (IOException e){
            System.out.println(e.toString()+"<main@MyCrypt>");
        }
	}
}

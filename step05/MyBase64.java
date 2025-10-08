package step05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  �����Base64�̕ϊ����s���N���X
 *<BR>
 *<BR>  �����F
 *<BR>  �E�ϊ��͐ÓI�ȃ��\�b�h���ōs�����߁A�I�u�W�F�N�g�̐����͕s�v�B
 *<BR>
 *<BR>  �Ǘ����Ă����ȃt�B�[���h
 *<BR>  �Estatic String TABLE:  Base64�̕ϊ��e�[�u��
 *<BR>
 *<BR>  �Ǘ����Ă����ȃ��\�b�h
 *<BR>  �Estatic String encode(String str1):  Base64�̃G���R�[�h�ϊ�
 *<BR>  �Estatic String decode(String str1):  Base64�̃f�R�[�h�ϊ�
 */
public class MyBase64 {
    /** Base64�̕ϊ��e�[�u�� */
    public static String TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

/**
  �G���R�[�h���郁�\�b�h
  str1: ���̕�����
  �߂�l: �G���R�[�h��̕�����(str2)

  �A���S���Y��
  �����P�Dstr1��2�i���̕�����istrB�j�ɂ���i1�����萔��8bit��2�i���ɕϊ��j
  �����Q�DstrB��6bit���݂ŕ����萔�ɕϊ����A�V���ȕ�����istr2�j�����B
      �ϊ��ɂ�String�N���X�̃I�u�W�F�N�gTABLE���g���B
      6bit��2�i���𐮐��l(10�i��)�ɂ��āA���̒l�𕶎���TABLE��index�Ɏg���B
      ���̕�����TABLE��index�Ԗڂ̒l���A�ϊ���̕����萔�B
  �����R�Dstr2�̒�����4�̔{���ɂȂ�悤�ɒ�������B�s���́u=�v�Ŗ��߂�B
*/
    public static String encode(String str1){
        String str2 = ""; //Base64�ɃG���R�[�h��̕�����
        String strB = ""; //2�i���̕�����ɕϊ���̕�����

        //�����P�Dstr1��2�i���̕�����istrB�j�ɂ���i1�����萔��8bit��2�i���ɕϊ��j
        //�����Ƃ��ẮA1��������������strB�ɘA�������Ă����B
		for(int i=0; i<str1.length(); i++){
            String tmp = ""; //str1��2�i���̕�����ɕϊ�����tmp�Ɋi�[����B�y�P�D�E�ӕύX�z

            //tmp��8���ɂȂ��Ă��Ȃ��ꍇ�A��ʃr�b�g�̏ꏊ��0�����8���ɂ���B
            //for���Ȃǂ��g���āA������O����0������B�i4�s���炢�j�y�Q�D�쐬�z





		}

		//�o����������strB�̒�����6�̔{���łȂ��ꍇ�A�����Ƀ[���p�f�B���O�i3�s���炢�j�y�R�D�쐬�z�����R���Q�l





        //�����Q�DstrB��6bit���݂ŕ����萔�ɕϊ����A�V���ȕ�����istr2�j�����
		for(int i=0; (i+6)<=strB.length(); i+=6){
            String substr = ""; //6���i6�r�b�g�Ԃ�j�����o���B      �y�S�D�E�ӕύX�z
            int num = 0;  //2�i���̕������10�i���̐����ɕϊ�         �y�T�D�E�ӕύX�z
            System.out.println(substr+">>"+num);

            str2 += ""; //�ϊ����������i������TABLE��num�Ԗڂ̕����j��str2�ɘA��������B�y�U�D�E�ӕύX�z
		}

		//�����R�Dstr2�̒�����4�̔{���ɂȂ�悤�ɒ�������B�s���́u=�v�Ŗ��߂�B�y�m�F�̂݁z
		while(str2.length()%4 != 0){
            str2 +="="; //�u=�v�p�f�B���O
		}

        return str2;
    }


/**
  �f�R�[�h����֐�
  str2: ���̕�����
  �߂�l: �f�R�[�h��̕�����(str3)

  �A���S���Y��
  �����P�Dstr2�ɂ̓p�f�B���O�u=�v������̂ŏ���
  �����Q�Dstr2��2�i���̕�����istrB�j�ɂ���i1�����萔��6bit��2�i���ɕϊ��j
  �����R�DstrB��8bit���݂ŕ����萔�ɕϊ����A�V���ȕ�����istr3�j�����
*/
    public static String decode(String str2){
        String str3 = ""; //Base64�Ƀf�R�[�h��̕�����
        String strB = ""; //2�i���̕�����ɕϊ���̕�����
        int len2; //�p�f�B���O�u=�v���������������̌�

        //�����P�Dstr2�̃p�f�B���O�u=�v���������������̌���c���Blen2�����߂�B
        len2 = str2.indexOf("=");
        if(len2<0){
            len2 = str2.length();
        }

        //�����Q�Dstr2��2�i���̕�����istrB�j�ɂ���i1�����萔��6bit��2�i���ɕϊ��j
		for(int i=0; i<len2; i++){ //�u=�v��������I��
            int n = 0; //�ustr2��i�Ԗڂ̕����v���i�[����Ă��镶����TABLE�̏ꏊ�iindex�j�����߂�B�y�P�D�E�ӕύX�z
            String tmp = ""; //���ln��2�i���̕�����ɕϊ�����tmp�Ɋi�[�B�y�P�D�E�ӕύX�z
            //tmp��6���ɂȂ��Ă��Ȃ��ꍇ�A��ʃr�b�g�̏ꏊ��0�����6���ɂ���B
            //for���Ȃǂ��g���āA������O����0������B�i4�s���炢�j�y�R�D�쐬�z�G���R�[�h�łقړ������������Ă���B





		}

        //�����R�DstrB��8bit���݂ŕ����萔�ɕϊ����A�V���ȕ�����istr3�j�����
		for(int i=0; (i+8)<=strB.length(); i+=8){
            String substr = ""; //8���i8�r�b�g�Ԃ�j�����o���B�y�S�D�E�ӕύX�z
            int num = 0; //2�i���̕������10�i���̐����ɕϊ�    �y�T�D�E�ӕύX�z
            System.out.println(substr+">>"+num);
            str3 += (char)num; //num�̓A�X�L�[�R�[�h�̐����ɂȂ��Ă���̂ŁAchar�^�ɕϊ��i�L���X�g�j���ĕ�����str3�ɘA���B
		}

        return str3;
    }

/**
 * ���C�����\�b�h�F
 * ���삵��Base64�̓���m�F���s�����\�b�h
 * �W�����͂�����������ABase64�ňÍ������A���̌�A��������B
 */
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String str1 = reader.readLine();

            System.out.println("");
            String str2 = encode(str1);
            System.out.println("encode >> "+str2);

//            System.out.println("");
//            String str3 = decode(str2);
//            System.out.println("decode >> "+str3);

        } catch (IOException e){
            System.out.println(e.toString()+"<main@MyBase64>");
        }
	}

}

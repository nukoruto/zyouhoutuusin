#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>

/* Base64�̕ϊ��e�[�u�� */
static char TABLE[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

/* Base64�̏����ߒ���2�i���̕�������쐬����ꍇ */
char* encode(char *str, int len, int *len2); /* �G���R�[�_�[�i�����ߒ���2�i���̕�������쐬����ϊ��j */
char* decode(char *str2, int len2, int *len3); /* �f�R�[�_�[�i�����ߒ���2�i���̕�������쐬����ϊ��j */
char* toBary1(char c, int bit); /* 1�����萔��2�i���̕�����ɕϊ� */
char* toBaryN(char *str, int len, int bit); /* N�����̕������2�i���̕�����ɕϊ� */
int toDec(char *b, int bit); /* 2�i���̕������10�i����int�^�ɕϊ� */

/* �f�o�b�O�p */
void printArray(char *s, int len); /* �k�������̂Ȃ�������̕W���o�� */

/* ��Փx���� */
/* Base64�̏����ߒ���2�i���̕�������쐬���Ȃ��ꍇ�i�V�t�g���Z���g���j */
char* encode2(char *str, int len, int *len2); /* �G���R�[�_�[�i�����ߒ���2�i���̕�������쐬���Ȃ��ϊ��j */
char* decode2(char *str2, int len2, int *len3); /* �f�R�[�_�[�i�����ߒ���2�i���̕�������쐬���Ȃ��ϊ��j */




int main(void){
    char target[303200]; //="kurume kosen";/* ���̕�����i�k����������j */
    char *str1;/* ���̕����z��i�k�������Ȃ��̕����萔�̔z��j */
    char *str2;/* �G���R�[�h */
    char *str3;/* �f�R�[�h */
    int len1,len2, len3; /* �e������̒��� */
    int i;
    clock_t t1, t2;

    fgets(target, sizeof(target)-1, stdin);

    t1 = clock(); /* �v���J�n */
    /* str1�̍쐬�B�k������������char�z��Ƃ��Ċi�[���� */
    str1 = (char*)malloc(strlen(target)*sizeof(char));
    len1 = strlen(target)-1; //fgets���g���ꍇ�͉��s�����̂���-1����Bprintf�����̏ꍇ��-1���Ȃ��B
    printf("len1=%d\n", len1);
    for(i=0; i<len1; i++){
        *(str1+i) = *(target+i);
    }

//    for(i=0; i<100; i++){ //�v���pfor���̊J�n
    /* �G���R�[�h */
    str2 = encode(str1, len1, &len2);
//    str2 = encode2(str1, len1, &len2);
    printArray(str1, len1);
    printf(" => ");
    printArray(str2, len2);
    printf("\n");


    /* �f�R�[�h */
    str3 = decode(str2, len2, &len3);
    str3 = decode2(str2, len2, &len3);
    printArray(str2, len2);
    printf(" => ");
    printArray(str3, len3);
    printf("\n");
//    } //�v���pfor���̏I��
    t2 = clock(); /* �v���I�� */
    printf("run time: %f(sec)\n", (double)(t2-t1)/CLOCKS_PER_SEC);

    //free(target); //���I�Ɋ��蓖�Ă��Ă��Ȃ��̂�free�͕s�v
    free(str1); //�֐����œ��I�Ɋ��蓖�Ă��Ă���̂�free�����s
    free(str2); //�֐����œ��I�Ɋ��蓖�Ă��Ă���̂�free�����s
    free(str3); //�֐����œ��I�Ɋ��蓖�Ă��Ă���̂�free�����s
    return 0;
}



/**
  �G���R�[�h����֐�
  str1: ���̕�����i�k�������Ȃ��j
  len1: ���̕�����̒���
  *len2: �G���R�[�h��̕�����̒���
  �߂�l: �G���R�[�h��̕�����(str2)

  �A���S���Y��
  �����P�Dstr1��2�i���̕�����istrB�j�ɂ���i1�����萔��8bit��2�i���ɕϊ��j
  �����Q�DstrB��6bit���݂ŕ����萔�ɕϊ����A�V���ȕ�����istr2�j�����
      �ϊ��ɂ�char�z���TABLE���g���A6bit��2�i���𐮐��l(10�i��)�ɂ��āA�z��TABLE�̓Y�����Ɏg���B
      ���̔z��TABLE�̒l���A�ϊ���̕����萔�B
  �����R�Dstr2�̒�����4�̔{���ɂȂ�悤�ɒ�������B�s���́u=�v�Ŗ��߂�B
*/
char* encode(char *str1, int len1, int *len2){
    char *strB; /* 2�i���̕����� */
    char *str2; /* �G���R�[�h��̕����� */
    int lenB = len1*8; /* 2�i���̕�����̒����i�k�����������j */
    char tmp[6]; /* 6bit���Ƃɐ؂蕪����ۂɁA���̔z��Ɉꎞ�I�Ɋi�[���� */
    int i, j, k;

    /* �O���� */
    /* �܂��͏����R����Ή� */
    /* �G���R�[�h��̕�����̒������v�Z�A4�̔{���ɂȂ�悤�ɉ��Z�Œ��� */
    *len2 = (lenB/6);
    if(lenB%6 > 0){
        (*len2)++;
    }
    if((*len2)%4 != 0){
        *len2 += 4 - (*len2)%4;
    }

    /* �G���R�[�h��̕�����̗̈�m�� */
    str2 = (char*)malloc((*len2)*sizeof(char));

    /* �G���R�[�h��̕�����Ɋւ��āA��ɖ���4�����Ɂu=�v���p�f�B���O���Ă����i���X�璷�j */
    for(i=0; i<4; i++){
        *(str2+((*len2)-i)) = '=';
    }

    /* �G���R�[�h�̏����J�n */
    /* �����P�Dstr1��2�i���̕�����istrB�j�ɂ��� */
    strB = toBaryN(str1, len1, 8);
    /* �m�F */
//    printf("[�r���o��]");
//    printArray(str1, len1);
//    printf(" => ", str1);
//    printArray(strB, lenB);
//    printf("\n");

    /* �����Q�DstrB��6bit���݂ŕ����萔�ɕϊ����A�V���ȕ�����istr2�j����� */
    /* 6bit���Ƃɐ����l�ɕϊ����A�ϊ��e�[�u���iTABLE�j�̓Y�����Ƃ��Ďg�p���邱�ƂŎ��� */
    for(i=0,k=0; (i+6)<lenB; i+=6,k++){
        strncpy(tmp, strB+i, 6);
        *(str2+k) = TABLE[toDec(tmp,6)];  /* �ϊ��e�[�u�����g�����ϊ� */
        /* �m�F */
//        printArray(tmp, 6);
//        printf("=>%c\n",*(str2+k));
    }
    /* 6bit���݂ŁA�]�肪�o���ꍇ�̏��� */
    for(j=0; (i+j)<lenB; j++){
        tmp[j] = *(strB+(i+j));
    }
    for(i=j; i<6; i++){
        tmp[i]='0'; /* 6bit�ɖ����Ȃ��ꍇ�̓[���p�f�B���O */
    }
    *(str2+k) = TABLE[toDec(tmp,6)];  /* �ϊ��e�[�u�����g�����ϊ� */
    /* �m�F */
//    printArray(tmp, 6);
//    printf("=>%c\n",*(str2+k));

    free(strB); //�֐����œ��I�Ɋ��蓖�Ă��Ă���̂�free�����s

    return str2;
}



/**
  �f�R�[�h����֐�
  str2: ���̕�����i�k�������Ȃ��j
  len2: ���̕�����̒���
  *len3: �f�R�[�h��̕�����̒���
  �߂�l: �f�R�[�h��̕�����(str3)

  �A���S���Y��
  �����P�Dstr2�ɂ̓p�f�B���O�u=�v������̂ŏ���
  �����Q�Dstr2��2�i���̕�����istrB�j�ɂ���i1�����萔��6bit��2�i���ɕϊ��j
  �����R�DstrB��8bit���݂ŕ����萔�ɕϊ����A�V���ȕ�����istr3�j�����
*/
char* decode(char *str2, int len2, int *len3){
    char *strB; /* 2�i���̕����� */
    char *str3; /* �f�R�[�h��̕����� */
    int len2a=0; /* str2�ɂ�����p�f�B���O��������������̒������ϊ��Ώۂ̒��� */
    int i, j, k;
    char *tmp; /* �z��TABLE�̓Y������6bit��2�i���\�L�̕�����ɕϊ�����ۂɎg�p���� */
    char tmp1[8]; /* 8bit���Ƃɐ؂蕪����ۂɁA���̔z��Ɉꎞ�I�Ɋi�[���� */

    /* �O���� */
    /* �����P�Dstr2�ɂ̓p�f�B���O�u=�v������̂ŏ��� */
    /* str2�ɂ�����p�f�B���O��������������̒����ilen2a�j�����߂� */
    for(i=0; i<len2; i++){
        if(*(str2+i) != '='){
            len2a++;
        }
        else{
            break;
        }
    }
    /* �ϊ�����2�i���̕�����(strB)�ƁA�f�R�[�h��̕�����(str3)�̗̈�m�ہB */
    strB = (char*)malloc((len2a*6)*sizeof(char));
    /* strB�̒�������len3�i�f�R�[�h��̕�����̒����j���v�Z�\ */
    *len3 = len2a*6/8; /* 6bit�̃f�[�^��8bit�ɐ؂�Ȃ����ĕ����ɕϊ��A���̎��̒��� */
    str3 = (char*)malloc((*len3)*sizeof(char));
//    printf("len3=%d\n", *len3);

    /* �f�R�[�h�̏����J�n */
    /* �����Q�Dstr2��2�i���̕�����istrB�j�ɂ���i1�����萔��6bit��2�i���ɕϊ��j */
    k=0; /* k��strB�̓Y�����BstrB��6bit���ϊ������i���܂��Ă����j */
    for(i=0; i<len2a; i++){
        /* �ϊ��e�[�u���iTABLE�j�̒l�i�����萔�j�ƈ�v�����ۂ́u�Y�����ij�j�v�����߂� */
        for(j=0; j<sizeof(TABLE); j++){
            if(TABLE[j] == *(str2+i)){
                break;
            }
        }
//        printf("%c => %d\n", *(str2+i), TABLE[j]);

        /* �Y�����ij�j��6bit��2�i���̕�����ɕϊ� */
        tmp = toBary1(j, 6);
//        printf("%c=>", TABLE[j]);
//        printArray(tmp, 6);
//        printf("\n");
        /* �ϊ�����������itmp�j�����Ɍq����strB����� */
        for(j=0; j<6; j++){
            *(strB+k) = *(tmp+j);
            k++;
        }
        free(tmp); /* for�����J��Ԃ��x�ɁA�V����tmp�̗̈�m�ۂ�toBary1�֐��ōs����̂ŁA�����ŉ�� */
    }

    /* �m�F */
//    printf("[�r���o��]");
//    printArray(str2, len2);
//    printf(" => ");
//    printArray(strB, len2a*6);
//    printf("\n");


    /* �����R�DstrB��8bit���݂ŕ����萔�ɕϊ����A�V���ȕ�����istr3�j����� */
    /* 8bit���Ƃɕϊ� */
    for(i=0; i<*len3; i++){
        strncpy(tmp1, strB+i*8, 8); /* ����������tmp1����� */
//        printf("[%d]=",i);
//        printArray(tmp1, 8);
//        printf("\n");
        *(str3+i) = toDec(tmp1,8);  /* 10�i���i�A�X�L�[�R�[�h�j�ɕϊ� */
    }

    free(strB);
    return str3;
}

/**
    1�����萔(c)���w��r�b�g��(bit)��2�i���\�L�̕�����ɕϊ�����֐�
*/
char* toBary1(char c, int bit){
    char *bary;
    char tmp[10];
    int len, i, j;

    /* �߂�l�p�̃|�C���^���������A�E�l�߂Ȃ̂ŁA���Ɂu0�v���p�f�B���O����Ӗ������˂� */
    bary = (char*)malloc(bit*sizeof(char));
    for(i=0; i<bit; i++){
        *(bary+i) = '0';
    }

    /* ������2�i���̕\�L�ɂ���������ɕϊ��A�y���Ӂz�ŏ�ʂ̃r�b�g��0�̂Ƃ��A����0���������y���Ӂz */
    itoa(c,tmp,2);

    /* �w�肵���r�b�g���i����bit�j�ȏ�͖����Ƃ͎v�����O�̂��߃`�F�b�N */
    len = strlen(tmp);
    if(len>bit){
        printf("error at toBary()!\n");
        exit(1);
    }

    /* �w�肵���r�b�g���i����bit�j�����̏ꍇ�ɔ����āA�E�l�߂Ŋi�[ */
    j=0;
    for(i=bit-len; i<bit; i++,j++){
        *(bary+i) = *(tmp+j);
    }

    /* �m�F */
//    printf("%c=>",c); /* %c�ŏo�͂ł��Ȃ��f�[�^������̂Œ��� */
//    printArray(bary, bit);
//    printf("\n");

    return bary;
}

/**
  ����len�̕�����(str)���A2�i���\�L�̕�����ɕϊ�����֐�
  ���̍ہA1�����萔�͎w��r�b�g��(bit)�ɕϊ�����B
*/
char* toBaryN(char *str, int len, int bit){
    char *str2;
    char *tmp;
    int i, j;
    str2 = (char *)malloc(len*bit);

    /* len�̕��������ԂɁAN�r�b�g(bit)��2�i���̕�����ɕϊ����� */
    for(i=0; i<len; i++){
        tmp = toBary1(*(str+i), bit); /* ������2�i���̕�����ɕϊ� */
        for(j=0; j<bit; j++){
            *(str2+i*bit+j) = *(tmp+j);
        }
    }

    return str2;
}

/**
  �w��r�b�g��(bit)��2�i���\�L�̕������10�i����int�^�ɕϊ�����֐�
*/
int toDec(char *b, int bit){
    int i, n=0;

    for(i=0; i<bit; i++){
        if(*(b+i)=='1'){
            n += pow(2,bit-1-i);
        }
    }
    return n;
}

/**
  �I�[�����i�k�������j�̂Ȃ������̔z��̏o��
*/
void printArray(char *s, int len){
    int i;
    for(i=0; i<len; i++){
        printf("%c", *(s+i));
    }
}




/**
  ��Փx����***************************************************************
*/

/**
  �G���R�[�h����֐�
  �V�t�g���Z�𗘗p���āA2�i���\�L�̕���������Ȃ��B

  str1: ���̕�����i�k�������Ȃ��j
  len1: ���̕�����̒���
  *len2: �G���R�[�h��̕�����̒���
  �߂�l: �G���R�[�h��̕�����(str2)

  �A���S���Y��
  �����P�Dstr1��1����(8bit)�����ԂɃV�t�g���Z���g���Ďw�肵�����̒l�𒲂ׂ�B
  �����Q�D6��(6bit)���Ƃɐ����l�ɂ���B
  �����R�D���̒l��z��TABLE�̓Y�����ɂ��ĕ����萔���m��B�����P�ɖ߂�J��Ԃ��B
  �����S�Dstr2�̒�����4�̔{���ɂȂ�悤�ɒ�������B�s���́u=�v�Ŗ��߂�B
*/
char* encode2(char *str1, int len1, int *len2){
    char *str2; /* �G���R�[�h��̕����� */
    char c; /* str1����1���������o�����ۂɎg�p */
    int x; /* �����萔c��i�Ԗڂ̃r�b�g�̒l */
    int num, cnt, k;
    int i,j;

    /* �O���� */
    /* �܂��͏����S����Ή� */
    /* �G���R�[�h��̕�����̒������v�Z�A4�̔{���ɂȂ�悤�ɉ��Z�Œ��� */
    *len2 = (len1*8/6);
    if((len1*8)%6 > 0){
        (*len2)++;
    }
    if((*len2)%4 != 0){
        *len2 += 4 - (*len2)%4;
    }
    /* �G���R�[�h��̕�����̗̈�m�� */
    str2 = (char*)malloc((*len2)*sizeof(char));

    /* �G���R�[�h��̕�����Ɋւ��āA��ɖ���4�����Ɂu=�v���p�f�B���O���Ă����i���X�璷�j */
    for(i=0; i<4; i++){
        *(str2+((*len2)-i)) = '=';
    }


    /* �G���R�[�h�̏����J�n */
    /* �����P�{�����Q�{�����R */
    num=0; /* �u6���̃r�b�g�v���狁�߂�l */
    cnt=5; /* �u6���̃r�b�g�v�̂ǂ̈ʒu���v�Z���Ă��邩�̏�񁁌��̏�� */
    k=0; /* �G���R�[�h��̕�����̓Y���� */
    for(i=0; i<len1; i++){
        c = *(str1+i);
        /* �����P�F1�����萔��8bit�Ƃ��āi7���ڂ���0���ڂ܂ł��A���Ԃɒ��ׂ�j */
        for(j=7; j>=0; j--){
            x = (c>>j)&1; /* j���ڂ̒l��1�Ȃ�x�ɂ�1���i�[����� */
//            printf("%d", x);
            /* �����Q�D6��(6bit)���Ƃɐ����l�ɂ���B6����5���`0���Ȃ̂ɒ��ӁBcnt���g���Ăǂ̌��Ȃ̂��c���B */
            if(x == 1){
                num += pow(2,cnt); //�ϊ��Ώۂ́u6���̃r�b�g�v���Ƃ̐����l���v�Z
            }
            cnt--;
            /* �����R�D���̒l��z��TABLE�̓Y�����ɂ��ĕ����萔���m��B */
            if(cnt<0){
//                printf(" => %d\n", num);
                *(str2+k) = TABLE[num]; //�ϊ�
                num=0;
                cnt=5;
                k++;
            }
        }
//        printf("\n");
    }
    if(num >0){ //n��0���傫����6�Ŋ���؂�Ȃ������ꍇ�A���ʂ̃r�b�g��0�Ƃ݂Ȃ��āi�[���p�f�B���O�j�A���ϊ�
        *(str2+k) = TABLE[num]; //�ϊ�
    }

    return str2;
}

/**
  �G���R�[�h����֐�
  �V�t�g���Z�𗘗p���āA2�i���\�L�̕���������Ȃ��B

  str2: ���̕�����i�k�������Ȃ��j
  len2: ���̕�����̒���
  *len3: �f�R�[�h��̕�����̒���
  �߂�l: �f�R�[�h��̕�����(str3)

  �A���S���Y��
  �����P�Dstr2�ɂ̓p�f�B���O�u=�v������̂ŏ���
  �����Q�D�z��TABLE�̓Y����(6bit�ȓ��ŕ\�L�\�Ȓl)��������B
  �����R�D�Y�������V�t�g���Z���g���Ďw�肵�����̒l�𒲂ׂ�B
  �����S�D8��(8bit)���Ƃɐ����l�ɂ���B���̒l���A�X�L�[�R�[�h�B�����Q�ɖ߂�J��Ԃ��B

*/
char* decode2(char *str2, int len2, int *len3){
    char *str3; /* �f�R�[�h��̕����� */
    int len2a=0; /* str2�ɂ�����p�f�B���O��������������̒������ϊ��Ώۂ̒��� */
    int n; //str2����1���������o���������ƈ�v�����z��TALBE�̓Y�����B���̓Y�����̒l���r�b�g�ɒ����čČv�Z����B
    int x; //���ln��i�Ԗڂ̃r�b�g�̒l
    int num, cnt, k;
    int i, j;

    /* �O���� */
    /* �����P�Dstr2�ɂ̓p�f�B���O�u=�v������̂ŏ��� */
    /* str2�ɂ�����p�f�B���O��������������̒����ilen2a�j�����߂� */
    for(i=0; i<len2; i++){
        if(*(str2+i) != '='){
            len2a++;
        }
        else{
            break;
        }
    }
    /* len3�i�f�R�[�h��̕�����̒����j���v�Z�\ */
    *len3 = len2a*6/8; /* 6bit�̃f�[�^��8bit�ɐ؂�Ȃ����ĕ����ɕϊ��A���̎��̒��� */
    str3 = (char*)malloc((*len3)*sizeof(char));

    /* �f�R�[�h�̏����J�n */
    /* �����Q�{�����R�{�����S */
    num=0; /* �u8���̃r�b�g�v���狁�߂�l */
    cnt=7; /* �u8���̃r�b�g�v�̂ǂ̈ʒu���v�Z���Ă��邩�̏�񁁌��̏�� */
    k=0; //�f�R�[�h��̕�����̓Y����
    for(i=0; i<len2a; i++){
        /* �����Q�D�z��TABLE�̓Y����(6bit�ȓ��ŕ\�L�\�Ȓl)��������B�Y������n�Ɋi�[����B */
        for(n=0; n<strlen(TABLE); n++){
            if(TABLE[n] == *(str2+i)){
                break;
            }
        }

        /* �����R�D�Y������6bit�Ƃ��āi5���ڂ���0���ڂ܂ł��A���Ԃɒ��ׂ�j */
        for(j=5; j>=0; j--){
            x = (n>>j)&1; /* j���ڂ̒l��1�Ȃ�x�ɂ�1���i�[����� */
//            printf("%d", x);
            /* �����S�D8��(8bit)���Ƃɐ����l�ɂ���B8����7���`0���Ȃ̂ɒ��ӁBcnt���g���Ăǂ̌��Ȃ̂��c���B */
            if(x == 1){
                num += pow(2,cnt); //�ϊ��Ώۂ́u8���̃r�b�g�v���Ƃ̐����l���v�Z
            }
            cnt--;
            /* �����S�D�v�Z�����l�́A�A�X�L�[�R�[�h�Ȃ̂ŁA���̂܂�str3�ɑ������΃f�R�[�h�����B */
            if(cnt<0){
//                printf(" => %d\n", num);
                *(str3+k) = num; //�ϊ�
                num=0;
                cnt=7;
                k++;
            }
        }
//        printf("\n");
    }
    if(num >0){ //n��0���傫����8�Ŋ���؂�Ȃ������r�b�g������A�A�A���Ԃ񑶍݂��Ȃ��̂ł́H
        *(str2+k) = num; //�ϊ�
    }

    return str3;
}


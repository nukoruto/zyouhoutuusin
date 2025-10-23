#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>

/* Base64の変換テーブル */
static char TABLE[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

/* Base64の処理過程で2進数の文字列を作成する場合 */
char* encode(char *str, int len, int *len2); /* エンコーダー（処理過程で2進数の文字列を作成する変換） */
char* decode(char *str2, int len2, int *len3); /* デコーダー（処理過程で2進数の文字列を作成する変換） */
char* toBary1(unsigned char c, int bit); /* 1文字定数を2進数の文字列に変換 */
char* toBaryN(char *str, int len, int bit); /* N文字の文字列を2進数の文字列に変換 */
int toDec(char *b, int bit); /* 2進数の文字列を10進数のint型に変換 */

/* デバッグ用 */
void printArray(char *s, int len); /* ヌル文字のない文字列の標準出力 */

/* 難易度高め */
/* Base64の処理過程で2進数の文字列を作成しない場合（シフト演算を使う） */
char* encode2(char *str, int len, int *len2); /* エンコーダー（処理過程で2進数の文字列を作成しない変換） */
char* decode2(char *str2, int len2, int *len3); /* デコーダー（処理過程で2進数の文字列を作成しない変換） */




#ifndef MYBASE64_NO_MAIN
int main(void){
    char target[303200]; //="kurume kosen";/* 元の文字列（ヌル文字あり） */
    char *str1;/* 元の文字配列（ヌル文字なしの文字定数の配列） */
    char *str2;/* エンコード */
    char *str3;/* デコード */
    int len1,len2, len3; /* 各文字列の長さ */
    int i;
    clock_t t1, t2;

    fgets(target, sizeof(target)-1, stdin);

    t1 = clock(); /* 計測開始 */
    /* str1の作成。ヌル文字無しのchar配列として格納する */
    str1 = (char*)malloc(strlen(target)*sizeof(char));
    len1 = strlen(target)-1; //fgetsを使う場合は改行除去のため-1する。printfや代入の場合は-1しない。
    printf("len1=%d¥n", len1);
    for(i=0; i<len1; i++){
        *(str1+i) = *(target+i);
    }

//    for(i=0; i<100; i++){ //計測用for文の開始
    /* エンコード */
    str2 = encode(str1, len1, &len2);
//    str2 = encode2(str1, len1, &len2);
    printArray(str1, len1);
    printf(" => ");
    printArray(str2, len2);
    printf("¥n");


    /* デコード */
    str3 = decode(str2, len2, &len3);
    str3 = decode2(str2, len2, &len3);
    printArray(str2, len2);
    printf(" => ");
    printArray(str3, len3);
    printf("¥n");
//    } //計測用for文の終了
    t2 = clock(); /* 計測終了 */
    printf("run time: %f(sec)¥n", (double)(t2-t1)/CLOCKS_PER_SEC);

    //free(target); //動的に割り当てられていないのでfreeは不要
    free(str1); //関数内で動的に割り当てられているのでfreeを実行
    free(str2); //関数内で動的に割り当てられているのでfreeを実行
    free(str3); //関数内で動的に割り当てられているのでfreeを実行
    return 0;
}
#endif



/**
  エンコードする関数
  str1: 元の文字列（ヌル文字なし）
  len1: 元の文字列の長さ
  *len2: エンコード後の文字列の長さ
  戻り値: エンコード後の文字列(str2)

  アルゴリズム
  処理１．str1を2進数の文字列（strB）にする（1文字定数は8bitの2進数に変換）
  処理２．strBを6bit刻みで文字定数に変換し、新たな文字列（str2）を作る
      変換にはchar配列のTABLEを使い、6bitの2進数を整数値(10進数)にして、配列TABLEの添え字に使う。
      その配列TABLEの値が、変換後の文字定数。
  処理３．str2の長さは4の倍数になるように調整する。不足は「=」で埋める。
*/
char* encode(char *str1, int len1, int *len2){
    char *strB; /* 2進数の文字列 */
    char *str2; /* エンコード後の文字列 */
    int lenB = len1*8; /* 2進数の文字列の長さ（ヌル文字無し） */
    char tmp[6]; /* 6bitごとに切り分ける際に、この配列に一時的に格納する */
    int i, j, k;

    /* 前準備 */
    /* まずは処理３から対応 */
    /* エンコード後の文字列の長さを計算、4の倍数になるように加算で調整 */
    *len2 = (lenB/6);
    if(lenB%6 > 0){
        (*len2)++;
    }
    if((*len2)%4 != 0){
        *len2 += 4 - (*len2)%4;
    }

    /* エンコード後の文字列の領域確保 */
    str2 = (char*)malloc(((*len2)+1)*sizeof(char));

    /* エンコード後の文字列に関して、先に末尾4文字に「=」をパディングしておく（少々冗長） */
    if(*len2 > 0){
        int pad_count = (*len2 >= 4) ? 4 : *len2;
        for(i=0; i<pad_count; i++){
            *(str2 + (*len2 - 1 - i)) = '=';
        }
    }
    str2[*len2] = '\0';

    /* エンコードの処理開始 */
    /* 処理１．str1を2進数の文字列（strB）にする */
    strB = toBaryN(str1, len1, 8);
    /* 確認 */
//    printf("[途中経過]");
//    printArray(str1, len1);
//    printf(" => ", str1);
//    printArray(strB, lenB);
//    printf("¥n");

    /* 処理２．strBを6bit刻みで文字定数に変換し、新たな文字列（str2）を作る */
    /* 6bitごとに整数値に変換し、変換テーブル（TABLE）の添え字として使用することで実現 */
    for(i=0,k=0; (i+6)<lenB; i+=6,k++){
        strncpy(tmp, strB+i, 6);
        *(str2+k) = TABLE[toDec(tmp,6)];  /* 変換テーブルを使った変換 */
        /* 確認 */
//        printArray(tmp, 6);
//        printf("=>%c¥n",*(str2+k));
    }
    /* 6bit刻みで、余りが出た場合の処理 */
    for(j=0; (i+j)<lenB; j++){
        tmp[j] = *(strB+(i+j));
    }
    for(i=j; i<6; i++){
        tmp[i]='0'; /* 6bitに満たない場合はゼロパディング */
    }
    *(str2+k) = TABLE[toDec(tmp,6)];  /* 変換テーブルを使った変換 */
    /* 確認 */
//    printArray(tmp, 6);
//    printf("=>%c¥n",*(str2+k));

    free(strB); //関数内で動的に割り当てられているのでfreeを実行

    return str2;
}



/**
  デコードする関数
  str2: 元の文字列（ヌル文字なし）
  len2: 元の文字列の長さ
  *len3: デコード後の文字列の長さ
  戻り値: デコード後の文字列(str3)

  アルゴリズム
  処理１．str2にはパディング「=」があるので除去
  処理２．str2を2進数の文字列（strB）にする（1文字定数は6bitの2進数に変換）
  処理３．strBを8bit刻みで文字定数に変換し、新たな文字列（str3）を作る
*/
char* decode(char *str2, int len2, int *len3){
    char *strB; /* 2進数の文字列 */
    char *str3; /* デコード後の文字列 */
    int len2a=0; /* str2におけるパディングを除いた文字列の長さ＝変換対象の長さ */
    int i, j, k;
    char *tmp; /* 配列TABLEの添え字を6bitの2進数表記の文字列に変換する際に使用する */
    char tmp1[8]; /* 8bitごとに切り分ける際に、この配列に一時的に格納する */

    /* 前処理 */
    /* 処理１．str2にはパディング「=」があるので除去 */
    /* str2におけるパディングを除いた文字列の長さ（len2a）を求める */
    for(i=0; i<len2; i++){
        if(*(str2+i) != '='){
            len2a++;
        }
        else{
            break;
        }
    }
    /* 変換する2進数の文字列(strB)と、デコード後の文字列(str3)の領域確保。 */
    strB = (char*)malloc((len2a*6)*sizeof(char));
    /* strBの長さからlen3（デコード後の文字列の長さ）が計算可能 */
    *len3 = len2a*6/8; /* 6bitのデータを8bitに切りなおして文字に変換、その時の長さ */
    str3 = (char*)malloc(((*len3)+1)*sizeof(char));
//    printf("len3=%d¥n", *len3);

    /* デコードの処理開始 */
    /* 処理２．str2を2進数の文字列（strB）にする（1文字定数は6bitの2進数に変換） */
    k=0; /* kはstrBの添え字。strBは6bitずつ変換される（埋まっていく） */
    for(i=0; i<len2a; i++){
        /* 変換テーブル（TABLE）の値（文字定数）と一致した際の「添え字（j）」を求める */
        for(j=0; j<sizeof(TABLE); j++){
            if(TABLE[j] == *(str2+i)){
                break;
            }
        }
//        printf("%c => %d¥n", *(str2+i), TABLE[j]);

        /* 添え字（j）を6bitの2進数の文字列に変換 */
        tmp = toBary1(j, 6);
//        printf("%c=>", TABLE[j]);
//        printArray(tmp, 6);
//        printf("¥n");
        /* 変換した文字列（tmp）を順に繋げてstrBを作る */
        for(j=0; j<6; j++){
            *(strB+k) = *(tmp+j);
            k++;
        }
        free(tmp); /* for文を繰り返す度に、新しいtmpの領域確保がtoBary1関数で行われるので、ここで解放 */
    }

    /* 確認 */
//    printf("[途中経過]");
//    printArray(str2, len2);
//    printf(" => ");
//    printArray(strB, len2a*6);
//    printf("¥n");


    /* 処理３．strBを8bit刻みで文字定数に変換し、新たな文字列（str3）を作る */
    /* 8bitごとに変換 */
    for(i=0; i<*len3; i++){
        strncpy(tmp1, strB+i*8, 8); /* 部分文字列tmp1を作る */
//        printf("[%d]=",i);
//        printArray(tmp1, 8);
//        printf("¥n");
        *(str3+i) = toDec(tmp1,8);  /* 10進数（アスキーコード）に変換 */
    }

    str3[*len3] = '\0';

    free(strB);
    return str3;
}

/**
    1文字定数(c)を指定ビット長(bit)の2進数表記の文字列に変換する関数
*/
char* toBary1(unsigned char c, int bit){
    char *bary;
    int i;

    bary = (char*)malloc(bit*sizeof(char));
    for(i=0; i<bit; i++){
        int shift = bit - 1 - i;
        bary[i] = ((c >> shift) & 0x01) ? '1' : '0';
    }

    return bary;
}

/**
  長さlenの文字列(str)を、2進数表記の文字列に変換する関数
  その際、1文字定数は指定ビット長(bit)に変換する。
*/
char* toBaryN(char *str, int len, int bit){
    char *str2;
    char *tmp;
    int i, j;
    str2 = (char *)malloc(len*bit);

    /* len個の文字を順番に、Nビット(bit)の2進数の文字列に変換する */
    for(i=0; i<len; i++){
        tmp = toBary1((unsigned char)*(str+i), bit); /* ここで2進数の文字列に変換 */
        for(j=0; j<bit; j++){
            *(str2+i*bit+j) = *(tmp+j);
        }
    }

    return str2;
}

/**
  指定ビット長(bit)の2進数表記の文字列を10進数のint型に変換する関数
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
  終端文字（ヌル文字）のない文字の配列の出力
*/
void printArray(char *s, int len){
    int i;
    for(i=0; i<len; i++){
        printf("%c", *(s+i));
    }
}




/**
  難易度高め***************************************************************
*/

/**
  エンコードする関数
  シフト演算を利用して、2進数表記の文字列を作らない。

  str1: 元の文字列（ヌル文字なし）
  len1: 元の文字列の長さ
  *len2: エンコード後の文字列の長さ
  戻り値: エンコード後の文字列(str2)

  アルゴリズム
  処理１．str1を1文字(8bit)ずつ順番にシフト演算を使って指定した桁の値を調べる。
  処理２．6桁(6bit)ごとに整数値にする。
  処理３．その値を配列TABLEの添え字にして文字定数を確定。処理１に戻り繰り返す。
  処理４．str2の長さは4の倍数になるように調整する。不足は「=」で埋める。
*/
char* encode2(char *str1, int len1, int *len2){
    char *str2; /* エンコード後の文字列 */
    unsigned char c; /* str1から1文字ずつ取り出した際に使用 */
    int x; /* 文字定数cのi番目のビットの値 */
    int num, cnt, k;
    int i,j;

    /* 前準備 */
    /* まずは処理４から対応 */
    /* エンコード後の文字列の長さを計算、4の倍数になるように加算で調整 */
    *len2 = (len1*8/6);
    if((len1*8)%6 > 0){
        (*len2)++;
    }
    if((*len2)%4 != 0){
        *len2 += 4 - (*len2)%4;
    }
    /* エンコード後の文字列の領域確保 */
    str2 = (char*)malloc(((*len2)+1)*sizeof(char));

    /* エンコード後の文字列に関して、先に末尾4文字に「=」をパディングしておく（少々冗長） */
    if(*len2 > 0){
        int pad_count = (*len2 >= 4) ? 4 : *len2;
        for(i=0; i<pad_count; i++){
            *(str2 + (*len2 - 1 - i)) = '=';
        }
    }
    str2[*len2] = '\0';


    /* エンコードの処理開始 */
    /* 処理１＋処理２＋処理３ */
    num=0; /* 「6桁のビット」から求める値 */
    cnt=5; /* 「6桁のビット」のどの位置を計算しているかの情報＝桁の情報 */
    k=0; /* エンコード後の文字列の添え字 */
    for(i=0; i<len1; i++){
        c = (unsigned char)*(str1+i);
        /* 処理１：1文字手数を8bitとして（7桁目から0桁目までを、順番に調べる） */
        for(j=7; j>=0; j--){
            x = (c>>j)&1; /* j桁目の値が1ならxには1が格納される */
//            printf("%d", x);
            /* 処理２．6桁(6bit)ごとに整数値にする。6桁＝5桁〜0桁なのに注意。cntを使ってどの桁なのか把握。 */
            if(x == 1){
                num += pow(2,cnt); //変換対象の「6桁のビット」ごとの整数値を計算
            }
            cnt--;
            /* 処理３．その値を配列TABLEの添え字にして文字定数を確定。 */
            if(cnt<0){
//                printf(" => %d¥n", num);
                *(str2+k) = TABLE[num]; //変換
                num=0;
                cnt=5;
                k++;
            }
        }
//        printf("¥n");
    }
    if(num >0){ //nが0より大きい＝6で割り切れなかった場合、下位のビットは0とみなして（ゼロパディング）、即変換
        *(str2+k) = TABLE[num]; //変換
    }

    return str2;
}

/**
  エンコードする関数
  シフト演算を利用して、2進数表記の文字列を作らない。

  str2: 元の文字列（ヌル文字なし）
  len2: 元の文字列の長さ
  *len3: デコード後の文字列の長さ
  戻り値: デコード後の文字列(str3)

  アルゴリズム
  処理１．str2にはパディング「=」があるので除去
  処理２．配列TABLEの添え字(6bit以内で表記可能な値)を見つける。
  処理３．添え字をシフト演算を使って指定した桁の値を調べる。
  処理４．8桁(8bit)ごとに整数値にする。この値がアスキーコード。処理２に戻り繰り返す。

*/
char* decode2(char *str2, int len2, int *len3){
    char *str3; /* デコード後の文字列 */
    int len2a=0; /* str2におけるパディングを除いた文字列の長さ＝変換対象の長さ */
    int n; //str2から1文字ずつ取り出した文字と一致した配列TALBEの添え字。この添え字の値をビットに直して再計算する。
    int x; //数値nのi番目のビットの値
    int num, cnt, k;
    int i, j;

    /* 前処理 */
    /* 処理１．str2にはパディング「=」があるので除去 */
    /* str2におけるパディングを除いた文字列の長さ（len2a）を求める */
    for(i=0; i<len2; i++){
        if(*(str2+i) != '='){
            len2a++;
        }
        else{
            break;
        }
    }
    /* len3（デコード後の文字列の長さ）が計算可能 */
    *len3 = len2a*6/8; /* 6bitのデータを8bitに切りなおして文字に変換、その時の長さ */
    str3 = (char*)malloc(((*len3)+1)*sizeof(char));

    /* デコードの処理開始 */
    /* 処理２＋処理３＋処理４ */
    num=0; /* 「8桁のビット」から求める値 */
    cnt=7; /* 「8桁のビット」のどの位置を計算しているかの情報＝桁の情報 */
    k=0; //デコード後の文字列の添え字
    for(i=0; i<len2a; i++){
        /* 処理２．配列TABLEの添え字(6bit以内で表記可能な値)を見つける。添え字はnに格納する。 */
        for(n=0; n<strlen(TABLE); n++){
            if(TABLE[n] == *(str2+i)){
                break;
            }
        }

        /* 処理３．添え字を6bitとして（5桁目から0桁目までを、順番に調べる） */
        for(j=5; j>=0; j--){
            x = (n>>j)&1; /* j桁目の値が1ならxには1が格納される */
//            printf("%d", x);
            /* 処理４．8桁(8bit)ごとに整数値にする。8桁＝7桁〜0桁なのに注意。cntを使ってどの桁なのか把握。 */
            if(x == 1){
                num += pow(2,cnt); //変換対象の「8桁のビット」ごとの整数値を計算
            }
            cnt--;
            /* 処理４．計算した値は、アスキーコードなので、そのままstr3に代入すればデコード完了。 */
            if(cnt<0){
//                printf(" => %d¥n", num);
                *(str3+k) = (char)num; //変換
                num=0;
                cnt=7;
                k++;
            }
        }
//        printf("¥n");
    }
    if(num >0 && k < *len3){ //nが0より大きい＝8で割り切れなかったビットがある、、、たぶん存在しないのでは？
        *(str3+k) = (char)num; //変換
    }

    str3[*len3] = '\0';

    return str3;
}


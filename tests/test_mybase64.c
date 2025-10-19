#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MYBASE64_NO_MAIN
#include "../src/step05/MyBase64.c"

static void assert_roundtrip(const char *label, const char *input) {
    int len1 = (int)strlen(input);
    int len2 = 0;
    int len3 = 0;
    char *encoded = encode((char*)input, len1, &len2);
    char *decoded = decode(encoded, len2, &len3);

    assert(len3 == len1);
    assert(memcmp(decoded, input, len1) == 0);

    free(encoded);
    free(decoded);

    len2 = 0;
    len3 = 0;
    char *encoded2 = encode2((char*)input, len1, &len2);
    char *decoded2 = decode2(encoded2, len2, &len3);

    assert(len3 == len1);
    assert(memcmp(decoded2, input, len1) == 0);

    free(encoded2);
    free(decoded2);

    printf("%s: OK\n", label);
}

int main(void) {
    assert_roundtrip("ASCII", "hello");
    assert_roundtrip("UTF8", "日本語Σ");
    return 0;
}

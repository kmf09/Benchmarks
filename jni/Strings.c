/*
 * Strings.c
 *
 * Author: Ira Ray Jenkins
 */
#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "headers/Utils.h"
#include "headers/Strings.h"

/*static void backtrack(int **c, char *a, char *b, int i, int j) {
 if (i == 0 || j == 0)
 return;

 if (a[i - 1] == b[j - 1]) {
 backtrack(c, a, b, i - 1, j - 1);
 longestString[z++] = a[i - 1];
 } else if (c[i][j - 1] > c[i - 1][j])
 backtrack(c, a, b, i, j - 1);
 else
 backtrack(c, a, b, i - 1, j);
 }*/

static char* backtrack(int **c, char *a, char* b, int i, int j) {
	char* longest = (char*) calloc(i > j ? i : j, sizeof(char));
	int z = 0;

	while (i > 0 && j > 0) {
		if (a[i - 1] == b[j - 1]) {
			j--;
			longest[z++] = a[--i];
		} else if (c[i][j - 1] > c[i - 1][j])
			j--;
		else
			i--;
	}

	char *end = &longest[strlen(longest)];
	char *beg = &longest[0];
	for (--end; beg < end; ++beg, --end) {
		*beg ^= *end;
		*end ^= *beg;
		*beg ^= *end;
	}

	return longest;
}

static void longestSubsequence(char *a, int aLength, char *b, int bLength) {
	if (a == NULL || b == NULL || !(*a) || !(*b)) {
		LOGE("Empty input.");
		return;
	}

	int i, j;
	int **c = (int**) calloc(aLength + 1, sizeof(int*));

	if (c == NULL) {
		LOGE("Out of memory.");
		exit(1);
	}

	for (i = 0; i <= aLength; i++) {
		c[i] = (int*) calloc(bLength + 1, sizeof(int));
		if (c[i] == NULL) {
			LOGE("Out of memory.");
			exit(1);
		}
	}

	// find lcs, store length in c[aLength][bLength]
	for (i = 1; i <= aLength; i++)
		for (j = 1; j <= bLength; j++)
			if (a[i - 1] == b[j - 1])
				c[i][j] = c[i - 1][j - 1] + 1;
			else
				c[i][j] = c[i][j - 1] > c[i - 1][j] ? c[i][j - 1] : c[i - 1][j];

	char *longest = backtrack(c, a, b, aLength, bLength);
	PRINTI("%s", longest);

	for (i = 0; i <= aLength; i++) {
		free(c[i]);
	}

	free(c);
	free(longest);
}

static void longestSubstring(char *a, int aLength, char *b, int bLength) {
	if (a == NULL || b == NULL || !(*a) || !(*b)) {
		LOGE("Empty input.");
		return;
	}

	int i, j, z;
	char* lcs = (char*) calloc(aLength > bLength ? aLength : bLength,
			sizeof(char));
	int **c = (int**) calloc(aLength + 1, sizeof(int*));

	if (c == NULL || lcs == NULL) {
		LOGE("Out of memory.");
		exit(1);
	}

	for (i = 0; i <= aLength; i++) {
		c[i] = (int*) calloc(bLength + 1, sizeof(int));
		if (c[i] == NULL) {
			LOGE("Out of memory.");
			exit(1);
		}
	}

	for (i = 1; i <= aLength; i++)
		for (j = 1; j <= bLength; j++)
			if (a[i - 1] == b[j - 1]) {
				if (i == 1 || j == 1)
					c[i][j] = 1;
				else
					c[i][j] = c[i - 1][j - 1] + 1;

				if (c[i][j] > z) {
					z = c[i][j];
					strcpy(lcs, "");
				}

				if (c[i][j] == z) {
					strncpy(lcs, &a[i - z + 1], z - 1);
					lcs[z] = '\0';
				}
			} else
				c[i][j] = 0;

	PRINTI("Longest common substring: %s", lcs);

	for (i = 0; i <= aLength; i++) {
		free(c[i]);
	}

	free(lcs);
	free(c);
}

JNIEXPORT void JNICALL Java_edu_fsu_cs_mobile_benchmarks_search_Strings_longestSubsequenceNative(
		JNIEnv *env, jclass thiz, jstring fileName, jint size) {
	const char *cFileName = (*env)->GetStringUTFChars(env, fileName, NULL);

	const int MAX =
			size == 0 ?
					edu_fsu_cs_mobile_benchmarks_search_Strings_SMALL_SIZE :
					edu_fsu_cs_mobile_benchmarks_search_Strings_LARGE_SIZE;

	char *a = (char*) calloc(MAX, sizeof(char));
	char *b = (char*) calloc(MAX, sizeof(char));

	FILE* file = fopen(cFileName, "r");

	if (a == NULL || b == NULL) {
		LOGE("Not enough memory.");
		exit(1);
	}

	if (file == NULL) {
		LOGE("Cannot open input file.");
		exit(1);
	}

	(*env)->ReleaseStringUTFChars(env, fileName, cFileName);

	if ((fgets(a, MAX, file) == NULL) || (fgets(b, MAX, file) == NULL)) {
		LOGE("Error reading input.");
		free(a);
		free(b);
		fclose(file);
		exit(1);
	}

	longestSubsequence(a, strnlen(a, MAX), b, strnlen(b, MAX));

	free(a);
	free(b);
	fclose(file);
}

JNIEXPORT void JNICALL Java_edu_fsu_cs_mobile_benchmarks_search_Strings_longestSubstringNative(
		JNIEnv *env, jclass thiz, jstring fileName, jint size) {
	const char *cFileName = (*env)->GetStringUTFChars(env, fileName, NULL);

	const int MAX =
			size == 0 ?
					edu_fsu_cs_mobile_benchmarks_search_Strings_SMALL_SIZE :
					edu_fsu_cs_mobile_benchmarks_search_Strings_LARGE_SIZE;

	char *a = (char*) calloc(MAX, sizeof(char));
	char *b = (char*) calloc(MAX, sizeof(char));

	FILE* file = fopen(cFileName, "r");

	if (a == NULL || b == NULL) {
		LOGE("Not enough memory.");
		exit(1);
	}

	if (file == NULL) {
		LOGE("Cannot open input file.");
		exit(1);
	}

	(*env)->ReleaseStringUTFChars(env, fileName, cFileName);

	if ((fgets(a, MAX, file) == NULL) || (fgets(b, MAX, file) == NULL)) {
		LOGE("Error reading input.");
		free(a);
		free(b);
		fclose(file);
		exit(1);
	}

	longestSubstring(a, strnlen(a, MAX), b, strnlen(b, MAX));

	free(a);
	free(b);
	fclose(file);
}

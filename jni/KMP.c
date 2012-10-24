/*
 * KMP.c
 *
 * Author: Ira Ray Jenkins
 */

#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "headers/Utils.h"
#include "headers/KMP.h"

static int* failureFunction(char* pattern, int length) {
	int i = 2;
	int j = 0;

	int *f = (int*) calloc(length, sizeof(int));

	if (f == NULL) {
		LOGE("Not enough memory.");
		exit(1);
	}

	f[0] = -1;
	f[1] = 0;

	while (i < length) {
		if (pattern[i - 1] == pattern[j])
			f[i++] = ++j;
		else if (j > 0)
			j = f[j];
		else
			f[i++] = 0;
	}

	return f;
}

static void search(char* text, int textLength, char* pattern, int patternLength) {
	int i, j;
	int *f = failureFunction(pattern, patternLength);

	i = j = 0;

	while (i + j < textLength) {
		if (text[i + j] == pattern[i]) {
			if (i == patternLength - 1) {
				free(f);
				PRINTI("Match found at %d",j);
				return;
			}
			i++;
		} else {
			j = j + i - f[i];
			if (f[i] > -1)
				i = f[i];
			else
				i = 0;
		}
	}
	free(f);
	PRINTI("No match found.");
}

JNIEXPORT void JNICALL Java_edu_fsu_cs_mobile_benchmarks_search_KMP_searchNative(
		JNIEnv *env, jclass thiz, jstring fileName, jint size) {
	const char *cFileName = (*env)->GetStringUTFChars(env, fileName, NULL);

	const int MAX =
			size == 0 ?
					edu_fsu_cs_mobile_benchmarks_search_KMP_SMALL_SIZE :
					edu_fsu_cs_mobile_benchmarks_search_KMP_LARGE_SIZE;

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

	if (size == 0)
		search(a, strnlen(a,MAX), "GTAGC", strnlen("GTAGC", MAX));
	else
		search(a, strnlen(a, MAX), b, strnlen(b, MAX));

	free(a);
	free(b);
	fclose(file);
}

/*
 * Author: Ira Ray Jenkins
 */

#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>

#include "headers/Utils.h"
#include "headers/Matrix.h"

JNIEXPORT void JNICALL Java_edu_fsu_cs_mobile_benchmarks_math_Matrix_multiplyNative
  (JNIEnv *env, jclass thiz, jstring fileName, jint size) {
	int i, j, k;
	const char *cFileName = (*env)->GetStringUTFChars(env, fileName, NULL);
	double **a = (double**) calloc(size, sizeof(double*));
	double **b = (double**) calloc(size, sizeof(double*));
	double **c = (double**) calloc(size, sizeof(double*));

	if (a == NULL || b == NULL || c == NULL) {
		LOGE("Not enough memory.");
		exit(1);
	}

	for (i = 0; i < size; i++) {
		a[i] = (double*) calloc(size, sizeof(double));
		b[i] = (double*) calloc(size, sizeof(double));
		c[i] = (double*) calloc(size, sizeof(double));

		if (a[i] == NULL || b[i] == NULL || c[i] == NULL) {
			LOGE("Not enough memory.");
			exit(1);
		}
	}

	FILE* file = fopen(cFileName, "r");

	if (file == NULL) {
		LOGE("Cannot open input file.");
		exit(1);
	}

	(*env)->ReleaseStringUTFChars(env, fileName, cFileName);


	for (i = 0; i < size; i++) {
		for (j = 0; j < size; j++) {
			if (fscanf(file, "%Lf", &a[i][j]) != 1) {
				LOGE("Error reading input file.");
				exit(1);
			}
		}
	}

	for (i = 0; i < size; i++) {
		for (j = 0; j < size; j++) {
			if (fscanf(file, "%Lf", &b[i][j]) != 1) {
				LOGE("Error reading input file.");
				exit(1);
			}
		}
	}

	fclose(file);

	for (i = 0; i < size; i++)
		for (j = 0; j < size; j++)
			for (k = 0; k < size; k++)
				c[i][j] += a[i][k] * b[k][j];

	for(i = 0; i < size; i++)
		PRINTI("%.13Lf", c[i][i]);


	for(i = 0; i < size; i++) {
			free(a[i]);
			free(b[i]);
			free(c[i]);
	}

	free(a);
	free(b);
	free(c);
}


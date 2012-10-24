/*
 * Author: Ira Ray Jenkins
 * Original implementation: mibench - qsort_small.c
 */
#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <string.h>

#include "headers/Utils.h"
#include "headers/QuickSort.h"

#define UNLIMIT

struct stringStruct {
	char string[60];
};

struct my3DVertexStruct {
	int x, y, z;
	double distance;
};

static int strCompare(const void *elem1, const void *elem2) {
	return strcmp((*((struct stringStruct *) elem1)).string,
			(*((struct stringStruct *) elem2)).string);
}

static int vertexCompare(const void *elem1, const void *elem2) {
	double distance1, distance2;

	distance1 = (*((struct my3DVertexStruct *) elem1)).distance;
	distance2 = (*((struct my3DVertexStruct *) elem2)).distance;

	return (distance1 > distance2) ? 1 : ((distance1 == distance2) ? 0 : -1);
}

JNIEXPORT void JNICALL Java_edu_fsu_cs_mobile_benchmarks_sort_QuickSort_sortSmallNative(
		JNIEnv *env, jclass thiz, jstring fileName) {
	const jint MAX = edu_fsu_cs_mobile_benchmarks_sort_QuickSort_SMALL_SIZE;
	jint i, count = 0;
	const char *cFileName = (*env)->GetStringUTFChars(env, fileName, NULL);
	struct stringStruct *array = (struct stringStruct*) calloc(MAX,
			sizeof(struct stringStruct));

	FILE* file = fopen(cFileName, "r");

	if (array == NULL) {
		LOGE("Not enough memory.");
		exit(1);
	}

	if (file == NULL) {
		LOGE("Cannot open input file.");
		exit(1);
	}

	(*env)->ReleaseStringUTFChars(env, fileName, cFileName);

	while ((fscanf(file, "%s", &array[count].string) == 1) && (count < MAX)) {
		count++;
	}

	fclose(file);

	qsort(array, count, sizeof(struct stringStruct), strCompare);

	for (i = 0; i < count; i++)
		PRINTI("%s", array[i].string);

	free(array);
}

JNIEXPORT void JNICALL Java_edu_fsu_cs_mobile_benchmarks_sort_QuickSort_sortLargeNative(
		JNIEnv *env, jclass thiz, jstring fileName) {
	const jint MAX = edu_fsu_cs_mobile_benchmarks_sort_QuickSort_LARGE_SIZE;
	const char *cFileName = (*env)->GetStringUTFChars(env, fileName, NULL);
	jint i, x, y, z, count = 0;
	struct my3DVertexStruct *array = (struct my3DVertexStruct*) calloc(MAX,
			sizeof(struct my3DVertexStruct));

	FILE* file = fopen(cFileName, "r");

	if (array == NULL) {
		LOGE("Not enough memory.");
		exit(1);
	}

	if (file == NULL) {
		LOGE("Cannot open input file.");
		exit(1);
	}

	(*env)->ReleaseStringUTFChars(env, fileName, cFileName);

	while ((fscanf(file, "%d", &x) == 1) && (fscanf(file, "%d", &y) == 1)
			&& (fscanf(file, "%d", &z) == 1) && (count < MAX)) {
		array[count].x = x;
		array[count].y = y;
		array[count].z = z;
		array[count].distance = sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
		count++;
	}

	fclose(file);

	qsort(array, count, sizeof(struct my3DVertexStruct), vertexCompare);

	for (i = 0; i < count; i++)
		PRINTI("%d %d %d", array[i].x, array[i].y, array[i].z);

	free(array);
}

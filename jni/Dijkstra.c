/*
 * Dijkstra.c
 *
 * Author: Ira Ray Jenkins
 */

#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include <limits.h>
#include <math.h>
#include <assert.h>

#include "headers/Utils.h"
#include "headers/Dijkstra.h"

static int *distances;

static int distCompare(const void *elem1, const void *elem2) {
	assert(distances);
	return distances[*(int*)elem1] - distances[*(int*)elem2];
}

static int poll(int *Q, int size) {
	int result = Q[0];

	Q[0] = Q[size - 1];
	Q[size - 1] = -1;

	qsort(Q, size - 1, sizeof(int), distCompare);

	return result;
}

static int contains(int* S, int size, int item) {
	int i;
	for(i = 0; i < size; i++) {
		if(S[i] == item)
			return 1;
	}
	return 0;
}

static int* search(int **G, int size, int source) {
	int i, u, alt, scount = 0, qcount = size;
	int *predecessors = (int*) calloc(size, sizeof(int));
	int *Q = (int*) calloc(size, sizeof(int));
	int *S = (int*) calloc(size, sizeof(int));

	distances = (int*) calloc(size, sizeof(int));

	if(!distances || !predecessors || !Q || !S) {
		LOGE("Not enough memory.");
		exit(1);
	}

	for (i = 0; i < size; i++) {
		distances[i] = 10;
		Q[i] = i;
		predecessors[i] = S[i] = -1;
	}

	distances[source] = 0;

	qsort(Q, qcount, sizeof(int), distCompare);

	while (qcount > 0) {

		u = poll(Q, qcount--);

		if (distances[u] == INT_MAX)
			break;

		S[scount++] = u;

		for (i = 0; i < size; i++) {
			if (contains(S, scount, i) || G[u][i] < 1)
				continue;

			alt = distances[u] + G[u][i];

			if (alt < abs(distances[i])) {
				distances[i] = alt;
				predecessors[i] = u;

				qsort(Q, qcount, sizeof(int), distCompare);
			}
		}
	}

	free(Q);
	free(S);
	free(distances);

	return predecessors;
}

JNIEXPORT void JNICALL Java_edu_fsu_cs_mobile_benchmarks_graph_Dijkstra_searchNative(
		JNIEnv *env, jclass thiz, jstring fileName) {
	const char *cFileName = (*env)->GetStringUTFChars(env, fileName, NULL);
	int **G = NULL;
	int *result = NULL;
	int i, x, y, size;
	FILE* file = fopen(cFileName, "r");

	if (file == NULL) {
		LOGE("Cannot open input file.");
		exit(1);
	}

	(*env)->ReleaseStringUTFChars(env, fileName, cFileName);

	if(fscanf(file, "%d", &size) != 1) {
		LOGE("Error reading file.");
		exit(1);
	}

	G = (int**) calloc(size, sizeof(int*));

	if(G == NULL) {
		LOGE("Not enough memory.");
		exit(1);
	}

	for(i = 0; i < size; i++) {
		G[i] = (int*) calloc(size, sizeof(int));

		if(G == NULL) {
			LOGE("Not enough memory.");
			exit(1);
		}
	}

	while (fscanf(file, "%d %d %d", &x, &y, &i) == 3)
		G[x][y] = i;

	fclose(file);

	result = search(G, size, 0);

	for (i = 0; i < size; i++)
				PRINTI("Predecessor: [%d]: %d", i, result[i]);

	for(i = 0; i < size; i++)
		free(G[i]);

	free(G);
	free(result);
}


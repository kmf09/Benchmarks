/*
 * HuffmanCode.c
 *
 * Author: Ira Ray Jenkins
 */

#include <stddef.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include <headers/Utils.h>
#include <headers/HuffmanCode.h>

#define R edu_fsu_cs_mobile_benchmarks_crypto_HuffmanCode_R

struct Node {
	char ch;
	int freq;
	struct Node *left, *right;
};

static struct Node root;

static int nodeCompare(const void *elem1, const void *elem2) {
	return ((*((struct Node*) elem1)).freq - (*((struct Node*) elem2)).freq);
}

static int isLeaf(struct Node *node) {
	return (node->left == NULL && node->right == NULL);
}

static struct Node poll(struct Node *Q, int size) {
	struct Node temp = Q[0];

	Q[0] = Q[size - 1];
	Q[size - 1].ch = '\0';
	Q[size - 1].freq = -1;

	qsort(Q, size - 1, sizeof(struct Node), nodeCompare);

	return temp;
}

static char* out;
static int outSize = 0;
static void buildCode(char **st, struct Node *node) {
	if (!node)
		return;

	if(!isLeaf(node)) {
		out[outSize++] = '0';
		buildCode(st, node->left);
		out[outSize++] = '1';
		buildCode(st, node->right);
	} else {
		strncpy(st[node->ch], out, outSize--);
	}
}

static struct Node buildTree(int *frequency) {
	struct Node left, right;
	struct Node *pQueue = (struct Node*) calloc(R, sizeof(struct Node));
	struct Node temp;
	int qSize = 0;
	int i;

	if (!pQueue) {
		LOGE("Not enough memory.");
		exit(1);
	}

	// populate queue
	for (i = 0; i < R; i++)
		if (frequency[i] > 0) {
			pQueue[qSize].ch = i;
			pQueue[qSize].freq = frequency[i];
			pQueue[qSize].left = pQueue[qSize].right = NULL;
			qSize++;
		}

	qsort(pQueue, qSize, sizeof(struct Node), nodeCompare);

	// merge two smallest trees
	while (qSize > 1) {
		left = poll(pQueue, qSize--);
		right = poll(pQueue, qSize--);
		temp.ch = '\0';
		temp.freq = left.freq + right.freq;
		temp.left = &left;
		temp.right = &right;
		pQueue[qSize++] = temp;
	}

	temp = poll(pQueue, qSize);

	free(pQueue);

	return temp;
}

static char* compress(char *input) {
	int *frequency = (int*) calloc(R, sizeof(int));
	char *output = NULL;
	int i;

	out = (char*) calloc(edu_fsu_cs_mobile_benchmarks_crypto_HuffmanCode_LARGE_SIZE, sizeof(char));

	for (i = 0; i < strlen(input); i++)
		frequency[input[i]]++;

	root = buildTree(frequency);

	char **st = (char**) calloc(R, sizeof(char*));

	buildCode(st, &root);
//
//	for (i = 0; i < strlen(input); i++) {
//		if (asprintf(&output, "%s%c", output, st[input[i]]) == -1) {
//			LOGE("Error cat'ing output.");
//			exit(1);
//		}
//	}
//
//	for (i = 0; i < R; i++)
//		free(st[i]);
//	free(st);
	free(frequency);

	return output;
}

JNIEXPORT void JNICALL Java_edu_fsu_cs_mobile_benchmarks_crypto_HuffmanCode_runNative(
		JNIEnv *env, jclass thiz, jstring fileName, jint size) {
	const char *cFileName = (*env)->GetStringUTFChars(env, fileName, NULL);

	const int MAX =
			size == 0 ?
					edu_fsu_cs_mobile_benchmarks_crypto_HuffmanCode_SMALL_SIZE :
					edu_fsu_cs_mobile_benchmarks_crypto_HuffmanCode_LARGE_SIZE;

	char *line = (char*) calloc(MAX, sizeof(char));
	char *str = "";

	FILE* file = fopen(cFileName, "r");

	if (line == NULL) {
		LOGE("Not enough memory.");
		exit(1);
	}

	if (file == NULL) {
		LOGE("Cannot open input file.");
		exit(1);
	}

	(*env)->ReleaseStringUTFChars(env, fileName, cFileName);

	while ((fscanf(file, "%s", line)) == 1) {
		if (asprintf(&str, "%s%s", str, line) == -1) {
			LOGE("Error reading input.");
			free(str);
			free(line);
			fclose(file);
			exit(1);
		}
	}

	fclose(file);

	char *output = compress(str);
	//char *result = decompress(output);
	PRINTI("Compress: %s", output);
	//PRINTI("Decompress: %s" + result);

	free(output);
	//free(result);
	free(str);
	free(line);
}

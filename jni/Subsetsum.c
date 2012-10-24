/*
 * Author: Ira Ray Jenkins
 */
#include <stddef.h>
#include <stdlib.h>

#include "headers/Utils.h"
#include "headers/SubsetSum.h"

static int subsetSum(int* set, int size, int sum) {
	if (sum == 0)
		return 1;

	if (size == 0)
		return 0;

	return subsetSum(set, size - 1, sum)
			|| subsetSum(set, size - 1, sum - set[size - 1]);
}

JNIEXPORT jboolean JNICALL Java_edu_fsu_cs_mobile_benchmarks_math_SubsetSum_sumNative(
		JNIEnv *env, jclass thiz, jintArray set, jint size, jint sum) {
	jint *cset = (*env)->GetIntArrayElements(env, set, NULL);
	int result = subsetSum(set, size, sum);

	(*env)->ReleaseIntArrayElements(env, set, cset, 0);

	return result ? JNI_TRUE : JNI_FALSE;
}

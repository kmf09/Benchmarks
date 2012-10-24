/*
 * Author: Ira Ray Jenkins
 */
#include <stddef.h>
#include <stdlib.h>

#include "headers/Utils.h"
#include "headers/Functions.h"

static long* euclid(long p, long q) {
	long *result = (long*) calloc(3, sizeof(long));

	if (result == NULL) {
		LOGE("Not enough memory.");
		exit(1);
	}

	result[0] = p;
	result[1] = 1;
	result[2] = 0;

	if (q == 0) {
		return result;
	}

	long *vals = euclid(q, p % q);
	result[0] = vals[0];
	result[1] = vals[2];
	result[2] = vals[1] - (p / q) * vals[2];

	free(vals);

	return result;
}

static long fibonacci(long n) {
	return n < 1 ? 0 : n == 1 ? n : fibonacci(n - 1) + fibonacci(n - 2);
}


JNIEXPORT void JNICALL Java_edu_fsu_cs_mobile_benchmarks_math_Functions_extendedEuclidNative
  (JNIEnv *env, jclass thiz, jlong p, jlong q) {
	long* result = euclid(p, q);

	PRINTI("%ld %ld %ld", result[0], result[1], result[2]);

	free(result);
}

JNIEXPORT void JNICALL Java_edu_fsu_cs_mobile_benchmarks_math_Functions_nthFibonacciNative
  (JNIEnv *env, jclass thiz, jlong n) {
	PRINTI("%ld", fibonacci(n));
}

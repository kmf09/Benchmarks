#ifndef _Included_edu_fsu_cs_mobile_benchmarks_Utils
#define _Included_edu_fsu_cs_mobile_benchmarks_Utils
#ifdef __cplusplus
extern "C" {
#endif

#include <android/log.h>

#define LOGI(args...) __android_log_write(ANDROID_LOG_INFO, "edu.fsu.cs.mobile.benchmarks.native", args)
#define LOGE(args...) __android_log_write(ANDROID_LOG_ERROR, "edu.fsu.cs.mobile.benchmarks.native", args)
#define PRINTI(args...) __android_log_print(ANDROID_LOG_INFO, "edu.fsu.cs.mobile.benchmarks.native", args)
#define PRINTE(args...) __android_log_print(ANDROID_LOG_ERROR, "edu.fsu.cs.mobile.benchmarks.native", args)

#ifdef __cplusplus
}
#endif
#endif

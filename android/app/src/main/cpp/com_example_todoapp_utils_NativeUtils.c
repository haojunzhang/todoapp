#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_todoapp_utils_NativeUtils_getString(JNIEnv *env, jclass type,
                                                     jint i) {
    if (i == 0) {
        return (*env)->NewStringUTF(env, "http://192.168.0.123:8000/");
    } else if (i == 1001) { // app user token
        return (*env)->NewStringUTF(env, "xxx");
    } else if (i == 1002) { // app user encrypt public key
        return (*env)->NewStringUTF(env, "xxx");
    } else if (i == 1003) { // app user sign private key
        return (*env)->NewStringUTF(env, "xxx");
    } else {
        return (*env)->NewStringUTF(env, "hello");
    }
}
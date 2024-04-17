load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "kotlin-sonnet",
    srcs = glob(["*.java", "*.kt"]),
    visibility = [
        ":__subpackages__",
        "//java-tests/com/hounshell/kotlin-sonnet:__subpackages__",
    ],
)


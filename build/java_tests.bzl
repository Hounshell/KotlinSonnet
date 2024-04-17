load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library", "kt_jvm_test")

def all_java_tests(name, package=None, srcs=[], deps=[], **kwargs):
    support_files = [f for f in srcs if not _is_test_file(f)]
    test_files = [f for f in srcs if _is_test_file(f)]

    test_deps = [
            "@maven//:com_google_truth_truth",
            "@maven//:junit_junit",
    ]

    package = package or '.'.join(native.package_name().split("/")[1:]).replace("-", "_")

    if support_files:
        native.kt_jvm_library(
            name = "_support_",
            srcs = support_files,
            deps = deps,
            **kwargs,
        )

        deps = deps + [":_support_"]

    all_tests = []
    for test_src in test_files:
        test_filename = _filename(test_src)
        test_name = name + "_" + test_filename
        all_tests.append(test_name)

        kt_jvm_test(
            name=test_name,
            test_class=package + "." + test_filename,
            srcs=[test_src],
            deps=deps + test_deps)


    native.test_suite(
        name = name,
        tests = all_tests,
    )

def _is_test_file(path):
  return (_filename(path)[-4:] == "Test" or _filename(path)[-5:] == "Tests") and _extension(path) in ["kt", "java"]

def _filename(path):
  return ".".join(path.split("/")[-1].split(".")[:-1])

def _extension(path):
  return path.split("/")[-1].split(".")[-1]

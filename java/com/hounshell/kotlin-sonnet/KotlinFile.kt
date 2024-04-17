package com.hounshell.kotlin_sonnet

import java.io.Writer

interface KotlinFile : CodeWriter {
    interface Members {}
    interface Parent {}

    interface Builder<P>
    {
        var packageName: String?

        fun packageName(packageName: String?): Builder<P>

        fun addImport(typeReference: TypeReference): Builder<P>

        // TODO: Support more contents of a file.

        fun endFile(): P
    }

    class Impl<P>(parent: P, callback: (KotlinFile) -> Unit):
        BaseKotlinBlock<KotlinFile, P, Members>(parent, callback),
        Builder<P>,
        KotlinFile
    {
        override var packageName: String? = null
            get() = field
            set(value) {
                assertNotClosed()
                field = value
            }

        override fun packageName(packageName: String?): Builder<P> {
            this.packageName = packageName
            return this
        }

        override fun addImport(typeReference: TypeReference): Builder<P> {
            addMember(Import(typeReference))
            return this
        }

        // TODO: Support more contents of a file.

        override fun endFile(): P = close()

        override fun writeTo(writer: Writer) {
            assertClosed()
            if (packageName != null) {
                writer.write("package $packageName\n\n")
            }
            // TODO: Support more contents of a file.
        }

        private class Import(val typeReference: TypeReference): Members {}
    }
}

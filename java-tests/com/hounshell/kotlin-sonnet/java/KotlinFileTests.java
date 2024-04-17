package com.hounshell.kotlin_sonnet.java;

import static com.google.common.truth.Truth.assertThat;

import com.hounshell.kotlin_sonnet.KotlinFile;
import com.hounshell.kotlin_sonnet.KotlinFiles;
import com.hounshell.kotlin_sonnet.TypeReference;

import org.junit.Test;
import java.io.StringWriter;

public class KotlinFileTests {
    @Test
    public void endFileReturnsParentObject() {
        KotlinFiles parent = new KotlinFiles();
        assertThat(parent
            .addKotlinFile("foo.kt")
            .endFile()).isSameInstanceAs(parent);
    }

    @Test
    public void addImport() {
        KotlinFiles parent = new KotlinFiles();
        parent
            .addKotlinFile("foo.kt")
            .addImport(new TypeReference(String.class))
            .endFile();
    }

    @Test
    public void generatedCodeWithPackage() {
        KotlinFiles parent = new KotlinFiles();
        KotlinFile file = parent
            .addKotlinFile("foo.kt")
            .packageName("foo.bar")
            .endFile()
            .getFileByPath("foo.kt");

        StringWriter writer = new StringWriter();
        file.writeTo(writer);

        assertThat(writer.toString()).isEqualTo("package foo.bar\n\n");
    }

    @Test
    public void generatedCodeWithNoPackage() {
        KotlinFiles parent = new KotlinFiles();
        KotlinFile file = parent
            .addKotlinFile("foo.kt")
            .endFile()
            .getFileByPath("foo.kt");

        StringWriter writer = new StringWriter();
        file.writeTo(writer);

        assertThat(writer.toString()).isEqualTo("");
    }
}

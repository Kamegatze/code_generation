package com.kamegatze.code_generation.services;

import com.kamegatze.code_generation.dto.project.ProjectConfigDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final String url = "https://start.spring.io/starter.zip?" +
            "type=%s&" +
            "language=%s&" +
            "bootVersion=%s&" +
            "baseDir=%s&" +
            "groupId=%s&" +
            "artifactId=%s&" +
            "name=%s&" +
            "description=%s&" +
            "packageName=%s&" +
            "packaging=%s&" +
            "javaVersion=%s&" +
            "dependencies=%s";

    private static final int BUFFER_SIZE = 4096;

    @Value("${application.unzip.file.path}")
    private String path;

    public String getUrl(ProjectConfigDTO config) {

        StringBuilder builderDependencies = new StringBuilder();
        for (int i = 0; i < config.getDependencies().size(); i++) {
            if(i == config.getDependencies().size() - 1) {
                builderDependencies.append(config.getDependencies().get(i));
            } else {
                builderDependencies.append(config.getDependencies().get(i)).append(",");
            }
        }

        String dependencies = builderDependencies.toString();

         return String.format(url,
                config.getType(),
                config.getLanguage(),
                config.getBootVersion(),
                config.getBaseDirAndArtifactIdAndName(),
                config.getGroupId(),
                config.getBaseDirAndArtifactIdAndName(),
                config.getBaseDirAndArtifactIdAndName(),
                config.getDescription(),
                config.getPackageName(),
                config.getPackaging(),
                config.getJavaVersion(),
                dependencies
         );
    }

    public void extractFiles(byte[] zip) throws IOException {
        unzip(zip, path);
    }

    private static void unzip(byte[] data, String dirName) throws IOException {
        File destDir = new File(dirName);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(data));
        ZipEntry entry = zipIn.getNextEntry();

        while (entry != null) {
            String filePath = dirName + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}

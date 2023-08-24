package com.kamegatze.code_generation.logic_generate;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.nio.file.Files;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateEntity implements GenerateClass {

    private String nameClass;

    private String nameProject;

    private String packageName;

    private String path;

    private FieldSpec relationship;

    private JavaFile javaFile;


    private final Map<String, TypeName> fields = new HashMap<>();

    @Builder(builderMethodName = "builder")
    public static GenerateEntity newEntity(String nameClass,
                                           String nameProject, String packageName,
                                           String path, FieldSpec relationship,
                                           Map<String, String> fields) throws ClassNotFoundException {

        GenerateEntity entity = new GenerateEntity();
        entity.setNameClass(nameClass);
        entity.setNameProject(nameProject);
        entity.setPackageName(packageName);
        entity.setPath(path);
        entity.setRelationship(relationship);
        entity.setFields(fields);

        return entity;
    }

    public void setFields(Map<String, String> fields) throws ClassNotFoundException {

        Map<String, TypeName> fieldsCorrect = new HashMap<>();

        Set<String> nameFields = fields.keySet();

        for (String nameField : nameFields) {
            fieldsCorrect.put(nameField, TypeName.get(Class.forName(fields.get(nameField))));
        }

        this.fields.putAll(fieldsCorrect);
    }

    @Override
    public void toCreate() throws IOException {
        Path path = Paths.get(this.path +
                "/" + this.nameProject +
                "/src/main/java/" +
                this.packageName.replace(".", "/") +
                "/" + this.nameProject);

        if(!Files.exists(path)) {
            Files.createDirectory(path);
        }

        Set<String> keys = this.fields.keySet();

        List<FieldSpec> fieldSpecs = new ArrayList<>();

        //generate field id
        FieldSpec idField = FieldSpec.builder(TypeName.LONG, "id", Modifier.PRIVATE)
                .addAnnotation(Id.class)
                .addAnnotation(
                        AnnotationSpec.builder(Column.class)
                                .addMember("name", "$S", "id")
                                .build()
                )
                .addAnnotation(
                        AnnotationSpec.builder(GeneratedValue.class)
                                .addMember(
                                        "strategy",
                                        CodeBlock.builder()
                                                .add("GenerationType.IDENTITY")
                                                .build()
                                )
                                .build())
                .build();
        fieldSpecs.add(idField);

        //generate fields
        for (String name : keys) {
                FieldSpec fieldSpec = FieldSpec.builder(this.fields.get(name), name, Modifier.PRIVATE)
                        .addAnnotation(AnnotationSpec.builder(Column.class)
                                .addMember("name", "$S", name)
                                .build()
                        )
                        .build();
            fieldSpecs.add(fieldSpec);

        }

        //generate class entity
        TypeSpec someEntity = generateClass(this.nameClass, fieldSpecs, this.relationship);

        //create java file
        this.javaFile = JavaFile.builder(this.packageName + "." + this.nameProject + ".entity", someEntity)
                .indent("    ")
                .build();


        // path to packages
        path = Paths.get(this.path + "/" + this.nameProject + "/src/main/java/");

        javaFile.writeTo(path);
    }

    private TypeSpec generateClass(String nameEntity, List<FieldSpec> fieldSpecs, FieldSpec relationship) {
        if (relationship != null) {
            return TypeSpec.classBuilder(nameEntity)
                    .addMethod(
                            MethodSpec.constructorBuilder()
                                    .addModifiers(Modifier.PUBLIC)
                                    .build()
                    )
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Entity.class)
                    .addAnnotation(
                            AnnotationSpec.builder(Table.class)
                                    .addMember("name", "$S", nameEntity)
                                    .build()
                    )
                    .addAnnotation(ClassName.get("lombok", "Getter"))
                    .addAnnotation(ClassName.get("lombok", "Setter"))
                    .addFields(fieldSpecs)
                    .addField(relationship)
                    .build();
        }
        return TypeSpec.classBuilder(nameEntity)
                .addMethod(
                        MethodSpec.constructorBuilder()
                                .addModifiers(Modifier.PUBLIC)
                                .build()
                )
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Entity.class)
                .addAnnotation(
                        AnnotationSpec.builder(Table.class)
                                .addMember("name", "$S", nameEntity)
                                .build()
                )
                .addAnnotation(ClassName.get("lombok", "Getter"))
                .addAnnotation(ClassName.get("lombok", "Setter"))
                .addFields(fieldSpecs)
                .build();
    }
}

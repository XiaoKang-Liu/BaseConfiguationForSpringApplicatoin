package com.base.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

/**
 * @author lxk
 * @date 2022/5/5 15:06
 */
public class EntityCommentGenerator implements CommentGenerator {

    private final Properties properties;

    public EntityCommentGenerator(){
        properties = new Properties();
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (introspectedColumn.getActualColumnName().endsWith("_")) {
            field.addAnnotation("@Column(name = \""+introspectedColumn.getActualColumnName()+"\")");
        }
        if (introspectedColumn.isNullable()) {
            field.addAnnotation("@ApiModelProperty( value = \""+introspectedColumn.getRemarks()+"\")");
        }else {
            field.addAnnotation("@ApiModelProperty( required = true, value = \""+introspectedColumn.getRemarks()+"\")");
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String author = properties.getProperty("name");
        String dateFormat = properties.getProperty("dateFormat", "yyyy/MM/dd HH:mm");
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);

        // 获取表注释
        String remarks = introspectedTable.getRemarks();

        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + remarks);
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * @author " + author);
        topLevelClass.addJavaDocLine(" * @date " + dateFormatter.format(new Date()));
        topLevelClass.addJavaDocLine(" */");

        // 添加注解的 import
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addImportedType("lombok.EqualsAndHashCode");
        topLevelClass.addImportedType("lombok.experimental.Accessors");
        topLevelClass.addImportedType("javax.persistence.Table");

        // 添加注解
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper = true)");
        topLevelClass.addAnnotation("@Accessors(chain = true)");
        topLevelClass.addAnnotation("@Table(name = \""+ introspectedTable.getTableConfiguration().getTableName() + "\")");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean b) {

    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {

    }

    @Override
    public void addComment(XmlElement xmlElement) {

    }

    @Override
    public void addRootComment(XmlElement xmlElement) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

    }
}

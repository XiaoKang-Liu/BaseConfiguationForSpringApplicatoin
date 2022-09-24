package com.base.generator;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * @author lxk
 * @date 2022/5/8 22:44
 */
public class MybatisPlugin extends PluginAdapter {

    private static final String PARENT_MAPPER_PATH = "com.base.mapper.BaseMapper.BaseResultMap";

    @Override
    public boolean validate(List<String> list) {
        return true;
    }


    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType parentMapper = new FullyQualifiedJavaType(
                "Mapper<"+introspectedTable.getBaseRecordType()+">");
        interfaze.addSuperInterface(parentMapper);

        FullyQualifiedJavaType importClass = new FullyQualifiedJavaType("tk.mybatis.mapper.common.Mapper");
        interfaze.addImportedType(importClass);

        interfaze.getMethods().clear();
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        sqlMap.setMergeable(false);
        java.lang.reflect.Field document;
        try {
            //通过反射获取私有属性增加attribute
            document = sqlMap.getClass().getDeclaredField("document");
            document.setAccessible(true);
            Document doc = (Document)document.get(sqlMap);
            Attribute attribute = new Attribute("extends", PARENT_MAPPER_PATH);
            XmlElement element = (XmlElement)doc.getRootElement().getElements().get(0);
            element.getAttributes().add(attribute);
            document.set(sqlMap, doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.sqlMapGenerated(sqlMap, introspectedTable);
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getAttributes().clear();
        element.getElements().clear();
        element.setName("sql");
        Attribute attribute = new Attribute("id", "Base_Column_List");

        StringBuilder content = new StringBuilder("id_,create_time,update_time,creator_,rewriter_,");
        for (IntrospectedColumn baseColumn : introspectedTable.getBaseColumns()) {
            content.append(baseColumn.getActualColumnName()).append(",");
        }
        TextElement textElement = new TextElement(content.toString().substring(0,content.toString().lastIndexOf(",")));
        element.addElement(textElement);
        element.addAttribute(attribute);

        return true;
    }

    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }
}

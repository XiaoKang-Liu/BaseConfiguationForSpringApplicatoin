package com.base.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxk
 * @date 2022/5/5 14:44
 */
public class MybatisGenerator {

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        Configuration configuration = generateConfiguration("order", "Order", "BaseConfigurationForSpringApplication");
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println("生成成功");
    }

    private static Configuration generateConfiguration(String tableName, String entityName, String module) {
        Context context = new Context(ModelType.CONDITIONAL);
        context.setId("simple");
        context.setTargetRuntime("MyBatis3Simple");

        /*添加属性*/
        context.addProperty("javaFileEncoding", "UTF-8");
        context.addProperty("javaFormatter","org.mybatis.generator.api.dom.DefaultJavaFormatter");
        context.addProperty("xmlFormatter","org.mybatis.generator.api.dom.DefaultXmlFormatter");

        /*自定义插件*/
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("com.base.generator.MybatisPlugin");
        context.addPluginConfiguration(pluginConfiguration);

        /*注释生成器配置*/
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.setConfigurationType("com.base.generator.EntityCommentGenerator");
        commentGeneratorConfiguration.addProperty("name", "xxx");
        commentGeneratorConfiguration.addProperty("dateFormat", "yyyy/MM/dd HH:mm");
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

        /*jdbc 连接*/
        JDBCConnectionConfiguration jdbcConnectionConfig = new JDBCConnectionConfiguration();
        jdbcConnectionConfig.setDriverClass("com.mysql.jdbc.Driver");
        jdbcConnectionConfig.setConnectionURL("jdbc:mysql://ip:port/dataBase?serverTimezone=Asia/Shanghai&characterEncoding=UTF-8");
        jdbcConnectionConfig.setUserId("username");
        jdbcConnectionConfig.setPassword("password");
        //针对mysql数据库无法读取表和字段备注
        jdbcConnectionConfig.addProperty("useInformationSchema", "true");
        //解决同一数据库含有多个重复表问题
        jdbcConnectionConfig.addProperty("nullCatalogMeansCurrent", "true");
        context.setJdbcConnectionConfiguration(jdbcConnectionConfig);

        /*Model生成器配置*/
        JavaModelGeneratorConfiguration modelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        String targetProject ="src/main/java";
        modelGeneratorConfiguration.setTargetProject(targetProject);
        modelGeneratorConfiguration.setTargetPackage("com.base.entity");
        context.setJavaModelGeneratorConfiguration(modelGeneratorConfiguration);

        /*SqlMapper生成器配置(*Mapper.xml类文件)，要javaClient生成器类型配合*/
        SqlMapGeneratorConfiguration mapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        mapGeneratorConfiguration.setTargetProject("src/main/resources");
        mapGeneratorConfiguration.setTargetPackage("mapper");
        context.setSqlMapGeneratorConfiguration(mapGeneratorConfiguration);

        /*JavaClient生成器配置(*Mapper.java类文件)*/
        JavaClientGeneratorConfiguration clientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        clientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        clientGeneratorConfiguration.setTargetProject(targetProject);
        clientGeneratorConfiguration.setTargetPackage("com.base.mapper");
        context.setJavaClientGeneratorConfiguration(clientGeneratorConfiguration);

        TableConfiguration tableConfig = new TableConfiguration(context);
        tableConfig.setTableName(tableName);
        tableConfig.setDomainObjectName(entityName);
        tableConfig.addProperty("rootClass", "com.base.entity.BaseEntity");

        // 去除父类字段
        IgnoredColumn id = new IgnoredColumn("id_");
        tableConfig.addIgnoredColumn(id);
        IgnoredColumn createTime = new IgnoredColumn("create_time");
        tableConfig.addIgnoredColumn(createTime);
        IgnoredColumn updateTime = new IgnoredColumn("update_time");
        tableConfig.addIgnoredColumn(updateTime);
        IgnoredColumn creator = new IgnoredColumn("creator_");
        tableConfig.addIgnoredColumn(creator);
        IgnoredColumn rewriter = new IgnoredColumn("rewriter_");
        tableConfig.addIgnoredColumn(rewriter);

        tableConfig.setCountByExampleStatementEnabled(false);
        tableConfig.setDeleteByExampleStatementEnabled(false);
        tableConfig.setUpdateByExampleStatementEnabled(false);
        tableConfig.setSelectByExampleStatementEnabled(false);
        context.addTableConfiguration(tableConfig);

        Configuration config = new Configuration();
        config.addContext(context);
        return config;
    }
}

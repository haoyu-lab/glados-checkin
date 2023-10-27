package com.example.gladoscheckin.common;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @date 2023/05/01
 * @description 项目中使用的代码生成器
 **/
public class GeneratorCode {

    /*配置修改*/
    //放在那个包下面  industry
    /**
     *
     * model     TB_INDI_INFO,TB_INDI_SUBJECT,TB_INNER_INDI_PARAMS,TB_NATURE_INDI_ANS,TB_NATURE_INDI_ANS,TB_INNER_ASSESS_RECORD,TB_INDI_ASSESS_RESULT,TB_MODEL_INDI,TB_INNER_ASSESS_MODEL,TB_MODEL_DICT_ASSESS_REGULAR,TB_MODEL_SEG_ASSESS_REGULAR,TB_MODEL_COND_ASSESS_REGULAR,TB_MODEL_ANS_ASSESS_REGULAR

     *
     * industry    TB_INDUSTRY_INFO,TB_INDUSTRY_CATEGORY,TB_INDUSTRY_COMPANY_REL,CBONDISSUER
     *TB_HANDLE_SURVIVING_DEBT
     *
     *
     */
    private static String moduleName = "aliyuncheckin";
    private static String myTablse = "ALIYUN_CHECKIN";
    private static String myPrefix= "TB_";
    private static String myDbUrl = "jdbc:mysql://82.157.101.15:3306/glados?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    private static String myDbUser= "root";
    private static String myDbPWD= "123456hhy";


    private static String myDbDIRVER= "com.mysql.cj.jdbc.Driver";

    private static String projectPath = System.getProperty("user.dir");
    private static String packageName = "com.example.gladoscheckin";
    private static String outDir = projectPath+"\\src\\main\\java";
    private static String entity = "pojo";
    private static String mapper = "mapper";
    private static String service = "service";
    private static String impl = "service.impl";
    private static String controller = "controller";
    private static String xml = "mapper.xml";
    private static boolean isOverEntity = true;
    private static boolean isOverController = false;
    private static boolean isOverService = false;
    private static boolean isOverServiceImpl = false;
    private static boolean isOverMapper = false;
    private static boolean isOverXml = false;
    //private static String entityVM = "/templates/entity.vm";
    private static String entityVM = "/templates/entity.java2.vm";
    private static String controllerVM = "/templates/controller.vm";
    private static String serviceVM = "";
    private static String serviceImplVM = "";
    private static String mapperVM = "";
    private static String xmlVM = "";

    private static String [] baseDir = {entity, mapper, service, impl, controller};
    public static void main(String[] args) {
        //user -> UserService, 设置成true: user -> IUserService
        //int i = 1/0;
        boolean serviceNameStartWithI = false;
        myTablse = myTablse.replace(" ", "");
        generateByTables(serviceNameStartWithI, packageName,myPrefix,
                myTablse.split(","));
    }

    private static void generateByTables(boolean serviceNameStartWithI, String packageName,String prefix, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "";
        if(myDbUrl!=null){
            dbUrl = myDbUrl;
        }
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        // dataSourceConfig.setDbType(DbType.ORACLE)
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername(myDbUser)
                .setPassword(myDbPWD)
                .setDriverName(myDbDIRVER);
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(false)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .entityTableFieldAnnotationEnable(false)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix(prefix)
                //修改替换成你需要的表名，多个表名传数组
                .setInclude(tableNames);

        config.setActiveRecord(true)
                .setAuthor("hhy")
                .setSwagger2(true)
                // 设置自增为uuid
                .setIdType(IdType.ID_WORKER)
                .setOutputDir(outDir)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setFileOverride(true)
                .setEnableCache(false)
                // XML ResultMap
                .setBaseResultMap(true)
                // XML columList;
                .setBaseColumnList(true);

        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }

        PackageConfig pcf = initPackage();
        pcf.setModuleName(moduleName);
        TemplateConfig tc = initTemplateConfig(packageName);



        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList;
        focList = new ArrayList<>();

        focList.add(new FileOutConfig() {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                if (!StringUtils.isEmpty(pcf.getModuleName())) {
                    if(StringPool.DOT_XML.equals("xml")){
                        return projectPath + "/src/main/resources/mapping/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                    }else{
                        return projectPath + "/src/main/resources/mapping/" + pcf.getModuleName()
                                + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                    }

                } else {
                    return projectPath + "/src/main/resources/mapping/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
            }
        });
        cfg.setFileOutConfigList(focList);
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(pcf)
                .setCfg(cfg)
                .setTemplate(tc)
                .execute();
    }

    /**
     * 根据自己的需要，修改哪些包下面的 要覆盖还是不覆盖
     * @param packageName
     */
    private static TemplateConfig initTemplateConfig(String packageName) {
        TemplateConfig tc = new TemplateConfig();
        for(String tmp : baseDir) {
            initVM(tc);
            File file = new File(Paths.get(outDir, String.join("/", packageName.split("\\.")), tmp).toString());
            String[] list = file.list();
            if(list != null && list.length > 0) {
                if(!isOverController) {
                    tc.setController(null);
                }
                if(!isOverService) {
                    tc.setService(null);
                }
                if(!isOverServiceImpl) {
                    tc.setServiceImpl(null);
                }
                if(!isOverEntity) {
                    tc.setEntity(null);
                }
                if(!isOverMapper) {
                    tc.setEntity(null);
                }
                if(!isOverXml) {
                    tc.setXml(null);
                }
            }
        }
        return tc;
    }

    private static void initVM(TemplateConfig tc) {
        if(stringIsNotNull(entityVM)) {
            tc.setEntity(entityVM);
        }
        if(stringIsNotNull(mapperVM)) {
            tc.setMapper(mapperVM);
        }
        if(stringIsNotNull(serviceImplVM)) {
            tc.setServiceImpl(serviceImplVM);
        }
        if(stringIsNotNull(serviceVM)) {
            tc.setService(serviceVM);
        }
        if(stringIsNotNull(xmlVM)) {
            tc.setXml(xmlVM);
        }
        if(stringIsNotNull(controllerVM)) {
            tc.setController(controllerVM);
        }
    }

    /**
     * 简单判断字符串是不是为空 **加粗文字**
     * @param s
     * @return
     */
    private static boolean stringIsNotNull(String s) {
        if(null != s && !s.equals("") && s.length() > 0 && s.trim().length() > 0) {
            return true;
        }
        return false;
   }

    /**
     *
     * 初始化包目录配置
     * @return
     */
    private static PackageConfig initPackage() {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(packageName);
        packageConfig.setService(service);
        packageConfig.setServiceImpl(impl);
        packageConfig.setController(controller);
        packageConfig.setEntity(entity);
        packageConfig.setXml(xml);
        return packageConfig;
    }
}
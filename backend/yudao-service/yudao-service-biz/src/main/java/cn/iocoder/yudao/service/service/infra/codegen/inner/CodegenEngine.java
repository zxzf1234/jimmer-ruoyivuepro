package cn.iocoder.yudao.service.service.infra.codegen.inner;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.engine.velocity.VelocityEngine;
import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.date.DateUtils;
import cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils;
import cn.iocoder.yudao.framework.common.util.object.ObjectUtils;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum;
import cn.iocoder.yudao.service.convert.infra.codegen.CodegenConvert;
import cn.iocoder.yudao.service.enums.codegen.CodegenSceneEnum;
import cn.iocoder.yudao.service.framework.codegen.config.CodegenProperties;
import cn.iocoder.yudao.service.framework.codegen.config.SchemaHistory;
import cn.iocoder.yudao.service.model.infra.codegen.*;
import cn.iocoder.yudao.service.repository.infra.codegen.*;
import cn.iocoder.yudao.service.vo.infra.codegen.database.DatabaseUpdateReq;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static cn.hutool.core.map.MapUtil.getStr;
import static cn.hutool.core.text.CharSequenceUtil.*;

/**
 * 代码生成的引擎，用于具体生成代码
 * 目前基于 {@link org.apache.velocity.app.Velocity} 模板引擎实现
 *
 * 考虑到 Java 模板引擎的框架非常多，Freemarker、Velocity、Thymeleaf 等等，所以我们采用 hutool 封装的 {@link cn.hutool.extra.template.Template} 抽象
 *
 * @author 芋道源码
 */
@Component
public class CodegenEngine {

    /**
     * 后端的模板配置
     *
     * key：模板在 resources 的地址
     * value：生成的路径
     */
    private static final Map<String, String> TABLE_INSERT_TEMPLATES = MapUtil.<String, String>builder(new LinkedHashMap<>()) // 有序
            // Java module-biz Main
            .put(javaTemplatePath("vo/baseVO"), javaBaseVOFilePath("Base"))
            .put(javaTemplatePath("repository/repository"),
                    javaTableFilePath("repository","${classNameHump}Repository"))
            .put(javaTemplatePath("model/model"), javaTableFilePath("model","${classNameHump}"))
            .build();

    private static final Map<String, String> TABLE_UPDATE_TEMPLATES = MapUtil.<String, String>builder(new LinkedHashMap<>()) // 有序
            // Java module-biz Main
            .put(javaTemplatePath("vo/baseVO"), javaBaseVOFilePath("Base"))
            .put(javaTemplatePath("model/model"), javaTableFilePath("model","${classNameHump}"))
            .build();

    private static final Map<String, String> MODULE_TEMPLATES = MapUtil.<String, String>builder(new LinkedHashMap<>()) // 有序

            .put(javaTemplatePath("controller/controller"), javaControllerFilePath())
            .put(javaTemplatePath("convert/convert"), javaModuleFilePath("convert", "${nameHumpUp}Convert"))
            .put(javaTemplatePath("service/serviceImpl"), javaModuleFilePath("service", "${nameHumpUp}ServiceImpl"))
            .put(javaTemplatePath("service/service"), javaModuleFilePath("service", "${nameHumpUp}Service"))
            .build();
    private static final Map<String, String> INTERFACE_TEMPLATES = MapUtil.<String, String>builder(new LinkedHashMap<>()) // 有序

            .put(javaTemplatePath("controller/controllerInterface"), javaFilePath("controller/${sceneEnum.basePackage}/${modulePath}/${moduleNameHump}Controller")+ ".java")
            .put(javaTemplatePath("convert/convertInterface"), javaModuleFilePath("convert", "${moduleNameHump}Convert"))
            .put(javaTemplatePath("service/serviceImplInterface"), javaModuleFilePath("service", "${moduleNameHump}ServiceImpl"))
            .put(javaTemplatePath("service/serviceInterface"), javaModuleFilePath("service", "${moduleNameHump}Service"))
            .put(javaTemplatePath("vo/VOInput"), javaModuleFilePath("vo", "${moduleNameHump}/${moduleNameHumpUp}${interfaceNameHumpUp}Input"))
            .put(javaTemplatePath("vo/VOOutput"), javaModuleFilePath("vo", "${moduleNameHump}/${moduleNameHumpUp}${interfaceNameHumpUp}Output"))
            .build();


/*

    private static final Table<Integer, String, String> FRONT_TEMPLATES = ImmutableTable.<Integer, String, String>builder()
            // Vue2 标准模版
            .put(CodegenFrontTypeEnum.VUE2.getType(), vueTemplatePath("views/index.vue"),
                    vueFilePath("views/${table.moduleName}/${classNameVar}/index.vue"))
            .put(CodegenFrontTypeEnum.VUE2.getType(), vueTemplatePath("api/api.js"),
                    vueFilePath("api/${table.moduleName}/${classNameVar}.js"))
            // Vue3 标准模版
            .put(CodegenFrontTypeEnum.VUE3.getType(), vue3TemplatePath("views/index.vue"),
                    vue3FilePath("views/${table.moduleName}/${classNameVar}/index.vue"))
            .put(CodegenFrontTypeEnum.VUE3.getType(), vue3TemplatePath("views/form.vue"),
                    vue3FilePath("views/${table.moduleName}/${classNameVar}/${simpleClassName}Form.vue"))
            .put(CodegenFrontTypeEnum.VUE3.getType(), vue3TemplatePath("api/api.ts"),
                    vue3FilePath("api/${table.moduleName}/${classNameVar}/index.ts"))
            // Vue3 Schema 模版
            .put(CodegenFrontTypeEnum.VUE3_SCHEMA.getType(), vue3SchemaTemplatePath("views/data.ts"),
                    vue3FilePath("views/${table.moduleName}/${classNameVar}/${classNameVar}.data.ts"))
            .put(CodegenFrontTypeEnum.VUE3_SCHEMA.getType(), vue3SchemaTemplatePath("views/index.vue"),
                    vue3FilePath("views/${table.moduleName}/${classNameVar}/index.vue"))
            .put(CodegenFrontTypeEnum.VUE3_SCHEMA.getType(), vue3SchemaTemplatePath("views/form.vue"),
                    vue3FilePath("views/${table.moduleName}/${classNameVar}/${simpleClassName}Form.vue"))
            .put(CodegenFrontTypeEnum.VUE3_SCHEMA.getType(), vue3SchemaTemplatePath("api/api.ts"),
                    vue3FilePath("api/${table.moduleName}/${classNameVar}/index.ts"))
            // Vue3 vben 模版
            .put(CodegenFrontTypeEnum.VUE3_VBEN.getType(), vue3VbenTemplatePath("views/data.ts"),
                    vue3FilePath("views/${table.moduleName}/${classNameVar}/${classNameVar}.data.ts"))
            .put(CodegenFrontTypeEnum.VUE3_VBEN.getType(), vue3VbenTemplatePath("views/index.vue"),
                    vue3FilePath("views/${table.moduleName}/${classNameVar}/index.vue"))
            .put(CodegenFrontTypeEnum.VUE3_VBEN.getType(), vue3VbenTemplatePath("views/form.vue"),
                    vue3FilePath("views/${table.moduleName}/${classNameVar}/${simpleClassName}Modal.vue"))
            .put(CodegenFrontTypeEnum.VUE3_VBEN.getType(), vue3VbenTemplatePath("api/api.ts"),
                    vue3FilePath("api/${table.moduleName}/${classNameVar}/index.ts"))
            .build();*/

    @Resource
    private CodegenProperties codegenProperties;

    @Resource
    private InfraDatabaseTableRepository infraDatabaseTableRepository;

    @Resource
    private InfraInterfaceModuleRepository infraInterfaceModuleRepository;

    @Resource
    private InfraInterfaceRepository infraInterfaceRepository;

    @Resource
    private InfraInterfaceVoClassRepository infraInterfaceVoClassRepository;

    @Resource
    private InfraInterfaceSubclassRepository infraInterfaceSubclassRepository;


    @Resource
    private SchemaHistory schemaHistory;

    /**
     * 模板引擎，由 hutool 实现
     */
    private final TemplateEngine templateEngine;
    /**
     * 全局通用变量映射
     */
    private final Map<String, Object> globalBindingMap = new HashMap<>();

    public CodegenEngine() {
        // 初始化 TemplateEngine 属性
        TemplateConfig config = new TemplateConfig();
        config.setResourceMode(TemplateConfig.ResourceMode.CLASSPATH);
        this.templateEngine = new VelocityEngine(config);
    }

    @PostConstruct
    private void initGlobalBindingMap() {
        // 全局配置
        globalBindingMap.put("basePackage", codegenProperties.getBasePackage());
        globalBindingMap.put("baseFrameworkPackage", codegenProperties.getBasePackage()
                + '.' + "framework"); // 用于后续获取测试类的 package 地址
        // 全局 Java Bean
        globalBindingMap.put("CommonResultClassName", CommonResult.class.getName());
        globalBindingMap.put("PageResultClassName", PageResult.class.getName());
        // VO 类，独有字段
        globalBindingMap.put("PageParamClassName", PageParam.class.getName());
        globalBindingMap.put("DictFormatClassName", DictFormat.class.getName());
        // DO 类，独有字段
        globalBindingMap.put("BaseDOClassName", BaseDO.class.getName());
        globalBindingMap.put("QueryWrapperClassName", LambdaQueryWrapperX.class.getName());
        globalBindingMap.put("BaseMapperClassName", BaseMapperX.class.getName());
        // Util 工具类
        globalBindingMap.put("ServiceExceptionUtilClassName", ServiceExceptionUtil.class.getName());
        globalBindingMap.put("DateUtilsClassName", DateUtils.class.getName());
        globalBindingMap.put("ExcelUtilsClassName", ExcelUtils.class.getName());
        globalBindingMap.put("LocalDateTimeUtilsClassName", LocalDateTimeUtils.class.getName());
        globalBindingMap.put("ObjectUtilsClassName", ObjectUtils.class.getName());
        globalBindingMap.put("DictConvertClassName", DictConvert.class.getName());
        globalBindingMap.put("OperateLogClassName", OperateLog.class.getName());
        globalBindingMap.put("OperateTypeEnumClassName", OperateTypeEnum.class.getName());
    }

    private void convertParams(List<CodegenInterfaceParam> params){
        for(CodegenInterfaceParam param : params)
        {
            if(Objects.equals(param.getVariableType(), "VOClass")) {
                InfraInterfaceVoClass voClass = infraInterfaceVoClassRepository.findById(UUID.fromString(param.getRelatedId())).get();
                param.setVariableType(voClass.name());
            }
            if(Objects.equals(param.getVariableType(), "Subclass")) {
                InfraInterfaceSubclass subclass = infraInterfaceSubclassRepository.findById(UUID.fromString(param.getRelatedId())).get();
                param.setVariableType(subclass.name());
            }
            param.setHumpName(toCamelCase(param.getName()));
        }
    }

    private String srcExtendClass(UUID id){
       Optional<InfraInterfaceVoClass> opVoClass = infraInterfaceVoClassRepository.findById(id);
       if(!opVoClass.isPresent())
           return "";
        if(opVoClass.get().parentId().isEmpty())
            return "";
        if(opVoClass.get().type() == 1) {
            return srcExtendClass(UUID.fromString(opVoClass.get().parentId()));
        } else {
            return infraDatabaseTableRepository.findById(UUID.fromString(opVoClass.get().parentId())).get().name();
        }
    }

    private String getExtendClassImport(String extendClass, Map<String, Object> bindingMap){
        StringBuilder extendClassImport;
        Optional<InfraInterfaceVoClass> opVoClass = infraInterfaceVoClassRepository.findByName(extendClass);
        if(!opVoClass.isPresent())
            return "";
        if(opVoClass.get().type() == 0) {
            InfraDatabaseTable table = infraDatabaseTableRepository.findById(UUID.fromString(opVoClass.get().parentId())).get();
            String moduleName = table.name().substring(0, table.name().indexOf("_"));
            extendClassImport = new StringBuilder("import " +
                    getStr(bindingMap, "basePackage").replaceAll("\\.", ".") +
                    ".service.vo." +
                    moduleName +
                    "." +
                    table.businessName() +
                    ".baseVO." +
                    upperFirst(toCamelCase(removePrefix(table.name(), moduleName + "_"))) +
                    "Base"+
                    ";");
        }else{
            InfraInterface infraInterface = infraInterfaceRepository.findById(UUID.fromString(opVoClass.get().parentId())).get();
            List<String> parentNames = getParentName(Objects.requireNonNull(infraInterface.module()).id());
            parentNames.remove(0);
            extendClassImport = new StringBuilder("import " +
                    getStr(bindingMap, "basePackage").replaceAll("\\.", ".") +
                    ".service.vo.");
            for(String parentName : parentNames){
                extendClassImport.append(".").append(parentName);
            }
            InfraInterfaceModule infraInterfaceModule =  infraInterfaceModuleRepository.findById(infraInterface.moduleId()).get();
            extendClassImport.append(".").append(toCamelCase(infraInterfaceModule.name())).append(";");
        }
        return extendClassImport.toString();
    }

    private Map<String, Object> getInterfaceBindingMap(InfraInterface infraInterface){
        Map<String, Object> bindingMap = new HashMap<>(globalBindingMap);
        InfraInterfaceModule infraInterfaceModule =  infraInterfaceModuleRepository.findById(infraInterface.moduleId()).get();
        String interfaceNameHump = toCamelCase(replace(infraInterface.name(),"-","_"));
        String moduleNameHump = toCamelCase(replace(infraInterfaceModule.name(),"-","_"));
        String moduleNameHumpUp = upperFirst(moduleNameHump);

        bindingMap.put("sceneEnum", CodegenSceneEnum.valueOf("ADMIN"));
        bindingMap.put("interface", infraInterface);
        bindingMap.put("module", infraInterfaceModule);
        // 接口方法 首字母大写
        bindingMap.put("interfaceMethodUp", upperFirst(infraInterface.method()));
        // 接口名驼峰 首字母小写
        bindingMap.put("interfaceNameHump", interfaceNameHump);
        // 接口名驼峰 首字母大写
        bindingMap.put("interfaceNameHumpUp", upperFirst(interfaceNameHump));
        List<String> parentNames = getParentName(Objects.requireNonNull(infraInterface.module()).id());
        parentNames.remove(0);
        Collections.reverse(parentNames);
        bindingMap.put("parentNames", parentNames);
        bindingMap.put("moduleNameHump", moduleNameHump);
        bindingMap.put("moduleNameHumpUp", moduleNameHumpUp);
        bindingMap.put("modulePath", String.join("/", parentNames));

        List<CodegenInterfaceParam> inputParams = CodegenConvert.INSTANCE.convertList19(infraInterface.inputParams());
        convertParams(inputParams);
        bindingMap.put("inputParams", inputParams);

        List<CodegenInterfaceParam> outputParams = CodegenConvert.INSTANCE.convertList19(infraInterface.outputParams());
        convertParams(outputParams);
        bindingMap.put("outputParams", outputParams);

        List<String> controllerImportList = new ArrayList<>();
        List<String> convertImportList = new ArrayList<>();
        List<String> inputImportList = new ArrayList<>();
        List<String> outputImportList = new ArrayList<>();

        List<CodegenInterfaceSubclass> inputSubclasses = CodegenConvert.INSTANCE.convertList20(infraInterface.inputSubclasses());
        for(CodegenInterfaceSubclass inputSubclass : inputSubclasses){
            convertParams(inputSubclass.getSubclassParams());
            if(!inputSubclass.getInheritClass().isEmpty()){
                String inputSubExtendClassImport = getExtendClassImport(inputSubclass.getInheritClass(), bindingMap);
                if(!inputSubExtendClassImport.isEmpty())
                 inputImportList.add(inputSubExtendClassImport);
            }
        }
        bindingMap.put("inputSubclasses", inputSubclasses);

        List<CodegenInterfaceSubclass> outputSubclasses = CodegenConvert.INSTANCE.convertList20(infraInterface.outputSubclasses());
        for(CodegenInterfaceSubclass outputSubclass : outputSubclasses){
            convertParams(outputSubclass.getSubclassParams());
            if(!outputSubclass.getInheritClass().isEmpty()){
                String outputSubExtendClassImport = getExtendClassImport(outputSubclass.getInheritClass(), bindingMap);
                if(!outputSubExtendClassImport.isEmpty())
                    outputImportList.add(outputSubExtendClassImport);
            }
        }
        bindingMap.put("outputSubclasses", outputSubclasses);
        // src_extend_class
        String inputSrcExtendClass = "";
        String inputSrcExtendTableImport = "";
        String inputExtendClassImport = "";
        if(!infraInterface.inputExtendClass().isEmpty()) {
            InfraInterfaceVoClass voClass = infraInterfaceVoClassRepository.findByName(infraInterface.inputExtendClass()).get();
            if (Objects.equals(voClass.name(), "PageParam")){
                inputExtendClassImport = "import "+ PageParam.class.getName() +";";
            }else {
                inputSrcExtendClass = srcExtendClass(voClass.id());

                InfraDatabaseTable table = infraDatabaseTableRepository.findByName(inputSrcExtendClass).get();
                inputSrcExtendTableImport = "import " +
                        getStr(bindingMap, "basePackage").replaceAll("\\.", ".") +
                        ".service.model." +
                        table.name().substring(0, table.name().indexOf("_")) +
                        "." +
                        table.businessName() +
                        "." +
                        upperFirst(toCamelCase(inputSrcExtendClass))
                        + ";";
                inputSrcExtendClass = upperFirst(toCamelCase(inputSrcExtendClass));

                inputExtendClassImport = getExtendClassImport(infraInterface.inputExtendClass(), bindingMap);
            }
            convertImportList.add(inputSrcExtendTableImport);
            inputImportList.add(inputExtendClassImport);
        }
        bindingMap.put("inputSrcExtendClass", inputSrcExtendClass);


        String outputSrcExtendClass = "";
        String outputSrcExtendTableImport = "";
        String outputExtendClassImport = "";
        if(!infraInterface.outputExtendClass().isEmpty()) {
            InfraInterfaceVoClass voClass = infraInterfaceVoClassRepository.findByName(infraInterface.outputExtendClass()).get();
            outputSrcExtendClass = srcExtendClass(voClass.id());

            InfraDatabaseTable table = infraDatabaseTableRepository.findByName(outputSrcExtendClass).get();
            outputSrcExtendTableImport = "import " +
                    getStr(bindingMap, "basePackage").replaceAll("\\.", ".") +
                    ".service.model." +
                    table.name().substring(0, table.name().indexOf("_")) +
                    "."+
                    table.businessName()+
                    "."+
                    upperFirst(toCamelCase(outputSrcExtendClass)) +
                    ";";
            outputSrcExtendClass = upperFirst(toCamelCase(outputSrcExtendClass));
            outputExtendClassImport = getExtendClassImport(infraInterface.outputExtendClass(), bindingMap);
            convertImportList.add(outputSrcExtendTableImport);
            outputImportList.add(outputExtendClassImport);
        }
        bindingMap.put("outputSrcExtendClass", outputSrcExtendClass);


        // input
        String input = "";
        String inputVar = "inputVO";
        String inputRequest = "";
        String inputValid = "";
        String inputSingleParam = "";
        if(Objects.equals(infraInterface.inputType(), "void")){
            input = "";
        }else if(Objects.equals(infraInterface.inputType(), "param")){
            input = infraInterface.inputParams().get(0).variableType();
            if(infraInterface.inputParams().get(0).isList()) {
                input = "List<" + input + ">";
            }
            inputVar = infraInterface.inputParams().get(0).name();
            inputRequest = "@RequestParam(" + "\"" + inputVar + "\") ";
            inputSingleParam = "@Parameter(name = \"" + inputVar + "\", description = \"" +
                    infraInterface.inputParams().get(0).comment() + "\", " +
                    (infraInterface.inputParams().get(0).required()? "required = true, " : "") +
                    "example = \""+
                    infraInterface.inputParams().get(0).example() +"\")";
        } else {
            input =  moduleNameHumpUp + upperFirst(interfaceNameHump) + "Input";

            StringBuilder inputImport = new StringBuilder("import " +
                    getStr(bindingMap, "basePackage").replaceAll("\\.", ".") +
                    ".service.vo")  ;
            for(String parentName : parentNames){
                inputImport.append(".").append(parentName);
            }
            inputImport.append(".").append(moduleNameHump).append(".").append(input).append(";");
            controllerImportList.add(inputImport.toString());
            convertImportList.add(inputImport.toString());

            if(Objects.equals(infraInterface.inputType(), "VOClassList"))
            {
                input = "List<" + input + ">";
            }
            if(!Objects.equals(infraInterface.method(), "get")){
                inputRequest = "@RequestBody ";
            }
            inputValid = "@Valid ";
        }
        bindingMap.put("interfaceInput", input);
        bindingMap.put("interfaceInputVar", inputVar);
        bindingMap.put("interfaceInputRequest", inputRequest);
        bindingMap.put("interfaceInputValid", inputValid);
        bindingMap.put("interfaceInputSingleParam", inputSingleParam);

        // output
        String output = "";
        if(Objects.equals(infraInterface.outputType(), "void")){
            output = "void";
        }else if(Objects.equals(infraInterface.outputType(), "param")){
            output = infraInterface.outputParams().get(0).variableType();
            if(infraInterface.outputParams().get(0).isList()) {
                output = "List<" + output + ">";
            }
        }else {
            output =  moduleNameHumpUp + upperFirst(interfaceNameHump) + "Output";

            StringBuilder outputImport = new StringBuilder("import " +
                    getStr(bindingMap, "basePackage").replaceAll("\\.", ".") +
                    ".service.vo")  ;
            for(String parentName : parentNames){
                outputImport.append(".").append(parentName);
            }
            outputImport.append(".").append(moduleNameHump).append(".").append(output).append(";");
            controllerImportList.add(outputImport.toString());
            convertImportList.add(outputImport.toString());

            if(Objects.equals(infraInterface.outputType(), "VOClassList")) {
                output = "List<" + output + ">";
            }
            if(Objects.equals(infraInterface.outputType(), "VOClassPage")) {
                output = "PageResult<" + output + ">";
            }
        }
        bindingMap.put("interfaceOutput", output);

        bindingMap.put("controllerImportList", controllerImportList);
        bindingMap.put("convertImportList", convertImportList);
        bindingMap.put("inputImportList", inputImportList);
        bindingMap.put("outputImportList", outputImportList);

        return bindingMap;
    }

    private void fileInsertImport(StringBuilder fileContent, List<String> importList) {
        int importIndex = fileContent.indexOf("import");
        for(String strImport : importList) {
            if(fileContent.indexOf(strImport) < 0)
                fileContent.insert(importIndex, strImport + "\r\n");
        }
    }

    public void interfaceInsert(UUID interfaceId){
        InfraInterface infraInterface = infraInterfaceRepository.findDetailById(interfaceId).get();
        Map<String, Object> bindingMap = getInterfaceBindingMap(infraInterface);

        List<String> controllerImportList = Convert.toList(String.class, bindingMap.get("controllerImportList"));
        List<String> convertImportList = Convert.toList(String.class, bindingMap.get("convertImportList"));
        List<String> inputImportList = Convert.toList(String.class, bindingMap.get("inputImportList"));
        List<String> outputImportList = Convert.toList(String.class, bindingMap.get("outputImportList"));


        Map<String, String> templates = new LinkedHashMap<>(INTERFACE_TEMPLATES);
        for(Map.Entry<String, String> entry : templates.entrySet()){
            String vmPath = entry.getKey();
            String filePath = entry.getValue();
            if (vmPath.contains("VOInput")
                    && (Objects.equals(infraInterface.inputType(), "void") || Objects.equals(infraInterface.inputType(), "param")))
                continue;
            if (vmPath.contains("VOOutput")
                    && (Objects.equals(infraInterface.outputType(), "void") || Objects.equals(infraInterface.outputType(), "param")))
                continue;
            filePath = formatInterfaceFilePath(filePath, bindingMap);
            File newFile;
            if(!FileUtil.exist(filePath)) {
                if (vmPath.contains("VOInput") || vmPath.contains("VOOutput")){
                    newFile = FileUtil.touch(filePath);
                    RuntimeUtil.execForStr("git add " + filePath);
                } else
                    continue;
            }else{
                newFile = FileUtil.file(filePath);
            }

            String interfaceContent = "";
            if(!vmPath.isEmpty()) {
                interfaceContent = templateEngine.getTemplate(vmPath).render(bindingMap);
                // 去除字段后面多余的 , 逗号
                interfaceContent = interfaceContent.replaceAll(",\n}", "\n}").replaceAll(",\n  }", "\n  }");

                StringBuilder fileContent = new StringBuilder(FileUtil.readUtf8String(newFile));
                int index = fileContent.lastIndexOf("\r\n}");
                if(index > 0) {
                    fileContent.insert(index, interfaceContent);
                }else {
                    fileContent.append(interfaceContent);
                }
                if(vmPath.contains("convertInterface") && !convertImportList.isEmpty())
                    fileInsertImport(fileContent, convertImportList);

                if((vmPath.contains("serviceImplInterface") || vmPath.contains("serviceInterface") || vmPath.contains("controllerInterface"))
                        && !controllerImportList.isEmpty())
                    fileInsertImport(fileContent, controllerImportList);

                if(vmPath.contains("VOInput") && !inputImportList.isEmpty())
                    fileInsertImport(fileContent, inputImportList);

                if(vmPath.contains("VOOutput") && !outputImportList.isEmpty())
                    fileInsertImport(fileContent, outputImportList);

                FileUtil.writeUtf8String(fileContent.toString(), newFile);

            }
        }
    }
    public void interfaceUpdate(UUID newInterfaceId, InfraInterface oldInterface){
        InfraInterface newInterface = infraInterfaceRepository.findDetailById(newInterfaceId).get();

        Map<String, Object> newBindingMap = getInterfaceBindingMap(newInterface);

        Map<String, Object> oldBindingMap = getInterfaceBindingMap(oldInterface);

        List<String> newControllerImportList = Convert.toList(String.class, newBindingMap.get("controllerImportList"));
        List<String> newConvertImportList = Convert.toList(String.class, newBindingMap.get("convertImportList"));
        List<String> newInputImportList = Convert.toList(String.class, newBindingMap.get("inputImportList"));
        List<String> newOutputImportList = Convert.toList(String.class, newBindingMap.get("outputImportList"));

        List<String> oldControllerImportList = Convert.toList(String.class, oldBindingMap.get("controllerImportList"));
        List<String> oldConvertImportList = Convert.toList(String.class, oldBindingMap.get("convertImportList"));
        List<String> oldInputImportList = Convert.toList(String.class, oldBindingMap.get("inputImportList"));
        List<String> oldOutputImportList = Convert.toList(String.class, oldBindingMap.get("outputImportList"));

        Map<String, String> templates = new LinkedHashMap<>(INTERFACE_TEMPLATES);
        for(Map.Entry<String, String> entry : templates.entrySet()){
            String vmPath = entry.getKey();
            String filePath = entry.getValue();
            if (vmPath.contains("VOInput")
                    && (Objects.equals(newInterface.inputType(), "void") || Objects.equals(newInterface.inputType(), "param"))) {
                filePath = formatInterfaceFilePath(filePath, oldBindingMap);
                if(FileUtil.exist(filePath)) {
                    RuntimeUtil.execForStr("git rm -f " + filePath);
                }
                continue;
            }
            if (vmPath.contains("VOOutput")
                    && (Objects.equals(newInterface.outputType(), "void") || Objects.equals(newInterface.outputType(), "param"))) {
                filePath = formatInterfaceFilePath(filePath, oldBindingMap);
                if(FileUtil.exist(filePath)) {
                    RuntimeUtil.execForStr("git rm -f " + filePath);
                }
                continue;
            }
            filePath = formatInterfaceFilePath(filePath, newBindingMap);
            File newFile;
            if(!FileUtil.exist(filePath)) {
                if (vmPath.contains("VOInput") || vmPath.contains("VOOutput")){
                    newFile = FileUtil.touch(filePath);
                    RuntimeUtil.execForStr("git add " + filePath);
                } else
                    continue;
            }else{
                newFile = FileUtil.file(filePath);
            }

            String newInterfaceContent = "";
            String oldInterfaceContent = "";
            if(!vmPath.isEmpty()) {
                newInterfaceContent = templateEngine.getTemplate(vmPath).render(newBindingMap);
                oldInterfaceContent = templateEngine.getTemplate(vmPath).render(oldBindingMap);
                // 去除字段后面多余的 , 逗号
                newInterfaceContent = newInterfaceContent.replaceAll(",\n}", "\n}").replaceAll(",\n  }", "\n  }");
                // 去除字段后面多余的 , 逗号
                oldInterfaceContent = oldInterfaceContent.replaceAll(",\n}", "\n}").replaceAll(",\n  }", "\n  }");

                StringBuilder fileContent = new StringBuilder(FileUtil.readUtf8String(newFile));
                if(vmPath.contains("controllerInterface") || vmPath.contains("serviceImplInterface")){
                    if(!oldInterfaceContent.contains(") {"))
                        continue;
                    if(!newInterfaceContent.contains(") {"))
                        continue;
                    String oldFunctionContent = oldInterfaceContent.substring(0, oldInterfaceContent.indexOf(") {"));
                    String oldReturnContent = oldInterfaceContent.substring(oldInterfaceContent.indexOf(") {") + 5) ;

                    String newFunctionContent = newInterfaceContent.substring(0, newInterfaceContent.indexOf(") {"));
                    String newReturnContent = newInterfaceContent.substring(newInterfaceContent.indexOf(") {") + 5);

                    int functionIndex = fileContent.indexOf(oldFunctionContent);
                    if(functionIndex > 0){
                        fileContent.replace(functionIndex , functionIndex + oldFunctionContent.length(), newFunctionContent);
                    }

                    int returnIndex = fileContent.indexOf(oldReturnContent);
                    if(returnIndex > 0){
                        fileContent.replace(returnIndex , returnIndex + oldReturnContent.length(), newReturnContent);
                    }
                }else if(vmPath.contains("convertInterface") || vmPath.contains("serviceInterface")){
                    int index = fileContent.indexOf(oldInterfaceContent);
                    if(index > 0){
                        fileContent.replace(index , index + oldInterfaceContent.length(), newInterfaceContent);
                    }
                } else {
                    fileContent = new StringBuilder(newInterfaceContent);
                }
                if(vmPath.contains("convertInterface") && (!newConvertImportList.isEmpty() || !oldConvertImportList.isEmpty()))
                    fileUpdateImport(fileContent, oldConvertImportList, newConvertImportList);

                if((vmPath.contains("serviceImplInterface") || vmPath.contains("serviceInterface") || vmPath.contains("controllerInterface"))
                        && (!newControllerImportList.isEmpty() || !oldControllerImportList.isEmpty()))
                    fileUpdateImport(fileContent, oldControllerImportList, newControllerImportList);

                if(vmPath.contains("VOInput") && (!newInputImportList.isEmpty() || !oldInputImportList.isEmpty()))
                    fileUpdateImport(fileContent, oldInputImportList, newInputImportList);

                if(vmPath.contains("VOOutput") && (!newOutputImportList.isEmpty() || !oldOutputImportList.isEmpty()))
                    fileUpdateImport(fileContent, oldOutputImportList, newOutputImportList);
                FileUtil.writeUtf8String(fileContent.toString(), newFile);

            }
        }
    }

    private void fileUpdateImport(StringBuilder fileContent, List<String> oldImportList, List<String> newImportList) {
        int importIndex = 0;
        int lastImportIndex = fileContent.indexOf(";", fileContent.lastIndexOf("import "));
        for(String strImport : oldImportList) {
            if(strImport.isEmpty())
                continue;
            if(fileContent.indexOf(strImport) >= 0) {
                if (importIndex == 0){
                    importIndex = fileContent.indexOf(strImport);
                }
                int deleteIndex = strImport.lastIndexOf(".");
                String deleteClass = strImport.substring(deleteIndex + 1, strImport.lastIndexOf(";"));
                if(fileContent.indexOf(deleteClass, lastImportIndex) < 0)
                    fileContent.delete(importIndex, importIndex + strImport.length() + 2);
            }
        }
        if (importIndex == 0)
            importIndex = fileContent.indexOf("import");

        for(String strImport : newImportList) {
            if(fileContent.indexOf(strImport) < 0)
                fileContent.insert(importIndex, strImport + "\r\n");
        }
    }

    private String formatInterfaceFilePath(String filePath, Map<String, Object> bindingMap) {
        filePath = StrUtil.replace(filePath, "${basePackage}",
                getStr(bindingMap, "basePackage").replaceAll("\\.", "/"));
        // sceneEnum 包含的字段
        CodegenSceneEnum sceneEnum = (CodegenSceneEnum) bindingMap.get("sceneEnum");
        filePath = StrUtil.replace(filePath, "${sceneEnum.prefixClass}", sceneEnum.getPrefixClass());
        filePath = StrUtil.replace(filePath, "${sceneEnum.basePackage}", sceneEnum.getBasePackage());
        filePath = StrUtil.replace(filePath, "${nameHump}", getStr(bindingMap, "nameHump"));
        filePath = StrUtil.replace(filePath, "${moduleNameHump}", getStr(bindingMap, "moduleNameHump"));
        filePath = StrUtil.replace(filePath, "${moduleNameHumpUp}", getStr(bindingMap, "moduleNameHumpUp"));
        filePath = StrUtil.replace(filePath, "${interfaceNameHumpUp}", getStr(bindingMap, "interfaceNameHumpUp"));
        filePath = StrUtil.replace(filePath, "${modulePath}", getStr(bindingMap, "modulePath"));

        return filePath;
    }

    public void moduleInsertExecute(InfraInterfaceModule module){
       Map<String, Object> bindingMap = getModuleBindingMap(module);
       generateModule(bindingMap);
    }

    private String moduleUpdateContent(String content, InfraInterfaceModule oldModule, InfraInterfaceModule newModule){
        String oldName = oldModule.name();
        String oldHumpName = upperFirst(oldName);
        String oldComment = oldModule.comment();
        String newName = newModule.name();
        String newHumpName = upperFirst(newName);
        String newComment = newModule.comment();
        content = content.replace("\\" + oldName, "\\" + newName)
                .replace(oldName + "Service", newName + "Service")
                .replace(oldHumpName + "Service", newHumpName + "Service")
                .replace(oldHumpName + "Controller", newHumpName + "Controller")
                .replace("\"" + oldComment + "\"", "\"" + newComment + "\"")
                .replace(oldHumpName + "Convert", newHumpName + "Convert");

        return content;
    }

    public void moduleUpdateExecute(InfraInterfaceModule oldModule, InfraInterfaceModule newModule){
        Map<String, Object> oldBindingMap = getModuleBindingMap(oldModule);
        Map<String, Object> newBindingMap = getModuleBindingMap(newModule);
        Map<String, String> templates = new LinkedHashMap<>(MODULE_TEMPLATES);
        templates.forEach((vmPath, filePath) -> {
            String oldFilePath = formatModuleFilePath(filePath, oldBindingMap);
            String newFilePath = formatModuleFilePath(filePath, newBindingMap);
            if(!FileUtil.exist(oldFilePath))
                return;
            String content = FileUtil.readUtf8String(oldFilePath);
            content = moduleUpdateContent(content, oldModule, newModule);
            RuntimeUtil.execForStr("git mv " + oldFilePath + " " + newFilePath);
            File newFile = FileUtil.file(newFilePath);
            FileUtil.writeUtf8String(content, newFile);
        });

    }

    public void moduleDeleteExecute(InfraInterfaceModule module){
        Map<String, Object> bindingMap = getModuleBindingMap(module);
        Map<String, String> templates = new LinkedHashMap<>(MODULE_TEMPLATES);
        templates.forEach((vmPath, filePath) -> {
            filePath = formatModuleFilePath(filePath, bindingMap);
            RuntimeUtil.execForStr("git rm -f " + filePath);
        });
    }

    private Map<String, Object> getModuleBindingMap(InfraInterfaceModule module){
        Map<String, Object> bindingMap = new HashMap<>(globalBindingMap);

        bindingMap.put("sceneEnum", CodegenSceneEnum.valueOf("ADMIN"));
        bindingMap.put("module", module);
        List<String> parentNames = getParentName(module.id());
        // 去掉自身的name
        parentNames.remove(0);
        Collections.reverse(parentNames);
        bindingMap.put("parentNames", parentNames);
        bindingMap.put("nameHumpUp", upperFirst(toCamelCase(replace(module.name(), "-", "_"))));
        bindingMap.put("nameHump", toCamelCase(replace(module.name(), "-", "_")));
        bindingMap.put("modulePath", String.join("/", parentNames));
        return bindingMap;
    }

    List<String> getParentName(UUID id){
        List<String> parentNames = new ArrayList<>();
        InfraInterfaceModule module = infraInterfaceModuleRepository.findById(id).get();
        if(!Objects.equals(module.parentId(), "")) {
            parentNames.add(module.name());
            parentNames.addAll(getParentName(UUID.fromString(module.parentId())));
        }
        return parentNames;
    }

    public void generateModule(Map<String, Object> bindingMap)
    {
        Map<String, String> templates = new LinkedHashMap<>(MODULE_TEMPLATES);
        templates.forEach((vmPath, filePath) -> {
            filePath = formatModuleFilePath(filePath, bindingMap);
            File newFile;
            if(!FileUtil.exist(filePath)) {
                newFile = FileUtil.touch(filePath);
                RuntimeUtil.execForStr("git add " + filePath);
            }else{
                newFile = FileUtil.file(filePath);
            }

            String content = "";
            if(!vmPath.isEmpty()) {
                content = templateEngine.getTemplate(vmPath).render(bindingMap);
                // 去除字段后面多余的 , 逗号
                content = content.replaceAll(",\n}", "\n}").replaceAll(",\n  }", "\n  }");

                FileUtil.writeUtf8String(content, newFile);
            }
        });
    }

    private String formatModuleFilePath(String filePath, Map<String, Object> bindingMap) {
        filePath = StrUtil.replace(filePath, "${basePackage}",
                getStr(bindingMap, "basePackage").replaceAll("\\.", "/"));
        // sceneEnum 包含的字段
        CodegenSceneEnum sceneEnum = (CodegenSceneEnum) bindingMap.get("sceneEnum");
        filePath = StrUtil.replace(filePath, "${sceneEnum.prefixClass}", sceneEnum.getPrefixClass());
        filePath = StrUtil.replace(filePath, "${sceneEnum.basePackage}", sceneEnum.getBasePackage());
        filePath = StrUtil.replace(filePath, "${nameHump}", getStr(bindingMap, "nameHump"));
        filePath = StrUtil.replace(filePath, "${nameHumpUp}", getStr(bindingMap, "nameHumpUp"));
        filePath = StrUtil.replace(filePath, "${modulePath}", getStr(bindingMap, "modulePath"));
        InfraInterfaceModule module = (InfraInterfaceModule) bindingMap.get("module");
        filePath = StrUtil.replace(filePath, "${module.name}", module.name());

        return filePath;
    }

    public void generateInsertTable(Map<String, Object> bindingMap)
    {
        Map<String, String> templates = new LinkedHashMap<>(TABLE_INSERT_TEMPLATES);
        templates.forEach((vmPath, filePath) -> {
            filePath = formatTableFilePath(filePath, bindingMap);
            String content = templateEngine.getTemplate(vmPath).render(bindingMap);
            // 去除字段后面多余的 , 逗号
            content = content.replaceAll(",\n}", "\n}").replaceAll(",\n  }", "\n  }");
            File newFile;
            if(!FileUtil.exist(filePath)) {
                newFile = FileUtil.touch(filePath);
                RuntimeUtil.execForStr("git add " + filePath);
            }else{
                newFile = FileUtil.file(filePath);
            }
            FileUtil.writeUtf8String(content, newFile);
        });
    }

    public void tableInsertExecute(InfraDatabaseTable table){
        // 创建 bindingMap
        Map<String, Object> bindingMap = getTableBindingMap(table);

        generateInsertTable(bindingMap);

        generateNewTable(bindingMap);
    }

    public void generateNewTable(Map<String, Object> bindingMap){
        // 生成table
        String vmPath = "codegen/sql/newTable.vm";
        String filePath = tableFilePath();
        String content = templateEngine.getTemplate(vmPath).render(bindingMap);
        File newFile;
        if(!FileUtil.exist(filePath)) {
            newFile = FileUtil.touch(filePath);
            RuntimeUtil.execForStr("git add " + filePath);
        }else{
            newFile = FileUtil.file(filePath);
        }
        FileUtil.appendUtf8String(content, newFile);
        // 生成repository
        vmPath = javaTemplatePath("repository/repository");
        filePath = javaTableFilePath("repository","${classNameHump}Repository");
        filePath = formatTableFilePath(filePath, bindingMap);
        content = templateEngine.getTemplate(vmPath).render(bindingMap);
        // 去除字段后面多余的 , 逗号
        content = content.replaceAll(",\n}", "\n}").replaceAll(",\n  }", "\n  }");
        if(!FileUtil.exist(filePath)) {
            newFile = FileUtil.touch(filePath);
            RuntimeUtil.execForStr("git add " + filePath);
            FileUtil.writeUtf8String(content, newFile);
        }
    }

    public void generateUpdateTable(Map<String, Object> bindingMap)
    {
        Map<String, String> templates = new LinkedHashMap<>(TABLE_UPDATE_TEMPLATES);
        templates.forEach((vmPath, filePath) -> {
            filePath = formatTableFilePath(filePath, bindingMap);
            String content = templateEngine.getTemplate(vmPath).render(bindingMap);
            // 去除字段后面多余的 , 逗号
            content = content.replaceAll(",\n}", "\n}").replaceAll(",\n  }", "\n  }");
            File newFile;
            if(!FileUtil.exist(filePath)) {
                newFile = FileUtil.touch(filePath);
                RuntimeUtil.execForStr("git add " + filePath);
            }else{
                newFile = FileUtil.file(filePath);
            }
            FileUtil.writeUtf8String(content, newFile);
        });
    }

    public void tableUpdateExecute(InfraDatabaseTable table, DatabaseUpdateReq reqVo, String updateSql){
        Map<String, Object> bindingMap = getTableBindingMap(table);
        generateUpdateTable(bindingMap);
        generateUpdateTable(updateSql);
        if (!Objects.equals(table.name(), reqVo.getName())){
            deleteOldTableFile(reqVo);
        }
    }

    public void generateUpdateTable(String updateSql){
        if (Objects.equals(updateSql, ""))
            return;
        String filePath = tableFilePath();
        File newFile;
        if(!FileUtil.exist(filePath)) {
            newFile = FileUtil.touch(filePath);
            RuntimeUtil.execForStr("git add " + filePath);
        }else{
            newFile = FileUtil.file(filePath);
        }
         FileUtil.appendUtf8String(updateSql, newFile);

    }

    private void deleteOldTableFile(DatabaseUpdateReq reqVo){

    }

    private Map<String, Object> getTableBindingMap(InfraDatabaseTable table){
        Map<String, Object> bindingMap = new HashMap<>(globalBindingMap);
        List<CodegenDatabaseColumn> codegenColumns = CodegenConvert.INSTANCE.convertList09(table.columns());
        for(CodegenDatabaseColumn column : codegenColumns)
        {
            if(!column.getRelatedTable().isEmpty()) {
                Optional<InfraDatabaseTable> opTable = infraDatabaseTableRepository.findByName(column.getRelatedTable());
                column.setHumpRelatedTable(upperFirst(toCamelCase(column.getRelatedTable())))
                        .setRelatedTableModuleName(opTable.get().businessName());
            }
            column.setHumpName(toCamelCase(column.getColumnName()));
        }
        List<CodegenDatabaseMapping> codegenMappings = CodegenConvert.INSTANCE.convertList16(table.mappings());
        for(CodegenDatabaseMapping mapping : codegenMappings)
        {
            mapping.setHumpMappingTable(upperFirst(toCamelCase(mapping.getMappingTable())));
        }
        bindingMap.put("table", table);
        bindingMap.put("columns", codegenColumns);
        bindingMap.put("indexes", table.indexes());
        bindingMap.put("mappings", codegenMappings);
        bindingMap.put("sceneEnum", CodegenSceneEnum.valueOf("ADMIN"));
        // 模块名称 例子system
        String moduleName = table.name().substring(0, table.name().indexOf("_"));
        bindingMap.put("moduleName", moduleName);
        // 简称类名 下划线命名 例子config_setting
        String simpleClassName = removePrefix(table.name(), moduleName+ "_");
        bindingMap.put("simpleClassName", simpleClassName );
        // 简称类名 驼峰命名 例子ConfigSetting
        bindingMap.put("simpleClassNameHump", upperFirst(toCamelCase(simpleClassName)));

        // 简称类名 驼峰命名 首字母小写 例子configSetting
        bindingMap.put("simpleLowerClassNameHump", toCamelCase(simpleClassName));

        // 全程类名 驼峰命名 例子SystemConfigSetting
        bindingMap.put("classNameHump", upperFirst(toCamelCase(table.name())));
        return bindingMap;
    }

/*    private Map<String, String> getTemplates(Integer frontType) {
        Map<String, String> templates = new LinkedHashMap<>();
        templates.putAll(SERVER_TEMPLATES);
        templates.putAll(FRONT_TEMPLATES.row(frontType));
        return templates;
    }*/

    private String formatTableFilePath(String filePath, Map<String, Object> bindingMap) {
        filePath = StrUtil.replace(filePath, "${basePackage}",
                getStr(bindingMap, "basePackage").replaceAll("\\.", "/"));
        filePath = StrUtil.replace(filePath, "${classNameVar}",
                getStr(bindingMap, "classNameVar"));
        filePath = StrUtil.replace(filePath, "${simpleClassName}",
                getStr(bindingMap, "simpleClassName"));
        // sceneEnum 包含的字段
        CodegenSceneEnum sceneEnum = (CodegenSceneEnum) bindingMap.get("sceneEnum");
        filePath = StrUtil.replace(filePath, "${sceneEnum.prefixClass}", sceneEnum.getPrefixClass());
        filePath = StrUtil.replace(filePath, "${sceneEnum.basePackage}", sceneEnum.getBasePackage());
        // table 包含的字段
        InfraDatabaseTable table = (InfraDatabaseTable) bindingMap.get("table");
        filePath = StrUtil.replace(filePath, "${moduleName}", getStr(bindingMap, "moduleName"));
        filePath = StrUtil.replace(filePath, "${table.businessName}", table.businessName());
        filePath = StrUtil.replace(filePath, "${simpleClassNameHump}", getStr(bindingMap, "simpleClassNameHump"));
        filePath = StrUtil.replace(filePath, "${simpleLowerClassNameHump}", getStr(bindingMap, "simpleLowerClassNameHump"));
        filePath = StrUtil.replace(filePath, "${classNameHump}", getStr(bindingMap, "classNameHump"));
        return filePath;
    }

   /* private String formatFilePath(String filePath, Map<String, Object> bindingMap) {
        filePath = StrUtil.replace(filePath, "${basePackage}",
                getStr(bindingMap, "basePackage").replaceAll("\\.", "/"));
        filePath = StrUtil.replace(filePath, "${classNameVar}",
                getStr(bindingMap, "classNameVar"));
        filePath = StrUtil.replace(filePath, "${simpleClassName}",
                getStr(bindingMap, "simpleClassName"));
        // sceneEnum 包含的字段
        CodegenSceneEnum sceneEnum = (CodegenSceneEnum) bindingMap.get("sceneEnum");
        filePath = StrUtil.replace(filePath, "${sceneEnum.prefixClass}", sceneEnum.getPrefixClass());
        filePath = StrUtil.replace(filePath, "${sceneEnum.basePackage}", sceneEnum.getBasePackage());
        // table 包含的字段
        InfraCodegenTable table = (InfraCodegenTable) bindingMap.get("table");
        filePath = StrUtil.replace(filePath, "${table.moduleName}", getStr(bindingMap, "moduleName"));
        filePath = StrUtil.replace(filePath, "${table.businessName}", table.businessName());
        filePath = StrUtil.replace(filePath, "${simpleClassNameHump}", getStr(bindingMap, "simpleClassNameHump"));
        filePath = StrUtil.replace(filePath, "${simpleLowerClassNameHump}", getStr(bindingMap, "simpleLowerClassNameHump"));
        filePath = StrUtil.replace(filePath, "${classNameHump}", getStr(bindingMap, "classNameHump"));
        return filePath;
    }*/

    private String tableFilePath(){
        String curDate = DateUtil.format(LocalDateTime.now(), "yyyyMMdd");
        String curMouth = DateUtil.format(LocalDateTime.now(), "yyyyMM");
        String curDay = DateUtil.format(LocalDateTime.now(), "dd");
        String filePath = FileUtil.getAbsolutePath("db/migration").replace("target/classes", "src/main/resources") + "/" + curMouth  + "/" + curDay;
        Integer curGitUserVersion = schemaHistory.getCurGitUserVersion();
        Integer curGitUserId = schemaHistory.getCurGitUserId();
        filePath = filePath + "/" + "V" + curDate + "_" + curGitUserId + "_" + String.format("%3d", curGitUserVersion + 1).replace(" ", "0") + ".sql";
        return filePath;
    }

    private static String javaTemplatePath(String path) {
        return "codegen/java/" + path + ".vm";
    }

    private static String javaBaseVOFilePath(String path) {
        return javaFilePath("vo/${moduleName}/${table.businessName}/${sceneEnum.prefixClass}baseVO/${sceneEnum.prefixClass}${simpleClassNameHump}" + path) + ".java";
    }

    private static String javaControllerFilePath() {
        return javaFilePath("controller/${sceneEnum.basePackage}/${modulePath}/${nameHumpUp}Controller")+ ".java";
    }

    private static String javaTableFilePath(String path, String file) {
        return javaFilePath(path) +  "/${moduleName}/${table.businessName}/" + file + ".java";
    }

    private static String javaModuleFilePath(String path, String file) {
        return javaFilePath(path) +  "/${modulePath}/" + file + ".java";
    }

/*    private static String javaModuleApiMainFilePath() {
        return javaFilePath("enums/ErrorCodeConstants_手动操作", "api", "main");
    }

    private static String javaModuleImplTestFilePath() {
        return javaFilePath("service/${moduleName}/${table.businessName}/${simpleClassNameHump}ServiceImplTest", "biz", "test");
    }*/

    private static String javaFilePath(String path) {
        return FileUtil.getParent(FileUtil.getAbsolutePath(""), 3) + "/yudao-service/yudao-service-biz/src/main/java/${basePackage}/service/" + path;
    }

/*    private static String vueTemplatePath(String path) {
        return "codegen/vue/" + path + ".vm";
    }

    private static String vueFilePath(String path) {
        return "yudao-ui-${sceneEnum.basePackage}/" + // 顶级目录
                "src/" + path;
    }

    private static String vue3TemplatePath(String path) {
        return "codegen/vue3/" + path + ".vm";
    }

    private static String vue3FilePath(String path) {
        return "yudao-ui-${sceneEnum.basePackage}-vue3/" + // 顶级目录
                "src/" + path;
    }

    private static String vue3SchemaTemplatePath(String path) {
        return "codegen/vue3_schema/" + path + ".vm";
    }

    private static String vue3VbenTemplatePath(String path) {
        return "codegen/vue3_vben/" + path + ".vm";
    }*/
}

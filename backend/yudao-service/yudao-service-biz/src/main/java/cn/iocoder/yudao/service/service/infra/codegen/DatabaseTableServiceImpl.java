package cn.iocoder.yudao.service.service.infra.codegen;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.util.entity.EntityUtils;
import cn.iocoder.yudao.service.convert.infra.codegen.CodegenConvert;
import cn.iocoder.yudao.service.model.infra.codegen.*;
import cn.iocoder.yudao.service.model.infra.db.InfraDataSourceConfig;
import cn.iocoder.yudao.service.repository.infra.codegen.*;
import cn.iocoder.yudao.service.service.infra.codegen.inner.CodegenEngine;
import cn.iocoder.yudao.service.service.infra.db.DataSourceConfigService;
import cn.iocoder.yudao.service.vo.infra.codegen.database.*;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.text.CharSequenceUtil.removePrefix;
import static cn.hutool.core.text.CharSequenceUtil.upperFirst;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.*;
@Service
public class DatabaseTableServiceImpl implements DatabaseTableService {
    @Resource
    private DataSourceConfigService dataSourceConfigService;

    @Resource
    private InfraDatabaseTableRepository infraDatabaseTableRepository;

    @Resource
    private InfraDatabaseColumnRepository infraDatabaseColumnRepository;

    @Resource
    private InfraDatabaseIndexRepository infraDatabaseIndexRepository;

    @Resource
    private InfraInterfaceValidationRepository infraInterfaceValidationRepository;

    @Resource
    private InfraInterfaceVoClassRepository infraInterfaceVoClassRepository;

    @Resource
    private InfraDatabaseMappingRepository infraDatabaseMappingRepository;

    @Resource
    private CodegenEngine codegenEngine;

    @Override
    public List<DatabaseTableResp> getDatabaseTableList(DatabaseTableListReqVO list) {
        List<InfraDatabaseTable> tables = infraDatabaseTableRepository.selectList(list);
        return CodegenConvert.INSTANCE.convertList05(tables);
    }

    @Override
    public List<DatabaseTableColumnResp> getColumnList(DatabaseTableListReqVO list) {
        List<InfraDatabaseTable> tables = infraDatabaseTableRepository.selectColumnList(list);
        return CodegenConvert.INSTANCE.convertList13(tables);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID createTable(DatabaseUpdateReq reqVo) {
        Optional<InfraDatabaseTable> opExistsTable = infraDatabaseTableRepository.findByName(reqVo.getName());
        if (opExistsTable.isPresent())
            throw exception(CODEGEN_DATABASE_TABLE_EXISTS);
        for(DatabaseUpdateReq.Column column : reqVo.getColumns()){
            if(!column.getValidations().isEmpty()){
                List<InfraInterfaceValidation> validations = CodegenConvert.INSTANCE.convertList17(column.getValidations());
                infraInterfaceValidationRepository.saveAll(validations);
            }
            column.setValidations(Collections.emptyList());
        }
        InfraDatabaseTable newDatabaseTable = CodegenConvert.INSTANCE.convert(reqVo);

        newDatabaseTable = infraDatabaseTableRepository.insert(newDatabaseTable);
        InfraDatabaseTable finalNewDatabaseTable = newDatabaseTable;
        InfraInterfaceVoClass newParamClass = InfraInterfaceVoClassDraft.$.produce(draft -> {
            String moduleName = reqVo.getName().substring(0, reqVo.getName().indexOf("_"));
            String simpleClassName = removePrefix(reqVo.getName(), moduleName+ "_");
            draft.setName(upperFirst(StrUtil.toCamelCase(simpleClassName)) + "Base")
                    .setParentId(finalNewDatabaseTable.id().toString())
                    .setComment(reqVo.getComment());
        });
        infraInterfaceVoClassRepository.insert(newParamClass);
        // 重新获取完整的table信息
        newDatabaseTable = infraDatabaseTableRepository.findDetailById(newDatabaseTable.id()).get();
        codegenEngine.tableInsertExecute(newDatabaseTable);
        return newDatabaseTable.id();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID updateTable(DatabaseUpdateReq reqVo){
        Optional<InfraDatabaseTable> tableOptional = infraDatabaseTableRepository.findById(reqVo.getId());
        if (!tableOptional.isPresent())
            throw exception(CODEGEN_DATABASE_TABLE_NOT_EXISTS);
        List<DatabaseUpdateReq.Column> reqVoColumnList = reqVo.getColumns();
        StringBuilder updateSql = new StringBuilder("ALTER TABLE " + tableOptional.get().name() + "\n");

        // 更新column
        for(DatabaseUpdateReq.Column reqVoColumn : reqVoColumnList){
            if(Objects.equals(reqVoColumn.getOperateType(), "delete")){
                updateSql.append(deleteColumn(reqVoColumn));
            }else if(Objects.equals(reqVoColumn.getOperateType(), "new")){
                updateSql.append(newColumn(reqVoColumn));
            } else {
                updateSql.append(updateColumn(reqVoColumn));
            }
        }
        // 更新index
        List<DatabaseUpdateReq.Index> reqVoIndexList = reqVo.getIndexes();
        for(DatabaseUpdateReq.Index reqVoIndex : reqVoIndexList){
            if(Objects.equals(reqVoIndex.getOperateType(), "delete")){
                updateSql.append(deleteIndex(reqVoIndex));
            }else if(Objects.equals(reqVoIndex.getOperateType(), "new")){
                updateSql.append(newIndex(reqVoIndex, reqVo.getId()));
            }else{
                updateSql.append(updateIndex(reqVoIndex, reqVo.getId()));
            }
        }

        List<DatabaseUpdateReq.mapping> reqVoMappingList = reqVo.getMappings();
        for(DatabaseUpdateReq.mapping reqVoMapping : reqVoMappingList){
            if(Objects.equals(reqVoMapping.getOperateType(), "delete")){
                infraDatabaseMappingRepository.deleteById(reqVoMapping.getId());
            }else if(Objects.equals(reqVoMapping.getOperateType(), "new")){
                InfraDatabaseMapping newMapping = CodegenConvert.INSTANCE.convert(reqVoMapping);
                newMapping = InfraDatabaseMappingDraft.$.produce(newMapping, draft -> {
                    draft.setTableId(reqVo.getId());
                });
                infraDatabaseMappingRepository.insert(newMapping);
            }else{
                InfraDatabaseMapping updateMapping = CodegenConvert.INSTANCE.convert(reqVoMapping);
                updateMapping = InfraDatabaseMappingDraft.$.produce(updateMapping, draft -> {
                    draft.setTableId(reqVo.getId());
                });
                Optional<InfraDatabaseMapping> opOldMapping = infraDatabaseMappingRepository.findById(reqVoMapping.getId());
                if (!opOldMapping.isPresent())
                    throw exception(CODEGEN_DATABASE_MAPPING_NOT_EXITS);
                if (!EntityUtils.isEquals(opOldMapping.get(), updateMapping))
                    infraDatabaseMappingRepository.update(updateMapping);
            }
        }

        // 更新table
        if(!Objects.equals(tableOptional.get().name(), reqVo.getName())
                || !Objects.equals(tableOptional.get().businessName(), reqVo.getBusinessName())
                || !Objects.equals(tableOptional.get().remark(), reqVo.getRemark())
                || !Objects.equals(tableOptional.get().comment(), reqVo.getComment())){
            InfraDatabaseTable updateDatabaseTable = InfraDatabaseTableDraft.$.produce(draft -> {
                draft.setName(reqVo.getName()).setBusinessName(reqVo.getBusinessName()).setComment(reqVo.getComment()).setRemark(reqVo.getRemark()).setId(reqVo.getId());
            });
            infraDatabaseTableRepository.updateById(reqVo.getId(), updateDatabaseTable);
        }
        // 将最后一个，改成；
        if(updateSql.lastIndexOf(",") > 0)
            updateSql.replace(updateSql.lastIndexOf(","), updateSql.lastIndexOf(",") + 1, ";") ;
        else
            updateSql = new StringBuilder("");
        InfraDatabaseTable updateDatabaseTable = infraDatabaseTableRepository.findDetailById(reqVo.getId()).get();
        codegenEngine.tableUpdateExecute(updateDatabaseTable, reqVo, updateSql.toString());
        return tableOptional.get().id();
    }

    private String deleteColumn(DatabaseUpdateReq.Column reqVoColumn){
        // 删除校验
        List<InfraInterfaceValidation> validationList = infraInterfaceValidationRepository.findByParentId(reqVoColumn.getId());
        for(InfraInterfaceValidation validation : validationList)
            infraInterfaceValidationRepository.deleteById(validation.id());
        // 删除字段
        infraDatabaseColumnRepository.deleteById(reqVoColumn.getId());
        return "DROP COLUMN `" + reqVoColumn.getColumnName() + "`,\n";
    }

    private String newColumn(DatabaseUpdateReq.Column reqVoColumn){
        InfraDatabaseColumn newColumn = CodegenConvert.INSTANCE.convert(reqVoColumn);
        List<InfraInterfaceValidation> validations = CodegenConvert.INSTANCE.convertList17(reqVoColumn.getValidations());
        infraInterfaceValidationRepository.saveAll(validations);
        reqVoColumn.setValidations(Collections.emptyList());
        infraDatabaseColumnRepository.insert(newColumn);
        return "ADD COLUMN `" + newColumn.columnName() + "`"
                + " " + newColumn.dataType()
                + " " + (newColumn.nullable() ? "": "NOT NULL")
                + " " + (Objects.equals(newColumn.defaultValue(), "") ? "" : ("DEFAULT " + newColumn.defaultValue()))
                + " " + (Objects.equals(newColumn.columnComment(), "") ? "" : ("COMMENT '" + newColumn.columnComment() + "'"))
                + ",\n";
    }

    private String updateColumn(DatabaseUpdateReq.Column reqVoColumn){
        if (Objects.equals(reqVoColumn.getColumnName(), "create_time")
                || Objects.equals(reqVoColumn.getColumnName(), "update_time") || Objects.equals(reqVoColumn.getColumnName(), "creator_id")
                || Objects.equals(reqVoColumn.getColumnName(), "updater_id") || Objects.equals(reqVoColumn.getColumnName(), "deleted"))
            return "";
        if(reqVoColumn.getValidations() != null) {
            for(DatabaseUpdateReq.Validation validation : reqVoColumn.getValidations())
            {
                if (Objects.equals(validation.getOperateType(), "new")) {
                    InfraInterfaceValidation newValidation = CodegenConvert.INSTANCE.convert(validation);
                    newValidation = InfraInterfaceValidationDraft.$.produce(newValidation, draft -> {
                        draft.setParentId(reqVoColumn.getId());
                    });
                    infraInterfaceValidationRepository.insert(newValidation);
                }
                else if (Objects.equals(validation.getOperateType(), "delete")) {
                    infraInterfaceValidationRepository.deleteById(validation.getId());

                }else {
                    InfraInterfaceValidation oldValidation = infraInterfaceValidationRepository.findById(validation.getId()).get();
                    if(!Objects.equals(oldValidation.validation(), validation.getValidation())
                            || !Objects.equals(oldValidation.validationCondition(), validation.getValidationCondition())
                            || !Objects.equals(oldValidation.message(), validation.getMessage())) {
                        InfraInterfaceValidation updateValidation = CodegenConvert.INSTANCE.convert(validation);
                        updateValidation = InfraInterfaceValidationDraft.$.produce(updateValidation, draft -> {
                            draft.setParentId(reqVoColumn.getId());
                        });
                        infraInterfaceValidationRepository.update(updateValidation);
                    }
                }
            }
            reqVoColumn.setValidations(Collections.emptyList());
        }

        boolean isColumnChange = false;
        InfraDatabaseColumn updateColumn = CodegenConvert.INSTANCE.convert(reqVoColumn);
        InfraDatabaseColumn oldColumn = infraDatabaseColumnRepository.findById(reqVoColumn.getId()).get();
        if(!Objects.equals(oldColumn.columnName(), reqVoColumn.getColumnName())
                ||!Objects.equals(oldColumn.columnComment(), reqVoColumn.getColumnComment())
                ||!Objects.equals(oldColumn.dataType(), reqVoColumn.getDataType())
                ||!Objects.equals(oldColumn.defaultValue(), reqVoColumn.getDefaultValue())
                ||!Objects.equals(oldColumn.nullable(), reqVoColumn.getNullable())
                ||!Objects.equals(oldColumn.dictType(), reqVoColumn.getDictType())
                ||!Objects.equals(oldColumn.example(), reqVoColumn.getExample())
                ||!Objects.equals(oldColumn.javaType(), reqVoColumn.getJavaType())
                ||!Objects.equals(oldColumn.required(), reqVoColumn.getRequired()
                ||!Objects.equals(oldColumn.relatedTable(), reqVoColumn.getRelatedTable()))

        ) {
            isColumnChange = true;
            infraDatabaseColumnRepository.updateColumn(updateColumn);
        }


        if (isColumnChange)
            return "CHANGE `" + oldColumn.columnName() + "`"
                    + " " + "`" + updateColumn.columnName() + "`"
                    + " " + updateColumn.dataType()
                    + " " + (updateColumn.nullable() ? "": "NOT NULL")
                    + " " + (Objects.equals(updateColumn.defaultValue(), "") ? "" : ("DEFAULT " + updateColumn.defaultValue()))
                    + " " + (Objects.equals(updateColumn.columnComment(), "") ? "" : ("COMMENT '" + updateColumn.columnComment() + "'"))
                    + ",\n";
        else
            return "";
    }

    private String deleteIndex(DatabaseUpdateReq.Index reqVoIndex){
        infraDatabaseIndexRepository.deleteById(reqVoIndex.getId());
        return "DROP INDEX `" + reqVoIndex.getIndexName() + "`,\n";
    }

    private String newIndex(DatabaseUpdateReq.Index reqVoIndex, UUID tableId){
        InfraDatabaseIndex newIndex = CodegenConvert.INSTANCE.convert(reqVoIndex);
        newIndex = InfraDatabaseIndexDraft.$.produce(newIndex, draft -> {
            draft.setTableId(tableId);
        });
        if (isExistIllegalColumn(tableId, reqVoIndex))
            return "";

        infraDatabaseIndexRepository.insert(newIndex);
        return "ADD " + reqVoIndex.getIndexType()
                + " " +" `" + reqVoIndex.getIndexName() + "`"
                + " " + "(" + reqVoIndex.getColumnNames().stream().map((name) -> "`" + name + "`").collect(Collectors.joining(","))+ ")"
                + ",\n";
    }

    private boolean isExistIllegalColumn(UUID tableId, DatabaseUpdateReq.Index reqVoIndex){
        int existsCount = infraDatabaseColumnRepository.countByTableIdAndColumnNameIn(tableId, reqVoIndex.getColumnNames());
        return existsCount != reqVoIndex.getColumnNames().size();
    }

    private String updateIndex(DatabaseUpdateReq.Index reqVoIndex, UUID tableId){
        InfraDatabaseIndex oldIndex = infraDatabaseIndexRepository.findById(reqVoIndex.getId()).get();
        if (Objects.equals(oldIndex.indexName(), reqVoIndex.getIndexName())
                && Objects.equals(oldIndex.indexType(), reqVoIndex.getIndexType())
                && Objects.equals(oldIndex.columnNames().toString(), reqVoIndex.getColumnNames().toString()))
        {
            return "";
        }
        if (isExistIllegalColumn(tableId, reqVoIndex))
            return "";
        InfraDatabaseIndex updateIndex = CodegenConvert.INSTANCE.convert(reqVoIndex);
        updateIndex = InfraDatabaseIndexDraft.$.produce(updateIndex, draft -> {
            draft.setTableId(tableId);
        });
        infraDatabaseIndexRepository.update(updateIndex);
        return "DROP INDEX `" + oldIndex.indexName() + "`,\n"
                + "ADD " + reqVoIndex.getIndexType()
                + " " +" `" + reqVoIndex.getIndexName() + "`"
                + " " + "(" + reqVoIndex.getColumnNames().stream().map((name) -> "`" + name + "`").collect(Collectors.joining(","))+ ")"
                + ",\n";
    }


    @Override
    public List<TableInfo> getTableList(Long dataSourceConfigId, String nameLike, String commentLike) {
        List<TableInfo> tables = getTableList0(dataSourceConfigId, null);
        return tables.stream().filter(tableInfo -> (StrUtil.isEmpty(nameLike) || tableInfo.getName().contains(nameLike))
                        && (StrUtil.isEmpty(commentLike) || tableInfo.getComment().contains(commentLike)))
                .collect(Collectors.toList());
    }

    @Override
    public DatabaseTableDetailResp getDatabaseTable(UUID tableId){
        Optional<InfraDatabaseTable> infraDatabaseTableOptional = infraDatabaseTableRepository.findDetailById(tableId);
        DatabaseTableDetailResp detailRespVo = new DatabaseTableDetailResp();
        if(infraDatabaseTableOptional.isPresent()) {
            detailRespVo = CodegenConvert.INSTANCE.convert(infraDatabaseTableOptional.get());
        }
        return detailRespVo;
    }

    @Override
    public TableInfo getTable(Long dataSourceConfigId, String name) {
        return CollUtil.getFirst(getTableList0(dataSourceConfigId, name));
    }

    private List<TableInfo> getTableList0(Long dataSourceConfigId, String name) {
        // 获得数据源配置
        InfraDataSourceConfig config = dataSourceConfigService.getDataSourceConfig(dataSourceConfigId);
        Assert.notNull(config, "数据源({}) 不存在！", dataSourceConfigId);

        // 使用 MyBatis Plus Generator 解析表结构
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(config.url(), config.username(),
                config.password()).build();
        StrategyConfig.Builder strategyConfig = new StrategyConfig.Builder();
        if (StrUtil.isNotEmpty(name)) {
            strategyConfig.addInclude(name);
        } else {
            // 移除工作流和定时任务前缀的表名 // TODO 未来做成可配置
            strategyConfig.addExclude("ACT_[\\S\\s]+|QRTZ_[\\S\\s]+|FLW_[\\S\\s]+");
        }

        GlobalConfig globalConfig = new GlobalConfig.Builder().dateType(DateType.TIME_PACK).build(); // 只使用 Date 类型，不使用 LocalDate
        ConfigBuilder builder = new ConfigBuilder(null, dataSourceConfig, strategyConfig.build(),
                null, globalConfig, null);
        // 按照名字排序
        List<TableInfo> tables = builder.getTableInfoList();
        tables.sort(Comparator.comparing(TableInfo::getName));
        return tables;
    }
}

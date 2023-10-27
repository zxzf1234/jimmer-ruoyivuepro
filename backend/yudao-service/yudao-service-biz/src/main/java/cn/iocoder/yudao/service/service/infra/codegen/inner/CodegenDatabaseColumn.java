package cn.iocoder.yudao.service.service.infra.codegen.inner;

import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.InterfaceValidationBase;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CodegenDatabaseColumn {
    private String columnName;

    private String dataType;

    private String columnComment;

    private Boolean nullable;

    private String defaultValue;

    private String javaType;

    private String dictType;

    private String example;

    private String listOperationCondition;

    private String relatedTable;

    private String htmlType;

    private Boolean required;

    private String humpName;

    private String humpRelatedTable;

    private String relatedTableModuleName;

    private List<Validation> validations;

    @Data
    public static class Validation extends InterfaceValidationBase {
        private UUID id;
    }
}

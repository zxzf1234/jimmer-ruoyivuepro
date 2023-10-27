package cn.iocoder.yudao.service.service.infra.codegen.inner;

import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.InterfaceValidationBase;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CodegenInterfaceParam {

    private String name;

    private String humpName;

    private String comment;

    private Boolean isList;

    private String variableType;

    private String relatedId;

    private Integer relatedType;

    private String example;

    private Boolean required;

    private UUID parentId;

    private Integer parentType;

    private Integer inoutType;

    private List<Validation> validations;

    @Data
    public static class Validation extends InterfaceValidationBase {
        private UUID id;
    }
}

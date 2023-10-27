package cn.iocoder.yudao.service.framework.codegen.config;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Repository
@Data
public class SchemaHistory {
    private Integer curGitUserVersion;
    private Integer curGitUserId;
}

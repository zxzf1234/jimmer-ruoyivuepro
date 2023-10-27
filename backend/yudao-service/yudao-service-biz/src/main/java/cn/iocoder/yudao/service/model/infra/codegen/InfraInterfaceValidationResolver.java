package cn.iocoder.yudao.service.model.infra.codegen;

import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceValidation;
import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceValidationTable;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.TransientResolver;
import org.babyfish.jimmer.sql.ast.tuple.Tuple2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class InfraInterfaceValidationResolver implements TransientResolver<UUID, List<UUID>> {
    private final JSqlClient sqlClient;

    // 构造注入
    public InfraInterfaceValidationResolver(JSqlClient sqlClient) {
        this.sqlClient = sqlClient;
    }
    @Override
    public Map<UUID, List<UUID>> resolve(Collection<UUID> ids) {
        return Tuple2.toMultiMap(sqlClient
                .createQuery(InfraInterfaceValidationTable.$)
                        .where(InfraInterfaceValidationTable.$.parentId().in(ids))
                        .select(InfraInterfaceValidationTable.$.parentId(), InfraInterfaceValidationTable.$.id())
                        .execute());
    }


    @Override
    public List<UUID> getDefaultValue() {
       return Collections.emptyList();
    }
}

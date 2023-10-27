package cn.iocoder.yudao.service.model.infra.codegen;

import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceParamTable;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.TransientResolver;
import org.babyfish.jimmer.sql.ast.tuple.Tuple2;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InfraInterfaceInputParamResolver implements TransientResolver<UUID, List<UUID>> {
    private final JSqlClient sqlClient;

    // 构造注入
    public InfraInterfaceInputParamResolver(JSqlClient sqlClient) {
        this.sqlClient = sqlClient;
    }
    @Override
    public Map<UUID, List<UUID>> resolve(Collection<UUID> ids) {
        return Tuple2.toMultiMap(sqlClient
                .createQuery(InfraInterfaceParamTable.$)
                .where(InfraInterfaceParamTable.$.parentId().in(ids))
                .where(InfraInterfaceParamTable.$.inoutType().eq(0))
                .where(InfraInterfaceParamTable.$.parentType().eq(0))
                .select(InfraInterfaceParamTable.$.parentId(), InfraInterfaceParamTable.$.id())
                .execute());
    }


    @Override
    public List<UUID> getDefaultValue() {
        return Collections.emptyList();
    }
}

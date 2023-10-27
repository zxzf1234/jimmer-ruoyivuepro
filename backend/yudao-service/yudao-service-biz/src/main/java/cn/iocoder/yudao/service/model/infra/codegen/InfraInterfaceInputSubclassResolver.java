package cn.iocoder.yudao.service.model.infra.codegen;

import org.apache.ibatis.jdbc.SQL;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.TransientResolver;
import org.babyfish.jimmer.sql.ast.tuple.Tuple2;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InfraInterfaceInputSubclassResolver implements TransientResolver<UUID, List<UUID>> {
    private final JSqlClient sqlClient;

    // 构造注入
    public InfraInterfaceInputSubclassResolver(JSqlClient sqlClient) {
        this.sqlClient = sqlClient;
    }

    @Override
    public Map<UUID, List<UUID>> resolve(Collection<UUID> ids) {
        return Tuple2.toMultiMap(sqlClient
                .createQuery(InfraInterfaceSubclassTable.$)
                .where(InfraInterfaceSubclassTable.$.parentId().in(ids))
                .where(InfraInterfaceSubclassTable.$.type().eq(0))
                .select(InfraInterfaceSubclassTable.$.parentId(), InfraInterfaceSubclassTable.$.id())
                .execute()
        );
    }

    @Override
    public List<UUID> getDefaultValue() {
        return Collections.emptyList();
    }
}

package cn.iocoder.yudao.service.repository.infra.file;

import cn.iocoder.yudao.service.vo.infra.file.config.FileConfigPageReqVO;
import cn.iocoder.yudao.service.model.infra.file.InfraFileConfig;
import cn.iocoder.yudao.service.model.infra.file.InfraFileConfigTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

public interface InfraFileConfigRepository extends JRepository<InfraFileConfig, Long> {
    InfraFileConfigTable infraFileConfigTable = InfraFileConfigTable.$;

    default Page<InfraFileConfig> selectPage(FileConfigPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql().createQuery(infraFileConfigTable)
                        .whereIf(reqVO.getStorage() != null, infraFileConfigTable.storage().eq(reqVO.getStorage()))
                        .whereIf(StringUtils.hasText(reqVO.getName()), infraFileConfigTable.name().eq(reqVO.getName()))
                        .whereIf(reqVO.getCreateTime() != null, ()-> infraFileConfigTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(infraFileConfigTable)
        );
    }
}

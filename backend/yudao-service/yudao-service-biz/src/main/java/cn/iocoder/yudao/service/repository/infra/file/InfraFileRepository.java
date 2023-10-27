package cn.iocoder.yudao.service.repository.infra.file;

import cn.iocoder.yudao.service.vo.infra.file.file.FilePageReqVO;
import cn.iocoder.yudao.service.model.infra.file.InfraFile;
import cn.iocoder.yudao.service.model.infra.file.InfraFileTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

public interface InfraFileRepository extends JRepository<InfraFile, Long> {
    InfraFileTable infraFileTable = InfraFileTable.$;

    default Page<InfraFile> selectPage(FilePageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql().createQuery(infraFileTable)
                        .whereIf(StringUtils.hasText(reqVO.getPath()), infraFileTable.path().eq(reqVO.getPath()))
                        .whereIf(StringUtils.hasText(reqVO.getType()), infraFileTable.type().eq(reqVO.getType()))
                        .whereIf(reqVO.getCreateTime() != null, ()-> infraFileTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(infraFileTable)
        );
    }
}

package cn.iocoder.yudao.service.repository.infra.sensitiveWord;

import cn.iocoder.yudao.service.vo.infra.sensitiveword.SensitiveWordExportReqVO;
import cn.iocoder.yudao.service.vo.infra.sensitiveword.SensitiveWordPageReqVO;
import cn.iocoder.yudao.service.model.infra.sensitiveWord.SystemSensitiveWord;
import cn.iocoder.yudao.service.model.infra.sensitiveWord.SystemSensitiveWordTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

public interface SystemSensitiveWordRepository extends JRepository<SystemSensitiveWord,Long> {
    SystemSensitiveWordTable systemSenticeWordTable = SystemSensitiveWordTable.$;

    default Page<SystemSensitiveWord> selectPage(SensitiveWordPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemSenticeWordTable)
                        .whereIf(reqVO.getStatus() != null, systemSenticeWordTable.status().eq(reqVO.getStatus()))
                        .whereIf(StringUtils.hasText(reqVO.getName()), systemSenticeWordTable.name().eq(reqVO.getName()))
//                        .whereIf(StringUtils.hasText(reqVO.getTag()), systemSenticeWordTable.tags().eq(reqVO.getTag()))
                        .whereIf(reqVO.getCreateTime() != null, ()->systemSenticeWordTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(systemSenticeWordTable)
        );
    }

    default List<SystemSensitiveWord> selectList(SensitiveWordExportReqVO reqVO){
        return sql()
                .createQuery(systemSenticeWordTable)
                .whereIf(reqVO.getStatus() != null, systemSenticeWordTable.status().eq(reqVO.getStatus()))
                .whereIf(StringUtils.hasText(reqVO.getName()), systemSenticeWordTable.name().eq(reqVO.getName()))
//                .whereIf(StringUtils.hasText(reqVO.getTag()), systemSenticeWordTable.tags().eq(reqVO.getTag()))
                .whereIf(reqVO.getCreateTime() != null, ()->systemSenticeWordTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                .select(systemSenticeWordTable)
                .execute();
    }

    Optional<SystemSensitiveWord> findByName(String name);
}

package cn.iocoder.yudao.service.repository.system.notify;

import cn.iocoder.yudao.service.vo.system.notify.message.NotifyMessageMyPageReqVO;
import cn.iocoder.yudao.service.vo.system.notify.message.NotifyMessagePageReqVO;
import cn.iocoder.yudao.service.model.system.notify.SystemNotifyMessage;
import cn.iocoder.yudao.service.model.system.notify.SystemNotifyMessageTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

public interface SystemNotifyMessageRepository extends JRepository<SystemNotifyMessage, Long> {
    SystemNotifyMessageTable systemNotifyMessageTable = SystemNotifyMessageTable.$;

    default Page<SystemNotifyMessage> selectPage(NotifyMessagePageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemNotifyMessageTable)
                        .whereIf(StringUtils.hasText(reqVO.getTemplateCode()), systemNotifyMessageTable.templateCode().eq(reqVO.getTemplateCode()))
                        .whereIf(reqVO.getTemplateType() != null, systemNotifyMessageTable.templateType().eq(reqVO.getTemplateType()))
                        .whereIf(reqVO.getUserId() != null, systemNotifyMessageTable.userId().eq(reqVO.getUserId()))
                        .whereIf(reqVO.getUserType() != null, systemNotifyMessageTable.userType().eq(reqVO.getUserType()))
                        .whereIf(reqVO.getCreateTime() !=null, ()-> systemNotifyMessageTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(systemNotifyMessageTable)
        );
    }

    default Page<SystemNotifyMessage> selectPage(NotifyMessageMyPageReqVO reqVO, Long userId, Integer userType){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemNotifyMessageTable)
                        .whereIf(reqVO.getReadStatus() != null, systemNotifyMessageTable.readStatus().eq(reqVO.getReadStatus()))
                        .whereIf(userId != null, systemNotifyMessageTable.userId().eq(userId))
                        .whereIf(userType != null, systemNotifyMessageTable.userType().eq(userType))
                        .whereIf(reqVO.getCreateTime() !=null, ()-> systemNotifyMessageTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(systemNotifyMessageTable)
        );
    }
    default int updateAllNotifyMessageRead(Long userId, Integer userType){
        return sql()
                .createUpdate(systemNotifyMessageTable)
                .set(systemNotifyMessageTable.userType(), userType)
                .where(systemNotifyMessageTable.userId().eq(userId))
                .execute();
    }

    default int updateNotifyMessageRead(Collection<Long> ids, Long userId, Integer userType){
        return sql()
                .createUpdate(systemNotifyMessageTable)
                .set(systemNotifyMessageTable.userId(), userId)
                .set(systemNotifyMessageTable.userType(), userType)
                .where(systemNotifyMessageTable.id().in(ids))
                .execute();
    }

    default List<SystemNotifyMessage> getUnreadNotifyMessageList(Long userId, Integer userType, Integer size){
        return sql()
                .createQuery(systemNotifyMessageTable)
                .where(systemNotifyMessageTable.userId().eq(userId))
                .where(systemNotifyMessageTable.userType().eq(userType))
                .select(systemNotifyMessageTable)
                .limit(size, 0)
                .execute();
    };

    default long countByUserIdAndUserType(Long userId, Integer userType){
        return sql()
                .createQuery(systemNotifyMessageTable)
                .where(systemNotifyMessageTable.userId().eq(userId))
                .where(systemNotifyMessageTable.userType().eq(userType))
                .select(systemNotifyMessageTable)
                .count();
    };

}

package cn.iocoder.yudao.service.framework.db.jimmer;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.service.model.base.BaseEntityDraft;
import cn.iocoder.yudao.service.model.base.BaseEntityProps;
import cn.iocoder.yudao.service.model.system.user.SystemUserDraft;
import org.babyfish.jimmer.ImmutableObjects;
import org.babyfish.jimmer.sql.DraftInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class BaseEntityDraftInterceptor implements DraftInterceptor<BaseEntityDraft> {
    @Override
    public void beforeSave(BaseEntityDraft draft, boolean isNew) {

        if (!ImmutableObjects.isLoaded(draft, BaseEntityProps.UPDATER)) {
            draft.setUpdater(SystemUserDraft.$.produce(editor -> {
                if (SecurityFrameworkUtils.getLoginUserId() == null)
                    editor.setId(0);
                else
                    editor.setId(SecurityFrameworkUtils.getLoginUserId());
            }));
        }
        if (isNew) {

            if (!ImmutableObjects.isLoaded(draft, BaseEntityProps.CREATOR)) {
                draft.setCreator(SystemUserDraft.$.produce(creator -> {
                    if (SecurityFrameworkUtils.getLoginUserId() == null)
                        creator.setId(0);
                    else
                        creator.setId(SecurityFrameworkUtils.getLoginUserId());
                }));
            }
        }
    }
}

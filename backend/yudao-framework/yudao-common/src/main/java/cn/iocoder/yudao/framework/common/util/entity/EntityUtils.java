package cn.iocoder.yudao.framework.common.util.entity;

import org.babyfish.jimmer.ImmutableObjects;
import org.babyfish.jimmer.meta.ImmutableProp;
import org.babyfish.jimmer.meta.ImmutableType;

import java.util.Collection;

public class EntityUtils {
    public static boolean isEquals(Object oldObject, Object newObject){
        Collection<ImmutableProp> oldProps = ImmutableType.get(oldObject.getClass()).getProps().values();
        Collection<ImmutableProp> newProps = ImmutableType.get(newObject.getClass()).getProps().values();
        if(oldProps.size() != newProps.size())
            return false;
        for(ImmutableProp oldProp : oldProps){
            if(oldProp.getTargetType()  == null
                    && !oldProp.getName().equals("createTime")
                    && !oldProp.getName().equals("updateTime")
                    && !oldProp.getName().equals("creatorId")
                    && !oldProp.getName().equals("updaterId")
                    && !oldProp.getName().equals("deleted")){
                if(!ImmutableObjects.get(oldObject, oldProp).equals(ImmutableObjects.get(newObject, oldProp)))
                    return false;
            }
        }

        return true;
    }
}

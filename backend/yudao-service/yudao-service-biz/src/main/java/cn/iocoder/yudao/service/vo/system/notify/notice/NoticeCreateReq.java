package cn.iocoder.yudao.service.vo.system.notify.notice;

import cn.iocoder.yudao.service.vo.system.notify.baseVO.NoticeBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 通知公告创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeCreateReq extends NoticeBase {
}

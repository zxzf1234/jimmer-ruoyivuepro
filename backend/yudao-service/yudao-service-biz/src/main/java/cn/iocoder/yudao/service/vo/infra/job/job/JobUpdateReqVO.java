package cn.iocoder.yudao.service.vo.infra.job.job;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "管理后台 - 定时任务更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JobUpdateReqVO extends JobBaseVO {

    @Schema(description = "任务编号", required = true, example = "1024")
    @NotNull(message = "任务编号不能为空")
    private UUID id;

}

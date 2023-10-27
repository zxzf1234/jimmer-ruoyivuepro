package cn.iocoder.yudao.service.vo.infra.job.job;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.service.enums.infra.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 定时任务 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class JobExcelVO {

    @ExcelProperty("任务编号")
    private UUID id;

    @ExcelProperty("任务名称")
    private String name;

    @ExcelProperty(value = "任务状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.JOB_STATUS)
    private Integer status;

    @ExcelProperty("处理器的名字")
    private String handlerName;

    @ExcelProperty("处理器的参数")
    private String handlerParam;

    @ExcelProperty("CRON 表达式")
    private String cronExpression;

    @ExcelProperty("最后一次执行的开始时间")
    private LocalDateTime executeBeginTime;

    @ExcelProperty("最后一次执行的结束时间")
    private LocalDateTime executeEndTime;

    @ExcelProperty("上一次触发时间")
    private LocalDateTime firePrevTime;

    @ExcelProperty("下一次触发时间")
    private LocalDateTime fireNextTime;

    @ExcelProperty("监控超时时间")
    private Integer monitorTimeout;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}

<!-- 模版管理页面 -->

<template>
  <doc-alert title="站内信配置" url="https://doc.iocoder.cn/notify/" />

  <!-- 搜索工作栏 -->
  <ContentWrap>
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="模板名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入模板名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="模板编号" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入模版编码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择开启状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['system:notify-template:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" />新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <Table
    :columns="columns"
    :page-param="queryParams"
    :page-data="nofityTemplate"
    @page-change="getList"
    save-key="notify-template"
  >
    <template #menu="{ row }">
      <context-menu-item
        label="修改"
        @click="openForm('update', row.id)"
        v-hasPermi="['system:notify-template:update']"
      />
      <context-menu-item
        label="测试"
        @click="openSendForm(row)"
        v-hasPermi="['system:notify-template:send-notify']"
      />
      <context-menu-item
        label="删除"
        @click="handleDelete(row.id)"
        v-hasPermi="['system:notify-template:delete']"
      />
    </template>
    <template #type="{ row }">
      <dict-tag :type="DICT_TYPE.SYSTEM_NOTIFY_TEMPLATE_TYPE" :value="row.type" />
    </template>
    <template #status="{ row }">
      <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="row.status" />
    </template>
  </Table>

  <!-- 表单弹窗：添加/修改 -->
  <NotifyTemplateForm ref="formRef" @success="getList" />

  <!-- 表单弹窗：测试发送 -->
  <NotifyTemplateSendForm ref="sendFormRef" />
</template>
<script setup lang="ts" name="NotifySmsTemplate">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
// import { dateFormatter } from '@/utils/formatTime'
import * as NotifyTemplateApi from '@/api/system/notify/template'
import NotifyTemplateForm from './NotifyTemplateForm.vue'
import NotifyTemplateSendForm from './NotifyTemplateSendForm.vue'
import { formatDate } from '@/utils/formatTime'
const message = useMessage() // 消息弹窗

const loading = ref(false) // 列表的加载中

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: undefined,
  status: undefined,
  code: undefined,
  createTime: []
})
const columns: TableColumnList = [
  {
    label: '模板编码',
    prop: 'code'
  },
  {
    label: '模板名称',
    prop: 'name'
  },
  {
    label: '类型',
    prop: 'type',
    slot: 'type'
  },
  {
    label: '发送人名称',
    prop: 'nickname'
  },
  {
    label: '模板内容',
    prop: 'content',
    showOverflowTooltip: true
  },

  {
    label: '开启状态',
    prop: 'status',
    slot: 'status'
  },
  {
    label: '备注',
    prop: 'remark'
  },
  {
    label: '创建时间',
    prop: 'createTime',
    formatter: ({ createTime }) => formatDate(createTime)
  }
]
const queryFormRef = ref() // 搜索的表单

/** 查询列表 */

const nofityTemplate = ref()
const getList = async () => {
  loading.value = true
  try {
    nofityTemplate.value = await NotifyTemplateApi.getNotifyTemplatePage(queryParams)
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await NotifyTemplateApi.deleteNotifyTemplateApi(id)
    message.success('删除成功')
    // 刷新列表
    await getList()
  } catch {}
}

/** 测试按钮*/
const sendFormRef = ref() // 表单 Ref
const openSendForm = (row: NotifyTemplateApi.NotifyTemplateVO) => {
  sendFormRef.value.open(row.id)
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>

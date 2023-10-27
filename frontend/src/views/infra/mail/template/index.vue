<template>
  <doc-alert title="邮件配置" url="https://doc.iocoder.cn/mail" />

  <!-- 搜索工作栏 -->
  <ContentWrap>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="68px">
      <el-form-item label="模板编码" prop="code">
        <el-input
          v-model="queryParams.code"
          class="!w-240px"
          clearable
          placeholder="请输入模板编码"
          @keyup.enter="getList"
        />
      </el-form-item>
      <el-form-item label="模板名称" prop="name">
        <el-input
          v-model="queryParams.name"
          class="!w-240px"
          clearable
          placeholder="请输入模板名称"
          @keyup.enter="getList"
        />
      </el-form-item>
      <el-form-item label="开启状态" prop="status">
        <el-select
          v-model="queryParams.status"
          class="!w-240px"
          clearable
          placeholder="请选择发送状态"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
        <el-form-item label="创建时间" prop="createTime">
          <el-date-picker
            v-model="queryParams.createTime"
            :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
            class="!w-240px"
            end-placeholder="结束日期"
            start-placeholder="开始日期"
            type="daterange"
            value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
      </el-form-item>
      <el-form-item label="邮箱账号" prop="accountId">
        <el-select
          v-model="queryParams.accountId"
          class="!w-240px"
          clearable
          placeholder="请选择邮箱账号"
        >
          <el-option
            v-for="account in accountList"
            :key="account.id"
            :label="account.mail"
            :value="account.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button @click="getList">
          <Icon class="mr-5px" icon="ep:search" />
          搜索
        </el-button>
        <el-button @click="resetQuery">
          <Icon class="mr-5px" icon="ep:refresh" />
          重置
        </el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['infra:mail-account:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <Table
      :columns="allSchemas.tableColumns"
      :page-param="queryParams"
      :page-data="templateData"
      adaptive
    >
      <template #menu="{ row }">
        <context-menu-item
          label="测试"
          @click="openSendForm(row.id)"
          v-hasPermi="['infra:mail-template:send-mail']"
        />
        <context-menu-item
          label="编辑"
          @click="openForm(row.id)"
          v-hasPermi="['infra:mail-template:update']"
        />
        <context-menu-item
          label="删除"
          @click="handleDelete(row.id)"
          v-hasPermi="['infra:mail-template:delete']"
        />
      </template>
    </Table>
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <MailTemplateForm ref="formRef" @success="getList" />

  <!-- 表单弹窗：发送测试 -->
  <MailTemplateSendForm ref="sendFormRef" />
</template>
<script setup lang="ts" name="SystemMailTemplate">
import { allSchemas } from './template.data'
import * as MailTemplateApi from '@/api/system/mail/template'
import MailTemplateForm from './MailTemplateForm.vue'
import MailTemplateSendForm from './MailTemplateSendForm.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import * as MailAccountApi from '@/api/system/mail/account'

const queryParams = reactive({
  code: undefined,
  name: undefined,
  status: undefined,
  createTime: undefined,
  accountId: undefined
})
const accountList = ref()
const queryFormRef = ref() // 搜索的表单
const dbTableLoading = ref(true) // 数据源的加载中
const templateData = ref()

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = (id: number) => {
  MailTemplateApi.deleteMailTemplate(id)
}

/** 发送测试操作 */
const sendFormRef = ref()
const openSendForm = (id: number) => {
  sendFormRef.value.open(id)
}
const resetQuery = async () => {
  queryFormRef.value.resetFields()
  await getList()
}

const getList = async () => {
  dbTableLoading.value = true
  try {
    templateData.value = await MailTemplateApi.getMailTemplatePage(queryParams)
  } finally {
    dbTableLoading.value = false
  }
}
/** 初始化 **/
onMounted(async () => {
  accountList.value = await MailAccountApi.getSimpleMailAccountList()
  getList()
})
</script>

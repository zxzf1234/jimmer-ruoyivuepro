<template>
  <doc-alert title="邮件配置" url="https://doc.iocoder.cn/mail" />

  <!-- 搜索工作栏 -->
  <ContentWrap>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="68px">
      <el-form-item label="用户编号" prop="mail">
        <el-input
          v-model="queryParams.userId"
          class="!w-240px"
          clearable
          placeholder="请输入名称"
          @keyup.enter="getList"
        />
      </el-form-item>
      <el-form-item label="用户类型" prop="status">
        <el-select
          v-model="queryParams.userType"
          class="!w-240px"
          clearable
          placeholder="请选择用户类型"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.USER_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发送状态" prop="status">
        <el-select
          v-model="queryParams.sendStatus"
          class="!w-240px"
          clearable
          placeholder="请选择发送状态"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.SYSTEM_MAIL_SEND_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
      <el-form-item label="模板编号" prop="templateId">
        <el-input
          v-model="queryParams.templateId"
          class="!w-240px"
          clearable
          placeholder="请输入模板编号"
          @keyup.enter="getList"
        />
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
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <Table :columns="allSchemas.tableColumns" :page-param="queryParams" :page-data="logData">
      <template #menu="{ row }">
        <context-menu-item
          label="详情"
          @click="openDetail(row.id)"
          v-hasPermi="['infra:mail-log:query']"
        />
      </template>
    </Table>
  </ContentWrap>

  <!-- 表单弹窗：详情 -->
  <mail-log-detail ref="detailRef" />
</template>
<script setup lang="ts" name="SystemMailLog">
import { allSchemas } from './log.data'
import * as MailLogApi from '@/api/system/mail/log'
import MailLogDetail from './MailLogDetail.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import * as MailAccountApi from '@/api/system/mail/account'

const queryParams = reactive({
  userId: undefined,
  userType: undefined,
  sendStatus: undefined,
  templateId: undefined,
  accountId: undefined
})
const queryFormRef = ref() // 搜索的表单
const dbTableLoading = ref(true) // 数据源的加载中
const logData = ref()
const getList = async () => {
  dbTableLoading.value = true
  try {
    logData.value = await MailLogApi.getMailLogPage(queryParams)
  } finally {
    dbTableLoading.value = false
  }
}
const accountList = ref()

/** 详情操作 */
const detailRef = ref()
const openDetail = (id: number) => {
  detailRef.value.open(id)
}
const resetQuery = async () => {
  queryFormRef.value.resetFields()
  await getList()
}

/** 初始化 **/
onMounted(async () => {
  accountList.value = await MailAccountApi.getSimpleMailAccountList()
  getList()
})
</script>

<template>
  <doc-alert title="邮件配置" url="https://doc.iocoder.cn/mail" />

  <!-- 搜索工作栏 -->
  <ContentWrap>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="68px">
      <el-form-item label="邮箱" prop="mail">
        <el-input
          v-model="queryParams.mail"
          class="!w-240px"
          clearable
          placeholder="请输入名称"
          @keyup.enter="getList"
        />
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model="queryParams.username"
          class="!w-240px"
          clearable
          placeholder="请输入名称"
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
    <Table :columns="allSchemas.tableColumns" :page-param="queryParams" :page-data="accountData">
      <template #menu="{ row }">
        <context-menu-item
          label="编辑"
          @click="openForm('update', row.id)"
          v-hasPermi="['infra:mail-account:update']"
        />
        <context-menu-item
          label="详情"
          @click="openDetail(row.id)"
          v-hasPermi="['infra:mail-account:query']"
        />
        <context-menu-item
          label="删除"
          @click="handleDelete(row.id)"
          v-hasPermi="['infra:mail-account:delete']"
        />
      </template>
    </Table>
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <MailAccountForm ref="formRef" @success="getList" />
  <!-- 详情弹窗 -->
  <MailAccountDetail ref="detailRef" />
</template>
<script setup lang="ts" name="SystemMailAccount">
import { allSchemas } from './account.data'
import * as MailAccountApi from '@/api/system/mail/account'
import MailAccountForm from './MailAccountForm.vue'
import MailAccountDetail from './MailAccountDetail.vue'
const dbTableLoading = ref(true) // 数据源的加载中
const accountData = ref()

/** 添加/修改操作 */
const formRef = ref()
const queryFormRef = ref() // 搜索的表单
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}
const queryParams = reactive({ mail: undefined, username: undefined })

/** 详情操作 */
const detailRef = ref()
const openDetail = (id: number) => {
  detailRef.value.open(id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  await MailAccountApi.deleteMailAccount(id)
}

/** 初始化 **/
onMounted(() => {
  getList()
})
const getList = async () => {
  dbTableLoading.value = true
  try {
    accountData.value = await MailAccountApi.getMailAccountPage(queryParams)
  } finally {
    dbTableLoading.value = false
  }
}
const resetQuery = async () => {
  queryFormRef.value.resetFields()
  await getList()
}
</script>

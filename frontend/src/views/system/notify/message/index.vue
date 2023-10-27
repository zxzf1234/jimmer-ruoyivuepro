<template>
  <doc-alert title="站内信配置" url="https://doc.iocoder.cn/notify/" />

  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="用户编号" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="用户类型" prop="userType">
        <el-select
          v-model="queryParams.userType"
          placeholder="请选择用户类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.USER_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="模板编码" prop="templateCode">
        <el-input
          v-model="queryParams.templateCode"
          placeholder="请输入模板编码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="模版类型" prop="templateType">
        <el-select
          v-model="queryParams.templateType"
          placeholder="请选择模版类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.SYSTEM_NOTIFY_TEMPLATE_TYPE)"
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
      </el-form-item>
    </el-form>
  </ContentWrap>
  <Table
    :columns="columns"
    :page-param="queryParams"
    :page-data="notifyData"
    @page-change="getList"
    save-key="notify-message"
  >
    <template #menu="{ row }">
      <context-menu-item
        label="详情"
        @click="openDetail(row)"
        v-hasPermi="['system:notify-message:query']"
      />
    </template>
    <template #userType="{ row }">
      <dict-tag :type="DICT_TYPE.USER_TYPE" :value="row.userType" />
    </template>
    <template #templateType="{ row }">
      <dict-tag :type="DICT_TYPE.SYSTEM_NOTIFY_TEMPLATE_TYPE" :value="row.templateType" />
    </template>
    <template #readStatus="{ row }">
      <dict-tag :type="DICT_TYPE.INFRA_BOOLEAN_STRING" :value="row.readStatus" />
    </template>
    <template #templateParams="{ row }">
      {{ row.templateParams }}
    </template>
  </Table>

  <!-- 表单弹窗：详情 -->
  <NotifyMessageDetail ref="detailRef" />
</template>
<script setup lang="ts" name="SystemNotifyMessage">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import * as NotifyMessageApi from '@/api/system/notify/message'
import NotifyMessageDetail from './NotifyMessageDetail.vue'
import { formatDate } from '@/utils/formatTime'
const loading = ref(true) // 列表的加载中

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  userType: undefined,
  userId: undefined,
  templateCode: undefined,
  templateType: undefined,
  createTime: []
})
const columns: TableColumnList = [
  {
    label: '编号',
    prop: 'id'
  },
  {
    label: '用户类型',
    prop: 'userType',
    slot: 'userType'
  },
  {
    label: '用户编号',
    prop: 'userId'
  },
  {
    label: '模板编码',
    prop: 'templateCode'
  },
  {
    label: '发送人名称',
    prop: 'templateNickname'
  },
  {
    label: '模版内容',
    prop: 'templateContent',
    showOverflowTooltip: true
  },
  {
    label: '模版参数',
    prop: 'templateParams',
    slot: 'templateParams',
    showOverflowTooltip: true
  },
  {
    label: '模版类型',
    prop: 'templateType',
    slot: 'templateType'
  },
  {
    label: '是否已读',
    prop: 'readStatus',
    slot: 'readStatus'
  },
  {
    label: '阅读时间',
    prop: 'readTime',
    formatter: ({ readTime }) => formatDate(readTime)
  },
  {
    label: '创建时间',
    prop: 'createTime',
    formatter: ({ createTime }) => formatDate(createTime)
  }
]
const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
const notifyData = ref()
const getList = async () => {
  loading.value = true
  try {
    notifyData.value = await NotifyMessageApi.getNotifyMessagePage(queryParams)
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 详情操作 */
const detailRef = ref()
const openDetail = (data: NotifyMessageApi.NotifyMessageVO) => {
  detailRef.value.open(data)
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>

<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="公告标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入公告标题"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="公告状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择公告状态"
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
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['system:notice:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <Table
    :columns="columns"
    :page-param="queryParams"
    :page-data="noticeData"
    @page-change="getList"
    save-key="notice"
  >
    <template #menu="{ row }">
      <context-menu-item
        label="编辑"
        @click="openForm('update', row.id)"
        v-hasPermi="['system:notice:update']"
      />
      <context-menu-item
        label="删除"
        @click="handleDelete(row.id)"
        v-hasPermi="['system:notice:delete']"
      />
    </template>
    <template #type="{ row }">
      <dict-tag :type="DICT_TYPE.SYSTEM_NOTICE_TYPE" :value="row.type" />
    </template>
    <template #status="{ row }">
      <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="row.status" />
    </template>
  </Table>
  <!-- 列表 -->

  <!-- 表单弹窗：添加/修改 -->
  <NoticeForm ref="formRef" @success="getList" />
</template>
<script setup lang="tsx" name="SystemNotice">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
// import { dateFormatter } from '@/utils/formatTime'
import * as NoticeApi from '@/api/system/notice'
import NoticeForm from './NoticeForm.vue'
import { formatDate } from '@/utils/formatTime'
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const loading = ref(true) // 列表的加载中

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  title: '',
  type: undefined,
  status: undefined
})

const columns: TableColumnList = [
  {
    label: '公告编号',
    prop: 'id'
  },
  {
    label: '公告标题',
    prop: 'title'
  },
  {
    label: '公告类型',
    prop: 'type',
    slot: 'type'
  },
  {
    label: '角色标识',
    prop: 'code'
  },

  {
    label: '状态',
    prop: 'status',
    slot: 'status'
  },
  {
    label: '创建时间',
    prop: 'createTime',
    formatter: ({ createTime }) => formatDate(createTime)
  }
]
const queryFormRef = ref() // 搜索的表单
const noticeData = ref()
/** 查询公告列表 */
const getList = async () => {
  loading.value = true
  try {
    noticeData.value = await NoticeApi.getNoticePage(queryParams)
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
    await NoticeApi.deleteNotice(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>

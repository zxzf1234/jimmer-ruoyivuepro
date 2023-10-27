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
      <el-form-item label="岗位名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入岗位名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位编码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入岗位编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
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
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['infra:config:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <Table
    :columns="columns"
    :page-param="queryParams"
    :page-data="postData"
    @page-change="getList"
    save-key="post"
  >
    <template #menu="{ row }">
      <context-menu-item
        label="编辑"
        @click="openForm('update', row.id)"
        v-hasPermi="['system:post:update']"
      />
      <context-menu-item
        label="删除"
        @click="handleDelete(row.id)"
        v-hasPermi="['system:post:delete']"
      />
    </template>
    <template #status="{ row }">
      <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="row.status" />
    </template>
  </Table>

  <!-- 表单弹窗：添加/修改 -->
  <PostForm ref="formRef" @success="getList" />
</template>
<script setup lang="tsx" name="SystemPost">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
// import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import * as PostApi from '@/api/system/post'
import PostForm from './PostForm.vue'
import { formatDate } from '@/utils/formatTime'
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  code: '',
  name: '',
  status: undefined
})
const columns: TableColumnList = [
  {
    label: '岗位编号',
    prop: 'id'
  },
  {
    label: '岗位名称',
    prop: 'name'
  },
  {
    label: '岗位编码',
    prop: 'code'
  },
  {
    label: '岗位顺序',
    prop: 'sort'
  },

  {
    label: '岗位备注',
    prop: 'remark'
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
const exportLoading = ref(false) // 导出的加载中

const postData = ref()
/** 查询岗位列表 */
const getList = async () => {
  loading.value = true
  try {
    postData.value = await PostApi.getPostPage(queryParams)
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

// /** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await PostApi.deletePost(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await PostApi.exportPost(queryParams)
    download.excel(data, '岗位列表.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>

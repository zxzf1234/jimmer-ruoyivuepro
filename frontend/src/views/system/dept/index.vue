<template>
  <!-- 搜索工作栏 -->
  <ContentWrap>
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="部门名称" prop="title">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入部门名称"
          clearable
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="部门状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择部门状态"
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
          v-hasPermi="['system:dept:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button type="danger" plain @click="toggleExpandAll">
          <Icon icon="ep:sort" class="mr-5px" /> 展开/折叠
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <Table
    :columns="columns"
    :data="deptData"
    save-key="dept"
    :loading="loading"
    :default-expand-all="isExpandAll"
    v-if="refreshTable"
  >
    <template #menu="{ row }">
      <context-menu-item
        label="修改"
        @click="openForm('update', row.id)"
        v-hasPermi="['system:dept:update']"
      />
      <context-menu-item
        label="删除"
        @click="handleDelete(row.id)"
        v-hasPermi="['system:dept:delete']"
      />
    </template>
    <template #leader="{ row }">
      {{ userList.find((user) => user.id === row.leaderUserId)?.nickname }}
    </template>
    <template #status="{ row }">
      <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="row.status" />
    </template>
  </Table>

  <!-- 表单弹窗：添加/修改 -->
  <DeptForm ref="formRef" @success="getList" />
</template>
<script setup lang="ts" name="SystemDept">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
// import { dateFormatter } from '@/utils/formatTime'
import { handleTree } from '@/utils/tree'
import * as DeptApi from '@/api/system/dept'
import DeptForm from './DeptForm.vue'
import * as UserApi from '@/api/system/user'
import { formatDate } from '@/utils/formatTime'
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const queryParams = reactive({
  title: '',
  name: undefined,
  status: undefined,
  pageNo: 1,
  pageSize: 100
})
const columns: TableColumnList = [
  {
    label: '部门名称',
    prop: 'name'
  },
  {
    label: '负责人',
    prop: 'leader',
    slot: 'leader'
  },
  {
    label: '排序',
    prop: 'sort'
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
const isExpandAll = ref(true) // 是否展开，默认全部展开
const refreshTable = ref(true) // 重新渲染表格状态
const userList = ref<UserApi.UserVO[]>([]) // 用户列表

/** 查询部门列表 */
const deptData = ref()
const getList = async () => {
  loading.value = true
  try {
    const data = await DeptApi.getDeptList(queryParams)
    deptData.value = handleTree(data)
  } finally {
    loading.value = false
  }
}

/** 展开/折叠操作 */
const toggleExpandAll = () => {
  refreshTable.value = false
  isExpandAll.value = !isExpandAll.value
  nextTick(() => {
    refreshTable.value = true
  })
}

/** 搜索按钮操作 */
const handleQuery = () => {
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryParams.pageNo = 1
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
    await DeptApi.deleteDept(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(async () => {
  await getList()
  // 获取用户列表
  userList.value = await UserApi.getSimpleUserList()
})
</script>

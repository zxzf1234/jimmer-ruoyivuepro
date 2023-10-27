<template>
  <div>
    <doc-alert title="功能权限" url="https://doc.iocoder.cn/resource-permission" />
    <doc-alert title="数据权限" url="https://doc.iocoder.cn/data-permission" />
    <ContentWrap class="search-form">
      <!-- 搜索工作栏 -->
      <el-form
        ref="queryFormRef"
        :inline="true"
        :model="queryParams"
        class="-mb-15px"
        label-width="68px"
      >
        <el-form-item label="角色名称" prop="name">
          <el-input
            v-model="queryParams.name"
            class="!w-240px"
            clearable
            placeholder="请输入角色名称"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="角色标识" prop="code">
          <el-input
            v-model="queryParams.code"
            class="!w-240px"
            clearable
            placeholder="请输入角色标识"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select
            v-model="queryParams.status"
            class="!w-240px"
            clearable
            placeholder="请选择状态"
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
            :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
            class="!w-240px"
            end-placeholder="结束日期"
            start-placeholder="开始日期"
            type="daterange"
            value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
        <el-form-item>
          <el-button @click="handleQuery">
            <Icon class="mr-5px" icon="ep:search" />
            搜索
          </el-button>
          <el-button @click="resetQuery">
            <Icon class="mr-5px" icon="ep:refresh" />
            重置
          </el-button>

          <el-button
            v-hasPermi="['system:role:export']"
            :loading="exportLoading"
            plain
            type="success"
            @click="handleExport"
          >
            <Icon class="mr-5px" icon="ep:download" />
            导出
          </el-button>
        </el-form-item>
      </el-form>
    </ContentWrap>
    <Table
      :columns="columns"
      :page-param="queryParams"
      :page-data="Roledata"
      @page-change="getList"
      save-key="role"
    >
      <template #menu="{ row }">
        <context-menu-item
          label="编辑"
          @click="openForm('update', row.id)"
          v-hasPermi="['system:role:update']"
        />
        <context-menu-item
          label="菜单权限"
          @click="openAssignMenuForm(row)"
          v-hasPermi="['system:permission:assign-role-menu']"
        />
        <context-menu-item
          label="删除"
          @click="handleDelete(row.id)"
          v-hasPermi="['system:role:delete']"
        />
      </template>
      <template #status="{ row }">
        <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="row.status" />
      </template>
    </Table>
    <!-- 表单弹窗：添加/修改 -->
    <RoleForm ref="formRef" @success="getList" />
    <!-- <EditColun ref="edit" /> -->
    <!-- 表单弹窗：菜单权限 -->
    <RoleAssignMenuForm ref="assignMenuFormRef" @success="getList" />
    <!-- 表单弹窗：数据权限 -->
    <RoleDataPermissionForm ref="dataPermissionFormRef" @success="getList" />
  </div>
</template>
<script lang="ts" name="SystemRole" setup>
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import RoleForm from './RoleForm.vue'
import RoleAssignMenuForm from './RoleAssignMenuForm.vue'
import RoleDataPermissionForm from './RoleDataPermissionForm.vue'
import download from '@/utils/download'
import * as RoleApi from '@/api/system/role'
import { formatDate } from '@/utils/formatTime'
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const exportLoading = ref(false) // 导出的加载中
const loading = ref(true) // 列表的加载中
/** 重置按钮操作 */
const queryFormRef = ref() // 搜索的表单
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}
/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

const Roledata = ref()
/** 查询角色列表 */
const getList = async () => {
  loading.value = true
  try {
    Roledata.value = await RoleApi.getRolePage(queryParams)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  getList()
})

/** 搜索按钮操作 */
const handleQuery = () => {
  // queryParams.pageNo = 1
  getList()
}
const message2 = useMessage() // 消息弹窗
/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message2.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await RoleApi.exportRole(queryParams)
    download.excel(data, '角色列表.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 菜单权限操作 */
const assignMenuFormRef = ref()
const openAssignMenuForm = async (row: RoleApi.RoleVO) => {
  assignMenuFormRef.value.open(row)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await RoleApi.deleteRole(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  code: '',
  name: '',
  status: undefined,
  createTime: []
})

const columns: TableColumnList = [
  {
    label: '用户编号',
    prop: 'id'
  },
  {
    label: '用户昵称',
    prop: 'name'
  },
  {
    label: '角色类型',
    prop: 'type'
  },
  {
    label: '角色标识',
    prop: 'code'
  },
  {
    label: '显示顺序',
    prop: 'sort'
  },
  {
    label: '备注',
    prop: 'remark'
  },
  {
    label: '状态',
    prop: 'status',
    slot: 'isStatus'
  },
  {
    label: '创建时间',
    prop: 'createTime',
    formatter: ({ createTime }) => formatDate(createTime)
  }
]
</script>
<style>
.el-checkbox-group {
  display: flex;
  flex-direction: column;
}
</style>

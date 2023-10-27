<template>
  <Dialog v-model="dialogVisible" title="接口关联字段" width="1200px" class="h-[1000px]">
    <ContentWrap :class="[isBatchAdd == 1 ? 'h-[600px]' : 'h-[900px]']">
      <!-- 搜索栏 -->
      <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="68px">
        <el-form-item label="关联类型" prop="name">
          <el-select v-model="queryParams.variableType" @change="handleVariableTypeChange">
            <el-option v-if="(variableType & (1 << 1)) > 0" label="table字段" value="1" />
            <el-option v-if="(variableType & (1 << 2)) > 0" label="VO类" value="2" />
            <el-option v-if="(variableType & (1 << 3)) > 0" label="子类" value="3" />
          </el-select>
        </el-form-item>
        <template v-if="queryParams.variableType === '1'">
          <el-form-item label="表名称" prop="name">
            <el-input
              v-model="queryParams.name"
              class="!w-240px"
              clearable
              placeholder="请输入表名称"
              @keyup.enter="getList"
            />
          </el-form-item>
          <el-form-item label="表描述" prop="comment">
            <el-input
              v-model="queryParams.comment"
              class="!w-240px"
              clearable
              placeholder="请输入表描述"
              @keyup.enter="getList"
            />
          </el-form-item>
        </template>
        <template v-else>
          <el-form-item label="模块" prop="moduleName">
            <el-tree-select
              v-model="queryParams.moduleName"
              :data="moduleTree"
              :props="defaultProps"
              check-strictly
              default-expand-all
              placeholder="请选择模块"
              value-key="Id"
              @keyup.enter="getList"
            />
          </el-form-item>
          <el-form-item label="接口名" prop="name">
            <el-input
              v-model="queryParams.name"
              class="!w-240px"
              clearable
              placeholder="请输入名称"
              @keyup.enter="getList"
            />
          </el-form-item>
        </template>
        <el-form-item>
          <el-button @click="getList">
            <Icon class="mr-5px" icon="ep:search" />
            搜索
          </el-button>
          <el-button @click="resetQuery">
            <Icon class="mr-5px" icon="ep:refresh" />
            重置
          </el-button>
          <el-button v-if="isBatchAdd == 1" @click="saveParams"> 保存 </el-button>
        </el-form-item>
      </el-form>
      <template v-if="queryParams.variableType === '1'">
        <el-table
          :data="dbTableList"
          height="700"
          highlight-current-row
          row-key="id"
          @row-dblclick="handleTableDblclick"
        >
          <el-table-column type="expand">
            <template #default="scope">
              <el-table
                highlight-current-row
                :data="scope.row.columns"
                style="width: 1100px"
                class="mx-10"
                @row-dblclick="(row) => handleColumnDblclick(row, scope)"
              >
                <el-table-column label="字段列名" prop="columnName" />
                <el-table-column label="java类型" prop="javaType" />
                <el-table-column label="字段描述" prop="columnComment" />
                <el-table-column label="示例" prop="example" />
              </el-table>
            </template>
          </el-table-column>
          <el-table-column label="表名称" prop="name" />
          <el-table-column label="表描述" prop="comment" />
          <el-table-column label="模块名" prop="businessName" />
        </el-table>
      </template>
      <template v-else-if="queryParams.variableType === '2'">
        <el-table
          :data="dbVOList"
          height="700"
          highlight-current-row
          row-key="id"
          @row-dblclick="handleVODblclick"
        >
          <el-table-column label="接口名" prop="name" />
          <el-table-column label="描述" prop="comment" />
          <el-table-column label="模块" prop="moduleName" />
        </el-table>
      </template>
      <template v-else>
        <el-table
          :data="dbSubclasses"
          height="700"
          highlight-current-row
          row-key="id"
          @row-dblclick="handleSubclassDblclick"
        >
          <el-table-column label="子类名" prop="name" />
          <el-table-column label="子类描述" prop="comment" />
          <el-table-column label="继承类" prop="inheritClass" />
        </el-table>
      </template>
    </ContentWrap>
    <ContentWrap v-if="isBatchAdd == 1" class="h-[230px]">
      <el-table
        highlight-current-row
        default-expand-all
        :data="dbSelectdColumnList"
        value-key="Id"
        max-height="200"
        class="mx-10"
      >
        <el-table-column label="表名" prop="tableName" />
        <el-table-column label="字段列名" prop="columnName" />
        <el-table-column label="java类型" prop="javaType" />
        <el-table-column label="字段描述" prop="columnComment" />
        <el-table-column label="示例" prop="example" />
      </el-table>
    </ContentWrap>
  </Dialog>
</template>
<script lang="ts" name="InterfaceRelatedParam" setup>
import * as CodegenApi from '@/api/infra/codegen'
import { ElTable } from 'element-plus'
import { defaultProps, handleTree } from '@/utils/tree'
export type selectedColumnVO = {
  id: string
  tableName: string
  columnName: string
  columnComment: string
  example: string
  javaType: string
}
const emit = defineEmits(['rowDblclick', 'saveParam'])
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dbLoading = ref(true) // 数据源的加载中
const moduleTree = ref() // 树形结构

const dbTableList = ref<CodegenApi.DatabaseTableVO[]>([]) // 表的列表
const dbVOList = ref<CodegenApi.InterfaceVoClassVO[]>([]) // 接口的列表
const dbSubclasses = ref<CodegenApi.InterfaceSubclassVO[]>([]) // 子类的列表
const queryParams = reactive({
  name: undefined,
  comment: undefined,
  moduleName: undefined,
  variableType: ''
})
const dbSelectdColumnList = ref<CodegenApi.DatabaseColumnVO[]>([]) // 选中的字段列表
let variableType = 0
let isBatchAdd = 0
// const dblclickRow = ref()

/** 打开弹窗 */
const open = async (params) => {
  dialogVisible.value = true
  dbSubclasses.value = params.Subclasses
  variableType = params.variableType
  isBatchAdd = params.isBatchAdd
  dbSelectdColumnList.value = []
  if ((variableType & (1 << 1)) > 0) queryParams.variableType = '1'
  else if ((variableType & (1 << 2)) > 0) queryParams.variableType = '2'
  else queryParams.variableType = '3'
  await getList()
  await getTree()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 查询表数据 */
const getList = async () => {
  dbLoading.value = true
  try {
    if (queryParams.variableType === '1')
      dbTableList.value = await CodegenApi.getDatabaseTableColumnList(queryParams)
    else dbVOList.value = await CodegenApi.getInterfaceVOClassList(queryParams)
  } finally {
    dbLoading.value = false
  }
}

/** 重置操作 */
const resetQuery = async () => {
  queryParams.name = undefined
  queryParams.comment = undefined
  await getList()
}

const saveParams = () => {
  emit('saveParam', dbSelectdColumnList.value)
  close()
}

const handleTableDblclick = () => {
  message.alertError('请双击选择字段')
}

const handleColumnDblclick = (row, scope) => {
  if (isBatchAdd == 1) {
    row.tableName = scope.row.name
    if (dbSelectdColumnList.value.indexOf(row) == -1) dbSelectdColumnList.value.push(row)
  } else {
    row.relatedColumn = scope.row.name + '.' + row.columnName
    row.relatedType = queryParams.variableType
    emit('rowDblclick', row)
    close()
  }
}

const handleVODblclick = (row) => {
  row.relatedColumn = row.name
  row.relatedType = queryParams.variableType
  emit('rowDblclick', row)
  close()
}
const handleSubclassDblclick = (row) => {
  row.relatedColumn = row.name
  row.relatedType = queryParams.variableType
  emit('rowDblclick', row)
  close()
}

const handleVariableTypeChange = () => {
  getList()
}

/** 关闭弹窗 */
const close = async () => {
  dialogVisible.value = false
}

/** 获得模块树 */
const getTree = async () => {
  moduleTree.value = []
  const data = await CodegenApi.getSimpleList()
  moduleTree.value = handleTree(data)
}
</script>

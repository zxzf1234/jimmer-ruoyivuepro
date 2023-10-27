<template>
  <ContentWrap class="h-[600px]">
    <!-- 搜索栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="68px">
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
      <el-form-item>
        <el-button @click="getList">
          <Icon class="mr-5px" icon="ep:search" />
          搜索
        </el-button>
        <el-button @click="resetQuery">
          <Icon class="mr-5px" icon="ep:refresh" />
          重置
        </el-button>
        <el-button @click="newTable">
          <Icon class="mr-5px" icon="ep:plus" />
          新建
        </el-button>
      </el-form-item>
    </el-form>
    <!-- 列表 -->
    <el-row>
      <el-table
        ref="tableRef"
        v-loading="dbTableLoading"
        :data="dbTableList"
        highlight-current-row
        @row-click="handleRowClick"
      >
        <el-table-column :show-overflow-tooltip="true" label="表名称" prop="name" />
        <el-table-column :show-overflow-tooltip="true" label="表描述" prop="comment" />
        <el-table-column :show-overflow-tooltip="true" label="备注" prop="remark" />
        <el-table-column :show-overflow-tooltip="true" label="模块名" prop="businessName" />
        <el-table-column align="center" fixed="right" label="操作" width="300px">
          <template #default="scope">
            <el-button link type="primary" @click="handleUpdate(scope.row)"> 修改 </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
  </ContentWrap>
  <ContentWrap>
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="表字段" name="column">
        <el-table
          ref="columnTable"
          :data="dbColumnList"
          height="300"
          highlight-current-row
          row-key="id"
        >
          <el-table-column type="expand">
            <template #default="scope">
              <el-table
                highlight-current-row
                :data="scope.row.validations"
                style="width: 1100px"
                class="mx-10"
              >
                <el-table-column
                  label="校验注解"
                  prop="validation"
                  min-width="30%"
                  align="center"
                />
                <el-table-column label="校验条件" prop="validationCondition" />
                <el-table-column label="报错信息" align="center" prop="message" />
              </el-table>
            </template>
          </el-table-column>
          <el-table-column label="字段列名" min-width="10%" prop="columnName" />
          <el-table-column label="字段描述" min-width="10%" prop="columnComment" />
          <el-table-column label="物理类型" min-width="10%" prop="dataType" />
          <el-table-column label="Java类型" min-width="11%" prop="javaType" />
          <el-table-column label="允许NULL" min-width="6%" prop="nullable" />
          <el-table-column label="默认值" min-width="6%" prop="defaultValue" />
          <el-table-column label="字典类型" min-width="12%" prop="dictType" />
          <el-table-column label="示例" min-width="10%" prop="example" />
          <el-table-column label="前端必传" min-width="6%" prop="required" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="表索引" name="index">
        <el-table :data="dbIndexList" height="700" highlight-current-row>
          <el-table-column label="索引类型" align="center" prop="indexType" min-width="20%" />
          <el-table-column label="索引名称" align="center" prop="indexName" min-width="40%" />
          <el-table-column label="字段" align="center" prop="columnNames" min-width="40%" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="表映射" name="mapping">
        <el-table :data="dbMappingList" height="700" highlight-current-row>
          <el-table-column label="名称" align="center" prop="name" min-width="25%" />
          <el-table-column label="是否是list" align="center" prop="isList" min-width="15%" />
          <el-table-column label="映射表" align="center" prop="mappingTable" min-width="25%" />
          <el-table-column label="注解" align="center" prop="annotate" min-width="25%" />
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </ContentWrap>
  <!-- 弹窗：编辑数据表 -->
  <DatabaseTableEdit ref="editRef" @success="getList" />
</template>
<script lang="ts" name="DatabaseTable" setup>
import * as CodegenApi from '@/api/infra/codegen'
import DatabaseTableEdit from './DatabaseTableEdit.vue'
import { ElTable } from 'element-plus'
import * as DictDataApi from '@/api/system/dict/dict.type'

const dbTableLoading = ref(true) // 数据源的加载中
const dbTableList = ref<CodegenApi.DatabaseTableVO[]>([]) // 表的列表
const queryParams = reactive({
  name: undefined,
  comment: undefined
})
const queryFormRef = ref() // 搜索的表单
const activeName = ref('column')
const editRef = ref()
const dbColumnList = ref<CodegenApi.DatabaseColumnVO[]>([]) // 字段的列表
const dbIndexList = ref<CodegenApi.DatabaseIndexVO[]>([]) // 索引的列表
const dbMappingList = ref<CodegenApi.DatabaseMappingVO[]>([]) // 索引的列表

/** 查询表数据 */
const getList = async () => {
  dbTableLoading.value = true
  try {
    dbTableList.value = await CodegenApi.getDatabaseTableList(queryParams)
    dbColumnList.value = []
    dbIndexList.value = []
    dbMappingList.value = []
  } finally {
    dbTableLoading.value = false
  }
}

/** 重置操作 */
const resetQuery = async () => {
  queryParams.name = undefined
  queryParams.comment = undefined
  dbColumnList.value = []
  dbIndexList.value = []
  dbMappingList.value = []
  await getList()
}

const tableRef = ref<typeof ElTable>() // 表格的 Ref

/** 处理某一行的点击 */
const handleRowClick = async (row: CodegenApi.DatabaseTableVO) => {
  const data = await CodegenApi.getDatabaseTable(row.id)
  dbColumnList.value = data.columns
  dbIndexList.value = data.indexes
  dbMappingList.value = data.mappings
}

const handleUpdate = (row: CodegenApi.DatabaseTableVO) => {
  editRef.value.open('update', row.id)
}

const newTable = () => {
  editRef.value.open('create')
}

/** 查询字典下拉列表 */
const dictOptions = ref<DictDataApi.DictTypeVO[]>()
const getDictOptions = async () => {
  dictOptions.value = await DictDataApi.getSimpleDictTypeList()
}

/** 初始化 **/
onMounted(async () => {
  await getList()
  await getDictOptions()
})
</script>

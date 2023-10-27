<template>
  <!-- 搜索栏 -->
  <ContentWrap class="h-[55%]">
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="68px">
      <el-form-item label="模块" prop="module">
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
      <el-form-item>
        <el-button @click="getList">
          <Icon class="mr-5px" icon="ep:search" />
          搜索
        </el-button>
        <el-button @click="resetQuery">
          <Icon class="mr-5px" icon="ep:refresh" />
          重置
        </el-button>
        <el-button @click="openForm('create')">
          <Icon class="mr-5px" icon="ep:plus" />
          新建接口
        </el-button>
      </el-form-item>
    </el-form>
    <!-- 列表 -->
    <el-row>
      <Table
        :columns="interfaceColumns"
        :page-param="queryParams"
        :page-data="interfaceData"
        @row-click="handleRowClick"
        @page-change="getList"
        adaptive
        save-key="interface"
      >
        <template #menu="{ row }">
          <context-menu-item label="修改" @click="openForm('update', row.id)" />
        </template>
        <template #isTransaction="{ row, props }">
          <el-tag :size="props.size" :style="tagStyle(row.isTransaction)">
            {{ row.isTransaction === 1 ? '是' : '否' }}
          </el-tag>
        </template>
      </Table>
    </el-row>
  </ContentWrap>
  <ContentWrap class="h-[35%]">
    <el-tabs v-model="tabActiveName" type="card" height="700">
      <el-tab-pane label="入参" name="inputParam">
        <el-form ref="inputParamRef" :inline="true" :model="rowDetail" label-width="90px">
          <el-row>
            <el-col :span="8">
              <el-form-item label="入参类型" prop="inputType">
                <el-select v-model="rowDetail.inputType" disabled>
                  <el-option label="void" value="void" />
                  <el-option label="单参数" value="single" />
                  <el-option label="VO类" value="VOClass" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="入参继承类" style="display: flex" prop="inputExtendClass">
                <div style="display: flex">
                  <el-input v-model="rowDetail.inputExtendClass" disabled />
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-checkbox
                label="入参传入HttpServlet"
                v-model="rowDetail.inputServlet"
                false-label="false"
                true-label="true"
              />
            </el-col>
          </el-row>
        </el-form>
        <Table :columns="paramsColumns" :data="rowDetail.inputParams">
          <template #expand="{ row }">
            <Table :data="row.validations" :columns="validationColumns" />
          </template>
        </Table>
      </el-tab-pane>
      <el-tab-pane label="出参" name="outputParam">
        <el-form
          ref="outputParamRef"
          :inline="true"
          :model="rowDetail"
          row-key="id"
          label-width="90px"
        >
          <el-row>
            <el-col :span="8">
              <el-form-item label="出参类型" prop="outputType" disabled>
                <el-select v-model="rowDetail.outputType" disabled>
                  <el-option label="void" value="void" />
                  <el-option label="单参数" value="single" />
                  <el-option label="VO类" value="VOClass" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="出参继承类" prop="outputExtendClass">
                <div style="display: flex">
                  <el-input v-model="rowDetail.outputExtendClass" disabled />
                </div>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <Table :columns="paramsColumns" :data="rowDetail.outputParams">
          <template #expand="{ row }">
            <Table :data="row.validations" :columns="validationColumns" />
          </template>
        </Table>
      </el-tab-pane>
      <el-tab-pane label="入参子类" name="inputParamSubclass">
        <Table :columns="subclassColumns" :data="rowDetail.inputSubclasses">
          <template #expand="{ row }">
            <Table :data="row.subclassParams" :columns="paramsColumns">
              <template #expand="{ scope }">
                <Table :data="scope.validations" :columns="validationColumns" />
              </template>
            </Table>
          </template>
        </Table>
      </el-tab-pane>
      <el-tab-pane label="出参子类" name="outputParamSubclass">
        <Table :columns="subclassColumns" :data="rowDetail.outputSubclasses">
          <template #expand="{ row }">
            <Table :data="row.subclassParams" :columns="paramsColumns">
              <template #expand="{ scope }">
                <Table :data="scope.validations" :columns="validationColumns" />
              </template>
            </Table>
          </template>
        </Table>
      </el-tab-pane>
    </el-tabs>
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <InterfaceEdit ref="formRef" @success="getList" />
</template>
<script lang="ts" name="Interface" setup>
import * as CodegenApi from '@/api/infra/codegen'
import InterfaceEdit from './InterfaceEdit.vue'
import { defaultProps, handleTree } from '@/utils/tree'
import { ElInput } from 'element-plus'
import { formatDate } from '@/utils/formatTime'
import { usePublicHooks } from './../../system/dept/hooks'

const { tagStyle } = usePublicHooks()

const initFormData = {
  id: '',
  name: '',
  comment: '',
  method: '',
  authorize: '',
  isTransaction: 0,
  inputType: '',
  inputExtendClass: '',
  inputServlet: 0,
  outputExtendClass: '',
  outputType: '',
  moduleId: '',
  moduleName: '',
  createTime: '',
  operateType: 'new',
  inputParams: [],
  outputParams: [],
  inputSubclasses: [],
  outputSubclasses: []
}

const dbTableLoading = ref(true) // 数据源的加载中
const interfaceData = ref()

const queryFormRef = ref() // 搜索的表单
const tabActiveName = ref('inputParam')
const formRef = ref()
const moduleTree = ref() // 树形结构
const rowInfo = ref()
const rowDetail = ref<CodegenApi.InterfaceVO>(initFormData)

const queryParams = reactive({
  name: undefined,
  moduleName: undefined
})
const interfaceColumns: TableColumnList = [
  {
    label: '接口模块',
    prop: 'moduleName'
  },
  {
    label: '接口名',
    prop: 'name'
  },
  {
    label: '描述',
    prop: 'comment'
  },
  {
    label: '调用方法',
    prop: 'method'
  },
  {
    label: '权限',
    prop: 'authorize'
  },
  {
    label: '是否开启事务',
    prop: 'isTransaction',
    slot: 'isTransaction'
  },
  {
    label: '创建时间',
    prop: 'createTime',
    formatter: ({ createTime }) => formatDate(createTime)
  }
]
const paramsColumns: TableColumnList = [
  {
    type: 'expand',
    slot: 'expand'
  },
  {
    label: '参数名',
    prop: 'name'
  },
  {
    label: '参数描述',
    prop: 'comment'
  },
  {
    label: '是否是List',
    prop: 'isList'
  },
  {
    label: '参数类型',
    prop: 'variableType'
  },
  {
    label: '关联字段',
    prop: 'relatedColumn'
  },
  {
    label: '示例',
    prop: 'example'
  },
  {
    label: '前端必传',
    prop: 'required'
  }
]
const validationColumns: TableColumnList = [
  {
    label: '校验注解',
    prop: 'validation'
  },
  {
    label: '校验条件',
    prop: 'validationCondition'
  },
  {
    label: '报错信息',
    prop: 'message'
  }
]
const subclassColumns: TableColumnList = [
  {
    type: 'expand',
    slot: 'expand'
  },
  {
    label: '子类名',
    prop: 'name'
  },
  {
    label: '子类描述',
    prop: 'comment'
  },
  {
    label: '继承类',
    prop: 'inheritClass'
  }
]

/** 查询表数据 */
const getList = async () => {
  dbTableLoading.value = true
  try {
    interfaceData.value = await CodegenApi.getInterfaceList(queryParams)
  } finally {
    dbTableLoading.value = false
  }
}

/** 重置操作 */
const resetQuery = async () => {
  queryFormRef.value.resetFields()
  await getList()
}

/** 处理某一行的点击 */
const handleRowClick = async (row: CodegenApi.DatabaseTableVO) => {
  rowInfo.value = row
  const data = await CodegenApi.getInterface(row.id)
  rowDetail.value = data
}

/** 添加/修改操作 */
const openForm = (type: string, id?: string) => {
  formRef.value.open(type, id)
}

/** 初始化 **/
onMounted(async () => {
  await getList()
  await getTree()
})
/** 获得模块树 */
const getTree = async () => {
  moduleTree.value = []
  const data = await CodegenApi.getSimpleList()
  moduleTree.value = handleTree(data)
}
</script>

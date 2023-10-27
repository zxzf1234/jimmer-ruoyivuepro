<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="80px"
    >
      <el-form-item label="上级分组" prop="parentId">
        <el-tree-select
          v-model="formData.parentId"
          :data="moduleTree"
          :props="defaultProps"
          check-strictly
          default-expand-all
          placeholder="请选择上级分组"
          value-key="Id"
        />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入名称" />
      </el-form-item>
      <el-form-item label="描述" prop="comment">
        <el-input v-model="formData.comment" placeholder="请输入描述" />
      </el-form-item>
      <el-form-item label="菜单类型" prop="type">
        <el-radio-group v-model="formData.type">
          <el-radio-button label="0">分组</el-radio-button>>
          <el-radio-button label="1">模块</el-radio-button>>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" name="InterfaceModuleEdit" setup>
import { defaultProps, handleTree } from '@/utils/tree'
import * as CodegenApi from '@/api/infra/codegen'
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  parentId: undefined,
  name: undefined,
  comment: undefined,
  type: 0
})
const formRules = reactive({
  parentId: [{ required: true, message: '上级分组不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '名称不能为空', trigger: 'blur' }],
  comment: [{ required: true, message: '描述不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const moduleTree = ref() // 树形结构

/** 打开弹窗 */
const open = async (type: string, id?: string) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await CodegenApi.getMoudle(id)
    } finally {
      formLoading.value = false
    }
  }
  // 获得模块树
  await getTree()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as CodegenApi.InterfaceModuleVO
    if (formType.value === 'create') {
      console.log(data)
      await CodegenApi.createInterfaceModule(data)
      message.success(t('common.createSuccess'))
    } else {
      await CodegenApi.updateInterfaceModule(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    parentId: undefined,
    name: undefined,
    comment: undefined,
    type: 0
  }
  formRef.value?.resetFields()
}

/** 获得模块树 */
const getTree = async () => {
  moduleTree.value = []
  const data = await CodegenApi.getSimpleList()
  moduleTree.value = handleTree(data)
}
</script>

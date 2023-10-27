import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
// 表单校验
export const rules = reactive({
  name: [required],
  code: [required],
  accountId: [required],
  label: [required],
  content: [required],
  params: [required],
  status: [required]
})

// CrudSchema：https://doc.iocoder.cn/vue3/crud-schema/
const crudSchemas = reactive<CrudSchema[]>([
  {
    label: '模板编码',
    prop: 'code',
    field: 'code',
    isSearch: true
  },
  {
    label: '模板名称',
    prop: 'name',
    field: 'name',
    isSearch: true
  },
  {
    label: '模板标题',
    prop: 'title',
    field: 'title'
  },
  {
    label: '模板内容',
    prop: 'content',
    field: 'code',
    form: {
      component: 'Editor',
      componentProps: {
        valueHtml: '',
        height: 200
      }
    }
  },
  {
    label: '邮箱账号',
    prop: 'accountId',
    field: 'accountId',
    width: '200px'
  },
  {
    label: '发送人名称',
    field: 'nickname',
    prop: 'nickname'
  },
  {
    label: '开启状态',
    prop: 'status',
    field: 'status',
    isSearch: true,
    dictType: DICT_TYPE.COMMON_STATUS,
    dictClass: 'number'
  },
  {
    label: '备注',
    prop: 'remark',
    field: 'remark',
    isTable: false
  },
  {
    label: '创建时间',
    field: 'createTime',
    prop: 'createTime',
    isForm: false
  }
])
export const { allSchemas } = useCrudSchemas(crudSchemas)

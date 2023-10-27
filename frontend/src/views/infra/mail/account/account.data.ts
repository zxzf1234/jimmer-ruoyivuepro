import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { dateFormatter } from '@/utils/formatTime'
const { t } = useI18n() // 国际化

// 表单校验
export const rules = reactive({
  mail: [
    { required: true, message: t('profile.rules.mail'), trigger: 'blur' },
    {
      type: 'email',
      message: t('profile.rules.truemail'),
      trigger: ['blur', 'change']
    }
  ],
  username: [required],
  password: [required],
  host: [required],
  port: [required],
  sslEnable: [required]
})

// CrudSchema：https://doc.iocoder.cn/vue3/crud-schema/
const crudSchemas = reactive<CrudSchema[]>([
  {
    label: '邮箱',
    field: 'mail',
    prop: 'mail',
    isSearch: true
  },
  {
    label: '用户名',
    field: 'username',
    prop: 'username',
    isSearch: true
  },
  {
    label: '密码',
    field: 'password',
    prop: 'password',
    isTable: false
  },
  {
    label: 'SMTP 服务器域名',
    field: 'host',
    prop: 'host'
  },
  {
    label: 'SMTP 服务器端口',
    field: 'port',
    prop: 'port',
    form: {
      component: 'InputNumber',
      value: 465
    }
  },
  {
    label: '是否开启 SSL',
    field: 'sslEnable',
    prop: 'sslEnable',
    dictType: DICT_TYPE.INFRA_BOOLEAN_STRING,
    dictClass: 'boolean',
    form: {
      component: 'Radio'
    }
  },
  {
    label: '创建时间',
    field: 'createTime',
    prop: 'createTime',
    isForm: false,
    formatter: dateFormatter,
    detail: {
      dateFormat: 'YYYY-MM-DD HH:mm:ss'
    }
  }
])
export const { allSchemas } = useCrudSchemas(crudSchemas)

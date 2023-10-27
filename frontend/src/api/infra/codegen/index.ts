import request from '@/config/axios'

export type DatabaseTableVO = {
  id: string
  name: string
  comment: string
  businessName: string
  remark: string
  columns: DatabaseColumnVO[]
  indexes: DatabaseIndexVO[]
  mappings: DatabaseMappingVO[]
}
export type InterfaceValidationVO = {
  id: string
  parentId: string
  validation: string
  validationCondition: string
  message: string
  operateType: string | undefined
}

export type DatabaseColumnVO = {
  id: string
  tableId: string
  columnName: string
  dataType: string
  columnComment: string
  nullable: number
  defaultValue: string
  javaType: string
  dictType: string
  example: string
  relatedTable: string
  required: number
  sort: number
  operateType: string | undefined
  validations: InterfaceValidationVO[]
}

export type DatabaseIndexVO = {
  id: string
  tableId: string
  indexType: string
  indexName: string
  columnNames: string
  operateType: string | undefined
}

export type DatabaseMappingVO = {
  id: string
  tableId: string
  name: string
  isList: number
  annotate: string
  mappingTable: string
  operateType: string | undefined
}

export type CodegenCreateListReqVO = {
  dataSourceConfigId: number
  tableNames: string[]
}

export type InterfaceModuleVO = {
  id: string
  name: string
  comment: string
  type: number
  parentId: string
}

export type InterfaceParamVO = {
  id: string
  name: string
  comment: string
  isList: number
  variableType: string
  relatedColumn: string
  relatedId: string
  relatedType: number
  example: string
  required: number
  parentId: string
  parentType: number
  inoutType: number
  operateType: string | undefined
  validations: InterfaceValidationVO[]
}

export type InterfaceSubclassVO = {
  id: string
  name: string
  comment: string
  inheritClass: string
  inheritType: number
  parentId: string
  operateType: string | undefined
  subclassParams: InterfaceParamVO[]
}

export type InterfaceVO = {
  id: string
  name: string
  comment: string
  method: string
  authorize: string
  isTransaction: number
  moduleId: string
  moduleName: string
  inputType: string
  inputExtendClass: string
  inputServlet: number
  outputType: string
  outputExtendClass: string
  createTime: string
  operateType: string | undefined
  inputParams: InterfaceParamVO[]
  outputParams: InterfaceParamVO[]
  inputSubclasses: InterfaceSubclassVO[]
  outputSubclasses: InterfaceSubclassVO[]
}

export type InterfaceVoClassVO = {
  id: string
  name: string
  type: number
  parentId: string
}

// 查询数据库表
export const getDatabaseTableList = (params) => {
  return request.get({ url: '/infra/codegen/database-table/list', params })
}

// 查询数据库表字段
export const getDatabaseTableColumnList = (params) => {
  return request.get({ url: '/infra/codegen/database-table/column_list', params })
}

// 新增数据库表
export const createDatabaseTable = (data: DatabaseTableVO) => {
  return request.post({ url: '/infra/codegen/database-table/create', data })
}

// 获取数据库表详情
export const getDatabaseTable = (id: string) => {
  return request.get({ url: '/infra/codegen/database-table/get?id=' + id })
}

// 更新数据库表
export const updateDatabaseTable = (data: DatabaseTableVO) => {
  return request.post({ url: '/infra/codegen/database-table/update', data })
}

// 编辑接口模块
export const editInterfaceModule = (data: InterfaceModuleVO) => {
  return request.post({ url: '/infra/codegen/interface-module/update', data })
}

// 查询接口模块
export const getInterfaceModuleList = (params) => {
  return request.get({ url: '/infra/codegen/interface-module/list', params })
}

// 新建接口模块
export const createInterfaceModule = (data: InterfaceModuleVO) => {
  return request.post({ url: '/infra/codegen/interface-module/create', data })
}

// 更新接口模块
export const updateInterfaceModule = (data: InterfaceModuleVO) => {
  return request.post({ url: '/infra/codegen/interface-module/update', data })
}

// 查询接口模块（精简)列表
export const getSimpleList = () => {
  return request.get({ url: '/infra/codegen/interface-module/list-all-simple' })
}

// 查询单个接口模块
export const getMoudle = (id: string) => {
  return request.get({ url: '/infra/codegen/interface-module/get?id=' + id })
}

// 删除接口模块
export const deleteModule = (id: string) => {
  return request.delete({ url: '/infra/codegen/interface-module/delete?id=' + id })
}

// 查询接口
export const getInterfaceList = (params) => {
  return request.get({ url: '/infra/codegen/interface/list', params })
}

// 新建接口
export const createInterface = (data: InterfaceVO) => {
  return request.post({ url: '/infra/codegen/interface/create', data })
}

// 更新接口
export const updateInterface = (data: InterfaceVO) => {
  return request.post({ url: '/infra/codegen/interface/update', data })
}

// 查询单个接口
export const getInterface = (id: string) => {
  return request.get({ url: '/infra/codegen/interface/get?id=' + id })
}

//查询VO类
export const getInterfaceVOClassList = (params) => {
  return request.get({ url: '/infra/codegen/interface/vo-class/list', params })
}

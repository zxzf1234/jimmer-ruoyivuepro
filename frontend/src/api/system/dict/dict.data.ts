import request from '@/config/axios'

export type DictDataVO = {
  id: string | undefined
  sort: number | undefined
  label: string
  value: string
  dictType: string
  status: number
  colorType: string
  cssClass: string
  remark: string
  createTime: Date
}

// 查询字典数据（精简)列表
export const listSimpleDictData = () => {
  return request.get({ url: '/infra/data/dict-data/list-all-simple' })
}

// 查询字典数据列表
export const getDictDataPage = (params: PageParam) => {
  return request.get({ url: '/infra/data/dict-data/page', params })
}

// 查询字典数据详情
export const getDictData = (id: string) => {
  return request.get({ url: '/infra/data/dict-data/get?id=' + id })
}

// 新增字典数据
export const createDictData = (data: DictDataVO) => {
  return request.post({ url: '/infra/data/dict-data/create', data })
}

// 修改字典数据
export const updateDictData = (data: DictDataVO) => {
  return request.put({ url: '/infra/data/dict-data/update', data })
}

// 删除字典数据
export const deleteDictData = (id: number) => {
  return request.delete({ url: '/infra/data/dict-data/delete?id=' + id })
}

// 导出字典类型数据
export const exportDictData = (params) => {
  return request.download({ url: '/infra/data/dict-data/export', params })
}

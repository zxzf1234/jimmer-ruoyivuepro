import request from '@/config/axios'

export type DictTypeVO = {
  id: string | undefined
  name: string
  type: string
  status: number
  remark: string
  createTime: Date
}

// 查询字典（精简)列表
export const getSimpleDictTypeList = () => {
  return request.get({ url: '/infra/data/dict-type/list-all-simple' })
}

// 查询字典列表
export const getDictTypePage = (params: PageParam) => {
  return request.get({ url: '/infra/data/dict-type/page', params })
}

// 查询字典详情
export const getDictType = (id: string) => {
  return request.get({ url: '/infra/data/dict-type/get?id=' + id })
}

// 新增字典
export const createDictType = (data: DictTypeVO) => {
  return request.post({ url: '/infra/data/dict-type/create', data })
}

// 修改字典
export const updateDictType = (data: DictTypeVO) => {
  return request.put({ url: '/infra/data/dict-type/update', data })
}

// 删除字典
export const deleteDictType = (id: string) => {
  return request.delete({ url: '/infra/data/dict-type/delete?id=' + id })
}
// 导出字典类型
export const exportDictType = (params) => {
  return request.download({ url: '/infra/data/dict-type/export', params })
}

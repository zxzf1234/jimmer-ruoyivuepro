import request from '@/config/axios'

export interface SmsChannelVO {
  id: number
  code: string
  status: number
  signature: string
  remark: string
  apiKey: string
  apiSecret: string
  callbackUrl: string
  createTime: Date
}

// 查询短信渠道列表
export const getSmsChannelPage = (params: PageParam) => {
  return request.get({ url: '/infra/sms-channel/page', params })
}

// 获得短信渠道精简列表
export function getSimpleSmsChannelList() {
  return request.get({ url: '/infra/sms-channel/list-all-simple' })
}

// 查询短信渠道详情
export const getSmsChannel = (id: number) => {
  return request.get({ url: '/infra/sms-channel/get?id=' + id })
}

// 新增短信渠道
export const createSmsChannel = (data: SmsChannelVO) => {
  return request.post({ url: '/infra/sms-channel/create', data })
}

// 修改短信渠道
export const updateSmsChannel = (data: SmsChannelVO) => {
  return request.put({ url: '/infra/sms-channel/update', data })
}

// 删除短信渠道
export const deleteSmsChannel = (id: number) => {
  return request.delete({ url: '/infra/sms-channel/delete?id=' + id })
}

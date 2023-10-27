import request from '@/config/axios'

export interface MailLogVO {
  id: number
  userId: number
  userType: number
  toMail: string
  accountId: number
  fromMail: string
  templateId: number
  templateCode: string
  templateNickname: string
  templateTitle: string
  templateContent: string
  templateParams: string
  sendStatus: number
  sendTime: Date
  sendMessageId: string
  sendException: string
}

// 查询邮件日志列表
export const getMailLogPage = async (params) => {
  return await request.get({ url: '/infra/mail-log/page', params })
}

// 查询邮件日志详情
export const getMailLog = async (id: number) => {
  return await request.get({ url: '/infra/mail-log/get?id=' + id })
}

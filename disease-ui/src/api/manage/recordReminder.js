import request from '@/utils/request'

// 查询病历提醒列表
export function listRecordReminder(query) {
  return request({
    url: '/manage/recordReminder/list',
    method: 'get',
    params: query
  })
}

// 查询病历提醒详细
export function getRecordReminder(reminderId) {
  return request({
    url: '/manage/recordReminder/' + reminderId,
    method: 'get'
  })
}

// 新增病历提醒
export function addRecordReminder(data) {
  return request({
    url: '/manage/recordReminder',
    method: 'post',
    data: data
  })
}

// 修改病历提醒
export function updateRecordReminder(data) {
  return request({
    url: '/manage/recordReminder',
    method: 'put',
    data: data
  })
}

// 删除病历提醒
export function delRecordReminder(reminderId) {
  return request({
    url: '/manage/recordReminder/' + reminderId,
    method: 'delete'
  })
}

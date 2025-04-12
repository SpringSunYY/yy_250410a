import request from '@/utils/request'

// 查询数据备份记录列表
export function listDataBackup(query) {
  return request({
    url: '/manage/dataBackup/list',
    method: 'get',
    params: query
  })
}

// 查询数据备份记录详细
export function getDataBackup(backupId) {
  return request({
    url: '/manage/dataBackup/' + backupId,
    method: 'get'
  })
}

// 新增数据备份记录
export function addDataBackup(data) {
  return request({
    url: '/manage/dataBackup',
    method: 'post',
    data: data
  })
}

// 修改数据备份记录
export function updateDataBackup(data) {
  return request({
    url: '/manage/dataBackup',
    method: 'put',
    data: data
  })
}

// 删除数据备份记录
export function delDataBackup(backupId) {
  return request({
    url: '/manage/dataBackup/' + backupId,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询病历信息列表
export function listMedicalRecord(query) {
  return request({
    url: '/manage/medicalRecord/list',
    method: 'get',
    params: query
  })
}

// 查询病历信息详细
export function getMedicalRecord(recordId) {
  return request({
    url: '/manage/medicalRecord/' + recordId,
    method: 'get'
  })
}

// 新增病历信息
export function addMedicalRecord(data) {
  return request({
    url: '/manage/medicalRecord',
    method: 'post',
    data: data
  })
}

// 修改病历信息
export function updateMedicalRecord(data) {
  return request({
    url: '/manage/medicalRecord',
    method: 'put',
    data: data
  })
}

// 删除病历信息
export function delMedicalRecord(recordId) {
  return request({
    url: '/manage/medicalRecord/' + recordId,
    method: 'delete'
  })
}

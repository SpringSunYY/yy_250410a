import request from '@/utils/request'

// 查询病历模板列表
export function listTemplateInfo(query) {
  return request({
    url: '/manage/templateInfo/list',
    method: 'get',
    params: query
  })
}

// 查询病历模板详细
export function getTemplateInfo(templateId) {
  return request({
    url: '/manage/templateInfo/' + templateId,
    method: 'get'
  })
}

// 新增病历模板
export function addTemplateInfo(data) {
  return request({
    url: '/manage/templateInfo',
    method: 'post',
    data: data
  })
}

// 修改病历模板
export function updateTemplateInfo(data) {
  return request({
    url: '/manage/templateInfo',
    method: 'put',
    data: data
  })
}

// 删除病历模板
export function delTemplateInfo(templateId) {
  return request({
    url: '/manage/templateInfo/' + templateId,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询套餐审核列表
export function listAudit(query) {
  return request({
    url: '/flowdefine/audit/list',
    method: 'get',
    params: query
  })
}

// 查询套餐审核详细
export function getAudit(id) {
  return request({
    url: '/flowdefine/audit/' + id,
    method: 'get'
  })
}

// 新增套餐审核
export function addAudit(data) {
  return request({
    url: '/flowdefine/audit',
    method: 'post',
    data: data
  })
}

// 修改套餐审核
export function updateAudit(data) {
  return request({
    url: '/flowdefine/audit',
    method: 'put',
    data: data
  })
}

// 删除套餐审核
export function delAudit(id) {
  return request({
    url: '/flowdefine/audit/' + id,
    method: 'delete'
  })
}

// 进度查看
export function progressAudit(id) {
  return request({
    url: '/flowdefine/audit/progressAudit/' + id,
    method: 'get'
  })
}


// 测试
export function testOne() {
  return request({
    url: '/flowdefine/audit/test',
    method: 'get'
  })
}

// 审批历史
export function historyAudit(id) {
  return request({
    url: `/flowdefine/audit/history/${id}`,
    method: 'get'
  })
}

// 撤销审核
export function cancelAudit(id) {
  return request({
    url: `/flowdefine/audit/cancel/${id}`,
    method: 'post'
  })
}


// 我的待办列表
export function todoAudit(query) {
  return request({
    url: `/flowdefine/audit/todoQuery`,
    method: 'get',
    params: query
  })
}

export function packageReviewAudit(param) {
  return request({
    url: '/flowdefine/audit/packageReview',
    method: 'post',
    data:param
  })
}

export function doneAudit(query) {
  return request({
    url: '/flowdefine/audit/doneAudit',
    method: 'get',
    params:query
  })
}

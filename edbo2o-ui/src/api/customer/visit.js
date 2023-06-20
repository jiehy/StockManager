import request from '@/utils/request'

// 查询客户拜访列表
export function listVisit(query) {
  return request({
    url: '/customer/visit/list',
    method: 'get',
    params: query
  })
}

// 查询客户拜访详细
export function getVisit(id) {
  return request({
    url: '/customer/visit/' + id,
    method: 'get'
  })
}

// 新增客户拜访
export function addVisit(data) {
  return request({
    url: '/customer/visit',
    method: 'post',
    data: data
  })
}

// 修改客户拜访
export function updateVisit(data) {
  return request({
    url: '/customer/visit',
    method: 'put',
    data: data
  })
}

// 删除客户拜访
export function delVisit(id) {
  return request({
    url: '/customer/visit/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询客户档案列表
export function listCustomer(query) {
  return request({
    url: '/archives/customer/list',
    method: 'get',
    params: query
  })
}

// 查询录入人列表
export function enterCustomer() {
  return request({
    url: '/archives/customer/enter',
    method: 'get',
  })
}

// 查询客户档案详细
export function getCustomer(id) {
  return request({
    url: '/archives/customer/' + id,
    method: 'get'
  })
}

// 新增客户档案
export function addCustomer(data) {
  return request({
    url: '/archives/customer',
    method: 'post',
    data: data
  })
}

// 修改客户档案
export function updateCustomer(data) {
  return request({
    url: '/archives/customer',
    method: 'put',
    data: data
  })
}

// 删除客户档案
export function delCustomer(id) {
  return request({
    url: '/archives/customer/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询客户基本信息管理列表
export function listInformation(query) {
  return request({
    url: '/customer/information/list',
    method: 'get',
    params: query
  })
}

// 查询客户基本信息管理详细
export function getInformation(id) {
  return request({
    url: '/customer/information/' + id,
    method: 'get'
  })
}

// 新增客户基本信息管理
export function addInformation(data) {
  return request({
    url: '/customer/information',
    method: 'post',
    data: data
  })
}

// 修改客户基本信息管理
export function updateInformation(data) {
  return request({
    url: '/customer/information',
    method: 'put',
    data: data
  })
}

// 删除客户基本信息管理
export function delInformation(id) {
  return request({
    url: '/customer/information/' + id,
    method: 'delete'
  })
}

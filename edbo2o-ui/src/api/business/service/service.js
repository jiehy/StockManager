import request from '@/utils/request'

// 查询服务项列表
export function listService(query) {
  return request({
    url: '/service/service/list',
    method: 'get',
    params: query
  })
}

// 查询服务项详细
export function getService(id) {
  return request({
    url: '/service/service/' + id,
    method: 'get'
  })
}

// 新增服务项
export function addService(data) {
  return request({
    url: '/service/service',
    method: 'post',
    data: data
  })
}

// 修改服务项
export function updateService(data) {
  return request({
    url: '/service/service',
    method: 'put',
    data: data
  })
}

// 删除服务项
export function delService(id) {
  return request({
    url: '/service/service/' + id,
    method: 'delete'
  })
}

// 上架服务项
export function saleOn(id) {
  return request({
    url: '/service/service/saleOn/' + id,
    method: 'put'
  })
}

// 下架服务项
export function saleOff(id) {
  return request({
    url: '/service/service/saleOff/' + id,
    method: 'put'
  })
}

export function auditInfoService(id) {
  return request({
    url: '/service/service/audit/' + id,
    method: 'get'
  })
}

export function auditService(params) {
  return request({
    url: '/service/service/subAudit',
    method: 'post',
    data: params
  })
}

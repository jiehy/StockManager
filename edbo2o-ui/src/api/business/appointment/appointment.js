import request from '@/utils/request'

// 查询养修信息预约列表
export function listAppointment(query) {
  return request({
    url: '/appointment/appointment/list',
    method: 'get',
    params: query
  })
}

// 查询养修信息预约详细
export function getAppointment(id) {
  return request({
    url: '/appointment/appointment/' + id,
    method: 'get'
  })
}

// 新增养修信息预约
export function addAppointment(data) {
  return request({
    url: '/appointment/appointment',
    method: 'post',
    data: data
  })
}

// 修改养修信息预约
export function updateAppointment(data) {
  return request({
    url: '/appointment/appointment',
    method: 'put',
    data: data
  })
}

// 删除养修信息预约
export function delAppointment(id) {
  return request({
    url: '/appointment/appointment/' + id,
    method: 'delete'
  })
}

// 批量删除养修信息预约
export function batchDelAppointment(id) {
  return request({
    url: '/appointment/appointment/batchDelete/' + id,
    method: 'delete'
  })
}

// 到店养修信息预约
export function arrlAppointment(id) {
  return request({
    url: '/appointment/appointment/arrl/' + id,
    method: 'get'
  })
}

// 用户取消养修信息预约
export function closeAppointment(id) {
  return request({
    url: '/appointment/appointment/close/' + id,
    method: 'get'
  })
}

// 生成结算单养修信息预约
export function getSettlement(id) {
  return request({
    url: '/appointment/appointment/settlement/' + id,
    method: 'put',
  })
}

// 生成结算单养修信息预约
export function syncAppointment(data) {
  return request({
    url: '/appointment/appointment/sync',
    method: 'post',
    data: data
  })
}

import request from '@/utils/request'

// 查询分类管理列表
export function listManagement(query) {
  return request({
    url: '/classify/management/list',
    method: 'get',
    params: query
  })
}

// 查询上级分类列表
export function superiorList() {
  return request({
    url: '/classify/management/superior',
    method: 'get',
  })
}

// 查询全部列表
export function allList() {
  return request({
    url: '/classify/management/all',
    method: 'get',
  })
}

// 查询分类管理详细
export function getManagement(id) {
  return request({
    url: '/classify/management/' + id,
    method: 'get'
  })
}

// 新增分类管理
export function addManagement(data) {
  return request({
    url: '/classify/management',
    method: 'post',
    data: data
  })
}

// 修改分类管理
export function updateManagement(data) {
  return request({
    url: '/classify/management',
    method: 'put',
    data: data
  })
}

// 删除分类管理
export function delManagement(id) {
  return request({
    url: '/classify/management/' + id,
    method: 'delete'
  })
}

// 分类迁移
export function moveManagement(data) {
  return request({
    url: '/classify/management/move',
    method: 'put',
    data: data
  })
}

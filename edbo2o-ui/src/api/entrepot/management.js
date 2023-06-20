import request from '@/utils/request'

// 查询仓库管理列表
export function listManagement(query) {
  return request({
    url: '/entrepot/management/list',
    method: 'get',
    params: query
  })
}

// 查询仓库管理详细
export function getManagement(id) {
  return request({
    url: '/entrepot/management/' + id,
    method: 'get'
  })
}

// 新增仓库管理
export function addManagement(data) {
  return request({
    url: '/entrepot/management',
    method: 'post',
    data: data
  })
}

// 修改仓库管理
export function updateManagement(data) {
  return request({
    url: '/entrepot/management',
    method: 'put',
    data: data
  })
}

// 删除仓库管理
export function delManagement(id) {
  return request({
    url: '/entrepot/management/' + id,
    method: 'delete'
  })
}

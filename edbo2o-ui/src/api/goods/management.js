import request from '@/utils/request'

// 查询物品基本信息列表
export function listGoodsManagement(query) {
  return request({
    url: '/goods/management/list',
    method: 'get',
    params: query
  })
}

// 查询物品基本信息列表
export function AllListGoodsManagement() {
  return request({
    url: '/goods/management/AllList',
    method: 'get',
  })
}

// 查询物品基本信息详细
export function getManagement(id) {
  return request({
    url: '/goods/management/' + id,
    method: 'get'
  })
}

// 新增物品基本信息
export function addManagement(data) {
  return request({
    url: '/goods/management',
    method: 'post',
    data: data
  })
}

// 修改物品基本信息
export function updateManagement(data) {
  return request({
    url: '/goods/management',
    method: 'put',
    data: data
  })
}

// 删除物品基本信息
export function delManagement(id) {
  return request({
    url: '/goods/management/' + id,
    method: 'delete'
  })
}

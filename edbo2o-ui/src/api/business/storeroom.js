import request from '@/utils/request'

// 查询出入库管理列表
export function listStoreroom(query) {
  return request({
    url: '/warehouse/storeroom/list',
    method: 'get',
    params: query
  })
}

// 查询出入库管理详细
export function getStoreroom(id) {
  return request({
    url: '/warehouse/storeroom/' + id,
    method: 'get'
  })
}

// 新增出入库管理
export function addStoreroom(data) {
  return request({
    url: '/warehouse/storeroom',
    method: 'post',
    data: data
  })
}

// 修改出入库管理
export function updateStoreroom(data) {
  return request({
    url: '/warehouse/storeroom',
    method: 'put',
    data: data
  })
}

// 删除出入库管理
export function delStoreroom(id) {
  return request({
    url: '/warehouse/storeroom/' + id,
    method: 'delete'
  })
}

// 出入库管理作废
export function invalidStoreroom(id) {
  return request({
    url: '/warehouse/storeroom/' + id,
    method: 'put'
  })
}

import request from '@/utils/request'

// 查询客户联系人列表
export function listPerson(query) {
  return request({
    url: '/person/person/list',
    method: 'get',
    params: query
  })
}

// 查询客户联系人详细
export function getPerson(id) {
  return request({
    url: '/person/person/' + id,
    method: 'get'
  })
}

// 新增客户联系人
export function addPerson(data) {
  return request({
    url: '/person/person',
    method: 'post',
    data: data
  })
}

// 修改客户联系人
export function updatePerson(data) {
  return request({
    url: '/person/person',
    method: 'put',
    data: data
  })
}

// 删除客户联系人
export function delPerson(id) {
  return request({
    url: '/person/person/' + id,
    method: 'delete'
  })
}

// 所属客户
export function affiliationCustomers() {
  return request({
    url: '/person/person/affiliation',
    method: 'get',
  })
}

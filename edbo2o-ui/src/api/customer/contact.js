import request from '@/utils/request'

// 查询客户联系人列表
export function listContact(query) {
  return request({
    url: '/customer/contact/list',
    method: 'get',
    params: query
  })
}

// 查询客户联系人详细
export function getContact(id) {
  return request({
    url: '/customer/contact/' + id,
    method: 'get'
  })
}

// 新增客户联系人
export function addContact(data) {
  return request({
    url: '/customer/contact',
    method: 'post',
    data: data
  })
}

// 修改客户联系人
export function updateContact(data) {
  return request({
    url: '/customer/contact',
    method: 'put',
    data: data
  })
}

// 删除客户联系人
export function delContact(id) {
  return request({
    url: '/customer/contact/' + id,
    method: 'delete'
  })
}


// 遍历企业
export function customerList() {
  return request({
    url: '/customer/contact/customerList',
    method: 'get'
  })
}

// 遍历联系人
export function contactList(customerName) {
  return request({
    url: '/customer/contact/contactList?customerName=' + customerName,
    method: 'get',
  })
}

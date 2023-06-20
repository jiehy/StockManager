import request from '@/utils/request'

// 查询合同管理列表
export function listContractManagement(query) {
  return request({
    url: '/business/contract/list',
    method: 'get',
    params: query
  })
}

// 查询合同管理详细
export function getContractManagement(id) {
  return request({
    url: '/business/contract/' + id,
    method: 'get'
  })
}

// 新增合同管理
export function addContractManagement(data) {
  return request({
    url: '/business/contract',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data;charset=UTF-8'
    },
    data: data
  })
}

// 修改合同管理
export function updateContractManagement(data) {
  return request({
    url: '/business/contract/',
    method: 'put',
    headers: {
      'Content-Type': 'multipart/form-data;charset=UTF-8'
    },
    data: data
  })
}

// 删除合同管理
export function delContractManagement(id) {
  return request({
    url: '/business/contract/' + id,
    method: 'delete'
  })
}

//审批通过合同
export function auditSuccess(id) {
  return request({
    url: `/business/contract/auditSuccess/${id}`,
    method: 'get',
  })
}

//审批不通过合同
export function auditPass(id) {
  return request({
    url: `/business/contract/auditPass/${id}`,
    method: 'get',
  })
}

//确认盖章
export function toStamp(id) {
  return request({
    url: `/business/contract/toStamp/${id}`,
    method: 'get',
  })
}

//合同作废
export function invalid(id) {
  return request({
    url: `/business/contract/invalid/${id}`,
    method: 'get',
  })
}

//合同作废
// 查询客户集合
export function getCustomerList() {
  return request({
    url: '/business/contract/getCustomerList',
    method: 'get'
  })
}

//合同作废
export function downloadFile(id) {
  return request({
    url: `/business/contract/download/${id}`,
    method: 'get',
  })
}





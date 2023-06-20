import request from '@/utils/request'

// 查询审核流程定义列表
export function listBpmninfo(query) {
  return request({
    url: '/flowdefine/bpmninfo/list',
    method: 'get',
    params: query
  })
}

// 查询审核流程定义详细
export function getBpmninfo(id) {
  return request({
    url: '/flowdefine/bpmninfo/' + id,
    method: 'get'
  })
}

// 新增审核流程定义
export function addBpmninfo(data) {
  return request({
    url: '/flowdefine/bpmninfo',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data: data
  })
}

// 修改审核流程定义
export function updateBpmninfo(data) {
  return request({
    url: '/flowdefine/bpmninfo',
    method: 'put',
    data: data
  })
}

// 取消审核流程定义
export function cancelBpmninfo(id) {
  return request({
    url: '/flowdefine/bpmninfo/' + id,
    method: 'delete'
  })
}

// 查看审核流程定义资源
export function resourcesBpmninfo(id,type) {
  return request({
    url: `/flowdefine/bpmninfo/${id}/${type}`,
    method: 'get'
  })
}

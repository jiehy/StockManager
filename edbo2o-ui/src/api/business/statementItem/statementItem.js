import request from '@/utils/request'

export function payStatement(id) {
  return request({
    url: '/appointment/statementItem/pay/'+id,
    method: 'put',
  })
}

export function listStatementItem(query) {
  return request({
    url: '/appointment/statementItem',
    method: 'get',
    params: query
  })
}

export function addStatementItem(query) {
  return request({
    url: '/appointment/statementItem',
    method: 'post',
    data: query
  })
}

export function getQRcode() {
  return request({
    url: '/appointment/statementItem/QRCode',
    method: 'put',
  })
}

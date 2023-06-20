import request from '@/utils/request'

//消费单
export function statisticShop(params) {
  return request({
    url: '/report/shop',
    method: 'get',
    params
  })
}

//统计金额
export function statMoney(params) {
  return request({
    url: '/report/shop/countMoney',
    method: 'get',
    params
  })
}

//手机号码，消费金额
export function statisticCustomer(params) {
  return request({
    url: '/report/customer',
    method: 'get',
    params
  })
}

//客户消费金额
export function userMoney(params) {
  return request({
    url: '/report/shop/userMoney',
    method: 'get',
    params
  })
}

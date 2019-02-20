//index.js
//获取应用实例
// 初审2 付款0 已付款1

const app = getApp()

Page({
    data: {
        openId: '',
        inputShowed: false,
        inputVal: "",
        searchList: [{
            text: "实时搜索文本",
            value: "1",
            path: ""
        }, {
            text: "实时搜索文本",
            value: "1",
            path: ""
        }, {
            text: "实时搜索文本",
            value: "1",
            path: ""
        }, {
            text: "实时搜索文本",
            value: "1",
            path: ""
        }],
        searchResult: [],
        map: {
            // fail: '不通过',
            // success: '通过',
            // wait: '待审核',
            // firstTrailToPay: '初审待付款',
            // firstTrailPayed: '初审已付款',
            // finalTrailToPayPre: '终审通过预付款',
            // finalTrailToPaySuffix: '终审通过后付款',
            // finalTrailPayed: '终审通过已付款'
            '0': '待初审',
            '1': '初审通过待付款',
            '2': '初审通过已付款',
            '3': '初审不通过',
            '4': '终审通过待付款',
            '5': '终审通过已付款',
            '6': '终审不通过',
            '7': '待终审',
            '8': '终审通过待付款',
            '9': '终审通过已付款',
            '10': '终审不通过',
            '11': '确定电话预约时间点',
            '12': '已确定电话预约时间点',
            '13': '二审通过',
            '14': '待补充资料',
            '15': '已补充资料',
            '16': '初审待审批',
            '17': '初审不通过',
            '18': '您已拒绝'
        },
        urlList: {
            0: '../payOrderDetail/payOrderDetail?orderId=',
            1: '../payedOrderDetail/payedOrderDetail?orderId=',
            2: '../orderDetail/orderDetail?orderId=',
          11: '../telAppointment/telAppointment?orderId='
        },
        orders: {},
        movies: [
            { url: 'http://img04.tooopen.com/images/20130712/tooopen_17270713.jpg' },
            { url: 'http://img04.tooopen.com/images/20130617/tooopen_21241404.jpg' },
            { url: 'http://img04.tooopen.com/images/20130701/tooopen_20083555.jpg' },
            { url: 'http://img02.tooopen.com/images/20141231/sy_78327074576.jpg' }
        ]
    },
    onShow: function () {
        var that = this;
        if (!wx.getStorageSync('openId')) {
            app.getOpenId().then(function (res) {
                if (res.status == 200) {
                    that.setData({
                      openId: wx.setStorageSync('openId', res.data)
                    })
                    that.queryLawOrder()
                }
            });
        } else {
            that.setData({
                openId: wx.getStorageSync('openId')
            })
            that.queryLawOrder()
        }
    },
    queryLawOrder: function () {
        const that = this;
        wx.showLoading({
          title: '加载中',
        })
        wx.request({
            url: app.globalData.url + 'queryLawOrderByOpenId',
            header: { 'Content-Type': 'application/json' },
            data: {
                openId: this.data.openId
            },
            method: 'POST',
            success: res => {
                wx.hideLoading()
                // 测试代码，为初审页面添加step属性
                for (let i = 0; i < res.data.length; i++) {
                    //待审核、审核不通过跳转报单详情页面
                  let status = res.data[i].status;
                  if (status == '0' || status == '3' || status == '6' || status == '7' || status == '10' || status == '14' || status == '16' || status == '17') {
                        res.data[i]['step'] = 2
                    } else if (res.data[i].status == '1' || res.data[i].status == '4' || res.data[i].status == '8') {
                        res.data[i]['step'] = 0
                    } else if (res.data[i].status == '11' ){
                      res.data[i]['step'] = 11;//电话预约时间确定环节
                    } else {
                        res.data[i]['step'] = 1
                    }
                }
                // const obj = {
                //   searchResult: that.data.searchResult.concat(res.data)
                // }
                // this.setData(obj);
                // -----------------------------
                // 真实代码
                this.setData({
                    searchResult: res.data
                })
                // -----------------------------
            }
        })
    }
})

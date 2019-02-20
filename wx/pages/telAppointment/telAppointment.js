const app = getApp()
const date = new Date();
const years = [];
const months = [];
const days = [];
const hours = [];
const minutes = [];
//获取年
for (let i = 2019; i <= date.getFullYear() + 5; i++) {
  years.push("" + i);
}
//获取月份
for (let i = 1; i <= 12; i++) {
  if (i < 10) {
    i = "0" + i;
  }
  months.push("" + i);
}
//获取日期
for (let i = 1; i <= 31; i++) {
  if (i < 10) {
    i = "0" + i;
  }
  days.push("" + i);
}
//获取小时
for (let i = 0; i < 24; i++) {
  if (i < 10) {
    i = "0" + i;
  }
  hours.push("" + i);
}
//获取分钟
for (let i = 0; i < 60; i++) {
  if (i < 10) {
    i = "0" + i;
  }
  minutes.push("" + i);
}

Page({

  /**
   * 页面的初始数据
   */
  data: {
    untilNow: true,
    edit: true,
    statusMap: {
      // success: '已接单',
      // fail: '已驳回',
      // wait: '待审核'
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
      '10': '终审不通过'
    },
    statusImgMap: {
      '2': '../../images/detail/xqright.png',//sucess
      '5': '../../images/detail/xqright.png',//sucess
      '3': '../../images/detail/xqwrong.png',//fail
      '6': '../../images/detail/xqwrong.png',//fail
      '0': ''//wait
      // '2': ''//wait
    },
    people: true, //当事人显示隐藏
    oppPeople: true, //对方当事人显示隐藏,
    relatePeople: true, //关系人显示隐藏
    array: ['是', '否'],
    orderId: '',
    openId: '',               //openId
    status: 'wait',           //处理状态
    startTime: '',            //开始时间
    endTime: '',              //结束时间
    lawOrder: {
      acceptAddress: null,
      acceptContracts: null,
      acceptDate: null,
      acceptPhone: null,
      acceptRemark: null,
      appeal: "是的发生",
      doneCode: "20180920492365420456710144",
      doneDate: "2018-09-20 16:04:23",
      endTime: "2018-09-20 16:04:23",
      files: "",
      happen: "安徽啊",
      involvingMoney: 0,
      openId: "oBJPM4nmLexOEgu2zDAzviRxkL8U",
      orderId: 10012,
      photos: "",
      place: "上海",
      possessions: "发达",
      refuseReason: null,
      startTime: "2018-07-20",
      status: "wait",
      videos: "",
      caseBaseLevel: "",
      caseSubLevel: ""
    },
    radioItems: [{
      name: '预约时间1',
      value: 0,
      checked: false
    },
     {
        name: '预约时间2',
        value: 1,
        checked: false
      }, {
        name: '预约时间3',
        value: 2,
        checked: false
      }, {
        name: '自定义',
        value: 3,
        checked: true
      }],
    time: '',
    multiArray: [years, months, days, hours, minutes],
    multiIndex: [new Date().getFullYear()-years[0], new Date().getMonth(), new Date().getDate() - 1, new Date().getHours(), new Date().getMinutes()],
    choose_year: '',
  },
  radioChange: function (e) {
    var radioItems = this.data.radioItems;
    for (var i = 0, len = radioItems.length; i < len; ++i) {
      radioItems[i].checked = radioItems[i].value == e.detail.value;
    }
    if (e.detail.value == 0) {
      this.setData({
        radioItems: radioItems,
        untilNow: true
      });
    } else {
      this.setData({
        radioItems: radioItems,
        untilNow: false
      });
    }
  },
  onLoad: function (options) {
    this.setData({
      orderId: options.orderId,
      status: options.status
    })
    this.getLawOrderExt(options.orderId);
    //设置默认的年份
    this.setData({
      choose_year: this.data.multiArray[0][0]
    });
  }, 
  collapseToggle: function (e) {
    var obj = {};
    obj[e.currentTarget.dataset.key] = !this.data[e.currentTarget.dataset.key]
    this.setData(obj)
  },
  bindDateChange: function (e) {
    var obj = {};
    obj[e.target.dataset.key] = e.detail.value;
    this.setData(obj)
  },
  inputEvent: function (e) {
    var obj = {};
    obj[e.currentTarget.dataset.key] = e.detail.value;
    this.setData(obj);
  },
  getLawOrderExt: function (orderId) {
    wx.request({
      url: app.globalData.url + 'getLawOrderExt',
      header: { 'Content-Type': 'application/json' },
      data: {
        orderId: orderId
      },
      method: 'POST',
      success: res => {
       
        var radioItemsTemp = []; 
        var telAppo1 = {};
        this.data.radioItems[0].name = res.data.phoneStartTime1+"-"+res.data.phoneEndTime1;
        radioItemsTemp.push(this.data.radioItems[0]);
        this.data.radioItems[1].name = res.data.phoneStartTime2 + "-" + res.data.phoneEndTime2;
        radioItemsTemp.push(this.data.radioItems[1]);
        this.data.radioItems[2].name = res.data.phoneStartTime3 + "-" + res.data.phoneEndTime3;
        radioItemsTemp.push(this.data.radioItems[2]);
        radioItemsTemp.push(this.data.radioItems[3]);
         this.setData({
           radioItems: radioItemsTemp
         });

        
        
      }
    })
  },
  check: function (value, name) {
    if ( (typeof (value) == 'string' && value == '') ||
      (typeof (value) == 'object' && value == null)) {
      wx.showModal({
        title: '提示',
        content: '请输入' + name,
        showCancel: false,
        success: function (res) {
          return false
        }
      })
    }else{
      return true
    }
  },
  validate: function () {
    if (!this.check(this.data.startTime, '开始时间')) {
      return false
    }
    if (!this.check(this.data.untilNow ? '至今' : this.data.endTime, '结束时间')) {
      return false
    }
    if (!this.check(this.data.place, '地点')) {
      return false
    }
   
    if (!this.check(this.data.happen, '案件详情')) {
      return false
    }
    if (!this.check(this.data.involvingMoney, '涉及钱财')) {
      return false
    }
  
    if (!this.check(this.data.appeal, '诉求')) {
      return false
    }
    return true
  },
  submit: function () {
    
    let selected = null;
    for (var i = 0; i < this.data.radioItems.length;i++){
      if (this.data.radioItems[i].checked){
        selected = this.data.radioItems[i].value;
      }
    }
    let customStratTime = this.data.startTime;
    let customEndTime = this.data.endTime;
    if (selected==3){
      if (customStratTime == "" || customEndTime==""){
        wx.showModal({
          title: '提示',
          showCancel: false,
          content: "请选择时间！",
          success: function () {
            return;
          }
        })
        return;
      }
      if (new Date(customStratTime) >= new Date(customEndTime)){
        wx.showModal({
          title: '提示',
          showCancel: false,
          content: "开始时间不能大于结束时间！",
          success: function () {
            return;
          }
        })
        return;
      }

      //获取自定义时间点
    }
      let law = {
        "orderId": this.data.orderId,
        "selected": selected,
        "customStratTime": customStratTime,
        "customEndTime": customEndTime
      }
      const requestTask = wx.request({
        url: app.globalData.url + 'uodateOrderExt', //仅为示例，并非真实的接口地址
        data: law,
        method: 'POST',
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          wx.showModal({
            title: '提示',
            showCancel: false,
            content: '保存成功 \r\n编号:' + res.data.orderId,
            success: function () {
              wx.switchTab({
                url: "../index/index"
              })
            }
          })
        }
      })
    
  },
  //获取时间日期
  bindMultiPickerChange: function (e) {
    // console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      multiIndex: e.detail.value
    })
    const index = this.data.multiIndex;
    const year = this.data.multiArray[0][index[0]];
    const month = this.data.multiArray[1][index[1]];
    const day = this.data.multiArray[2][index[2]];
    const hour = this.data.multiArray[3][index[3]];
    const minute = this.data.multiArray[4][index[4]];
    // console.log(`${year}-${month}-${day}-${hour}-${minute}`);
    

    var obj = {};
    obj[e.target.dataset.key] = year + '-' + month + '-' + day + ' ' + hour + ':' + minute;
    this.setData(obj)
    // console.log(this.data.time);
  },
  //监听picker的滚动事件
  bindMultiPickerColumnChange: function (e) {
    //获取年份
    if (e.detail.column == 0) {
      let choose_year = this.data.multiArray[e.detail.column][e.detail.value];
      console.log(choose_year);
      this.setData({
        choose_year
      })
    }
    //console.log('修改的列为', e.detail.column, '，值为', e.detail.value);
    if (e.detail.column == 1) {
      let num = parseInt(this.data.multiArray[e.detail.column][e.detail.value]);
      let temp = [];
      if (num == 1 || num == 3 || num == 5 || num == 7 || num == 8 || num == 10 || num == 12) { //判断31天的月份
        for (let i = 1; i <= 31; i++) {
          if (i < 10) {
            i = "0" + i;
          }
          temp.push("" + i);
        }
        this.setData({
          ['multiArray[2]']: temp
        });
      } else if (num == 4 || num == 6 || num == 9 || num == 11) { //判断30天的月份
        for (let i = 1; i <= 30; i++) {
          if (i < 10) {
            i = "0" + i;
          }
          temp.push("" + i);
        }
        this.setData({
          ['multiArray[2]']: temp
        });
      } else if (num == 2) { //判断2月份天数
        let year = parseInt(this.data.choose_year);
        console.log(year);
        if (((year % 400 == 0) || (year % 100 != 0)) && (year % 4 == 0)) {
          for (let i = 1; i <= 29; i++) {
            if (i < 10) {
              i = "0" + i;
            }
            temp.push("" + i);
          }
          this.setData({
            ['multiArray[2]']: temp
          });
        } else {
          for (let i = 1; i <= 28; i++) {
            if (i < 10) {
              i = "0" + i;
            }
            temp.push("" + i);
          }
          this.setData({
            ['multiArray[2]']: temp
          });
        }
      }
      console.log(this.data.multiArray[2]);
    }
    var data = {
      multiArray: this.data.multiArray,
      multiIndex: this.data.multiIndex
    };
    data.multiIndex[e.detail.column] = e.detail.value;
    this.setData(data);
  }
})
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    untilNow: true,
    edit: false,
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
    place: '',                //地点 
    prosecutorName: '',       //当事人姓名 
    prosecutorAge: '',        //当事人年龄
    prosecutorCardNo: '',     //当事人身份证
    prosecutorPhone: '',      //当事人手机号
    defendantName: '',        //对方当事人姓名
    defendantAge: '',         //对方当事人年龄
    defendantCardNo: '',      //对方当事人身份证
    defendantPhone: '',       //对方当事人手机号
    relationName: '',         //关系人姓名
    relationAge: '',          //关系人年龄
    relationCardNo: '',       //关系人身份证
    relationPhone: '',        //关系人手机号
    happen: '',               //案件详情
    involvingMoney: 0,        //涉及钱财
    principal: '',            //本金
    interest: '',             //利息
    defaultInterest: '',      //罚息
    penalty: '',              //违约金
    appeal: '',               //诉求
    photo: [],
    lawPhoto: [],
    photos: '',               //照片
    file: [],
    lawFile: [],
    files: '',                //文件
    video: [],
    lawVideo: [],
    videos: '',               //视频
    lawOrder: {
      acceptAddress: null,
      acceptContracts: null,
      acceptDate: null,
      acceptPhone: null,
      acceptRemark: null,
      appeal: "是的发生",
      doneCode: "20180920492365420456710144",
      doneDate: "2018-09-20 16:04:23",
      endTime: "2018-09-20",
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
    person1: [],
    person2: [],
    person3: [],
    radioItems: [{
      name: '至今',
      value: 0,
      checked: true
    }, {
      name: '',
      value: 1,
      checked: false
    }]
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
  changeOrder: function () {
    const obj = {
      edit: true,
      people: false,
      oppPeople: false,
      relatePeople: false
    };
    if (this.data.endTime == '至今') {
      obj["endTime"] = '';
    } else {
      this.setData({
        "radioItems[0].checked": false,
        "radioItems[1].checked": true
      })
    }
    this.setData(obj)
  },
  goPerson: function (e) {
    const that = this;
    let type = e.currentTarget.dataset.type
    const lawPerson = JSON.stringify(this.data['person' + type]);
    wx.navigateTo({
      url: '../person/person?type=' + type + '&edit=' + that.data.edit + '&lawPerson=' + lawPerson,
    })
  },
  onLoad: function (options) {
    console.log(options.status)
    this.setData({
      orderId: options.orderId,
      status: options.status
    })
    this.getLawOrder()
  }, 
  collapseToggle: function (e) {
    var obj = {};
    obj[e.currentTarget.dataset.key] = !this.data[e.currentTarget.dataset.key]
    this.setData(obj)
  },
  bindPickerChange: function (e) {
    this.setData({
      involvingMoney: e.detail.value
    })
    if (e.detail.value == 1){
      this.setData({
        principal: ''
      })
      this.setData({
        interest: ''
      })
      this.setData({
        defaultInterest: ''
      })
      this.setData({
        penalty: ''
      })
    }
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
  chooseImage: function (e) {
    var that = this;
    wx.chooseImage({
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      count: 1,
      success: function (res) {
        wx.showLoading({
          mask: true
        })
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFiles = res.tempFiles
        for (var i in tempFiles) {
          console.log(tempFiles[i])
          wx.uploadFile({
            url: app.globalData.url + '/uploadFile',      //此处换上你的接口地址
            filePath: tempFiles[i].path,
            name: 'file',
            header: {
              "Content-Type": "multipart/form-data",
            },
            success: function (res) {
              that.setData({
                photo: that.data.photo.concat(tempFiles[i].path)
              });
              that.setData({
                lawPhoto: that.data.lawPhoto.concat(res.data)
              });
              that.setData({
                photos: that.data.lawPhoto.toString()
              })
              wx.hideLoading()
            },
            fail: function (res) {
              wx.hideLoading()
              wx.showModal({
                title: '提示',
                showCancel: false,
                content: '上传失败:' + res.errMsg,
              })
            },
          })
        }
      }
    })
  },
  chooseVideo: function () {
    var that = this;
    wx.chooseVideo({
      success: function (res) {
        wx.showLoading({
          mask: true
        })
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePath = res.tempFilePath
        var size = res.size
        if (size > 10240 * 1024) {
          wx.hideLoading()
          wx.showModal({
            title: '提示',
            showCancel: false,
            content: '视频大小不能超过10M',
          })
        } else {
          wx.uploadFile({
            url: app.globalData.url + '/uploadFile',      //此处换上你的接口地址
            filePath: tempFilePath,
            name: 'file',
            header: {
              "Content-Type": "multipart/form-data",
            },
            success: function (res) {
              that.setData({
                video: that.data.video.concat(tempFilePath)
              });
              that.setData({
                lawVideo: that.data.lawVideo.concat(res.data)
              });
              that.setData({
                videos: that.data.lawVideo.toString()
              })
              wx.hideLoading()
            },
            fail: function (res) {
              wx.hideLoading()
              wx.showModal({
                title: '提示',
                showCancel: false,
                content: '上传失败:' + res.errMsg,
              })
            },
          })
        }
      }
    })
  },
  chooseFile: function () {
    var that = this;
    wx.chooseImage({
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album'],
      success: function (res) {
        wx.showLoading({
          mask: true
        })
        var tempFilePaths = res.tempFilePaths
        wx.uploadFile({
          url: app.globalData.url + '/uploadFile',      //此处换上你的接口地址
          filePath: tempFilePaths[0],
          name: 'file',
          header: {
            "Content-Type": "multipart/form-data",
          },
          success: function (res) {
            that.setData({
              file: that.data.file.concat(tempFilePaths[0])
            });
            that.setData({
              lawFile: that.data.lawFile.concat(res.data)
            });
            that.setData({
              files: that.data.lawFile.toString()
            })
            wx.hideLoading()
          },
          fail: function (res) {
            wx.hideLoading()
            wx.showModal({
              title: '提示',
              showCancel: false,
              content: '上传失败:' + res.errMsg,
            })
          },
        })
      }
    })
  },
  getLawOrder: function () {
    wx.request({
      url: app.globalData.url + 'getLawOrder',
      header: { 'Content-Type': 'application/json' },
      data: {
        orderId: this.data.orderId
      },
      method: 'POST',
      success: res => {
        for(var attr in res.data.lawOrder){
          var obj = {};
          if(res.data.lawOrder[attr]!=null){
            obj[attr] = res.data.lawOrder[attr];
          }
          this.setData(obj);
        }
        this.setData({
          person1: res.data.lawPerson1,
          person2: res.data.lawPerson2,
          person3: res.data.lawPerson3
        });

        if (!res.data.lawOrder.photos==''){
          this.setData({
            photo: res.data.lawOrder.photos.split(','),
            lawPhoto: res.data.lawOrder.photos.split(',')
          })
        }
        if (!res.data.lawOrder.videos == '') {
          this.setData({
            video: res.data.lawOrder.videos.split(','),
            lawVoid: res.data.lawOrder.videos.split(',')
          })
        }
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
    // if (!this.check(this.data.prosecutorName, '当事人姓名')) {
    //   return false
    // }
    // if (!this.check(this.data.prosecutorPhone, '当事人手机号')) {
    //   return false
    // }
    // if (!this.check(this.data.defendantName, '对方当事人姓名')) {
    //   return false
    // }
    // if (!this.check(this.data.defendantPhone, '对方当事人手机号')) {
    //   return false
    // }
    if (!this.check(this.data.happen, '案件详情')) {
      return false
    }
    if (!this.check(this.data.involvingMoney, '涉及钱财')) {
      return false
    }
    // if (this.data.involvingMoney == 0) {
    //   if (!this.check(this.data.principal, '本金')) {
    //     return false
    //   }
    //   if (!this.check(this.data.interest, '利息')) {
    //     return false
    //   }
    //   if (!this.check(this.data.defaultInterest, '罚息')) {
    //     return false
    //   }
    // }
    if (!this.check(this.data.appeal, '诉求')) {
      return false
    }
    return true
  },
  submit: function () {
    if (this.validate()) {
      let order = {
        "orderId": this.data.orderId,
        "startTime": this.data.startTime,
        "endTime": this.data.untilNow ? '至今' : this.data.endTime,
        "place": this.data.place,
        "happen": this.data.happen,
        "involvingMoney": this.data.involvingMoney,
        "possessions": this.data.possessions,
        "appeal": this.data.appeal,
        "photos": this.data.photos,
        "files": this.data.files,
        "videos": this.data.videos,
        "openId": this.data.openId,
        "status": this.data.status,
      }
      let law = {
        "lawOrder": order,
        "lawPerson1": this.data.person1,
        "lawPerson2": this.data.person2,
        "lawPerson3": this.data.person3,
        "orderId": this.data.orderId,
        "personId": this.data.personId
      }
      const requestTask = wx.request({
        url: app.globalData.url + 'addLawOrder', //仅为示例，并非真实的接口地址
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
    }
  }
})
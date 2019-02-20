//index.js
//获取应用实例
const app = getApp();
Date.prototype.format = function (format) {
  var date = {
    'M+': this.getMonth() + 1,
    'd+': this.getDate(),
    'h+': this.getHours(),
    'm+': this.getMinutes(),
    's+': this.getSeconds(),
    'q+': Math.floor((this.getMonth() + 3) / 3),
    'S+': this.getMilliseconds()
  }
  if (/(y+)/i.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  for (var k in date) {
    if (new RegExp('(' + k + ')').test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length === 1
        ? date[k] : ('00' + date[k]).substr(('' + date[k]).length))
    }
  }
  return format
}

Page({
  data: {
    untilNow: true,
    gallery: false,
    currentFile: '',
    startTime: '',            //开始时间
    endTime: '',              //结束时间
    place: '',                //地点
    happen: '',               //案件详情
    involvingMoney: '0',      //涉及钱财
    possessions: '',          //财物
    appeal: '',               //诉求
    photo: [],
    lawPhoto: [],
    photos: '',               //照片
    file:[],
    lawFile:[],
    files: '',                //文件
    video: [],
    lawVideo: [],
    videos: '',               //视频
    openId: '',               //openId
    status: '0',           //处理状态
    customItem: '全部',
    array: ['是', '否'],
    currentDate: new Date().format('yyyy-MM-dd'),
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
  collapseToggle: function (e) {
    var obj = {};
    obj[e.currentTarget.dataset.key] = !this.data[e.currentTarget.dataset.key];
    this.setData(obj);

  },
  radioChange: function (e) {
    var radioItems = this.data.radioItems;
    for (var i = 0, len = radioItems.length; i < len; ++i) {
      radioItems[i].checked = radioItems[i].value == e.detail.value;
    }
    if (e.detail.value == 0 ) {
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
  bindPickerChange: function (e) {
    this.setData({
      involvingMoney: e.detail.value.toString()
    })
  },
  onLoad: function (options) {
    var that = this;
    if (!wx.getStorageSync('openId')) {
      app.getOpenId().then(function (res) {
        if (res.status == 200) {
          that.setData({
            openId: wx.getStorageSync('openId')
          })
        }
      });
    } else {
      that.setData({
        openId: wx.getStorageSync('openId')
      })
    }
  },
  inputEvent: function (e) {
    
    var obj = {};
    obj[e.currentTarget.dataset.key] = e.detail.value;
    this.setData(obj);
  },
  bindDateChange: function (e) {
    var obj = {};
    obj[e.target.dataset.key] = e.detail.value;
    this.setData(obj)
  },
  goPerson: function (e) {
    let type = e.currentTarget.dataset.type
    const lawPerson = JSON.stringify(this.data['person' + type]);
    wx.navigateTo({
      url: '../person/person?type='+type+'&lawPerson='+lawPerson,
    })
  },
  chooseImage: function (e) {
    var that = this;
    wx.chooseImage({
      sizeType: ['original'], // 可以指定是原图还是压缩图，默认二者都有
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
  previewImage: function (e) {
    this.setData({
      gallery: true,
      currentFile: e.currentTarget.id
    })
    // wx.previewImage({
    //   current: e.currentTarget.id, // 当前显示图片的http链接
    //   urls: this.data.files // 需要预览的图片http链接列表
    // })
  },
  previewCancel: function (e) {
    this.setData({
      gallery: false,
      currentFile: ""
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
        if(size > 50*10240*1024){
          wx.hideLoading()
          wx.showModal({
            title: '提示',
            showCancel: false,
            content: '视频大小不能超过50M',
          })
        }else{
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
      sizeType: ['original'], // 可以指定是原图还是压缩图，默认二者都有
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
  check: function (value,name){
    if ((typeof (value) == 'string' && value == '') ||
      (typeof (value) == 'object' && value == null)) {
      wx.showModal({
        title: '提示',
        content: '请输入' + name,
        showCancel: false,
        success: function (res) {
          return false
        }
      })
    } else {
      return true
    }
  },
  validate: function() {
    if(!this.check(this.data.startTime,'开始时间')){
      return false
    }
    if (!this.check(this.data.untilNow ? '至今' : this.data.endTime,'结束时间')){
      return false
    }
    if (!this.check(this.data.place,'地点')){
      return false
    }
    if (!this.check(this.data.happen, '案件详情')) {
      return false
    }
    if (!this.check(this.data.involvingMoney, '涉及钱财')) {
      return false
    }
    if(this.data.involvingMoney==0){
      if (!this.check(this.data.possessions, '涉及财物')) {
        return false
      }
    }
    if (!this.check(this.data.appeal, '诉求')) {
      return false
    }
    return true
  },
  submit: function () {
    if(this.validate()){
      let order = {
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
        "lawPerson1":this.data.person1,
        "lawPerson2": this.data.person2,
        "lawPerson3": this.data.person3,
      }
      const requestTask = wx.request({
        url: app.globalData.url + '/addLawOrder', //仅为示例，并非真实的接口地址
        data: law,
        method: 'POST',
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          wx.showModal({
            title: '提示',
            showCancel: false,
            content: '提交成功 \r\n编号:' + res.data.orderId,
            success: function(){
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
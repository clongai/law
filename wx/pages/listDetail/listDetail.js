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
    place: '北京市，北京市，东城区',                //地点
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
    }],
    multiArray: [],
    multiIndex: [0, 0, 0],
    provinces:""
  }, lifetimes: {
    // 生命周期函数，可以为函数，或一个在methods段中定义的方法名
    attached: function () {
      this.getCityInfo()
    }
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
    this.getCityInfo();
  },
  inputEvent: function (e) {
    
    var obj = {};
    obj[e.currentTarget.dataset.key] = e.detail.value;
    this.setData(obj);
  },
  setPlace: function(e){
    
  }
  ,bindDateChange: function (e) {
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
  },
  getCityInfo: function () {
    //debugger;
     wx.showLoading({
       title: 'Loading...',
     })
    //因为数据库只存有一个总的数据字典，所以指定它的ID直接获取数据
    var that = this;
  
    wx.request({
      url: app.globalData.url + 'getCityInfo',
      header: { 'Content-Type': 'application/json' },
      data: {},
      method: 'POST',
      success: function (res) {
        wx.hideLoading();
       
        if (res.data) {
    
          var temp = res.data.data;
          //初始化更新数据
          that.setData({
            provinces: temp,
            multiArray: [temp, temp[0].citys, temp[0].citys[0].areas],
            multiIndex: [0, 0, 0]
          })
     
        }
      },
      fail: function () {
        wx.showToast({
          title: '系统异常！',
          icon: 'none',
          duration: 3000
        })
      }
    })
   
  },
  //点击确定
  bindMultiPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    var placel = this.data.multiArray[0][this.data.multiIndex[0]].name;
    var place2 = this.data.multiArray[1].length > 0 ? ('，' + this.data.multiArray[1][this.data.multiIndex[1]].name) : '';
    var place3 = this.data.multiArray[2].length > 0 ? ('，' + this.data.multiArray[2][this.data.multiIndex[2]].name) : '';
    this.setData({
      multiIndex: e.detail.value,
      place: placel + place2 + place3
    })
    console.log(this.data.place)
  },
  //滑动
  bindMultiPickerColumnChange: function (e) {
    console.log('修改的列为', e.detail.column, '，值为', e.detail.value);
    var data = {
      multiArray: this.data.multiArray,
      multiIndex: this.data.multiIndex
    };
    //更新滑动的第几列e.detail.column的数组下标值e.detail.value
    data.multiIndex[e.detail.column] = e.detail.value;
    //如果更新的是第一列“省”，第二列“市”和第三列“区”的数组下标置为0
    if (e.detail.column == 0) {
      data.multiIndex = [e.detail.value, 0, 0];
    } else if (e.detail.column == 1) {
      //如果更新的是第二列“市”，第一列“省”的下标不变，第三列“区”的数组下标置为0
      data.multiIndex = [data.multiIndex[0], e.detail.value, 0];
    } else if (e.detail.column == 2) {
      //如果更新的是第三列“区”，第一列“省”和第二列“市”的值均不变。
      data.multiIndex = [data.multiIndex[0], data.multiIndex[1], e.detail.value];
    }
    var temp = this.data.provinces;
    data.multiArray[0] = temp;
    if ((temp[data.multiIndex[0]].citys).length > 0) {
      //如果第二列“市”的个数大于0,通过multiIndex变更multiArray[1]的值
      data.multiArray[1] = temp[data.multiIndex[0]].citys;
      var areaArr = (temp[data.multiIndex[0]].citys[data.multiIndex[1]]).areas;
      //如果第三列“区”的个数大于0,通过multiIndex变更multiArray[2]的值；否则赋值为空数组
      data.multiArray[2] = areaArr.length > 0 ? areaArr : [];
    } else {
      //如果第二列“市”的个数不大于0，那么第二列“市”和第三列“区”都赋值为空数组
      data.multiArray[1] = [];
      data.multiArray[2] = [];
    }
   
    //setData更新数据
    this.setData(data);
  }

})
<template>
  <div class="echarts-content clearfix">
    <div class="echarts-title">总{{title}}趋势</div>
    <div class="echarts-info">
      <div class="echarts-info-item echarts-info-total">
        <div class="info-top">总{{title}}</div>
        <div class="info-bottom">{{total}}</div>
      </div>
      <div class="echarts-info-item echarts-info-current">
        <div class="info-top">本月{{title}}</div>
        <div class="info-bottom">{{current}}</div>
      </div>
    </div>
    <div class="echarts-view">
      <div :id="id" class="echarts-view-item"></div>
    </div>
    <!--<div style="width: 400px; height: 100px; background-image: linear-gradient(to top, #b2d8ed, #188cca)"></div>-->
  </div>
</template>
<script>
  let echarts = require('echarts/lib/echarts');
  require('echarts/lib/chart/line');
  // require('echarts/lib/component/tooltip');
  // require('echarts/lib/component/toolbox');
  // require('echarts/lib/component/legend');
  // require('echarts/lib/component/markLine');
  require('echarts/lib/component/title');

  export default {
    name: "lineChart",
    props: {
      id: {
        type: String,
        default: "Box"
      },
      color: {
        type: Array,
        default: function () {
          return ['#b2d8ed', '#0778ca'];
        }
      },
      title: {
        type: String,
        default: ''
      },
      url: {
        type: String,
        default: ''
      },
      yUnit: {
        type: String,
        default: ''
      },
      xUnit: {
        type: String,
        default: ''
      }
    },
    mounted () {
      const _this = this;
      // 基于准备好的dom，初始化echarts实例
     
      
   
      this.$axios.post('/api/getStatisticsData',{
          type: _this.id
      }).then((res) =>{ 
        //debugger;
        this.total=res.data.total;
        this.current = res.data.current;
        this.realData = res.data.data;
        this.createChartOption();
      })
  
     
    },
    data () {
      return {
        total: "26,238",
        current: '3201',
        xAxisData: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
        realData: [],
        yMax: 0
      }
    },methods:{
      createChartOption(){
        const _this = this;
      let myChart = echarts.init(document.getElementById(_this.id));
      for (let i = 0; i < this.realData.length; i++) {
        if (this.yMax < this.realData[i]) {
          this.yMax = this.realData[i]
        }
      }
     
      const option = {
        grid: {
          top: 20,
          bottom: 40
        },
        xAxis: {
          name: '{a|月}',
          type: 'category',
          boundaryGap: false,
          data: _this.xAxisData,
          splitLine: {
            show: true,
            interval: 0
          },
          axisTick: {
            show: false
          },
          axisLine: {
            symbol: ['none', 'arrow'],
            lineStyle: {
              color: '#666'
            }
          },
          axisLabel: {
            textStyle: {
              fontSize: 10
            }
          },
          nameTextStyle: {
            rich: {
              a: {
                color: '#666',
                padding: [-25, 0, 0, 0],
                fontSize: 10
              }
            }
          }
        },
        yAxis: {
          type: 'value',
          name: "{a|元}",
          max: 2000,
          axisLabel: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLine: {
            symbol: ['none', 'arrow'],
            lineStyle: {
              color: '#666'
            }
          },
          nameTextStyle: {
            rich: {
              a: {
                color: '#666',
                padding: [-30, 30, 0, -10],
                fontSize: 10
              }
            }
          }
        },
        series: [{
          data: _this.realData,
          type: 'line',
          areaStyle: {},
          itemStyle: {
            color: {
              type: 'linear',
              x: 0,
              y: 1,
              x2: 0,
              y2: 0,
              colorStops: [{
                offset: 0, color: _this.color[0] // 0% 处的颜色
              }, {
                offset: 1, color: _this.color[1] // 100% 处的颜色
              }],
              globalCoord: false // 缺省为 false
            }
          }
        }]
      };

      // 绘制图表
      myChart.setOption(option);
      }
    }
  }
</script>
<style>
  .echarts-content {
    width: 100%;
    height: 100%;
  }
  #Box {
    width: 400px;
    height: 250px;
  }
  .echarts-info {
    float: left;
    width: 20%;
    height: calc(100% - 16px);
  }
  .echarts-view {
    float: left;
    width: 80%;
    height: 100%;
  }
  .echarts-view-item {
    width: 100%;
    height: 100%;
  }
  .echarts-info-item {
    height: 40%;
    padding-top: 15px;
  }
  .info-top {
    width: 100%;
    color: #ababab;
  }
  .info-bottom {
    font-size: 20px;
    line-height: 38px;
  }
  .echarts-title {
    font-size: 13px;
    line-height: 16px;
    text-align: center;
  }
</style>
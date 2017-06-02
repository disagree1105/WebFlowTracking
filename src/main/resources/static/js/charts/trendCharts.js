function getVisitTrend(appId){
    var result=new Array();
        $.ajax({
                url: "http://localhost:8888/today/visitTrend/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                        result[0]=data.today;
                        result[1]=data.yesterday;
                        result[2]=data.lastMonth;
                    
                },
             error:function(){
                console.log("服务器连接失败，无法查询浏览趋势信息");
             }
            })
        return result;
}

function getOperTrend(appId){
    var result=new Array();
        $.ajax({
                url: "http://localhost:8888/today/operTrend/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                        result[0]=data.today;
                        result[1]=data.yesterday;
                        result[2]=data.lastMonth;
                    
                },
             error:function(){
                console.log("服务器连接失败，无法查询浏览趋势信息");
             }
            })
        return result;
}

var todayVisitTrend = getVisitTrend(GetQueryString('appId'));
var todayOperTrend = getOperTrend(GetQueryString('appId'));

var visitUserTrend = new Highcharts.Chart('visitUserTrend', {
                                title: {
                                    text: '访问用户数量趋势',
                                    x: -20
                                },
                                subtitle: {
                                    text: '',
                                    x: -20
                                },
                                xAxis: {
                                    categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11','12','13','14','15','16','17','18','19','20','21','22','23']
                                },
                                yAxis: {
                                    title: {
                                        text: '访问用户数量'
                                    },
                                    plotLines: [{
                                        value: 0,
                                        width: 1,
                                        color: '#808080'
                                    }]
                                },
                                tooltip: {
                                    formatter: function () {
                                    var s = '<b>' + this.x + '点-'+ (parseInt(this.x)+1)+'点'+'</b>';

                                    $.each(this.points, function () {
                                        s += '<br/>' + this.series.name + ': ' +
                                            this.y ;
                                    });

                                    return s;
                                    },
                                    shared: true
                                },
                                legend: {
                                    layout: 'vertical',
                                    align: 'right',
                                    verticalAlign: 'middle',
                                    borderWidth: 0
                                },
                                series: [{
                                    name: '今日',
                                    data: todayVisitTrend[0]
                                }, {
                                    name: '昨日',
                                    // data: [0, 0, 0, 0, 0, 5, 8, 9, 3, 5, 7, 13,24,20,36,40,25,35,25,20,15,7,2,0]
                                    data: todayVisitTrend[1]
                                }, {
                                    name: '最近30日',
                                    // data: [0, 0, 0, 0, 0, 0, 2, 4, 3, 5, 13, 20,16,20,26,29,35,39,40,25,20,12,8,6]
                                    data: todayVisitTrend[2]
                                }],
                                credits: {
                                enabled: false
                                }
                            });


 var operCountTrend = new Highcharts.Chart('operCountTrend', {
                                title: {
                                    text: '操作数量趋势',
                                    x: -20
                                },
                                subtitle: {
                                    text: '',
                                    x: -20
                                },
                                xAxis: {
                                    categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11','12','13','14','15','16','17','18','19','20','21','22','23']
                                },
                                yAxis: {
                                    title: {
                                        text: '操作数量'
                                    },
                                    plotLines: [{
                                        value: 0,
                                        width: 1,
                                        color: '#808080'
                                    }]
                                },
                                tooltip: {
                                    formatter: function () {
                                    var s = '<b>' + this.x + '点-'+ (parseInt(this.x)+1)+'点'+'</b>';

                                    $.each(this.points, function () {
                                        s += '<br/>' + this.series.name + ': ' +
                                            this.y ;
                                    });

                                    return s;
                                    },
                                    shared: true
                                },
                                legend: {
                                    layout: 'vertical',
                                    align: 'right',
                                    verticalAlign: 'middle',
                                    borderWidth: 0
                                },
                                series: [{
                                    name: '今日',
                                    //data: [0, 0, 0, 0, 0, 0, 3, 8, 4, 6, 10]
                                    data: todayOperTrend[0]
                                }, {
                                    name: '昨日',
                                    //data: [0, 0, 0, 0, 0, 5, 8, 9, 3, 5, 7, 13,24,20,36,40,25,35,25,20,15,7,2]
                                    data: todayOperTrend[1]
                                }, {
                                    name: '最近30日',
                                    //data: [0, 0, 0, 0, 0, 0, 2, 4, 3, 5, 13, 20,16,20,26,29,35,39,40,25,20,12,8]
                                    data: todayOperTrend[2]
                                }],
                                credits: {
                                enabled: false
                                }
                            });


function getUserMap(appId){
    var map={};
        $.ajax({
                url: "http://localhost:8888/userMap/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    //map['beijing']=data.beijing;
                    map=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询用户分布图");
             }
            })
        return map;
}

var map=getUserMap(GetQueryString('appId'));

   // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('userMap'));
    var option = {
    tooltip: {},
    visualMap: {
        min: 0,
        max: 100,
        left: 'left',
        top: 'bottom',
        text: ['High','Low'],
        seriesIndex: [1],
        inRange: {
            color: ['#e0ffff', '#006edd']
        },
        calculable : true
    },
    geo: {
        map: 'china',
        roam: true,
        label: {
            normal: {
                show: true,
                textStyle: {
                    color: 'rgba(0,0,0,0.4)'
                }
            }
        },
        itemStyle: {
            normal:{
                borderColor: 'rgba(0, 0, 0, 0.2)'
            },
            emphasis:{
                areaColor: null,
                shadowOffsetX: 0,
                shadowOffsetY: 0,
                shadowBlur: 20,
                borderWidth: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        }
    },
    series : [
       {
           type: 'scatter',
           coordinateSystem: 'geo',

           symbolSize: 20,
           symbol: 'path://M1705.06,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705.06,1318.313z',
           symbolRotate: 35,
           label: {
               normal: {
                   formatter: '{b}',
                   position: 'right',
                   show: false
               },
               emphasis: {
                   show: true
               }
           },
           itemStyle: {
               normal: {
                    color: '#F06C00'
               }
           }
        },
        {
            name: '访问用户',
            type: 'map',
            geoIndex: 0,
            // tooltip: {show: false},
            data:[
                {name: '北京', value: map['beijing']},
                {name: '天津', value: map['tianjin']},
                {name: '上海', value: map['shanghai']},
                {name: '重庆', value: map['chongqin']},
                {name: '河北', value: map['hebei']},
                {name: '河南', value: map['henan']},
                {name: '云南', value: map['yunnan']},
                {name: '辽宁', value: map['liaoning']},
                {name: '黑龙江', value: map['heilongjiang']},
                {name: '湖南', value: map['hunan']},
                {name: '安徽', value: map['anhui']},
                {name: '山东', value: map['shandong']},
                {name: '新疆', value: map['xinjiang']},
                {name: '江苏', value: map['jiangsu']},
                {name: '浙江', value: map['zhejiang']},
                {name: '江西', value: map['jiangxi']},
                {name: '湖北', value: map['hubei']},
                {name: '广西', value: map['guangxi']},
                {name: '甘肃', value: map['gansu']},
                {name: '山西', value: map['shanxi1']},
                {name: '内蒙古', value: map['neimenggu']},
                {name: '陕西', value: map['shanxi3']},
                {name: '吉林', value: map['jilin']},
                {name: '福建', value: map['fujian']},
                {name: '贵州', value: map['guizhou']},
                {name: '广东', value: map['guangdong']},
                {name: '青海', value: map['qinghai']},
                {name: '西藏', value: map['xizang']},
                {name: '四川', value: map['sichuan']},
                {name: '宁夏', value: map['ningxia']},
                {name: '海南', value: map['hainan']},
                {name: '台湾', value: map['taiwan']},
                {name: '香港', value: map['xianggang']},
                {name: '澳门', value: map['aomen']}
            ]
        }
    ]
};
myChart.setOption(option);
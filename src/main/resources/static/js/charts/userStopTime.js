function getStopTime(appId){
        var count=new Array();
        $.ajax({
                url: "http://localhost:8888/stopTime/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    count=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询用户停留时间分布图");
             }
            })
        return count;
    }

var count=getStopTime(GetQueryString('appId'));

$(function () {
                                $('#container-stopTime').highcharts({
                                    chart: {
                                        type: 'column'
                                    },
                                    title: {
                                        text: '用户停留页面时间分布图'
                                    },
                                    subtitle: {
                                        text: ''
                                    },
                                    xAxis: {
                                        categories: [
                                            '小于1分钟',
                                            '1-10分钟',
                                            '11-20分钟',
                                            '21-30分钟',
                                            '31-40分钟',
                                            '41-50分钟',
                                            '51-60分钟',
                                            '大于60分钟'
                                        ],
                                        crosshair: true
                                    },
                                    yAxis: {
                                        min: 0,
                                        title: {
                                            text: '用户数量'
                                        }
                                    },
                                    tooltip: {
                                        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                                        pointFormat: '<tr><td style="color:{series.color};padding:0">用户数量： </td>' +
                                        '<td style="padding:0"><b>{point.y:.0f}</b></td></tr>',
                                        footerFormat: '</table>',
                                        shared: true,
                                        useHTML: true
                                    },
                                    plotOptions: {
                                        column: {
                                            pointPadding: 0.2,
                                            borderWidth: 0
                                        }
                                    },
                                    series: [{
                                        name: "用户停留页面数量",
                                        data: [
                                            count[0],
                                            count[1], 
                                            count[2], 
                                            count[3], 
                                            count[4], 
                                            count[5], 
                                            count[6], 
                                            count[7]]
                                    }],
                                    credits: {
                                        enabled: false
                                    }
                                });
                            });
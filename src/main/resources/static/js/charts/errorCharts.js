function getErrorCountRealTime(appId){
        var count=0;
        $.ajax({
                url: "http://localhost:8888/errorCountRealTime/get",
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
                console.log("服务器连接失败，无法查询错误实时数据");
             }
            })
        return count;
}

function getErrorTrend(appId){
        var trend=[];
        $.ajax({
                url: "http://localhost:8888/errorTrend/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    trend=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询错误趋势");
             }
            })
        return trend;
    }    

Highcharts.setOptions({
    global: {
        useUTC: false
    }
});
function activeLastPointToolip(chart) {
    var points = chart.series[0].points;
    chart.tooltip.refresh(points[points.length -1]);
}
$('#errorTrendRealTime').highcharts({
    chart: {
        type: 'spline',
        animation: Highcharts.svg, // don't animate in old IE
        marginRight: 10,
        events: {
            load: function () {
                // set up the updating of the chart each second
                var series = this.series[0],
                    chart = this;
                setInterval(function () {
                    var x = (new Date()).getTime(), // current time
                        y = getErrorCountRealTime(GetQueryString('appId'));
                    series.addPoint([x, y], true, true);
                    activeLastPointToolip(chart)
                }, 1000*60);
            }
        }
    },
    title: {
        text: '页面出错数量趋势实时数据'
    },
    xAxis: {
        type: 'datetime',
        tickPixelInterval: 150
    },
    yAxis: {
        title: {
            text: '页面出错数量'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    },
    tooltip: {
        formatter: function () {
            return '<b>' + this.series.name + '</b><br/>' +
                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                Highcharts.numberFormat(this.y, 0);
        }
    },
    legend: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
    series: [{
        name: '页面出错数量',
        data: (function () {
            // generate an array of random data
            var data = [],
                time = (new Date()).getTime(),
                i;
            var count = new Array();
                count = getErrorTrend(GetQueryString('appId'));
            for (i = -19; i <= 0; i += 1) {
                data.push({
                    x: time + i * 1000 * 60 ,
                    y: count[i+19]
                });
            }
            return data;
        }())
    }],
    credits: {
        enabled: false
    }
}, function(c) {
    activeLastPointToolip(c)
});
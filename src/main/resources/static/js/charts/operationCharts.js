function getOperCountRealTime(appId){
        var operCount=0;
        $.ajax({
                url: "http://localhost:8888/operCountRealTime/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    operCount=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询操作实时数据");
             }
            })
        return operCount;
}

function getOperTrend(appId){
        var operTrend=[];
        $.ajax({
                url: "http://localhost:8888/operTrend/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    operTrend=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询操作趋势");
             }
            })
        return operTrend;
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
$('#operTrendRealTime').highcharts({
    chart: {
        type: 'spline',
        animation: Highcharts.svg, // don't animate in old IE
        marginRight: 10,
        events: {
            load: function () {
                // set up the updating of the chart each minute
                var series = this.series[0],
                    chart = this;
                setInterval(function () {
                    var x = (new Date()).getTime(), // current time
                        y = getOperCountRealTime(GetQueryString('appId'));
                    console.log("更新了y值");
                    series.addPoint([x, y], true, true);
                    activeLastPointToolip(chart)
                }, 1000 * 60 );
            }
        }
    },
    title: {
        text: '操作数量趋势实时数据'
    },
    xAxis: {
        type: 'datetime',
        tickPixelInterval: 150
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
        name: '操作数量',
        data: (function () {
            // generate an array of random data
            var data = [],
                time = (new Date()).getTime(),
                i;
            var count = new Array();
                count = getOperTrend(GetQueryString('appId'));
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